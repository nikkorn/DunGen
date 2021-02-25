package dungen;

import java.util.HashMap;

/**
 * An entity linked to a cell.
 */
public class CellEntity {
    /**
     * The entity name.
     */
    private String name;
    /**
     * The additional entity attributes.
     */
    private HashMap<String, String> attributes = new HashMap<String, String>();

    /**
     * Creates a new instance of the CellEntity class.
     * @param name
     */
    public CellEntity(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the cell entity.
     * @return The name of the cell entity.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the cell entity attributes.
     * @return The cell entity attributes.
     */
    public HashMap<String, String> getAttributes() {
        return this.attributes;
    }
}
