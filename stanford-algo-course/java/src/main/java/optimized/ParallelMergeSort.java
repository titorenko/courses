package optimized;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

class ParallelMergeSort extends MergeSort {
	private static final int PARALLELISM_CUTOFF = 30_000;
	
	@Override
	public void sort(int[] input) {
		int[] buffer = Arrays.copyOf(input, input.length);
		SortTask task = new SortTask(input, buffer, 0, input.length);
		task.invoke();
	}
	
	private class SortTask extends RecursiveTask<Void> {
		private static final long serialVersionUID = -4799271806880121163L;
		
		private final int[] input;
		private final int[] buffer;
		private final int start;
		private final int end;


		public SortTask(int[] input, int[] buffer, int start, int end) {
			this.input = input;
			this.buffer = buffer;
			this.start = start;
			this.end = end;
		}

		@Override
		protected Void compute() {
			if ((end - start) < PARALLELISM_CUTOFF) {
				mergeSort(input, start, end, buffer);
			} else {
				sortInParallel();
			}
			return null;
		}

		private void sortInParallel() {
			final int mid = (start + end) >>> 1;
			SortTask left = new SortTask(buffer, input, start, mid);
			left.fork();
			SortTask right = new SortTask(buffer, input, mid, end);
			right.compute();
			left.join();
			merge(buffer, start, mid, end, input);
		}
	}
}