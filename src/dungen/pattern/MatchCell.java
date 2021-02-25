package dungen.pattern;

import org.json.JSONArray;
import dungen.Cell;

/**
 * A cell defined as a matcher for an applicable pattern.
 */
public class MatchCell {
    /**
     * The cell pattern selector.
     */
    private String selector;
    /**
     * The x offset.
     */
    private int xOffset;
    /**
     * The y offset.
     */
    private int yOffset;

    /**
     * Creates a new instance of the PatternCell class.
     * @param selector The cell pattern selector.
     * @param xOffset
     * @param yOffset
     */
    private MatchCell(String selector, int xOffset, int yOffset) {
        this.selector = selector.replaceAll("\\s+","");
        this.xOffset  = xOffset;
        this.yOffset  = yOffset;
    }

    public int getOffsetX() {
        return xOffset;
    }

    public int getOffsetY() {
        return yOffset;
    }

    /**
     * Gets whether this pattern cell matches the given cell.
     * @param cell
     * @return Whether this pattern cell matches the given cell.
     */
    public boolean matchesCell(Cell cell) {

        // MATCHING
        // Cell type 'EMPTY'
        // Cell attribute with type exists '[IsGoodCell]'
        // Cell attribute has given value '[IsGoodCell:true]'
        // Cell entity with name exists '(Chest)'
        // Cell entity with attribute exists '(Chest[Quality])'
        // Cell entity attribute has given value '(Chest[Quality:5])'
        // Pattern can be split with '|' and each section must be true.
        // ! Negates, (e.g. '!EMPTY|!GRASS' not a grass or empty cell)








        // The name of this cell can represent a number of applicable types.
        String[] names = this.selector.split(",");

        for(int nameIndex = 0; nameIndex < names.length; nameIndex++){
            if(names[nameIndex].equalsIgnoreCase(cell.getType())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Deserialise a MatchCell object from a JSON array.
     * The array will be structured like [xOffset, yOffet, selector].
     * @param cellJson The JSON array.
     * @return
     */
    public static MatchCell fromJSON(JSONArray cellJson) {
        // Create the cell with its selector and x/y offsets.
        return new MatchCell(cellJson.getString(2), cellJson.getInt(0), cellJson.getInt(1));
    }
}
