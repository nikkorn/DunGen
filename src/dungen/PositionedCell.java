package dungen;

import dungen.room.Cell;
import dungen.room.Room;

/**
 * Represents a positioned cell in a dungeon.
 */
public class PositionedCell {
	/**
	 * The cell.
	 */
	private Cell cell;
	/**
	 * The absolute cell position.
	 */
	private Position position;
	/**
	 * The depth at which the room was placed.
	 */
	private int depth;
	/**
	 * The room.
	 */
	private Room room;
	/**
	 * The room instance id.
	 */
	private String roomInstanceId;
	
	/**
	 * Create a new instance of the PositionedCell class.
	 * @param cell The cell.
	 * @param position The absolute cell position.
	 * @param depth The depth at which the room was placed.
	 * @param room The room.
	 * @param roomInstanceId The room instance id.
	 */
	public PositionedCell(Cell cell, Position position, int depth, Room room, String roomInstanceId) {
		this.cell           = cell;
		this.position       = position;
		this.depth          = depth;
		this.room           = room;
		this.roomInstanceId = roomInstanceId;
	}
	
	/**
	 * Get the cell.
	 * @return The cell.
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * Get the absolute cell position.
	 * @return The absolute cell position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Get the depth at which the room was placed.
	 * @return The depth at which the room was placed.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Get the room.
	 * @return The room.
	 */
	public Room getRoom() {
		return this.room;
	}

	/**
	 * Get the room instance id.
	 * @return The room instance id.
	 */
	public String getRoomInstanceId() {
		return roomInstanceId;
	}
}
