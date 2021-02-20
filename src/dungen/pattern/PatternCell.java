package dungen.pattern;

import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A cell defined as part on an applicable pattern.
 */
public class PatternCell {
	/**
	 * The cell name, or a csv list of cell names.
	 */
	private String name;
	/**
	 * The x offset.
	 */
	private int xOffset;
	/**
	 * The y offset.
	 */
	private int yOffset;
	/**
	 * The additional cell details.
	 */
	private HashMap<String, String> details = new HashMap<String, String>();
	
	/**
	 * Creates a new instance of the PatternCell class.
	 * @param name The cell name, or a csv list of cell names.
	 * @param xOffset
	 * @param yOffset
	 */
	private PatternCell(String name, int xOffset, int yOffset) {
		this.name    = name;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/**
	 * Gets the cell name, or a csv list of cell names.
	 * @return
	 */
	public String getName() {
		return name;
	}

	public int getOffsetX() {
		return xOffset;
	}

	public int getOffsetY() {
		return yOffset;
	}

	public HashMap<String, String> getDetails() {
		return details;
	}
	
	/**
	 * Gets whether this pattern cell matches the given type.
	 * @param type
	 * @return Whether this pattern cell matches the given type.
	 */
	public boolean matchesType(String type) {
		// The name of this cell can represent a number of applicable types.
		String[] names = this.name.split(",");
		
		for(int nameIndex = 0; nameIndex < names.length; nameIndex++){
			if(names[nameIndex].equalsIgnoreCase(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Deserialise a PatternCell object from a JSON array.
	 * The array will be structured like [xOffset, yOffet, name, { detail: value }] where the fourth element is optional.
	 * @param cellJson The JSON array.
	 * @return
	 */
	public static PatternCell fromJSON(JSONArray cellJson) {
		// Create the cell with its name and x/y offsets.
		PatternCell cell = new PatternCell(cellJson.getString(2), cellJson.getInt(0), cellJson.getInt(1));
		
		// Parse all details and add them to cell if we have any.
		if (cellJson.length() == 4) {
			JSONObject cellDetails = cellJson.getJSONObject(3);
			
			Iterator<String> keys = cellDetails.keys();
			
			while (keys.hasNext()) {
				String key = keys.next();
				
				cell.getDetails().put(key.toLowerCase(), cellDetails.getString(key).toLowerCase());
			}
		}
		
		return cell;
	}
}
