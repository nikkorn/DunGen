package dungen.room;

import org.json.JSONObject;
import dungen.Direction;

/**
 * Represents an entrance to a cell.
 */
public class Entrance {
	/**
	 * The direction defining the side of a cell at which the entrance is placed.
	 */
	private Direction direction;
	/**
	 * The entrance attributes.
	 */
	private JSONObject attributes;
	
	/**
	 * Create a new instance of the Entrance class.
	 * @param direction The direction defining the side of a cell at which the entrance is placed.
	 * @param attributes The entrance attributes.
	 */
	public Entrance(Direction direction, JSONObject attributes) {
		this.direction  = direction;
		this.attributes = attributes;
	}

	/**
	 * Gets the direction defining the side of a cell at which the entrance is placed.
	 * @return The direction defining the side of a cell at which the entrance is placed.
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Gets the entrance attributes.
	 * @return The entrance attributes.
	 */
	public JSONObject getAttributes() {
		return this.attributes;
	}
}
