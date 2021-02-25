package dungen.pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import dungen.CellEntity;

/**
 * A cell defined as part on an applicable pattern.
 */
public class ApplicationCell {
    /**
     * The cell type.
     */
    private String type;
    /**
     * The x offset.
     */
    private int xOffset;
    /**
     * The y offset.
     */
    private int yOffset;
    /**
     * The cell attributes.
     */
    private HashMap<String, String> attributes = new HashMap<String, String>();
    /**
     * The list of entities linked to the cell.
     */
    private ArrayList<CellEntity> entities;

    /**
     * Creates a new instance of the ApplicationCell class.
     * @param type The cell type.
     * @param xOffset
     * @param yOffset
     */
    private ApplicationCell(String type, int xOffset, int yOffset) {
        this.type     = type;
        this.xOffset  = xOffset;
        this.yOffset  = yOffset;
    }

    /**
     * Gets the cell type.
     * @return
     */
    public String getType() {
        return type;
    }

    public int getOffsetX() {
        return xOffset;
    }

    public int getOffsetY() {
        return yOffset;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public ArrayList<CellEntity> getEntities() {
        return entities;
    }

    /**
     * Deserialise a PatternCell object from a JSON array.
     * The array will be structured like [xOffset, yOffet, name, { detail: value }] where the fourth element is optional.
     * @param cellJson The JSON array.
     * @return
     */
    public static ApplicationCell fromJSON(JSONArray cellJson) {
        // Create the cell with its name and x/y offsets.
        ApplicationCell cell = new ApplicationCell(cellJson.getString(2), cellJson.getInt(0), cellJson.getInt(1));


        // TODO Parse entities!


        // Parse all of the cell attributes and add them to cell if we have any.
        if (cellJson.length() == 4) {
            JSONObject cellDetails = cellJson.getJSONObject(3);

            Iterator<String> keys = cellDetails.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                cell.getAttributes().put(key.toLowerCase(), cellDetails.getString(key).toLowerCase());
            }
        }

        return cell;
    }
}