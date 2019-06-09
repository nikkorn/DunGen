package dungen.room;

import java.util.ArrayList;

/**
 * Represents a grouping of generatable rooms.
 */
public class RoomGroup {
	/**
	 * The group name.
	 */
	private String name;
	/**
	 * The minimum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 */
	private Integer minimum;
	/**
	 * The maximum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 */
	private Integer maximum;
	/**
	 * The depth range defining how far into the dungeon rooms in the group can be generated.
	 */
	private DepthRange depth;
	/**
	 * The list of names of any rooms in the group.
	 */
	private ArrayList<String> rooms;
	
	/**
	 * Create a new instance of the RoomGroup class.
	 * @param name The group name.
	 * @param minimum The minimum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 * @param maximum The maximum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 * @param depth The depth range defining how far into the dungeon rooms in the group can be generated.
	 * @param rooms The list of names of any rooms in the group.
	 */
	public RoomGroup(String name, Integer minimum, Integer maximum, DepthRange depth, ArrayList<String> rooms) {
		this.name    = name;
		this.minimum = minimum;
		this.maximum = maximum;
		this.depth   = depth;
		this.rooms   = rooms;
	}

	/**
	 * Get the name of the room.
	 * @return The name of the room.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the minimum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 * @return The minimum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 */
	public Integer getMinimum() {
		return minimum;
	}
	
	/**
	 * Get the maximum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 * @return The maximum number of instances of any rooms in the group that are required to generate a valid dungeon.
	 */
	public Integer getMaximum() {
		return maximum;
	}
	
	/**
	 * Get the depth range defining how far into the dungeon rooms in the group can be generated.
	 * @return The depth range defining how far into the dungeon rooms in the group can be generated.
	 */
	public DepthRange getDepth() {
		return depth;
	}
	
	/**
	 * Get the list of names of any rooms in the group.
	 * @return The list of names of any rooms in the group.
	 */
	public ArrayList<String> getRoomNames() {
		return this.rooms;
	}
	
	/**
	 * Get whether the group includes the specified room.
	 * @param room The room.
	 * @return Whether the group includes the specified room.
	 */
	public boolean includesRoom(Room room) {
		for (String groupedRoomName : this.rooms) {
			if (groupedRoomName.equals(room.getName())) {
				return true;
			}
		}
		// The room is not in this group.
		return false;
	}
}
