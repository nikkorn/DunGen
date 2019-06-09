package dungen.room;

import java.util.ArrayList;
import dungen.Direction;

/**
 * Represents a generatable room.
 */
public class Room {
	/**
	 * The room name.
	 */
	private String name;
	/**
	 * The minimum number of instances of this room that are required to generate a valid dungeon.
	 */
	private Integer minimum;
	/**
	 * The maximum number of instances of this room that are required to generate a valid dungeon.
	 */
	private Integer maximum;
	/**
	 * The chance of this room being generated, between 0 and 1.
	 */
	private Double chance;
	/**
	 * The depth range defining how far into the dungeon the room can be generated.
	 */
	private DepthRange depth;
	/**
	 * The list of cells that the room is composed of.
	 */
	private ArrayList<Cell> cells;
	
	/**
	 * Create a new instance of the Room class.
	 * @param name The room name.
	 * @param minimum The minimum number of instances of this room that are required to generate a valid dungeon.
	 * @param maximum The maximum number of instances of this room that are required to generate a valid dungeon.
	 * @param chance The chance of this room being generated, between 0 and 1.
	 * @param depth The depth range defining how far into the dungeon the room can be generated.
	 * @param cells The cells that the room are composed of.
	 */
	public Room(String name, Integer minimum, Integer maximum, Double chance, DepthRange depth, ArrayList<Cell> cells) {
		this.name    = name;
		this.minimum = minimum;
		this.maximum = maximum;
		this.chance  = chance;
		this.depth   = depth;
		this.cells   = cells;
	}

	/**
	 * Get the name of the room.
	 * @return The name of the room.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the minimum number of instances of this room that are required to generate a valid dungeon.
	 * @return The minimum number of instances of this room that are required to generate a valid dungeon.
	 */
	public Integer getMinimum() {
		return minimum;
	}
	
	/**
	 * Get the maximum number of instances of this room that are required to generate a valid dungeon.
	 * @return The maximum number of instances of this room that are required to generate a valid dungeon.
	 */
	public Integer getMaximum() {
		return maximum;
	}

	/**
	 * Get the chance of this room being generated, between 0 and 1.
	 * @return The chance of this room being generated, between 0 and 1.
	 */
	public Double getChance() {
		return chance;
	}
	
	/**
	 * Get the depth range defining how far into the dungeon the room can be generated.
	 * @return The depth range defining how far into the dungeon the room can be generated.
	 */
	public DepthRange getDepth() {
		return depth;
	}
	
	/**
	 * Get the list of cells that the room is composed of.
	 * @return The list of cells that the room is composed of.
	 */
	public ArrayList<Cell> getCells() {
		return this.cells;
	}
	
	/**
	 * Get the direction in which this room attaches to an existing room.
	 * @return The direction in which this room attaches to an existing room.
	 */
	public Direction getEntranceDirection() {
		// We need to find the room entrance cell. It will define the direction in which the room attaches to a cell.
		for (Cell cell : this.cells) {
			if (cell.getEntrance() != null) {
				return cell.getEntrance().getDirection();
			}
		}
		// The room did not have an entrance cell.
		return Direction.UNKNOWN;
	}
}
