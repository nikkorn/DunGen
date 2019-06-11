package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import dungen.DunGen;
import dungen.DunGenConfiguration;
import dungen.Dungeon;
import dungen.printing.DungeonPrinter;
import dungen.printing.ITileColourPicker;
import dungen.room.IRoomValidator;
import dungen.room.IValidatableRoom;
import dungen.tile.Tile;

/**
 * Test
 */
public class Test {

	public static void main(String[] args) {
		DunGenConfiguration config = new DunGenConfiguration();
		config.seed                = 12345;
		
		// Add a room validator for the boss room, as we cannot have any blocked rooms between it and the spawn.
		config.addRoomValidator("RoomIsNotBlocked", new IRoomValidator() {
			@Override
			public boolean validate(IValidatableRoom next, IValidatableRoom[] route) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		Dungeon dungeon = DunGen.generate("resources/test_room_resources", config);
		
		String dateTime = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS").format(new Date());
		
		dungeon.print(dateTime, "generated_images/", new ITileColourPicker() {
			@Override
			public int getTileColour(Tile tile) {
				switch (tile.getType()) {
					case EMPTY:
						if (tile.getDepth() == 0) {
							return DungeonPrinter.createColour(120, 120, 255);
						}
						return DungeonPrinter.createColour(100, 100, 100);
					case ENTRANCE:
						return DungeonPrinter.createColour(150, 150, 250);
					case WALL:
						return DungeonPrinter.createColour(255, 255, 255);
					default:
						return DungeonPrinter.createColour(0, 0, 0);
				}
			}
		});
	}
}
