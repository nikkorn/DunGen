package dungen.room;

import dungen.Direction;
import dungen.Position;

/**
 * Represents a dungeon cell anchor, defining a cell position at which a
 * room entrance cell can be attach to a placed non-blocked room cell. 
 */
public class Anchor {
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
	 * @param position The anchor position.
	 * @param joinDirection The direction at which to join the anchored cell.
	 * @param depth The depth of the room that the anchor is attached to.
	 */
	public Anchor(Position position, Direction joinDirection, int depth) {
		this.position      = position;
		this.joinDirection = joinDirection;
		this.depth         = depth;
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
}
