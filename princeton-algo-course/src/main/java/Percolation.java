public class Percolation {

    private static final int TOP_SINK = 0;

    private final int n;
    private final WeightedQuickUnionUF uf;

    private final boolean[] openSites;
    private final boolean[] isConnectedToBottom;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n should be > 1");
        this.n = n;
        int size = n * n + 1; // node 0 is virtual sink node
        this.openSites = new boolean[size];
        this.openSites[TOP_SINK] = true;
        this.uf = new WeightedQuickUnionUF(size);
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
        boolean isConnected = isConnectedToBottom[idx];
        for (int other : getOpenNeighbours(i, j)) {
            if (other == -1)
                continue;
            if (isConnected) {
                uf.union(idx, other);
            } else {
                int rootOther = uf.find(other);
                isConnected = isConnected || isConnectedToBottom[rootOther];
                uf.union(idx, rootOther);
            }
        }
        if (isConnected) {
            isConnectedToBottom[uf.find(idx)] = true;
        }
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
            result[2] = -1;
        if (i > 1)
            result[3] = translateIfOpen(i - 1, j);
        else
            result[3] = TOP_SINK;
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
            throw new IndexOutOfBoundsException();
        }
        return translate(i, j);
    }

    private int translate(int i, int j) {
        return (i - 1) * n + j;
    }
}