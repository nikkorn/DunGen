package dungen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Utility methods.
 */
public class Utility {
	
	/**
	 * Get a random item from a list.
	 * @param list The list from which to pick a random item.
	 * @param random The RNG to use.
	 * @return A random item from a list.
	 */
	public static <T> T getRandomListItem(ArrayList<T> list, Random random) {
		return list.get(random.nextInt(list.size()));
	}
}
