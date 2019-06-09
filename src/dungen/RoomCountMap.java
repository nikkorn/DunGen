package dungen;

import java.util.HashMap;

/**
 * A mapping of room names to the count of times the room has been generated.
 */
public class RoomCountMap extends HashMap<String, Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Get the number of times the room with the specified name has been generated.
	 * @param name The room name.
	 * @return The number of times the room with the specified name has been generated.
	 */
	public int getCount(String name) {
		return this.containsKey(name) ? this.get(name) : 0;
	}
	
	/**
	 * Increment the count for the specified room.
	 * @param name The room name.
	 */
	public void incrementCount(String name) {
		if (this.containsKey(name)) {
			this.put(name, this.get(name) + 1);
		} else {
			this.put(name, 1);
		}
	}
	
	/**
	 * Get the total number of generated rooms.
	 * @return The total number of generated rooms.
	 */
	public int getTotal() {
		int total = 0;
		for (Integer count : this.values()) {
			total += count;
		}
		return total;
	}
}
