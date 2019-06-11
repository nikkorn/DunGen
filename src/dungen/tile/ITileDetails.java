package dungen.tile;

import java.util.ArrayList;
import org.json.JSONObject;
import dungen.Direction;
import dungen.room.IRoomDetails;

/**
 * Represents detials of a generated tile.
 */
public interface ITileDetails {
	/**
	 * Gets the tile type.
	 * @return The tile type.
	 */
	public TileType getType();
	
	/**
	 * Gets the x position of this tile.
	 * @return The tile x position.
	 */
	public int getX();
	
	/**
	 * Gets the y position of this tile.
	 * @return The tile y position.
	 */
	public int getY();
	
	/**
	 * Gets the depth of the tile as the number of rooms that were passed through to reach it.
	 * @return The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public int getDepth();
	
	/**
	 * Gets the tile name.
	 * @return The tile name.
	 */
	public String getName();

	/**
	 * Gets the facing direction of the tile.
	 * @return The facing direction of the tile.
	 */
	public Direction getDirection();

	/**
	 * Gets the entities positioned on the tile.
	 * @return The entities positioned on the tile.
	 */
	public ArrayList<Entity> getEntities();

	/**
	 * Gets the additional tile attributes.
	 * @return The additional tile attributes.
	 */
	public JSONObject getAttributes();

	/**
	 * Gets the details of the room that the tile resides in.
	 * @return The details of the room that the tile resides in.
	 */
	public IRoomDetails getRoomDetails();
}
