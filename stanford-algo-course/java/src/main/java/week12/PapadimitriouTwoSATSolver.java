package week12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import common.ExtraMath;

/** 
 * Randomised 2-SAT solver that can give incorrect "false" answer to 2-SAT
 * solvability with probability 1 - 1/n 
 */
public class PapadimitriouTwoSATSolver {

	private final TwoSATProblem problem;
	private final int n;
	private final Random rnd = new Random();
	private List<Clause>[] dict;//clauses indexed by variable index that appears in the clause, negation is ignored
	private List<Clause> allClauses;
	
	private static class Clause {
		
		final int v1;
		final int v2;
		final int v1abs;
		final int v2abs;
		boolean leftValue;
		boolean rightValue;
		boolean value;
		
		Clause(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
			this.v1abs = Math.abs(v1);
			this.v2abs = Math.abs(v2);
		}
		
		boolean eval(boolean[] values) {
			this.leftValue = values[v1abs];
			this.rightValue = values[v2abs];
			if (v1 < 0) leftValue = !leftValue;
			if (v2 < 0) rightValue = !rightValue;
			value = leftValue || rightValue;
			return value;
		}

		boolean flip(int toFlip) {
			if (v1abs == toFlip) {
				leftValue = !leftValue;
			} else {
				rightValue = !rightValue;	
			}
			value = leftValue || rightValue;
			return value;
		}
	}

	PapadimitriouTwoSATSolver(TwoSATProblem problem) {
		problem = new TwoSATSimplifier(problem).simplify();
		this.problem = problem;
		this.n = this.problem.getVariableCount();
		initClauses();
	}
	
	@SuppressWarnings("unchecked")
	private void initClauses() {
		this.dict = (List<Clause>[]) new List[problem.getVariableCount()+1];
		for (int i = 0; i < dict.length; i++) {
			dict[i] = new ArrayList<>();
		}
		this.allClauses = new ArrayList<>();
		for (int i = 0; i < problem.getClauseCount(); i++) {
			int v1 = problem.leftVars[i];
			int v2 = problem.rightVars[i];
			Clause clause = new Clause(v1, v2);
			allClauses.add(clause);
			dict[clause.v1abs].add(clause);
			dict[clause.v2abs].add(clause);
		}
	}

	boolean isSolvable() {
		if (n == 0) return true;
		int nRepeats = ExtraMath.log2(n);
		//System.out.println("Performing: "+nRepeats+" cycles of "+(2L * n * n)+" random flips");
		for (int r = 0; r < nRepeats; r++) {
			if (findSolution()) return true;
		}
		return false;
	}
	
	/**
	 * Randomly flip bits in failing clauses.
	 * Starting from random initial state, randomly walk into local minimum using local improvements.
	 * Random walk reaches target state that is n steps far away in ~ n^2 steps.
	 * 
	 * @return true if solution was found
	 */
	boolean findSolution() {
		long nImprovements = 2L * n * n;
		List<Clause> failingClauses = evalInitialCandidate();
		while (nImprovements > 0) {
			int nImproved = improvementCycle(failingClauses);
			if (nImproved == 0) {
				return true;
			}
			nImprovements -= nImproved;
		}
		return false;
	}
	
	private int improvementCycle(List<Clause> failingClauses) {
		int nImprovements = 0;
		List<Clause> newFailingClauses = new ArrayList<>();
		for (Iterator<Clause> it = failingClauses.iterator(); it.hasNext();) {
			Clause c = it.next();
			if (c.value) {
				it.remove();
			} else {
				improve(c, newFailingClauses);
				nImprovements++;
			}			
		}
		failingClauses.addAll(newFailingClauses);
		return nImprovements;
	}

	private void improve(Clause c, List<Clause> newFailingClauses) {
		boolean isLeft = rnd.nextBoolean();
		int	toFlip = isLeft ? c.v1abs : c.v2abs;
		flip(toFlip, newFailingClauses);
	}

	private void flip(int toFlip, List<Clause> newFailingClauses) {
		for (Clause clauseToReeval : dict[toFlip]) {
			if (!clauseToReeval.flip(toFlip)) {
				newFailingClauses.add(clauseToReeval);
			}
		}
	}
	
	private List<Clause> evalInitialCandidate() {
		boolean[] assignment = new boolean[n+1];
		for (int i = 0; i < assignment.length; i++) {
			assignment[i] = rnd.nextBoolean();
		}
		List<Clause> failingClauses = new LinkedList<>();
		for (Clause c : allClauses) {
			if (!c.eval(assignment)) {
				failingClauses.add(c);
			}
		}
		return failingClauses;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		TwoSATProblem[] assignments = InputParser.assignments().toArray(TwoSATProblem[]::new);
		long duration = System.currentTimeMillis() - start;
		System.out.println("Parsed input in "+duration+" millis");
		start = System.currentTimeMillis();
		IntStream.range(0, 6).forEach(i -> {
			boolean isSolvable = new PapadimitriouTwoSATSolver(assignments[i]).isSolvable();
			System.out.println("Assignment "+i+": "+isSolvable);
		});
		duration = System.currentTimeMillis() - start;
		System.out.println("Total solution time: "+duration);
	}
}