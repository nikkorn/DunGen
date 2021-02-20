package dungen.pattern;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * An applicable pattern.
 */
public class Pattern {
	/**
	 * The pattern name.
	 */
	private String name;
	/**
	 * The minimum number of times the pattern is to be applied.
	 */
	private int minimumApplications;
	/**
	 * The maximum number of times the pattern is to be applied.
	 */
	private int maximumApplications;
	/**
	 * The percentage chance between 0 and 1 of the pattern being applied.
	 */
	private double applicationChance;
	/**
	 * The option that defines if we are freezing any spaces on pattern application.
	 */
	private PatternFreeze freeze;
	/**
	 * The pattern sequences.
	 */
	private ArrayList<Sequence> sequences;
	
	/**
	 * Creates a new instance of the Pattern calls.
	 * @param name
	 * @param minimumApplications
	 * @param maximumApplications
	 * @param applicationChance
	 * @param freeze
	 * @param sequences
	 */
	private Pattern(String name, int minimumApplications, int maximumApplications, double applicationChance, PatternFreeze freeze, ArrayList<Sequence> sequences) {
		this.name                = name;
		this.minimumApplications = minimumApplications;
		this.maximumApplications = maximumApplications;
		this.applicationChance   = applicationChance;
		this.freeze              = freeze;
		this.sequences           = sequences;
	}
	
	public String getName() {
		return name;
	}
	
	public double getApplicationChance() {
		return applicationChance;
	}

	public int getMaximumApplications() {
		return maximumApplications;
	}

	public int getMinimumApplications() {
		return minimumApplications;
	}

	public PatternFreeze getFreeze() {
		return freeze;
	}
	
	public ArrayList<Sequence> getSequences() {
		return sequences;
	}
	
	/**
	 * Deserialise a Pattern object from a JSON object.
	 * @param patternJson The JSON object.
	 * @return
	 */
	public static Pattern fromJSON(JSONObject patternJson) {
		String name                 = patternJson.getString("name");
		Integer minimumApplications = patternJson.optInt("min", 1);
		Integer maximumApplications = patternJson.optInt("max", 1);
		double applicationChance    = patternJson.optDouble("chance", 1);
		PatternFreeze freeze        = PatternFreeze.valueOf(patternJson.optString("freeze", "none").toUpperCase());
		
		ArrayList<Sequence> sequences = new ArrayList<Sequence>();
		
		// Determine whether the match/apply cells are defined here or as ticketed options.
		if (patternJson.has("matches")) {
			// The sequence properties are part of the pattern object itself.
			sequences.add(Sequence.fromJSON(patternJson));
		} else if (patternJson.has("options")) {
			// There are multiple sets or match/apply options to select from.
			for (int optionIndex = 0; optionIndex < patternJson.getJSONArray("options").length(); optionIndex++) {
				sequences.add(Sequence.fromJSON(patternJson.getJSONArray("options").getJSONObject(optionIndex)));
			}
		} else {
			throw new RuntimeException("no 'matches' or 'options' property for pattern '" + name + "'");
		}
		
		return new Pattern(name, minimumApplications, maximumApplications, applicationChance, freeze, sequences);
	}
}
