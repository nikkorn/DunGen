package dungen.pattern;

import org.json.JSONArray;
import dungen.Cell;
import dungen.Cells;

/**
 * A pattern matcher that attempts to match on an absolute cell position or area.
 */
public class Matcher {
    /**
     * The cell pattern selector.
     */
    private String selector;
    /**
     * The x/y offset for a single cell match.
     */
    private int xOffset, yOffset;
    /**
     * The x/y start/end offset for an area match.
     */
    private int xOffsetStart, yOffsetStart, xOffsetEnd, yOffsetEnd;
    /**
     * Whether this is a matcher for a single cell.
     */
    private boolean isSingleCellMatch;

    /**
     * Creates a new instance of the Matcher class.
     * @param selector
     * @param xOffsetStart
     * @param yOffsetStart
     * @param xOffsetEnd
     * @param yOffsetEnd
     */
    private Matcher(String selector, int xOffsetStart, int yOffsetStart, int xOffsetEnd, int yOffsetEnd) {
        this.selector          = selector.replaceAll("\\s+","");
        this.xOffsetStart      = xOffsetStart;
        this.yOffsetStart      = yOffsetStart;
        this.xOffsetEnd        = xOffsetEnd;
        this.yOffsetEnd        = yOffsetEnd;
        this.isSingleCellMatch = false;
    }

    /**
     * Creates a new instance of the Match class.
     * @param selector The cell pattern selector.
     * @param xOffset
     * @param yOffset
     */
    private Matcher(String selector, int xOffset, int yOffset) {
        this.selector          = selector.replaceAll("\\s+","");
        this.xOffset           = xOffset;
        this.yOffset           = yOffset;
        this.isSingleCellMatch = true;
    }

    /**
     * Gets whether this matcher matches the cell defined by x/y.
     * @param cells The existing cells.
     * @param x The absolute x position of the cell that the pattern is being applied to.
     * @param y The absolute y position of the cell that the pattern is being applied to.
     * @return Whether this matcher matches the cell defined by x/y.
     */
    public boolean isMatch(Cells cells, int x, int y) {
        if (this.isSingleCellMatch) {
            // Get the absolute positioned existing cell.
            Cell target = cells.get(x + this.xOffset, y + this.yOffset);

            return this.isCellMatch(target);
        } else {
            // Find the min/max of the x/y offsets to get the area.
            int minX = Math.min(this.xOffsetStart, this.xOffsetEnd);
            int maxX = Math.max(this.xOffsetStart, this.xOffsetEnd);
            int minY = Math.min(this.yOffsetStart, this.yOffsetEnd);
            int maxY = Math.max(this.yOffsetStart, this.yOffsetEnd);

            // Check that every cell in the area matches.
            for (int xPosition = minX; xPosition <= maxX; xPosition++) {
                for (int yPosition = minY; yPosition <= maxY; yPosition++) {
                    // Get the absolute positioned existing cell.
                    Cell target = cells.get(x + xPosition, y + yPosition);

                    if (!this.isCellMatch(target)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    /**
     * Freeze the matcher cells.
     * @param cells The existing cells.
     * @param x The absolute x position of the cell that the pattern is being applied to.
     * @param y The absolute y position of the cell that the pattern is being applied to.
     */
    public void freeze(Cells cells, int x, int y) {
        if (this.isSingleCellMatch) {
            // Freeze the matched cell.
            cells.get(x + this.xOffset, y + this.yOffset).setFrozen(true);
        } else {
            // Find the min/max of the x/y offsets to get the area.
            int minX = Math.min(this.xOffsetStart, this.xOffsetEnd);
            int maxX = Math.max(this.xOffsetStart, this.xOffsetEnd);
            int minY = Math.min(this.yOffsetStart, this.yOffsetEnd);
            int maxY = Math.max(this.yOffsetStart, this.yOffsetEnd);

            // Freeze every cell in the matched area.
            for (int xPosition = minX; xPosition <= maxX; xPosition++) {
                for (int yPosition = minY; yPosition <= maxY; yPosition++) {
                    cells.get(x + xPosition, y + yPosition).setFrozen(true);
                }
            }
        }
    }

    /**
     * Gets whether the given cell satisfies this matcher.
     * @param cell The cell.
     * @return Whether the given cell satisfies this matcher.
     */
    private boolean isCellMatch(Cell cell) {
        // MATCHING
        // Cell type 'EMPTY'
        // Cell attribute with type exists '[IsGoodCell]'
        // Cell attribute has given value '[IsGoodCell:true]'
        // Cell entity with name exists '(Chest)'
        // Cell entity with attribute exists '(Chest[Quality])'
        // Cell entity attribute has given value '(Chest[Quality:5])'
        // Pattern can be split with '|' and each section must be true.
        // ! Negates, (e.g. '!EMPTY|!GRASS' not a grass or empty cell)

        // No out-of-bounds cell can be treated as a matching cell.
        if (cell == Cells.OUT_OF_BOUNDS) {
            return false;
        }

        // No frozen cell can be treated as a matching cell.
        if (cell.isFrozen()) {
            return false;
        }

        // The type of the cell that exists at the specified position must be a match for the matching pattern cell.
        if (!this.selector.equalsIgnoreCase(cell.getType())) {
            return false;
        }

        return true;
    }

    /**
     * Deserialise a Matcher object from a JSON array.
     * The array will be structured like [xOffset, yOffset, selector] or [xOffsetStart, yOffsetStart, xOffsetEnd, yOffsetEnd, selector].
     * @param json The JSON array.
     * @return
     */
    public static Matcher fromJSON(JSONArray json) {
        if (json.length() == 3) {
            // We are matching on only a single cell position.
            return new Matcher(json.getString(2), json.getInt(0), json.getInt(1));
        } else if (json.length() == 5) {
            // We are matching on an area of cells.
            return new Matcher(json.getString(4), json.getInt(0), json.getInt(1), json.getInt(2), json.getInt(3));
        } else {
            throw new RuntimeException("wrong number of elements for a matcher array");
        }
    }
}
