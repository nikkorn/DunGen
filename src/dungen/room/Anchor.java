package dungen.room;

import java.util.ArrayList;

import dungen.Direction;
import dungen.Position;

/**
 * Represents a dungeon cell anchor, defining a cell position at which a
 * room entrance cell can be attach to a placed non-blocked room cell. 
 */
public class Anchor {
	/**
	 * The room that the anchor connects to.
	 */
	private Room room;
	/**
	 * The parent anchor.
	 */
	private Anchor parent;
	/**
	 * The anchor position.
	 */
	private Position position;
	/**
	 * The direction at which to join the anchored cell.
	 */
	private Direction joinDirection;
	/**
	 * The depth of the room that the anchor is attached to.
	 */
	private int depth;
	
	/**
	 * Create a new instance of the Anchor class.
	 * @paran room The room that the anchor connects to.
	 * @param parent The parent anchor.
	 * @param position The anchor position.
	 * @param joinDirection The direction at which to join the anchored cell.
	 * @param depth The depth of the room that the anchor is attached to.
	 */
	public Anchor(Room room, Anchor parent, Position position, Direction joinDirection, int depth) {
		this.room          = room;
		this.parent        = parent;
		this.position      = position;
		this.joinDirection = joinDirection;
		this.depth         = depth;
	}
	
	/**
	 * Gets the parent anchor.
	 * @return The parent anchor.
	 */
	public Anchor getParentAnchor() {
		return this.parent;
	}
	
	/**
	 * Gets the room that the anchor is connected to.
	 * @return The room that the anchor is connected to.
	 */
	public Room getConnectedRoom() {
		return this.room;
	}

	/**
	 * Get the anchor position.
	 * @return The anchor position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Get the direction at which to join the anchored cell.
	 * @return The direction at which to join the anchored cell.
	 */
	public Direction getJoinDirection() {
		return joinDirection;
	}

	/**
	 * Get the depth of the room that the anchor is attached to.
	 * @return The depth of the room that the anchor is attached to.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Get whether this anchor is within the specified depth range.
	 * @param range The depth range.
	 * @return Whether this anchor is within the specified depth range.
	 */
	public boolean isWithinDepthRange(DepthRange range) {
		// Check whether this anchor depth is below the minimum.
		if (range.getMinimum() != null && depth < range.getMinimum()) {
		    return false;
		}
		
		// Check whether this anchor depth is above the maximum.
		if (range.getMaximum() != null && depth > range.getMaximum()) {
		    return false;
		}
		
		// The anchor depth is within the specified depth range.
		return true;
	}
	
	/**
	 * Get the route of rooms from the anchor to the spawn as an array.
	 * @return The route of rooms from the anchor to the spawn as an array.
	 */
	public Room[] getRoomRoute() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		Anchor current        = this.parent;
		
		do {
			// Add the room for the current anchor to the list of rooms.
			rooms.add(current.getConnectedRoom());
			
			// Attempt to get the next anchor to process.
			current = current.getParentAnchor();
		} while (current != null);
		
		// Return the list of rooms as an immutable array.
		return rooms.toArray(new Room[rooms.size()]);
	}
}
