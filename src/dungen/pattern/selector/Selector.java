package dungen.pattern.selector;

import java.util.ArrayList;
import java.util.HashMap;
import dungen.Cell;

public class Selector {
    /**
     * The cached selector instances, keyed on the unprocessed selector string.
     */
    private static HashMap<String, Selector> cached;
    /**
     * The parts that make up the selector, separated by '|' in the unprocessed string.
     */
    private ArrayList<Part> parts = new ArrayList<Part>();

    static {
        cached = new HashMap<String, Selector>();
    }

    /**
     * Creates a new instance of the Selector class.
     * @param selector The unprocessed selector string.
     */
    private Selector(String selector) {
        if (selector == null || selector.isEmpty()) {
            throw new RuntimeException("selector string cannot be empty or null");
        }

        // Split the selector at '|', creating a new part instance for each value.
        for (String part : selector.split("\\|")) {
            this.parts.add(new Part(part));
        }
    }

    /**
     * Gets whether the provided cell satisfies the selector.
     * @param cell The cell.
     * @return Whether the provided cell satisfies the selector.
     */
    public boolean matches(Cell cell) {
        // Each part of the selector needs to match the cell.
        for (Part part : this.parts) {
            if (!part.matches(cell)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Creates a Selector instance based on an unprocessed selector string.
     * @param selector An unprocessed selector string.
     * @return A Selector instance based on an unprocessed selector string.
     */
    public static Selector create(String selector) {
        selector = selector.replaceAll("\\s+","");

        // If we have already cached this selector then just return it.
        if (cached.containsKey(selector)) {
            return cached.get(selector);
        }

        Selector instance = new Selector(selector);

        // Cache this selector.
        cached.put(selector, instance);

        return instance;
    }
}
