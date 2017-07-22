/**
 * This version consumes 17n^2 memory vs. 10n^2 for max representative
 * implementation and probably has similar or worse performance
 */
public class Percolation2UF {

    private final int n;
    private final WeightedQuickUnionUF mainUf;
    private final WeightedQuickUnionUF isFullUf; // uf to support isFull queries
    private final boolean[] openSites;
    private final int topSink;
    private final int bottomSink;

    public Percolation2UF(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n should be > 1");
        this.n = n;
        this.topSink = 0;
        int size = n * n + 2; // node 0 and n*n+1 are virtual sink nodes
        this.bottomSink = size - 1;
        this.openSites = new boolean[size];
        openSites[topSink] = true;
        openSites[bottomSink] = true;
        mainUf = new WeightedQuickUnionUF(size);
        isFullUf = new WeightedQuickUnionUF(size - 1);
    }

    /**
     * Open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        final int idx = translateChecking(i, j);
        openSites[idx] = true;
        int[] neighbours = getOpenNeighbours(i, j);
        for (int other : neighbours) {
            if (other == -1)
                continue;
            mainUf.union(idx, other);
            if (other != bottomSink)
                isFullUf.union(idx, other);
        }
    }

    public boolean isOpen(int i, int j) {
        final int idx = translateChecking(i, j);
        return openSites[idx];
    }

    public boolean isFull(int i, int j) {
        final int idx = translateChecking(i, j);
        return openSites[idx] && isFullUf.connected(topSink, idx);
    }

    public boolean percolates() {
        return mainUf.connected(topSink, bottomSink);
    }

    private int[] getOpenNeighbours(int i, int j) {
        int[] result = new int[4];
        if (j > 1)
            result[0] = translateIfOpen(i, j - 1);
        else
            result[0] = -1;
        if (j < n)
            result[1] = translateIfOpen(i, j + 1);
        else
            result[1] = -1;
        if (i < n)
            result[2] = translateIfOpen(i + 1, j);
        else
            result[2] = bottomSink;
        if (i > 1)
            result[3] = translateIfOpen(i - 1, j);
        else
            result[3] = topSink;
        return result;
    }

    private int translateIfOpen(int i, int j) {
        int idx = translate(i, j);
        if (openSites[idx])
            return idx;
        return -1;
    }

    private int translateChecking(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            String msg = String.format(
                    "Index %d or %d is out of range [1; %d]", i, j, n);
            throw new IndexOutOfBoundsException(msg);
        }
        return translate(i, j);
    }

    private int translate(int i, int j) {
        return (i - 1) * n + j;
    }
}