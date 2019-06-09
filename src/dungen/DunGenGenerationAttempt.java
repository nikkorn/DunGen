package dungen;

import java.util.ArrayList;
import dungen.tile.Tile;

/**
 * Represents an attempt at generating a dungeon.
 */
public class DunGenGenerationAttempt {
	/**
	 * The dungeon generation attempt result status.
	 */
	private DunGenGenerationAttemptStatus status;
	/**
	 * The generated dungeon tiles.
	 */
	private ArrayList<Tile> tiles;
	
	/**
	 * Create a new instance of the DunGenGenerationAttempt class.
	 * @param status The dungeon generation attempt result status.
	 * @param tiles
	 */
	public DunGenGenerationAttempt(DunGenGenerationAttemptStatus status, ArrayList<Tile> tiles) {
		this.status = status;
		this.tiles  = tiles;
	}

	/**
	 * Get the dungeon generation attempt result status.
	 * @return The dungeon generation attempt result status.
	 */
	public DunGenGenerationAttemptStatus getStatus() {
		return status;
	}

	/**
	 * Get the generated dungeon tiles.
	 * @return The generated dungeon tiles.
	 */
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
}
