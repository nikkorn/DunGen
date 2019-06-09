package dungen.printing;

import dungen.tile.Tile;

/**
 * Represents a provider of colours to use in printing tiles.
 */
public interface ITileColourPicker {
	
	/**
	 * Gets an integer value representing the colour to use in drawing the given tile.
	 * @param tile The tile.
	 * @return An integer value representing the colour to use in drawing the given tile.
	 */
	public int getTileColour(Tile tile);
}
