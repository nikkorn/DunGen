package dungen.tile;

import java.util.ArrayList;
import dungen.Direction;
import org.json.JSONObject;

/**
 * Represents a tile in the dungeon.
 */
public class Tile {
	/**
	 * The base tile type.
	 */
	private TileType type;
	/**
	 * The tile position.
	 */
	private int x, y;
	/**
	 * The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	private int depth;
	/**
	 * The name of the tile.
	 */
	private String name;
	/**
	 * The facing direction of the tile.
	 */
	private Direction direction = Direction.NORTH;
	/**
	 * The entities positioned on the tile.
	 */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	/**
	 * The additional attributes associated with the tile.
	 */
	private JSONObject attributes = new JSONObject();
	
	/**
	 * Create a new instance of the Tile class.
	 * @param type The tile type.
	 * @param x The tile x position.
	 * @param y The tile y position.
	 * @param depth The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public Tile(TileType type, int x, int y, int depth) {
		this.type  = type;
		this.x     = x;
		this.y     = y;
		this.depth = depth;
	}

	/**
	 * Gets the tile type.
	 * @return The tile type.
	 */
	public TileType getType() { return this.type; }
	
	/**
	 * Gets the x position of this tile.
	 * @return The tile x position.
	 */
	public int getX() { return this.x; }
	
	/**
	 * Gets the y position of this tile.
	 * @return The tile y position.
	 */
	public int getY() { return this.y; }
	
	/**
	 * Gets the depth of the tile as the number of rooms that were passed through to reach it.
	 * @return The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public int getDepth() { return this.depth; }
	
	/**
	 * Gets the tile name.
	 * @return The tile name.
	 */
	public String getName() { return this.name; }
	/**
	 * Sets the tile name.
	 * @param value The tile name.
	 */
	public void setName(String value) { this.name = value; }

	/**
	 * Gets the facing direction of the tile.
	 * @return The facing direction of the tile.
	 */
	public Direction getDirection() { return this.direction; }
	/**
	 * Sets the facing direction of the tile.
	 * @param value The facing direction of the tile.
	 */
	public void setDirection(Direction value) { this.direction = value; }

	/**
	 * Gets the entities positioned on the tile.
	 * @return The entities positioned on the tile.
	 */
	public ArrayList<Entity> getEntities() { return this.entities; }
	/**
	 * Sets the entities positioned on the tile.
	 * @param value The entities positioned on the tile.
	 */
	public void setEntities(ArrayList<Entity> value) { this.entities = value; }

	/**
	 * Gets the additional tile attributes.
	 * @return The additional tile attributes.
	 */
	public JSONObject getAttributes() { return this.attributes; }
	/**
	 * Sets the additional tile attributes.
	 * @param value The additional tile attributes.
	 */
	public void setAttributes(JSONObject value) { this.attributes = value; }
}
