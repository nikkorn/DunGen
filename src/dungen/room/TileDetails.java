package dungen.room;

import java.util.ArrayList;
import org.json.JSONObject;
import dungen.Direction;
import dungen.tile.Entity;

/**
 * Details associated with a tile position.
 */
public class TileDetails {
	/**
	 * The tile name.
	 */
	private String name = null;
	/**
	 * The tile direction.
	 */
	private Direction direction = Direction.UNKNOWN;
	/**
	 * The tile attributes.
	 */
	private JSONObject attributes = new JSONObject();
	/**
	 * The tile entities.
	 */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/** Gets/Sets the tile name. */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gets/Sets the tile direction. */
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/** Gets/Sets the tile attributes. */
	public JSONObject getAttributes() {
		return attributes;
	}
	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}
	
	/** Gets the tile entities. */
	public ArrayList<Entity> getEntities() {
		return entities;
	}
}
