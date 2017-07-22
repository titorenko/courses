package week4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.In;
import common.MinPQ;
import common.StdOut;

public class Solver {
    
    private static class SolvabitilyCheckerFactory {
        private static Map<Integer, EndgameDictionary> dictionaries = new HashMap<>();
        
        static SolvabitilyChecker getChecker(int n) {
            if (n > 4) return SolvabitilyChecker.NON_DICTIONARY_BASED;
            if (!dictionaries.containsKey(n)) {
                dictionaries.put(n, init(n));
            }
            System.out.println("n "+n+", "+dictionaries.get(n).size());
            return new DictionaryBasedSolvabilityChecker(dictionaries.get(n));
        }

        static private EndgameDictionary init(int n) {
            if (n==3) return new EndgameDictionary(16, n);
            return new EndgameDictionary(15, n);
        }
    }
    
    private static class DictionaryBasedSolvabilityChecker implements SolvabitilyChecker {

        private EndgameDictionary dict;
        private int bestMoves;
        private boolean foundSomeSolution;

        public DictionaryBasedSolvabilityChecker(EndgameDictionary dict) {
            this.dict = dict;
            this.bestMoves = Integer.MAX_VALUE;
        }

        @Override
        public boolean isNonSolvable(SearchNode partialSolution) {
            if (!foundSomeSolution) { // this only helps very slightly
                if (partialSolution.board.manhattan() <= dict.depth) {
                    int movesToSolve = dict.movesToSolve(partialSolution);
                    nLookups++;
                    if (movesToSolve == -1) return false;
                    bestMoves = movesToSolve +partialSolution.nMoves;
                    foundSomeSolution = true;
                } 
                return false;
            } else {
                int movesToSolve = dict.movesToSolve(partialSolution);
                nLookups++;
                if (movesToSolve == -1) {
                    return (bestMoves - partialSolution.nMoves) <= dict.depth;
                }
                int moves = movesToSolve +partialSolution.nMoves;
                if (moves< bestMoves) {
                    bestMoves = moves;
                }
                return moves< bestMoves;
            }
        }
    }
    
    private static interface SolvabitilyChecker {
        static SolvabitilyChecker NON_DICTIONARY_BASED = new SolvabitilyChecker() {
            @Override
            public boolean isNonSolvable(SearchNode node) {
                return false;
            }
        };
        boolean isNonSolvable(SearchNode partialSolution);
    }
    
    /** contains boards close to final solution*/
    private static class EndgameDictionary {
        
        private final int depth;
        private final int n;
        private final Map<SearchNode, SearchNode> dict;

        private EndgameDictionary(int depth, int n) {
            this.depth = depth;
            this.n = n;
            this.dict = buildDictionary();
        }
        
        public int size() {
            return dict.size();
        }

        public int movesToSolve(SearchNode node) {
            SearchNode dictNode = dict.get(node);
            return dictNode == null ? -1: dictNode.nMoves;
            
        }

        private Map<SearchNode, SearchNode> buildDictionary() {
            SearchNode solutionBoard = solutionNode(n);
            Map<SearchNode, SearchNode> endgameTable = new HashMap<>();
            Deque<SearchNode> q = new ArrayDeque<>();
            q.add(solutionBoard);
            while(!q.isEmpty()) {
                SearchNode node = q.poll();
                if (node.nMoves > depth) break;
                if(endgameTable.containsKey(node)) continue;
                endgameTable.put(node, node);
                for (SearchNode neighbor : node.neighbours()) {
                    q.add(neighbor);
                }
            }
            return endgameTable;
        }
        
        private SearchNode solutionNode(int n) {
            int[][] blocks = new int[n][];
            int value = 1;
            for (int i = 0; i < n; i++) {
                blocks[i] = new int[n];
                for (int j = 0; j < blocks.length; j++) {
                    blocks[i][j] = value == n*n ? 0 : value;
                    value++;
                }
            }
            return SearchNode.initial(new Board(blocks));
        }
    }
    
    private final Board initial;
    private SearchNode solution;
    
    public static int nFiltered = 0;
    public static int nInserts = 0;
    public static int nLookups = 0;
    public static int maxQ = 0;
    public static int nDelMin = 0;
    public static int maxmove = 0;
    
    public Solver(Board initial) {
        this.initial = initial;
        MinPQ<SearchNode> q1 = new MinPQ<>(initial.dimension()*initial.dimension(), SearchNode.HEURISTIC_COMPARATOR);
        MinPQ<SearchNode> q2 = new MinPQ<>(initial.dimension()*initial.dimension(), SearchNode.HEURISTIC_COMPARATOR);
        
        q1.insert(SearchNode.initial(initial));
        q2.insert(SearchNode.initial(initial.twin()));//in case original problem is not solvable, explore twin problem. one of them is solvable. Can be checked explicitly via some inversion counting. API constraints.
        SolvabitilyChecker checker = SolvabitilyCheckerFactory.getChecker(initial.dimension());
        int iteration = 0;
        do {
            runSearchIteration(q1, checker);
            if ((iteration & 0b11111) == 0b11111) runSearchIteration(q2, SolvabitilyChecker.NON_DICTIONARY_BASED);//solvable problems occur more often
            iteration++;
        } while (solution == null);
    }


    private void runSearchIteration(MinPQ<SearchNode> q1, SolvabitilyChecker checker) {
        SearchNode node = q1.delMin();
        nDelMin++;
        if (checker.isNonSolvable(node)) return;
        if (node.board.isGoal()) {
            this.solution = node;
        } else {
            node.insertNextMoves(q1);
        }
        if (q1.size() > maxQ) maxQ = q1.size();
    }
    
    static class SearchNode {
        static Comparator<SearchNode> HEURISTIC_COMPARATOR = new Comparator<SearchNode>() {
            public int compare(SearchNode s1, SearchNode s2) {
                int diff = s1.score - s2.score;
                return diff == 0 ? s2.nMoves - s1.nMoves : diff;
            };
        };   
        
        final Board board;
        private final int nMoves;
        private final SearchNode prev;
        private final int score;
        
        private SearchNode(Board board, int nMoves, SearchNode prev) {
            this.board = board;
            this.nMoves = nMoves;
            this.prev = prev;
            final int manhattan = board.manhattan();
            this.score = nMoves + manhattan;
        }

        private void insertNextMoves(MinPQ<SearchNode> q1) {
            Iterable<Board> neighbors = board.neighbors();
            for (Board nb : neighbors) {
                if (prev == null || !prev.board.equals(nb)) {
                    q1.insert(next(nb));
                    nInserts++;
                }
            }
        }

        private List<SearchNode> neighbours() {
            List<SearchNode> result = new ArrayList<>(4);
            Iterable<Board> neighbors = board.neighbors();
            for (Board nb : neighbors) {
                if (prev == null || !prev.board.equals(nb)) {
                    result.add(next(nb));
                }
            }
            return result;
        }
 
        private SearchNode next(Board b) {
            return new SearchNode(b, nMoves+1, this);
        }

        private static SearchNode initial(Board initial) {
            return new SearchNode(initial, 0, null);
        }

        @Override
        public boolean equals(Object obj) {
            SearchNode other = (SearchNode) obj;
            return board.equals(other.board);
        }
        
        @Override
        public int hashCode() {
            return board./*toString().*/hashCode();//API constraints
        }

        private Iterable<Board> reconstruct() {
            Deque<Board> result = new ArrayDeque<>();
            SearchNode node = this; 
            while(node != null) {
                result.addFirst(node.board);
                node = node.prev;
            }
            return result;
        }
        
        public boolean startsWith(Board board) {
            SearchNode node = this; 
            while(node.prev != null) node = node.prev;
            return node.board == board;
        }
    }

    public boolean isSolvable() {
        return solution.startsWith(initial);
    }

    public int moves() {
        return isSolvable() ? solution.nMoves : -1;
    }

    public Iterable<Board> solution() {
        return isSolvable() ? solution.reconstruct() : null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        long start = System.currentTimeMillis();
        Solver solver = new Solver(initial);
        long duration = System.currentTimeMillis() - start;
        System.out.println("Solved in "+duration);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
