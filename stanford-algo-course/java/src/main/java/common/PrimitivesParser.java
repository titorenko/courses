package common;

public class PrimitivesParser {
	
	private static final int RADIX = 10;

	/**
	 * Fast integer parse with minimal validation. Parsing will stop once non-digit character is encountered.
	 * 
	 * @param str - string
	 * @param from - parse from index
	 * @param to - parse to index
	 * @return result of parsing
	 */
	public static int parseInt(final String str, final int from, final int to) {
		int i=from;
		char firstChar = str.charAt(i);
		boolean isNegative = false;
		if (firstChar == '-') {
			isNegative = true;
			i++;
		}
		int result= parsePositiveInt(str, i, to);
		return isNegative ? result : -result;
	}

	/**
	 * Fast integer parse with minimal validation. Parsing will stop once non-digit character is encountered.
	 * Negative integer parsing is NOT supported.
	 * 
	 * @param str - string
	 * @param from - parse from index
	 * @param to - parse to index
	 * @return result of parsing
	 */
	public static int parsePositiveInt(final String str, final int from, final int to) {
		int i=from;
		int result= 0;
		while (i < to) {
			final char c = str.charAt(i++);
			if (c < '0') break;
			int digit = c - '0';
			result *= RADIX;
			result -= digit;
		}
		return -result;
	}
}