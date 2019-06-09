package dungen.printing;

import dungen.tile.Tile;

/**
 * The default tile colour picker instance to use.
 */
public class DefaultTileColourPicker implements ITileColourPicker  {

	@Override
	public int getTileColour(Tile tile) {
		switch (tile.getType()) {
			case EMPTY:
				return DungeonPrinter.createColour(100, 100, 100);
			case ENTRANCE:
				return DungeonPrinter.createColour(150, 150, 250);
			case WALL:
				return DungeonPrinter.createColour(255, 255, 255);
			default:
				return DungeonPrinter.createColour(0, 0, 0);
		}
	}
}
