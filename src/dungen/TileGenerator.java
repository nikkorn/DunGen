package dungen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import dungen.Direction;
import dungen.room.TileDetails;
import dungen.tile.Tile;
import dungen.tile.TileType;

/**
 * Generator of tiles based on positioned dungeon cells.
 */
public class TileGenerator {
	
	/**
	 * Generate a list of tiles based on cell and entity information.
	 * @param cells The dungeon cells.
	 * @param random The RNG to use in resolving entity participants.
	 * @return A list of tiles based on cell and entity information.
	 */
	public static ArrayList<Tile> generateFromCells(PositionedCellList cells, Random random) {
		// Create a map to hold all of the tiles.
		HashMap<Position, Tile> tileMap = new HashMap<Position, Tile>();
		
		// Convert each cell into a bunch of tiles.
		for (PositionedCell cell : cells) {
			// Get the cell position.
			Position position = cell.getPosition();
			
			// Get the min/max x/y positions of all of the tiles in the current cell.
			int tileXMin = position.getX() * (Constants.CELL_TILE_SIZE + 1);
			int tileYMin = position.getY() * (Constants.CELL_TILE_SIZE + 1);
			int tileXMax = tileXMin + Constants.CELL_TILE_SIZE;
			int tileYMax = tileYMin + Constants.CELL_TILE_SIZE;
			
			// Create a wall tile for each tile position around the cell.
			for (int tileX = tileXMin - 1; tileX <= tileXMax; tileX++) {
				for (int tileY = tileYMin - 1; tileY <= tileYMax; tileY++) {
					tileMap.put(new Position(tileX, tileY), new Tile(TileType.WALL, tileX, tileY, cell));
				}
			}
			
			// Create an empty tile for each tile position in the cell.
			for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileX, tileY), new Tile(TileType.EMPTY, tileX, tileY, cell));
				}
			}
			
			// Is the current cell the entrance cell for a room?
			if (cell.getCell().getEntrance() != null) {
				// Find the position of the entrance based on the entrance cell door direction.
				Position entrancePosition = getEntrancePosition(cell.getCell().getEntrance().getDirection(), tileXMin, tileYMin, tileXMax, tileYMax);
				
				// Create the entrance tile.
				Tile entrance = new Tile(TileType.ENTRANCE, entrancePosition.getX(), entrancePosition.getY(), cell);
			
				// Get the entrance attributes and add them to the tile.
				entrance.setAttributes(cell.getCell().getEntrance().getAttributes());
				
				// Get the entrance facing direction and apply it to the tile.
				entrance.setDirection(cell.getCell().getEntrance().getDirection());
				
				// Add the entrance tile to the tile map.
				tileMap.put(new Position(entrancePosition.getX(), entrancePosition.getY()), entrance);
			}
			
			// Is the cell above this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.NORTH)) {
				// Create an empty tile for each tile position between the two bridged cells.
				for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
					tileMap.put(new Position(tileX, tileYMax), new Tile(TileType.EMPTY, tileX, tileYMax, cell));
				}
			}
			
			// Is the cell below this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.SOUTH)) {
				// Create an empty tile for each tile position between the two bridged cells.
				for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
					tileMap.put(new Position(tileX, tileYMin - 1), new Tile(TileType.EMPTY, tileX, tileYMin - 1, cell));
				}
			}
			
			// Is the cell to the left of this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.WEST)) {
				// Create an empty tile for each tile position between the two bridged cells.
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileXMin - 1, tileY), new Tile(TileType.EMPTY, tileXMin - 1, tileY, cell));
				}
			}
			
			// Is the cell to the right of this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.EAST)) {
				// Create an empty tile for each tile position between the two bridged cells.
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileXMax, tileY), new Tile(TileType.EMPTY, tileXMax, tileY, cell));
				}
			}
						
			// Generate any entites for the cell and attach them to their corresponding tiles.
			for (Map.Entry<Position, TileDetails> entry : cell.getCell().generateTileDetails(random).getEntrySet()) {
				// There is nothing to do if there is no tile on which to place the entity.
				if (!tileMap.containsKey(entry.getKey())) {
					continue;
				}
				
				// Get the tile.
				Tile tile = tileMap.get(entry.getKey());
				
				// Set the tile name.
				tile.setName(entry.getValue().getName());
				
				// Set the tile direction.
				tile.setDirection(entry.getValue().getDirection());
				
				// Set the tile attributes.
				tile.setAttributes(entry.getValue().getAttributes());
				
				// Add any generated tile entites to the list of entities attached to the tile.
				tile.getEntities().addAll(entry.getValue().getEntities());
			}
		}
		
		// Return a list of the generated tiles.
		return new ArrayList<Tile>(tileMap.values());
	}
	
	/**
	 * Get the absolute position of an entrance tile for a cell.
	 * @param direction The entrance direction.
	 * @param tileXMin
	 * @param tileYMin
	 * @param tileXMax
	 * @param tileYMax
	 * @return The absolute position of an entrance tile for a cell.
	 */
	private static Position getEntrancePosition(Direction direction, int tileXMin, int tileYMin, int tileXMax, int tileYMax) {
		// Find the position of the door based on the entrance cell door direction.
		switch (direction) {
			case NORTH:
				return new Position(tileXMin + Constants.CELL_DOOR_OFFSET, tileYMax);
			case SOUTH:
				return new Position(tileXMin + Constants.CELL_DOOR_OFFSET, tileYMin - 1);
			case EAST:
				return new Position(tileXMax, tileYMin + Constants.CELL_DOOR_OFFSET);
			case WEST:
				return new Position(tileXMin - 1, tileYMin + Constants.CELL_DOOR_OFFSET);
			default:
				throw new RuntimeException("unexpected direction enum value: " + direction);
		}
	}
	
	/**
	 * Gets whether the cell at the initial position and the cell in the specified direction are in the same room.
	 * @param cell The list of all positioned cells.
	 * @param initialCell The initial cell.
	 * @param direction The direction of the cell to check.
	 * @returns Whether the cell at the initial position and the cell in the specified direction are in the same room.
	 */
	private static boolean areRoomCellsBridged(PositionedCellList cells, PositionedCell initialCell, Direction direction) {
		// Get the position of the adjacent cell.
		Position adjacentCellPosition = initialCell.getPosition().getAdjacent(direction);
		
		// Check whether there is even a cell at the adjacent position.
		if (!cells.isCellAt(adjacentCellPosition)) {
			return false;
		}
		
		// Are the cells within the same room instance.
		return cells.getCellAt(adjacentCellPosition).getRoomInstanceId() == initialCell.getRoomInstanceId();
	}
}
