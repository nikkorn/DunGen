package dungen;

import java.util.HashMap;

/**
 * A dungeon cell.
 */
public class Cell {
	/**
	 * The cell type.
	 */
	private String type;
	/**
	 * The additional cell details.
	 */
	private HashMap<String, String> details = new HashMap<String, String>();
	/**
	 * Whether the cell is frozen.
	 */
	private boolean isFrozen;
	
	/**
	 * Creates a new instance of the Cell class.
	 * @param type The cell type.
	 * @param details The cell details.
	 */
	public Cell(String type, HashMap<String, String> details) {
		this.type    = type;
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public HashMap<String, String> getDetails() {
		return details;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
}
