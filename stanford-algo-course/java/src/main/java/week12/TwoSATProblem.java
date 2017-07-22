package week12;

import com.google.common.base.Preconditions;

/**
 * Problem definition for 2-SAT 
 */
class TwoSATProblem {
	
	final int[] leftVars;
	final int[] rightVars;

	private int clauseIdx = 0;
	
	private final int n;
	private final int m;
	
	/** n - number of variables
		m - number of clauses */
	TwoSATProblem(int n, int m) {
		this.n = n;
		this.m = m;
		this.leftVars = new int[m];
		this.rightVars = new int[m];
	}

	TwoSATProblem(int n, int[] leftVars, int[] rightVars) {
		 Preconditions.checkArgument(leftVars.length == rightVars.length);
		 this.n = n;
		 this.leftVars = leftVars;
		 this.rightVars = rightVars;
		 this.m = leftVars.length;
	}

	/** if v1, v2 are negative it means that they are negations,
		otherwise they are normal variable references*/ 
	void addClause(int v1, int v2) {
		leftVars[clauseIdx] = v1; 
		rightVars[clauseIdx] = v2;
		clauseIdx++;
	}
	
	int getVariableCount() {
		return n;
	}

	int getClauseCount() {
		return m;
	}
	
	@Override
	public String toString() {
		return "2-SAT problem with "+n+" vars and "+m+" clauses";
	}
}