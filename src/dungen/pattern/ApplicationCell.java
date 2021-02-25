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
    private ArrayList<CellEntity> entities = new ArrayList<CellEntity>();

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
     * Deserialise a ApplicationCell object from a JSON array.
     * The array will be structured like [xOffset, yOffset, name, [{ name: some-entity-name }], { some-cell-attribute: value }] where the fourth and fifth elements are optional.
     * @param cellJson The JSON array.
     * @return
     */
    public static ApplicationCell fromJSON(JSONArray cellJson) {
        // Create the cell with its name and x/y offsets.
        ApplicationCell cell = new ApplicationCell(cellJson.getString(2), cellJson.getInt(0), cellJson.getInt(1));

        // Parse all of the cell entities and add them to cell if we have any.
        if (cellJson.length() > 3) {
            // Get the entities array.
            JSONArray cellEntities = cellJson.getJSONArray(3);

            for (int entityIndex = 0; entityIndex < cellEntities.length(); entityIndex++) {
                JSONObject entityObject = cellEntities.getJSONObject(entityIndex);

                if (!entityObject.has("name")) {
                    throw new RuntimeException("cell entity 'name' property is required");
                }

                CellEntity entity = new CellEntity(entityObject.getString("name").toLowerCase());

                Iterator<String> keys = entityObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();

                    // Skip the 'name' attribute when we come across it.
                    if (key.equalsIgnoreCase("name")) {
                        continue;
                    }

                    entity.getAttributes().put(key.toLowerCase(), entityObject.getString(key).toLowerCase());
                }

                cell.getEntities().add(entity);
            }
        }

        // Parse all of the cell attributes and add them to cell if we have any.
        if (cellJson.length() > 4) {
            JSONObject callAttributes = cellJson.getJSONObject(4);

            Iterator<String> keys = callAttributes.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                cell.getAttributes().put(key.toLowerCase(), callAttributes.getString(key).toLowerCase());
            }
        }

        return cell;
    }
}