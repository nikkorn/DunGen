package dungen.room;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import dungen.Direction;
import dungen.Position;

/**
 * Factory for creating Room and RoomGroup instances. 
 */
public class RoomFactory {
	
	/**
	 * Create a Room object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Room object based on a definition in JSON.
	 */
	public static Room createRoom(JSONObject object) {
		// Get the room name.
		String name = object.getString("name");
		
		// Get the room attributes.
		JSONObject attributes = object.has("attributes") ? object.getJSONObject("attributes") : new JSONObject();
		
		// Get the room validator.
		String validator = object.has("validator") ? object.getString("validator") : null;
				
		// Get the minimum/maximum generation count.
		Integer minimum = object.has("minimum") ? object.getInt("minimum") : null;
		Integer maximum = object.has("maximum") ? object.getInt("maximum") : null;
		
		// Get the room generation chance.
		Double chance = object.has("chance") ? object.getDouble("chance") : null;
		
		// Create the room depth.
		DepthRange depth = object.has("depth") ? createDepthRange(object.getJSONObject("depth")) : null;
		
		// Create the list to hold the room cells.
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		// Get the cells array from our room JSON.
		JSONArray cellsJsonArray = object.getJSONArray("cells");
		
		// Create an actual cell for each JSON cell array value.
		for (int cellIndex = 0; cellIndex < cellsJsonArray.length(); cellIndex++) {
			cells.add(createRoomCell(cellsJsonArray.getJSONObject(cellIndex)));
		}
		
		return new Room(name, attributes, validator, minimum, maximum, chance, depth, cells);
	}
	
	/**
	 * Create a RoomGroup object based on a definition in JSON.
	 * @param object The definition.
	 * @return A RoomGroup object based on a definition in JSON.
	 */
	public static RoomGroup createRoomGroup(JSONObject object) {
		// Get the group name.
		String name = object.getString("name");
		
		// Get the minimum/maximum generation count.
		Integer minimum = object.has("minimum") ? object.getInt("minimum") : null;
		Integer maximum = object.has("maximum") ? object.getInt("maximum") : null;
		
		// Create the group depth.
		DepthRange depth = object.has("depth") ? createDepthRange(object.getJSONObject("depth")) : null;
		
		// Create a list to hold the names of the rooms in the group.
		ArrayList<String> rooms = new ArrayList<String>();
		
		// Get the room names array from our group JSON.
		JSONArray roomsJsonArray = object.getJSONArray("rooms");
		
		// Create an actual room name entry for each JSON array value.
		for (int roomNameIndex = 0; roomNameIndex < roomsJsonArray.length(); roomNameIndex++) {
			rooms.add(roomsJsonArray.getString(roomNameIndex));
		}
				
		return new RoomGroup(name, minimum, maximum, depth, rooms);
	}
	
	/**
	 * Create a Cell object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Cell object based on a definition in JSON.
	 */
	private static Cell createRoomCell(JSONObject object) {
		// Create the cell position.
		Position position = createPosition(object.getJSONObject("position"));
		
		// Get the cell entrance, this would only be defined if this is the room entrance cell.
		Entrance entrance = object.has("entrance") ? createEntrance(object.getJSONObject("entrance")) : null;
		
		// Create the list to hold the directions in which anchors cannot be attached to the cell.
		ArrayList<Direction> blocked = new ArrayList<Direction>();
		
		// Get the blocked directions array from our room JSON.
		JSONArray blockedJsonArray = object.has("blocked") ? object.getJSONArray("blocked") : new JSONArray();
		
		// Create an actual blocked direction entry for each JSON array value.
		for (int directionIndex = 0; directionIndex < blockedJsonArray.length(); directionIndex++) {
			blocked.add(getDirection(blockedJsonArray.getString(directionIndex)));
		}
		
		return new Cell(position, entrance, blocked, object.getJSONArray("tiles"));
	}
	
	/**
	 * Create a DepthRange object based on a definition in JSON.
	 * @param object The definition.
	 * @return A DepthRange object based on a definition in JSON.
	 */
	private static DepthRange createDepthRange(JSONObject object) {
		Integer minimum = object.has("minimum") ? object.getInt("minimum") : null;
		Integer maximum = object.has("maximum") ? object.getInt("maximum") : null;
		return new DepthRange(minimum, maximum);
	}
	
	/**
	 * Create a Position object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Position object based on a definition in JSON.
	 */
	private static Position createPosition(JSONObject object) {
		return new Position(object.getInt("x"), object.getInt("y"));
	}
	
	/**
	 * Create an Entrance object based on a definition in JSON.
	 * @param object The definition.
	 * @return An Entrance object based on a definition in JSON.
	 */
	private static Entrance createEntrance(JSONObject object) {
		return new Entrance(getDirection(object.getString("direction")), object.getJSONObject("attributes"));
	}
	
	/**
	 * Get the Direction enum value based on its string value.
	 * @param direction The string value.
	 * @return The Direction enum value based on its string value.
	 */
	private static Direction getDirection(String direction) {
		return Direction.valueOf(direction.toUpperCase());
	}
}
