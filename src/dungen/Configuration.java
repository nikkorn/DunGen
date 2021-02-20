package dungen;

import java.util.Random;

/**
 * Configuration to use when generating a dungeon.
 */
public class Configuration {

    /** The seed to use in generating a dungeon. */
    public long seed = new Random().nextLong();

    /** The dungeon width. Default: 60. */
    public int width = 60;

    /** The dungeon height. Default: 60. */
    public int height = 60;

    /** The maximum width/height for each room. Default: 15. */
    public int maxRoomSize = 15;

    /** The minimum width/height for each room. Default: 5. */
    public int minRoomSize = 5;

    /** The minimum distance between each room. Default: 0. */
    public int roomBuffer = 0;

    /** The number of rooms to generate. Default: 10. */
    public int rooms = 10;

    /** The width of the corridors joining generated rooms. Default: 2 */
    public int corridorWidth = 2;
    
    /** The maximum number of times to try generating a room. Default: 1000. */
    public int maxRoomGenerationAttempts = 1000;
}