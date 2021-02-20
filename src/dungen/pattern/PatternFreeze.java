package dungen.pattern;

/**
 * Enumeration of PatternFreeze types.
 * This defines if we are freezing any spaces on pattern application.
 * A frozen space cannot be overwritten by any other pattern applications.
 */
public enum PatternFreeze {
	/**
	 * No spaces are frozen.
	 */
	NONE,
	
	/**
	 * Any spaces included in the matched array will be frozen.
	 */
	ON_MATCH,
	
	/**
	 * Any spaces set as part of the pattern application will be frozen.
	 */
	ON_SET,
	
	/**
	 * Any spaces set as part of the pattern application and any included in the matched array will be frozen.
	 */
	ON_BOTH
}
