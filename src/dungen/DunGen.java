package dungen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import dungen.room.Anchor;
import dungen.room.Cell;
import dungen.room.IRoomValidator;
import dungen.room.Room;
import dungen.room.RoomGroup;
import dungen.room.RoomResources;
import dungen.room.RoomResourcesReader;

/**
 * Generator of totally sweet dungeons.
 */
public class DunGen {
	
	/**
	 * Generate a dungeon!
	 * @param path The path to the directory containing the room/group files.
	 * @param configuration The configuration.
	 * @return The generated dungeon.
	 */
	public static Dungeon generate(String path, DunGenConfiguration configuration) {
		// Grab all of the room resources from disk.
		RoomResources resources = RoomResourcesReader.getResources(path);
		
		// Create the RNG to use in generating the dungeon.
		Random random = new Random(configuration.seed);
		
		// Keep track of the number of times we have attempted to create the dungeon and failed.
		int dungeonGenerationFailureCount = 0;
		
		// Keep trying to generate the dungeon until we hit the attempt limit.
		while (dungeonGenerationFailureCount < configuration.dungeonGenerationRetries) {
			// Attempt to generate a dungeon.
			DunGenGenerationAttempt result = attemptDungeonGeneration(resources, configuration, random);

			// If we succeeded then we are done!
			if (result.getStatus() == DunGenGenerationAttemptStatus.SUCCESS) {
				return new Dungeon(result.getTiles(), configuration);
			}

			// We failed in this attempt to generate a dungeon!
			dungeonGenerationFailureCount++;
		}
		
		throw new RuntimeException("reached dungeon generation attempt limit!");
	}
	
	/**
	 * Attempt to generate a dungeon and return the result.
	 * @param resources The room resources.
	 * @param configuration The configuration.
	 * @param randon The RNG to use throughout generation.
	 * @return The result of attempting to generate a dungeon.
	 */
	private static DunGenGenerationAttempt attemptDungeonGeneration(RoomResources resources, DunGenConfiguration configuration, Random random) {
		// Create the map to hold all of the placed dungeon cells.
		PositionedCellList cells = new PositionedCellList();
		
		// Create a map to hold the counts of generated rooms.
		RoomCountMap roomCounts = new RoomCountMap();
		
		// Add the spawn room to the centre of the dungeon, this should always be a success.
		addRoom(null, 0, 0, 0, resources.getRoom("spawn"), cells, roomCounts);
		
		// Keep track of the number of times we have attempted to add a room and failed.
		int roomGenerationFailureCount = 0;
		
		// While we need to populate our dungeon with rooms, find a room and bolt it on.
		while (roomCounts.getTotal() < configuration.maximumRoomCount && roomGenerationFailureCount < configuration.roomGenerationRetries) {
			// Find all available anchors and pick any random one.
			Anchor anchor = Utility.getRandomListItem(findAvailableAnchors(cells), random);

			// Get all rooms where the entrance matches the direction of the anchor.
			ArrayList<Room> attachableRooms = getRoomsWithEntranceDirection(anchor.getJoinDirection(), resources.getRooms());

			// TODO MAYBE Randomly pick a room rarity and filter X by that rarity.

			// Shuffle the attachable rooms so that we don't spend end up playing favourites with earlier items.
			Collections.shuffle(attachableRooms, random);
			
			// Randomly pick a generatable room definition.
			Room generatableRoom = null;
			for (Room room : attachableRooms) {
				if (canRoomBeGenerated(configuration, resources, room, anchor, roomCounts, cells)) {
					// We found a room that can be generated!
					generatableRoom = room;
					
					break;
				}
			}

			// Generate a room if we have a valid generatable room definition.
			if (generatableRoom != null) {
				// Add the room.
				addRoom(anchor, anchor.getPosition().getX(), anchor.getPosition().getY(), anchor.getDepth(), generatableRoom, cells, roomCounts);

				// Reset the room generation failure count now that we have had a success.
				roomGenerationFailureCount = 0;
			} else {
				// We failed to generate a room!
				roomGenerationFailureCount++;
			}
		}
		
		// We failed to generate the dungeon if we didn't meet the minimum number of rooms or 
		// any rooms that have a minimum count have not been added at least that many times.
		if (!areMinimumRoomCountsMet(resources, roomCounts, configuration)) {
			return new DunGenGenerationAttempt(DunGenGenerationAttemptStatus.FAIL, null);
		}
		
		// We succeeded to create a dungeon! Convert the cells to tiles and return them along with a success status.
		return new DunGenGenerationAttempt(DunGenGenerationAttemptStatus.SUCCESS, TileGenerator.generateFromCells(cells, random));
	}

	/**
	 * Get whether the room can be generated at an anchor.
	 * @param configuration The configuration.
	 * @param resources The room resources.
	 * @param room The room to add.
	 * @param anchor The anchor.
	 * @param roomCounts The room counts.
	 * @param cells The existing placed dungeon cells.
	 * @return Whether the room can be generated at an anchor.
	 */
	private static boolean canRoomBeGenerated(DunGenConfiguration configuration, RoomResources resources, Room room, Anchor anchor, RoomCountMap roomCounts, PositionedCellList cells) {
		// Carry out room validation if there is a validator associated with the room.
		if (room.getValidator() != null) {
			// Get the room validator.
			IRoomValidator validator = configuration.getRoomValidator(room.getValidator());
			
			// The validator has to have been defined!
			if (validator == null) {
				throw new RuntimeException("The room validator'" + room.getValidator() + "' has not been defined as part of the configuration");
			}
			
			// Validate whether we can generate the room based on user-defined validation.
			if (!validator.validate(room, anchor.getRoomRoute())) {
				return false;
			}
		}
		
		// Find the room group that the room is in (if there is one).
		RoomGroup roomGroup = null;
		for (RoomGroup group : resources.getRoomGroups()) {
			if (group.includesRoom(room)) {
				// We found a group that the room is in!
				roomGroup = group;
				break;
			}
		}
		
		// Check whether there is a restriction on the maximum number of times this room can be
		// generated. A max can be applied per group and per room, with the latter taking priority.
		if (room.getMaximum() != null) {
			// If the room has a max then return false if the count has already been met.
			if (roomCounts.getCount(room.getName()) >= room.getMaximum()) {
				return false;
			}
		} else if (roomGroup != null && roomGroup.getMaximum() != null) {
			// Get the total number of times that rooms in the same group have been generated.
			int roomGroupGenerationCount = 0;
			for (String roomName : roomGroup.getRoomNames()) {
				roomGroupGenerationCount += roomCounts.getCount(roomName);
			}

			// Have we already met the group max?
			if (roomGroupGenerationCount >= roomGroup.getMaximum()) {
				return false;
			}
		}
		
		// Check whether there is a restriction on the depth at which this room can be generated.
		// A depth range can be applied per group and per room, with the latter taking priority.
		if (room.getDepth() != null && !anchor.isWithinDepthRange(room.getDepth())) {
			return false;
		} else if (roomGroup != null && roomGroup.getDepth() != null && !anchor.isWithinDepthRange(roomGroup.getDepth())) {
			return false;
		}
		
		// Check to make sure that all of the cell positions that will be taken up by the room are available.
		for (Cell cell : room.getCells()) {
			// Get the absolute position of the room cell if it were to be generated.
			int cellPositionX = cell.getLocalPosition().getX() + anchor.getPosition().getX();
			int cellPositionY = cell.getLocalPosition().getY() + anchor.getPosition().getY();
			
			// If the cell position is already taken then we cannot generate the room.
			if (cells.isCellAt(new Position(cellPositionX, cellPositionY))) {
				return false;
			}
		}
			
		// The room can be generated!
		return true;
	}
	
	/**
	 * Add a room to the dungeon.
	 * @param anchor The anchor to attach the room to.
	 * @param x The absolute x position at which to place the room entrance cell.
	 * @param y The absolute y position at which to place the room entrance cell.
	 * @param depth The depth at which the room is being added.
	 * @param room The room to add.
	 * @param cells The existing placed dungeon cells.
	 * @param roomCounts The counts of the generated rooms.
	 */
	private static void addRoom(Anchor anchor, int x, int y, int depth, Room room, PositionedCellList cells, RoomCountMap roomCounts) {
		// Add a room count entry for this room.
		roomCounts.incrementCount(room.getName());

		// Increment the room count.
		roomCounts.put(room.getName(), roomCounts.get(room.getName()) + 1);
		
		// Create a room instance id for all cells in the room to share.
		String roomInstanceId = UUID.randomUUID().toString();
		
		// Add each cell of the room to the dungeon.
		for (Cell cell : room.getCells()) {
			// Get the absolute position of the cell.
			Position cellPosition = new Position(x + cell.getLocalPosition().getX(), y + cell.getLocalPosition().getY());
	
			cells.add(new PositionedCell(anchor, cell, cellPosition, depth, room, roomInstanceId));		
		}
	}
	
	/**
	 * Find all available anchor points in the dungeon.
	 * @param cells The cell to which anchors may be attached.
	 * @return All available anchor points in the dungeon.
	 */
	private static ArrayList<Anchor> findAvailableAnchors(PositionedCellList cells) {
		// Create a list to store all of the available anchors.
		ArrayList<Anchor> anchors = new ArrayList<Anchor>();

		for (PositionedCell positionedCell : cells) {	
			// Get the position of the cell.
			int cellX = positionedCell.getPosition().getX();
			int cellY = positionedCell.getPosition().getY();
			
			// Get the depth of the next room.
			int nextRoomDepth = positionedCell.getDepth() + 1;
			
			if (positionedCell.getCell().isJoinableAt(Direction.NORTH) && !cells.isCellAt(cellX, cellY + 1)) {
				anchors.add(new Anchor(positionedCell.getRoom(), positionedCell.getAnchor(), new Position(cellX, cellY + 1), Direction.SOUTH, nextRoomDepth));
			}
			
			if (positionedCell.getCell().isJoinableAt(Direction.SOUTH) && !cells.isCellAt(cellX, cellY - 1)) {
				anchors.add(new Anchor(positionedCell.getRoom(), positionedCell.getAnchor(), new Position(cellX, cellY - 1), Direction.NORTH, nextRoomDepth));
			}
			
			if (positionedCell.getCell().isJoinableAt(Direction.WEST) && !cells.isCellAt(cellX - 1, cellY)) {
				anchors.add(new Anchor(positionedCell.getRoom(), positionedCell.getAnchor(), new Position(cellX - 1, cellY), Direction.EAST, nextRoomDepth));
			}
			
			if (positionedCell.getCell().isJoinableAt(Direction.EAST) && !cells.isCellAt(cellX + 1, cellY)) {
				anchors.add(new Anchor(positionedCell.getRoom(), positionedCell.getAnchor(), new Position(cellX + 1, cellY), Direction.WEST, nextRoomDepth));
			}
		}
		
		return anchors;
	}
	
	/**
	 * Get whether the minimum number or overall rooms and individual room counts meet any minimums.
	 * @param resources The room resources.
	 * @param roomCounts The generated room counts.
	 * @param configuration The configuration.
	 * @return Whether the minimum number or overall rooms and individual room counts meet any minimums.
	 */
	private static boolean areMinimumRoomCountsMet(RoomResources resources, RoomCountMap roomCounts, DunGenConfiguration configuration) {
		// We fail to generate the dungeon if we didn't even meet the minimum number of rooms.
		if (roomCounts.getTotal() < configuration.minimumRoomCount) {
			return false;
		}
		
		// Any room group can have a minimum defined, meaning that the sum of all rooms generated for that group must meet the group minimum.
		for (RoomGroup group : resources.getRoomGroups()) {
			// We only care about groups that have a minimum defined.
			if (group.getMinimum() == null) {
				continue;
			}
			
			// Get the total number of times that rooms in the same group have been generated.
			int roomGroupGenerationCount = 0;
			for (String roomName : group.getRoomNames()) {
				roomGroupGenerationCount += roomCounts.getCount(roomName);
			}

			// Are we below the group minimum?
			if (roomGroupGenerationCount < group.getMinimum()) {
				return false;
			}
		}
		
		// Check whether there are any rooms that have their own minimum, if so we need to make sure every count was reached.
		for (Room room : resources.getRooms()) {
			// We only care about rooms that have a minimum defined.
			if (room.getMinimum() == null) {
				continue;
			}
			
			// Was the room not generated enough times?
			if (roomCounts.getCount(room.getName()) < room.getMinimum()) {
				return false;
			}
		}
		
		// We are good to go!
		return true;
	}
	
	/**
	 * Get a list of all rooms that have an entrance direction matching the specified one.
	 * @param entranceDirection The entrance direction.
	 * @param rooms The list of all the rooms.
	 * @return A list of all rooms that have an entrance direction matching the specified one.
	 */
	private static ArrayList<Room> getRoomsWithEntranceDirection(Direction entranceDirection, ArrayList<Room> rooms) {
		// Create a list to store all of the rooms that are found with a matching entrance cell direction.
		ArrayList<Room> roomsFound = new ArrayList<Room>();
		
		for (Room room : rooms) {
			if (room.getEntranceDirection() == entranceDirection) {
				roomsFound.add(room);
			}
		}
		
		// Return all the rooms that were found with a matching entrance cell direction.
		return roomsFound;
	}
}
