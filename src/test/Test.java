package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import dungen.DunGen;
import dungen.DunGenConfiguration;
import dungen.Dungeon;
import dungen.printing.DungeonPrinter;
import dungen.printing.ITileColourPicker;
import dungen.room.IRoomValidator;
import dungen.room.IRoomDetails;
import dungen.tile.ITileDetails;

/**
 * Test
 */
public class Test {

	public static void main(String[] args) {
		DunGenConfiguration config = new DunGenConfiguration();
		config.seed                = 01234;
		
		// Add a room validator for the boss room, as we cannot have any blocked rooms between it and the spawn.
		config.addRoomValidator("RoomIsNotBlocked", new IRoomValidator() {
			@Override
			public boolean validate(IRoomDetails next, IRoomDetails[] route) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		Dungeon dungeon = DunGen.generate("resources/test_room_resources", config);
		
		String dateTime = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS").format(new Date());
		
		// Print the generated dungeon to an image.
		dungeon.print(dateTime, "generated_images/", new ITileColourPicker() {
			@Override
			public int getTileColour(ITileDetails tile) {
				switch (tile.getType()) {
					case EMPTY:
						// Get whether this is the spawn room.
						boolean isSpawnRoom =  tile.getRoomDetails().getName().equals("spawn");
						
						// The colour of the tile depends on whether the room is the spawn room.
						if (isSpawnRoom) {
							return DungeonPrinter.createColour(120, 120, 255);
						} else {
							return DungeonPrinter.createColour(100, 100, 100);
						}
					case ENTRANCE:
						// Get whether the room that this tile is in has a blocking door
						boolean roomHasBlockingDoor = tile.getRoomDetails().getAttributes().getBoolean("hasBlockingDoor");
						
						// The colour of the entrance tile depends on whether the room has a blocking door.
						if (roomHasBlockingDoor) {
							return DungeonPrinter.createColour(255, 50, 50);
						} else {
							return DungeonPrinter.createColour(150, 150, 250);
						}
					case WALL:
						return DungeonPrinter.createColour(255, 255, 255);
					default:
						return DungeonPrinter.createColour(0, 0, 0);
				}
			}
		});
	}
}
