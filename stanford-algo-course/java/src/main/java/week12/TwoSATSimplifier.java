package week12;

import com.carrotsearch.hppc.IntArrayList;


/**
 * Simplify 2-SAT problem by removing unused variables and clauses that can be trivially solved by assigning values 
 * to variables that appear without counterpart negated variables.
 */

class TwoSATSimplifier {
	private static final int SIMPLIFICATION_CUTOFF = 1;//stop simplification once progress is smaller than this value

	private final TwoSATProblem problem;

	TwoSATSimplifier(TwoSATProblem problem) {
		this.problem = problem;
	}

	public TwoSATProblem simplify() {
		TwoSATProblem prev = problem;
		TwoSATProblem result = null;
		do {
			result = simplifyOnce(prev);
			if (result.getVariableCount() > prev.getVariableCount()-SIMPLIFICATION_CUTOFF) break;
			prev = result;
		} while(true);
		return result;
	}

	private TwoSATProblem simplifyOnce(TwoSATProblem problem) {
		int[] variableUsage = countVariableUsage(problem);
		int[][] remapping = computeRemapping(problem, variableUsage);
		int newN = remapping[1][0];
		IntArrayList newLeft = new IntArrayList();
		IntArrayList newRight = new IntArrayList();
		int[] dict = remapping[0];
		for (int i = 0; i < problem.getClauseCount(); i++) {
			int left = map(problem.leftVars[i], dict);
			int right = map(problem.rightVars[i], dict);
			if (left == 0 || right == 0) continue;
			newLeft.add(left);
			newRight.add(right);
		}
		return new TwoSATProblem(newN, newLeft.toArray(), newRight.toArray());
	}

	private int map(int v, int[] dict) {
		int newIdx = dict[Math.abs(v)];
		return v < 0 ? -newIdx : newIdx;
	}

	private int[][] computeRemapping(TwoSATProblem problem, int[] variableUsage) {
		int[][] result = new int[2][];
		int[] remapping = new int[problem.getVariableCount()+1];
		int n = problem.getVariableCount();
		int newVarIdx = 1;
		for (int i = 1; i <= n; i++) {
			int negUsage = variableUsage[-i+n];
			int posUsage = variableUsage[i+n];
			if (negUsage == 0 || posUsage == 0) {
				remapping[i] = 0;//this variable can be removed together with clause where it appears
			} else {
				remapping[i] = newVarIdx;//new index of variable that should be kept
				newVarIdx++;
			}
		}
		result[0] = remapping;
		result[1] = new int[] {newVarIdx - 1};
		return result;
	}

	int[] countVariableUsage(TwoSATProblem problem) {
		int n = problem.getVariableCount();
		int[] counts = new int[n*2+1];
		for (int i = 0; i < problem.getClauseCount(); i++) {
			int lvar = problem.leftVars[i]+n;
			counts[lvar] = counts[lvar] + 1;
			int rvar = problem.rightVars[i]+n;
			counts[rvar] = counts[rvar] + 1;
		}
		return counts;
	}
}