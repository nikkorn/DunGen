package dungen.room;

/**
 * Represents a range of depth.
 */
public class DepthRange {
	/**
	 * The depth minimum.
	 */
	private Integer minimum;
	/**
	 * The depth maximum.
	 */
	private Integer maximum;
	
	/**
	 * Create a new instance of the DepthRange class.
	 * @param minimum The minimum.
	 * @param maximum The maximum.
	 */
	public DepthRange(Integer minimum, Integer maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	/**
	 * Get the depth minimum.
	 * @return The depth minimum.
	 */
	public Integer getMinimum() {
		return minimum;
	}

	/**
	 * Get the depth maximum.
	 * @return The depth maximum.
	 */
	public Integer getMaximum() {
		return maximum;
	}
}
