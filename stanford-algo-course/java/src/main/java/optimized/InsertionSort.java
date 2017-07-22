package optimized;

public class InsertionSort {
	
    public static void sort(final int[] input) {
		sort(input, 0, input.length);
	}
	
	static void sort(final int[] input, final int start, final int end) {
		for (int i = start+1; i < end; i++) {
			final int x = input[i];
			int j = i;
			while(j > start && input[j-1] > x) {
				input[j] = input[j-1];
				j--;
			}
			input[j] = x;
		}
	}
}
