package dungen;

import java.util.Random;

/**
 * Configuration to use when generating a dungeon.
 */
public class DunGenConfiguration {
	/** The seed to use in generating a dungeon. */
	public long seed = new Random().nextLong();
	
	/** The maximum count of dungeon rooms. Default: 50. */
	public int maximumRoomCount = 50;
	
	/** The minimum count of dungeon rooms. Default: 10. */
	public int minimumRoomCount = 10;
	
	/** The maximum number of times allowed for dungeon regeneration attempts. Default: 1000. */
	public int dungeonGenerationRetries = 1000;
	
	/** The maximum number of times allowed for room regeneration attempts. Default: 1000. */
	public int roomGenerationRetries = 1000;
}