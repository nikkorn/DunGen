package dungen.pattern;

import java.awt.Point;

/**
 * Represents a sequence and position.
 */
public class ApplicationPosition {
	/**
	 * The applicable sequence.
	 */
	private Sequence sequence;
	/**
	 * The position at which the sequence can be applied.
	 */
	private Point position;
	
	/**
	 * Creates a new instance of the ApplicationPosition class.
	 * @param sequence The applicable sequence.
	 * @param position The position at which the sequence can be applied.
	 */
	public ApplicationPosition(Sequence sequence, Point position) {
		this.sequence = sequence;
		this.position = position;
	}

	/**
	 * Gets applicable sequence.
	 * @return The applicable sequence.
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/**
	 * Gets the position at which the sequence can be applied.
	 * @return The position at which the sequence can be applied.
	 */
	public Point getPosition() {
		return position;
	}
}
