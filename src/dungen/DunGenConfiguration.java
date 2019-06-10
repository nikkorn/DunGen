package dungen;

import java.util.HashMap;
import java.util.Random;
import dungen.room.IRoomValidator;

/**
 * Configuration to use when generating a dungeon.
 */
public class DunGenConfiguration {
	/**
	 * The registered room validators.
	 */
	private HashMap<String, IRoomValidator> roomValidators = new HashMap<String, IRoomValidator>();
	
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

	/**
	 * Add a room validator.
	 * @param name The name of the room validator.
	 * @param validator The room validator.
	 */
	public void addRoomValidator(String name, IRoomValidator validator) {
		this.roomValidators.put(name, validator);
	}

	/**
	 * Gets the room validator with the given name.
	 * @param name The name of the room validator.
	 * @return The room validator with the given name.
	 */
	public IRoomValidator getRoomValidator(String name) {
		return this.roomValidators.get(name);
	}
}