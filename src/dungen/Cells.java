package dungen;

import java.util.ArrayList;
import java.util.HashMap;

public class Cells {
	/**
	 * The singleton out-of-bounds cell.
	 */
	public static final Cell OUT_OF_BOUNDS = new Cell("OOB", null, null);
	/**
	 * The width/height of the cells map.
	 */
	private int width, height;
	/**
	 * A mapping of cell position to cell types.
	 */
	private HashMap<String, Cell> cells = new HashMap<String, Cell>();
	
	/**
	 * Creates a new instance of the Cells class.
	 * @param width The width of the cells map.
	 * @param height The height of the cells map.
	 */
	public Cells(int width, int height) {
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Sets the cells at x/y with width/height.
	 * @param type The type of each cell.
	 * @param attributes The attributes of each cell.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void set(String type, HashMap<String, String> attributes, ArrayList<CellEntity> entities, int x, int y, int width, int height) {
		for (int posX = x; posX < (x + width); posX++) {
			for (int posY = y; posY < (y + height); posY++) {
				this.cells.put(getKey(posX, posY), new Cell(type, attributes == null ? new HashMap<String, String>() : attributes, entities == null ? new ArrayList<CellEntity>() : entities));
			}
		}
	}
	
	/**
	 * Sets the cell at x/y.
	 * @param type The type of the cell.
	 * @param attributes The attributes of the cell.
	 * @param x
	 * @param y
	 */
	public void set(String type, HashMap<String, String> attributes, ArrayList<CellEntity> entities, int x, int y) {
		this.set(type, attributes, entities, x, y, 1, 1);
	}
	
	/**
	 * Gets the cell at the x/y position.
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell get(int x, int y) {
		// Is this position outside the dungeon area?
		if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
			return Cells.OUT_OF_BOUNDS;
		}
		
		// If there is an actual cell at this valid position then return it.
		if (this.cells.containsKey(getKey(x, y))) {
			return this.cells.get(getKey(x, y));
		}
		
		// If there is no actual cell at this valid position then return a wall cell.
		return new Cell("WALL", null, null);
	}
	
	
	/**
	 * Get a unique stringy key based on an x/y position.
	 * @param x The x position.
	 * @param y The y position.
	 * @return The unique key.
	 */
	private static String getKey(int x, int y) {
		return x + "-" + y;
	}
}
