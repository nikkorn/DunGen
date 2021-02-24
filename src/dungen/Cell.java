package dungen;

import java.util.ArrayList;
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
	 * The additional cell attributes.
	 */
	private HashMap<String, String> attributes = new HashMap<String, String>();
	/**
	 * The list of entities linked to the cell.
	 */
	private ArrayList<CellEntity> entities = new ArrayList<CellEntity>();
	/**
	 * Whether the cell is frozen.
	 */
	private boolean isFrozen;
	
	/**
	 * Creates a new instance of the Cell class.
	 * @param type The cell type.
	 * @param attributes The cell attributes.
	 */
	public Cell(String type, HashMap<String, String> attributes) {
		this.type       = type;
		this.attributes = attributes;
	}

	public String getType() {
		return type;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public ArrayList<CellEntity> getEntities() {
		return entities;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
}
