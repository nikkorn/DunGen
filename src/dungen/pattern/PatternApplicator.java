package dungen.pattern;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import dungen.Cells;
import dungen.Configuration;
import dungen.lotto.Lotto;

/**
 * Applicator of cell patterns to cell collections.
 */
public class PatternApplicator {
	/**
	 * Apply the given pattern to the cell collection.
	 * @param pattern The pattern to apply.
	 * @param cells The cell collection.
	 * @param config The generator configuration.
	 * @param rng The RNG to use.
	 */
	public static void apply(Pattern pattern, Cells cells, Configuration config, Random rng) {		
		// If there is a chance that the pattern should not be applied we should check now and skip the pattern if the chance fails.
		if (pattern.getApplicationChance() != 1 && rng.nextDouble() <= pattern.getApplicationChance()) {
			return;
		}
		
		// Pick how many patterns we are going to apply based on the pattern min/max values.
		int totalApplications = pattern.getMinimumApplications() + rng.nextInt(pattern.getMaximumApplications() - pattern.getMinimumApplications() + 1);
		
		// Keep track of how many applications were actually made for the current pattern.
		int applicationsMade = 0;
		
		// Apply the pattern randomly as many times as we have decided to.
		for (int count = 0; count < totalApplications; count++) {
			// Get a matching cell position.
			ApplicationPosition applicationPosition = findApplicationPosition(pattern, cells, config, rng);
			
			// Check whether we were able to actually find a valid position at which to apply a sequence of the current pattern.
			if (applicationPosition != null) {
				// Apply the sequence to the position at which it can be applied.
				applySequence(applicationPosition, cells, pattern.getFreeze());
				applicationsMade++;		
			}
		}
		
		// Check that we were able to meet at least the minimum number of applications required for the current pattern.
		if (applicationsMade < pattern.getMinimumApplications()) {
			throw new RuntimeException("could not apply pattern '" + pattern.getName() + "' the minimum number of times required");
		}
	}
	
	/**
	 * Finds a space at which the specified pattern can be applied, or null if no space exists.
	 * @return A space at which the specified pattern can be applied, or null if no space exists.
	 */
	private static ApplicationPosition findApplicationPosition(Pattern pattern, Cells cells, Configuration config, Random rng) {
		// Create a lotto to use in selecting a sequence based on ticket counts.
		Lotto<Sequence> sequenceLotto = new Lotto<Sequence>(rng);
		
		// Add each pattern sequence to our lotto.
		for (Sequence sequence : pattern.getSequences()) {
			sequenceLotto.add(sequence, sequence.getTickets());
		}
		
		// Select the winning sequence to use based on ticket counts.
		Sequence sequence = sequenceLotto.draw();
		
		// Create a list for every position in the dungeon where the sequence can be applied.
		ArrayList<ApplicationPosition> applicablePositions = new ArrayList<ApplicationPosition>();
		
		// Check this pattern against every space in the dungeon.
		for (int x = 0; x < config.width; x++) {
			for (int y = 0; y < config.height; y++) {
				// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
				if (doesSequenceApplyToSpace(sequence, cells, x, y)) {
					applicablePositions.add(new ApplicationPosition(sequence, new Point(x, y)));
				}
			}
		}
		
		// There may have been no applicable positions for the sequence.
		if (applicablePositions.isEmpty()) {
			return null;
		}
		
		// Return a random position at which we were able to apply the sequence.
		return applicablePositions.get(rng.nextInt(applicablePositions.size()));
	}
	
	/**
	 * Gets whether a sequence can be applied to a particular cell position.
	 * @param sequence
	 * @param cells
	 * @param x
	 * @param y
	 * @return
	 */
	private static boolean doesSequenceApplyToSpace(Sequence sequence, Cells cells, int x, int y) {
		// A sequence will apply to a cell if every match cell in the sequence 
		for (Matcher matcher : sequence.getMatchers()) {
			if (!matcher.isMatch(cells, x, y)) {
				return false;
			}
		}

		// Our pattern matched!
		return true;
	}
	
	/**
	 * Apply the given applicationPosition with associated sequence to a cells collection.
	 * @param applicationPosition
	 * @param cells
	 * @param freeze
	 */
	private static void applySequence(ApplicationPosition applicationPosition, Cells cells, PatternFreeze freeze) {
		// Apply the sequence application cells to our cells grid.
		for (ApplicationCell applyCell : applicationPosition.getSequence().getApplyCells()) {
			// Get the absolute x/y of the cell we are trying to set.
			int x = (int) (applicationPosition.getPosition().getX() + applyCell.getOffsetX());
			int y = (int) (applicationPosition.getPosition().getY() + applyCell.getOffsetY());
			
			// Set the apply cell.
			cells.set(applyCell.getType(), applyCell.getAttributes(), applyCell.getEntities(), x, y);
		}
		
		// Are we freezing our matched cells?
		if (freeze == PatternFreeze.ON_MATCH || freeze == PatternFreeze.ON_BOTH) {
			for (Matcher matcher : applicationPosition.getSequence().getMatchers()) {
				matcher.freeze(cells, (int)applicationPosition.getPosition().getX(), (int)applicationPosition.getPosition().getY());
			}
		}
		
		// Are we freezing our applied cells?
		if (freeze == PatternFreeze.ON_SET || freeze == PatternFreeze.ON_BOTH) {
			for (ApplicationCell applyCell : applicationPosition.getSequence().getApplyCells()) {
				// Get the absolute x/y of the cell we are trying to freeze.
				int x = (int) (applicationPosition.getPosition().getX() + applyCell.getOffsetX());
				int y = (int) (applicationPosition.getPosition().getY() + applyCell.getOffsetY());
				
				// Freeze the set cell.
				cells.get(x, y).setFrozen(true);
			}
		}
	}
}
