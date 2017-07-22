package week1;

import java.util.Random;


public class Percolation {

    private static final int TOP_SINK = 0;

    private final int n;
    private final int nSquared;
    
    private final UF uf;

    private final boolean[] openSites;
    private final boolean[] isConnectedToBottom;


    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n should be > 1");
        this.n = n;
        this.nSquared = n * n;
        int size = nSquared + 1; // node 0 is virtual sink node
        this.openSites = new boolean[size];
        this.openSites[TOP_SINK] = true;
        this.uf = new UF(size);
        this.isConnectedToBottom = new boolean[size];
        for (int i = size - n; i < size; i++) {
            isConnectedToBottom[i] = true;
        }
    }

    /**
     * Open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        final int idx = translateChecking(i, j);
        if (openSites[idx]) return;
        openSites[idx] = true;
        int rootIdx = idx;
        boolean isConnected = isConnectedToBottom[rootIdx];
        for (int other : getOpenNeighbours(i, j)) {
            if (other == -1)
                continue;
            if (isConnected) {
                rootIdx = uf.unionLeftRoot(rootIdx, other);
            } else {
                int rootOther = uf.find(other);
                isConnected = isConnected || isConnectedToBottom[rootOther];
                rootIdx = uf.unionRoots(rootIdx, rootOther);
            }
        }
        isConnectedToBottom[rootIdx] = isConnected;
    }

    public boolean isOpen(int i, int j) {
        final int idx = translateChecking(i, j);
        return openSites[idx];
    }

    public boolean isFull(int i, int j) {
        final int idx = translateChecking(i, j);
        return openSites[idx] && uf.connected(TOP_SINK, idx);
    }

    public boolean percolates() {
        return isConnectedToBottom[uf.find(TOP_SINK)];
    }

    private int[] getOpenNeighbours(int i, int j) {
        int[] result = new int[4];
        result[0] = j > 1 ? translateIfOpen(i, j - 1) : -1;
        result[1] = j < n ? translateIfOpen(i, j + 1) : -1;
        result[2] = i < n ? translateIfOpen(i + 1, j) : -1;
        result[3] = i > 1 ? translateIfOpen(i - 1, j) : TOP_SINK;
        return result;
    }

    private int translateIfOpen(int i, int j) {
        int idx = translate(i, j);
        return openSites[idx] ? idx : -1;
    }

    private int translateChecking(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        return translate(i, j);
    }

    private int translate(int i, int j) {
        return (i - 1) * n + j;
    }

    private int getOpenSitesCount() {
        int result = 0;
        for (boolean isOpen : openSites) {
            if (isOpen) result++;
        }
        return result;
    }
    
    private Random random = new Random();
    public int[] getRandomBlockedSite() {
        while (true) {
            int idx = random.nextInt(nSquared) + 1;
            if (!openSites[idx]) return translateBack(idx);
        }
    }
    
    private int[] translateBack(int idx) {
        int i = (idx - 1) / n + 1;
        int j = (idx - 1) % n + 1;
        return new int[] { i, j };
    }

    public double getThreshold() {
        int openCount = getOpenSitesCount();
        return (double) openCount / (double) (nSquared);
    }
}