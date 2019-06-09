package dungen;

/**
 * DunGen Constants.
 */
public class Constants {
	/** The number of tiles that make up a cell horizontally and vertically. */
	public static final int CELL_TILE_SIZE = 5;

	/** The number of tiles that a door will be offset by from a cell edge. */
	public static final int CELL_DOOR_OFFSET = (int) Math.floor(CELL_TILE_SIZE / 2);
}
