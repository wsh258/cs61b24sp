import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] grid ;
    WeightedQuickUnionUF unionGrid;
    WeightedQuickUnionUF antiBackWash;

    private int size; int top; int down;
    private int openSiteCount = 0;
    public Percolation(int N) {
        // TODO: Fill in this constructor. create N-by-N grid, with all sites initially blocked
        if (N < 0) {
            throw new IllegalArgumentException("Input must be non-negative!");
        }
        size = N;
        top = N * N;//sentinel head
        down = top + 1;//sentinel last
        grid = new boolean[N][N];
        unionGrid = new WeightedQuickUnionUF(N * N + 2);
        antiBackWash = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.   open the site (row, col) if it is not open already
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSiteCount++;

            if (row == 0) {
                unionGrid.union(rowAndColToNum(row, col), top);
                antiBackWash.union(rowAndColToNum(row, col), top);
            }
            if (row == size - 1) {
                unionGrid.union(rowAndColToNum(row, col), down);
            }
            unionNextToMe(row, col,unionGrid);
            unionNextToMe(row, col,antiBackWash);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.   is the site (row, col) open?
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.   is the site (row, col) full?
        return antiBackWash.connected(rowAndColToNum(row,col),top);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method. number of open sites
        return openSiteCount;
    }

    public boolean percolates() {
        // TODO: Fill in this method.  does the system percolate?
        return unionGrid.connected(top,down);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    // N*N是顶 再加一是底部
    private int rowAndColToNum (int row,int col) {
        return row * size + col;
    }
    private void unionNextToMe (int row, int col,WeightedQuickUnionUF WQ){
        if (row - 1 >= 0 && grid[row-1][col]) {
            WQ.union(rowAndColToNum(row, col), rowAndColToNum(row - 1, col));
        }
        if (row + 1 < size && grid[row+1][col]) {
            WQ.union(rowAndColToNum(row, col), rowAndColToNum(row + 1, col));
        }
        if (col - 1 >= 0 && grid[row][col-1]) {
            WQ.union(rowAndColToNum(row, col), rowAndColToNum(row, col - 1));
        }
        if (col + 1 < size && grid[row][col+1]) {
            WQ.union(rowAndColToNum(row, col), rowAndColToNum(row, col + 1));
        }
    }
}
