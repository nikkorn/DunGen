package dungen.tile;

import dungen.Direction;

/**
 * Represents an entity that can be placed onto an empty tile.
 */
public class Entity {
	/**
	 * The entity id.
	 */
	private String id;
	/**
	 * The entities facing direction.
	 */
	private Direction facingDirection;
	
	/**
	 * Create a new instance of the Entity class.
	 * @param id The entity id.
	 * @param facing The facing direction of the entity.
	 */
	public Entity(String id, Direction facing) {
		this.id              = id;
		this.facingDirection = facing;
	}

	/**
	 * Get the entity id.
	 * @return The entity id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the facing direction of the entity.
	 * @return The facing direction of the entity.
	 */
	public Direction getFacingDirection() {
		return facingDirection;
	}
}
