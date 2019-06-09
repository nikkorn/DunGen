package dungen.room;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import dungen.Position;

/**
 * Holds information about tiles at tile positions.
 */
public class PositionedTileDetails {
	/**
	 * The map of positions to tile details.
	 */
	private HashMap<Position, TileDetails> tileDetailsMap = new HashMap<Position, TileDetails>();
	
	/**
	 * Get the tile details associated with the given tile position.
	 * @param position The tile position.
	 * @return The tile details associated with the given tile position.
	 */
	public TileDetails getDetails(Position position) {
		// Create a TileDetails instance for this position if there isn't one.
		if (!tileDetailsMap.containsKey(position)) {
			tileDetailsMap.put(position, new TileDetails());
		}
		
		// Return the tile details for the position.
		return tileDetailsMap.get(position);
	}
	
	/**
	 * Get the entry set for tile position to tile detail mappings.
	 * @return The entry set for tile position to tile detail mappings.
	 */
	public Set<Map.Entry<Position, TileDetails>> getEntrySet() {
		return this.tileDetailsMap.entrySet();
	}
}
