package dungen.room;

import java.util.ArrayList;
import java.util.Random;
import dungen.Direction;
import dungen.Position;
import org.json.JSONArray;

/**
 * Represents a cell in a room.
 */
public class Cell {
	/**
	 * The local position of the cell, relative to a room entrance cell position.
	 */
	private Position position;
	/**
	 * The cell entrance, or null if this cell is not an entrance cell.
	 */
	private Entrance entrance;
	/**
	 * The list of the directions in which anchors cannot be attached to the cell.
	 */
	private ArrayList<Direction> blockedDirections;
	/**
	 * The JSON array holding generatable tile details for the cell.
	 */
	private JSONArray generatableTileDetails;
	
	/**
	 * Create a new instance of the Cell class.
	 * @param position The local position of the cell, relative to a room entrance cell position.
	 * @param entrance The cell entrance, or null if this cell is not an entrance cell.
	 * @param blockedDirections The list of the directions in which anchors cannot be attached to the cell.
	 * @param generatableTileDetails The generatable tile details.
	 */
	public Cell(Position position, Entrance entrance, ArrayList<Direction> blockedDirections, JSONArray generatableTileDetails) {
		this.position               = position;
		this.entrance               = entrance;
		this.blockedDirections      = blockedDirections;
		this.generatableTileDetails = generatableTileDetails;
	}
	
	/**
	 * Get the local position of the cell, relative to a room entrance cell position.
	 * @return The local position of the cell, relative to a room entrance cell position.
	 */
	public Position getLocalPosition() {
		return position;
	}

	/**
	 * Get the cell entrance, or null if this cell is not an entrance cell.
	 * @return The cell entrance, or null if this cell is not an entrance cell.
	 */
	public Entrance getEntrance() {
		return entrance;
	}
	
	/**
	 * Generate tile details for this cell.
	 * @param random The rng to use in generating tile details.
	 * @return The generated tile details for this cell.
	 */
	public PositionedTileDetails generateTileDetails(Random random) {
		return GeneratableTileDetailsProcessor.process(this.generatableTileDetails, random);		
	}
	
	/**
	 * Gets whether this cell is joinable in the specified direction.
	 * @param direction The direction to check.
	 * @return Whether this cell is joinable in the specified direction.
	 */
	public boolean isJoinableAt(Direction direction) {
		for (Direction blocked : this.blockedDirections) {
			if (direction == blocked) {
				// This cell is blocked in the specified direction.
				return false;
			}
		}
		// The direction is not blocked.
		return true;
	}
}
