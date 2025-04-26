import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] grid ;
    private boolean sentinalFirst = true;
    private boolean sentinalLast = true;
    WeightedQuickUnionUF unionGrid;
    public Percolation(int N) {
        // TODO: Fill in this constructor. create N-by-N grid, with all sites initially blocked
        if (N < 0) {
            throw new IllegalArgumentException("Input must be non-negative!");
        }
        grid = new boolean[N][N];
        unionGrid = new WeightedQuickUnionUF(N*N+2);
        for (int i = 0;i < N;i++) {
            unionGrid.union(i,N*N);
        }
        for (int i = 0;i < N;i++) {
            unionGrid.union(i,N*N);
        }

    }

    public void open(int row, int col) {
        // TODO: Fill in this method.   open the site (row, col) if it is not open already
        grid[row][col] = true;
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.   is the site (row, col) open?
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.   is the site (row, col) full?
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method. number of open sites
        return 0;
    }

    public boolean percolates() {
        // TODO: Fill in this method.  does the system percolate?
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
