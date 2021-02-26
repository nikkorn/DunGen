package dungen.pattern;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * A pattern sequence of cell tests and applied cells.
 */
public class Sequence {
	/**
	 * The cells/cell areas that this sequence matches on.
	 */
	private ArrayList<Matcher> matchers;
	/**
	 * The cells that are applied when this sequence is matched.
	 */
	private ArrayList<ApplicationCell> applies;
	/**
	 * The optional number of tickets help by this sequence. Defaults to 1.
	 */
	private int tickets;
	
	/**
	 * Creates a new instance of the Sequence class.
	 * @param matchers The cells that this sequence matches on.
	 * @param applies The cells that are applied when this sequence is matched.
	 * @param tickets The optional number of tickets help by this sequence.
	 */
	private Sequence(ArrayList<Matcher> matchers, ArrayList<ApplicationCell> applies, int tickets) {
		this.matchers = matchers;
		this.applies  = applies;
		this.tickets  = tickets;
	}
	
	/**
	 * Gets the cells that this sequence matches on.
	 * @return The cells that this sequence matches on.
	 */
	public ArrayList<Matcher> getMatchers() {
		return matchers;
	}

	/**
	 * Gets the cells that are applied when this sequence is matched.
	 * @return The cells that are applied when this sequence is matched.
	 */
	public ArrayList<ApplicationCell> getApplyCells() {
		return applies;
	}
	
	/**
	 * Gets the optional number of tickets help by this sequence. Defaults to 1.
	 * @return The optional number of tickets help by this sequence. Defaults to 1.
	 */
	public int getTickets() {
		return tickets;
	}
	
	/**
	 * Deserialise a Sequence object from a JSON object.
	 * @param sequenceJson The JSON object.
	 * @return
	 */
	public static Sequence fromJSON(JSONObject sequenceJson) {
		// Parse the pattern matchers.
		ArrayList<Matcher> matchCells = new ArrayList<Matcher>();
		for (int cellIndex = 0; cellIndex < sequenceJson.getJSONArray("matches").length(); cellIndex++) {
			matchCells.add(Matcher.fromJSON(sequenceJson.getJSONArray("matches").getJSONArray(cellIndex)));
		}
		
		// Parse the pattern apply cells.
		ArrayList<ApplicationCell> applyCells = new ArrayList<ApplicationCell>();
		for (int cellIndex = 0; cellIndex < sequenceJson.getJSONArray("applies").length(); cellIndex++) {
			applyCells.add(ApplicationCell.fromJSON(sequenceJson.getJSONArray("applies").getJSONArray(cellIndex)));
		}
		
		// Parse the number of tickets for the sequence.
		int tickets = sequenceJson.has("tickets") ? sequenceJson.getInt("tickets") : 1;
		
		return new Sequence(matchCells, applyCells, tickets);
	}
}
