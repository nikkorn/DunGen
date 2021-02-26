package dungen.pattern.selector;

import java.util.HashMap;
import java.util.Map;
import dungen.Cell;

public class Part {
    /**
     * Whether the part is negated.
     */
    private boolean isNegated = false;
    /**
     * The optional cell type.
     */
    private String cellType = null;
    /**
     * The optional cell attributes.
     */
    private HashMap<String, String> cellAttributes = new HashMap<String, String>();
    /**
     * The optional cell attributes.
     */
    private HashMap<String, HashMap<String, String>> cellEntities = new HashMap<String, HashMap<String, String>>();

    /**
     * Creates a new instance of the Part class.
     * @param part
     */
    public Part(String part) {
        if (part.isEmpty()) {
            throw new RuntimeException("selector part string cannot be empty");
        }

        // Parse our part string into some tokens.
        PartTokens tokens = PartTokens.parse(part);

        processTokens(tokens);
    }

    /**
     * Grab a mapping of attributes parsed from the provided part tokens.
     * @param tokens The part tokens.
     * @return A mapping of attributes parsed from the provided part tokens.
     */
    private HashMap<String, String> getAttributes(PartTokens tokens) {
        HashMap<String, String> attributes = new HashMap<String, String>();

        tokens.pop("[");

        boolean attributesRemain = true;

        while(attributesRemain) {
            // Get the name of the attribute.
            String name = tokens.next();
            String value = null;

            // Do we have an explicit value for the attribute? (name:value)
            if (tokens.peek().equals(":")) {
                tokens.pop(":");
                value = tokens.next();
            }

            attributes.put(name, value);

            // Check whether we have no more attributes to come.
            if (!tokens.peek().equals(",")) {
                attributesRemain = false;
            } else {
                tokens.pop(",");
            }
        }

        tokens.pop("]");

        return attributes;
    }

    /**
     * Gets whether the selector part matches the specified cell.
     * @param cell
     * @return Whether the selector part matches the specified cell.
     */
    public boolean matches(Cell cell) {
        // If the part has a cell type defined then the given cell type must match it.
        if (this.cellType != null && !cell.getType().equalsIgnoreCase(this.cellType)) {
            return false;
        }

        // Check that any cell attributes defined exist on the given cell.
        for (Map.Entry<String, String> entry : this.cellAttributes.entrySet()) {
            // Every cell attribute in the part must be present in the actual cell attributes.
            if (!cell.getAttributes().containsKey(entry.getKey())) {
                return false;
            }

            // Having just the attribute name in the part is valid, having a value also means that the value must match too.
            if (entry.getValue() != null && !cell.getAttributes().get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        // TODO Check entities.

        return true;
    }

    /**
     * Process the series of part tokens.
     * @param tokens
     */
    private void processTokens(PartTokens tokens) {
        // A part starting with '!' will be negated.
        if (tokens.peek().equals("!")) {
            this.isNegated = true;

            // Throw away the '!' token.
            tokens.next();

            // We must have something to be negated.
            if (!tokens.hasNext()) {
                throw new RuntimeException("'!' is not a valid selector part");
            }
        }

        // If the next token isn't '[' or '(' then it is a cell type.
        if (!tokens.peek().equals("[") && !tokens.peek().equals("(")) {
            this.cellType = tokens.next();
        }

        String currentEntity = null;

        while(tokens.hasNext()) {
            if (tokens.peek().equals("(")) {
                // Throw away the '(' token.
                tokens.next();

                // The next token must be the entity name.
                currentEntity = tokens.next();

                // Add the entity to our map of entity name to attributes.
                this.cellEntities.put(currentEntity, new HashMap<String, String>());
            } else if (tokens.peek().equals("[")) {
                // If the next token is '[' then we are grabbing attributes until ']'
                HashMap<String, String> attributes = getAttributes(tokens);

                // If we are not in an entity scope then these attributes must relate to the cell. If not, then they relate to the entity.
                if (currentEntity == null) {
                    this.cellAttributes = attributes;
                } else {
                    this.cellEntities.put(currentEntity, attributes);
                }
            } else if (tokens.peek().equals(")")) {
                // This closes the entity scope.
                currentEntity = null;
            } else {
                throw new RuntimeException("unexpected selector part token '" + tokens.peek() + "'");
            }
        }
    }
}
