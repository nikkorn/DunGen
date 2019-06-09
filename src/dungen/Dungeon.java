package dungen;

import java.util.ArrayList;
import dungen.tile.Tile;

/**
 * A generated dungeon.
 */
public class Dungeon {
	/** 
	 * The configuration used in generating this dungeon. 
	 */
	private DunGenConfiguration configuration;
	/**
	 * The generated tiles.
	 */
	private ArrayList<Tile> tiles;
	
	/**
	 * Create a new instance of the Dungeon class.
	 * @param tiles The generated dungeon tiles.
	 * @param configuration The configuration used in generating this dungeon. 
	 */
	public Dungeon(ArrayList<Tile> tiles, DunGenConfiguration configuration) {
		this.tiles         = tiles;
		this.configuration = configuration;
	}

	/**
	 * Get the dungeon tiles.
	 * @return The dungeon tiles.
	 */
	public ArrayList<Tile> getTiles() { return this.tiles; }

	/**
	 * Get the configuration used in generating this dungeon.
	 * @return configuration
	 */
	public DunGenConfiguration getConfiguration() { return configuration; }
}
