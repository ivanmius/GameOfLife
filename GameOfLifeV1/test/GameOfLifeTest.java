import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Testing the game of life (V1).
 *
 * @author Ivan Stoychev
 *
 */
public class GameOfLifeTest {

    /**
     * Import of printBoard.
     *
     * @param out
     *            stream out
     * @param grid
     *            the game board
     */
    private void printBoard(SimpleWriter out, boolean[][] grid) {
        GameOfLifeV1.printBoard(out, grid);
    }

    /**
     * Import of countNeighbors.
     *
     * @param grid
     *            the game board
     * @param row
     *            the row of the cell
     * @param column
     *            the column of the cell
     * @return the number of neighbors the cell has
     */
    private int countNeighbors(boolean[][] grid, int row, int column) {
        return GameOfLifeV1.countNeighbors(grid, row, column);
    }

    /**
     * Imports of tickGame.
     *
     * @param grid
     *            the game board
     */
    private void tickGame(boolean[][] grid) {
        GameOfLifeV1.tickGame(grid);
    }

    /**
     * Imports a beacon.
     */
    @Test
    public final void testPrintBoard() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        SimpleWriter out = new SimpleWriter1L();
        this.printBoard(out, grid);
    }

    /**
     * Testing [0, 0] of initial beacon.
     */
    @Test
    public final void testCountNeighbors_0_0() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        int row = 0;
        int column = 0;
        int neighbors = this.countNeighbors(grid, row, column);
        assertEquals(1, neighbors);
    }

    /**
     * Testing [2, 2] of initial beacon.
     */
    @Test
    public final void testCountNeighbors_2_2() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        int row = 2;
        int column = 2;
        int neighbors = this.countNeighbors(grid, row, column);
        assertEquals(4, neighbors);
    }

    /**
     * Testing [2, 3] of initial beacon.
     */
    @Test
    public final void testCountNeighbors_2_3() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        int row = 2;
        int column = 3;
        int neighbors = this.countNeighbors(grid, row, column);
        assertEquals(4, neighbors);
    }

    /**
     * Testing [4, 4] of initial beacon.
     */
    @Test
    public final void testCountNeighbors_4_4() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        int row = 4;
        int column = 4;
        int neighbors = this.countNeighbors(grid, row, column);
        assertEquals(3, neighbors);
    }

    /**
     * Testing [3, 3] of initial beacon.
     */
    @Test
    public final void testCountNeighbors_3_3() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, true, true, false, false, false },
                { false, true, true, false, false, false },
                { false, false, false, true, true, false },
                { false, false, false, true, true, false },
                { false, false, false, false, false, false } };
        int row = 3;
        int column = 3;
        int neighbors = this.countNeighbors(grid, row, column);
        assertEquals(4, neighbors);
    }

    /**
     * Tests a blinker (period 2) update.
     */
    @Test
    public final void testTickGame_blinker() {
        boolean[][] grid = { { false, false, false, false, false, false },
                { false, false, true, false, false, false },
                { false, false, true, false, false, false },
                { false, false, true, false, false, false },
                { false, false, false, false, false, false },
                { false, false, false, false, false, false } };
        final boolean[][] result = {
                { false, false, false, false, false, false },
                { false, false, false, false, false, false },
                { false, true, true, true, false, false },
                { false, false, false, false, false, false },
                { false, false, false, false, false, false },
                { false, false, false, false, false, false } };
        this.tickGame(grid);
        assertEquals(grid, result);
    }
}
