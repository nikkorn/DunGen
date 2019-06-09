package dungen;

import java.util.ArrayList;

import dungen.printing.DefaultTileColourPicker;
import dungen.printing.DungeonPrinter;
import dungen.printing.ITileColourPicker;
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
	
	/**
	 * Print an overview of the dungeon as a .png image file using the specified tile colour picker.
	 * @param name The image file name.
	 * @param path The path to the directory at which to generate the image.
	 * @param tileColourPicker The tile colour picker.
	 */
	public void print(String name, String path, ITileColourPicker tileColourPicker) {
		DungeonPrinter.print(name, path, this, tileColourPicker);
	}
	
	/**
	 * Print an overview of the dungeon as a .png image file using the default tile colour picker.
	 * @param name The image file name.
	 * @param path The path to the directory at which to generate the image.
	 */
	public void print(String name, String path) {
		print(name, path, new DefaultTileColourPicker());
	}
}
