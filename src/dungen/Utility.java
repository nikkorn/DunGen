package dungen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Utility methods.
 */
public class Utility {

	/**
	 * Get the first argument that is not null.
	 * @param params The parameters.
	 * @return The first argument that is not null.
	 */
	public static <T> T coalesce(T... params) {
	    for (T param : params)
	        if (param != null)
	            return param;
	    return null;
	}
	
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
