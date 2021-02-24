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

    public String getName() {
        return this.name;
    }

    public HashMap<String, String> getAttributes() {
        return this.attributes;
    }
}
