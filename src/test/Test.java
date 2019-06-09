package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import dungen.DunGen;
import dungen.DunGenConfiguration;
import dungen.Dungeon;

/**
 * Test
 */
public class Test {

	public static void main(String[] args) {
		DunGenConfiguration config = new DunGenConfiguration();
		config.seed                = 12345;
		
		Dungeon dungeon = DunGen.generate("resources/test_room_resources", config);
		
		String dateTime = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS").format(new Date());
		
		dungeon.print(dateTime, "generated_images/");
	}
}
