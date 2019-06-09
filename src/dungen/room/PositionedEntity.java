package dungen.room;

import dungen.Position;
import dungen.tile.Entity;

/**
 * Represents a positioned entity.
 */
public class PositionedEntity {
	/**
	 * The entity.
	 */
	private Entity entity;
	/**
	 * The absolute entity position.
	 */
	private Position position;
	
	/**
	 * Create a new instance of the PositionedEntity class.
	 * @param entity The entity.
	 * @param position The absolute entity position.
	 */
	public PositionedEntity(Entity entity, Position position) {
		this.entity   = entity;
		this.position = position;
	}
	
	/**
	 * Get the entity.
	 * @return The entity.
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Get the absolute entity position.
	 * @return The absolute entity position.
	 */
	public Position getPosition() {
		return position;
	}

}
