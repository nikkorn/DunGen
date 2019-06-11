package dungen.room;

import org.json.JSONObject;

/**
 * Represents a validatable room.
 */
public interface IValidatableRoom {
	
	/**
	 * Gets the name of the room.
	 * @return The name of the room.
	 */
	public String getName();
	
	/**
	 * Gets the room attributes.
	 * @return The room attributes.
	 */
	public JSONObject getAttributes();
}
