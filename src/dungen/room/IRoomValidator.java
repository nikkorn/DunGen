package dungen.room;

/**
 * Validates whether a room can be generated.
 */
public interface IRoomValidator {
	
	/**
	 * Validate whether this room can be generated.
	 * @param next The next room to be generated.
	 * @param route The route of already generated rooms from the spawn.
	 * @return Whether this room can be generated.
	 */
	public boolean validate(IRoomDetails next, IRoomDetails[] route);
}
