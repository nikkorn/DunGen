package dungen;

/**
 * Represents an x/y position.
 */
public class Position {
	/**
	 * The x position.
	 */
	private int x;
	/**
	 * The y position.
	 */
	private int y;
	
	/**
	 * Creates a new instance of the Position class.
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x position.
	 * @return The x position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y position.
	 * @return The y position.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the position adjacent to this one in the specified direction.
	 * @param direction The adjacent direction.
	 * @return The position adjacent to this one in the specified direction.
	 */
	public Position getAdjacent(Direction direction) {
		switch (direction) {
			case NORTH:
				return new Position(this.x, this.y + 1);
			case SOUTH:
				return new Position(this.x, this.y - 1);
			case EAST:
				return new Position(this.x + 1, this.y);
			case WEST:
				return new Position(this.x - 1, this.y);
			case UNKNOWN:
			default:
				throw new RuntimeException("unexpected direction enum value: " + direction);
		}
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        Position position = (Position) o;
        
        return this.getX() == position.getX() && this.getY() == position.getY();
    }
	
    @Override
    public int hashCode() {
        return (this.x + "_" + this.y).hashCode();
    }
}
