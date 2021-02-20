package dungen;

public class Result {
	/**
	 * The configuration used.
	 */
	private Configuration configuration;
	/**
	 * The dungeon cells.
	 */
	private Cells cells;
	
	/**
	 * Creates a new instance of the Result class.
	 * @param configuration The configuration used.
	 * @param cells The dungeon cells.
	 */
	public Result(Configuration configuration, Cells cells) {
		this.configuration = configuration;
		this.cells         = cells;
	}

	/**
	 * Get the configuration used.
	 * @return The configuration used.
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Gets the dungeon cells.
	 * @return The dungeon cells.
	 */
	public Cells getCells() {
		return cells;
	}
}
