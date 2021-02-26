package dungen.pattern.selector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A queue of raw selector part tokens.
 */
public class PartTokens {
    /**
     * The list of tokens.
     */
    protected ArrayList<String> tokens;

    /**
     * Creates a new instance of the Tokens class.
     * @param tokens The raw string tokens.
     */
    private PartTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Get whether the queue contains a token.
     * @return Whether the queue contains a token.
     */
    public boolean hasNext() {
        return this.tokens.size() > 0;
    }

    /**
     * Get the size of the queue.
     * @return The size of the queue.
     */
    public int size() {
        return this.tokens.size();
    }

    /**
     * Gets the next token from the queue.
     * @return The next token from the queue.
     */
    public String next() {
        return this.tokens.remove(0);
    }

    /**
     * Peeks at the next token from the queue, or null if none exist.
     * @return The next token from the queue, or null if none exist.
     */
    public String peek() {
        return this.tokens.isEmpty() ? null : this.tokens.get(0);
    }

    /**
     * Gets the count of tokens that match the given token.
     * @param token The token to count.
     * @return The count of tokens that match the given token.
     */
    public int countOf(String token) {
        int count = 0;
        for (String current : this.tokens) {
            if (token.trim().toUpperCase().equals(current.toUpperCase())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Pop the next raw token off of the queue and throw an error if it wasn't the expected one.
     * @param expected An string that we expect the next popped token to match.
     * @returns The popped token.
     */
    public String pop(String expected) {
        // We were expecting another token.
        if (!this.hasNext()) {
            throw new RuntimeException("expected token.");
        }

        // If an expected token was defined, was it the expected one?
        if (!expected.trim().toUpperCase().equals(this.peek().toUpperCase())) {
            throw new RuntimeException("token to pop is not the expected one.");
        }

        // Pop and return the expected token.
        return this.next();
    }

    public static PartTokens parse(String part) {
        String[] rawTokens = part.split("((?<=[\\[\\]()!:,])|(?=[\\[\\]()!:,]))");

        return new PartTokens(new ArrayList<>(Arrays.asList(rawTokens)));
    }
}
