import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * First attempt at game of life.
 *
 * @author Ivan Stoychev
 */
public final class GameOfLifeV1 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private GameOfLifeV1() {
        // no code needed here
    }

    /**
     * Size of the board.
     */
    private static final int BOARD_SIZE = 6;
    /**
     * The threshold for neighbors below which a cell dies.
     */
    private static final int CELL_DEATH_LOW = 2;
    /**
     * The threshold for neighbors above which a cell dies.
     */
    private static final int CELL_DEATH_HIGH = 3;
    /**
     * The length between board updates.
     */
    private static final int SLEEP = 750;

    /**
     * print the board using out and the grid.
     *
     * @param out
     *            the stream
     * @param grid
     *            the array of booleans that hold the game.
     */
    public static void printBoard(SimpleWriter out, boolean[][] grid) {
        out.print('\u2554');
        for (int i = 0; i < grid.length; i++) {
            out.print('\u2550');
        }
        out.println('\u2557');
        for (int i = 0; i < grid.length; i++) {
            boolean[] line = grid[i];
            out.print('\u2551');
            for (int j = 0; j < line.length; j++) {
                if (line[j]) {
                    out.print('\u2588');
                } else {
                    out.print("\u2591");
                }
            }
            out.print('\u2551');
            out.println();
        }
        out.print('\u255A');
        for (int i = 0; i < grid.length; i++) {
            out.print('\u2550');
        }
        out.println('\u255D');
    }

    /**
     * Applies the rules of Conway's Game of Life.
     *
     * @param grid
     *            The game board
     */
    public static void tickGame(boolean[][] grid) {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {

                newGrid[row][column] = grid[row][column];
                int neighbors = countNeighbors(grid, row, column);

                if (grid[row][column]) { // live cell
                    if (neighbors < CELL_DEATH_LOW
                            || neighbors > CELL_DEATH_HIGH) {
                        newGrid[row][column] = false;
                    }
                } else if (neighbors == CELL_DEATH_HIGH) { // dead cell
                    newGrid[row][column] = true;
                }
            }
        }
        updateBoard(grid, newGrid);
    }

    /**
     * Makes copies of all the boolean values from newGrid to oldGrid (i.e.
     * updates the game board).
     *
     * @param oldGrid
     *            The old game board
     * @param newGrid
     *            the new game board
     * @replaces oldGrid
     * @restores newGrid
     * @requires [size structure of @code oldGrid] = [size structure of @code
     *           newGrid]
     */
    public static void updateBoard(boolean[][] oldGrid, boolean[][] newGrid) {
        for (int row = 0; row < newGrid.length; row++) {
            for (int col = 0; col < newGrid[row].length; col++) {
                oldGrid[row][col] = newGrid[row][col];
            }
        }
    }

    /**
     * Counts the number of neighbors a cell has (up to 8).
     *
     * @param grid
     *            The game board
     * @param row
     *            The row of the cell in question
     * @param column
     *            The column of the cell in question
     * @return The number of neighbors
     */
    public static int countNeighbors(boolean[][] grid, int row, int column) {
        int[][] coordinates2Check = { { row - 1, column - 1 },
                { row - 1, column }, { row - 1, column + 1 },
                { row, column - 1 }, { row, column + 1 },
                { row + 1, column - 1 }, { row + 1, column },
                { row + 1, column + 1 } };
        int neighbors = 0;
        int checkRow, checkColumn;
        for (int i = 0; i < coordinates2Check.length; i++) {
            checkRow = coordinates2Check[i][0];
            checkColumn = coordinates2Check[i][1];

            // Wrap the row
            if (checkRow >= BOARD_SIZE) {
                checkRow = 0;
            } else if (checkRow < 0) {
                checkRow = BOARD_SIZE - 1;
            }

            // Wrap the column
            if (checkColumn >= BOARD_SIZE) {
                checkColumn = 0;
            } else if (checkColumn < 0) {
                checkColumn = BOARD_SIZE - 1;
            }

            if (grid[checkRow][checkColumn]) {
                neighbors++;
            }

        }
        return neighbors;
    }

    /**
     * Asks the user for starting condition among several provided options.
     *
     * @param out
     *            the output stream
     * @param in
     *            the input stream
     * @return the grid of the starting condition
     */
    public static boolean[][] askInput(SimpleWriter out, SimpleReader in) {
        final String[] optionNames = { "beacon", "blinker", "toad", "glider" };
        final boolean f = false;
        final boolean t = true;
        final boolean[][] beacon = { { f, f, f, f, f, f }, { f, t, t, f, f, f },
                { f, t, t, f, f, f }, { f, f, f, t, t, f },
                { f, f, f, t, t, f }, { f, f, f, f, f, f } };
        final boolean[][] blinker = { { f, f, f, f, f, f },
                { f, f, t, f, f, f }, { f, f, t, f, f, f },
                { f, f, t, f, f, f }, { f, f, f, f, f, f },
                { f, f, f, f, f, f } };
        final boolean[][] toad = { { f, f, f, f, f, f }, { f, f, t, t, t, f },
                { f, t, t, t, f, f }, { f, f, f, f, f, f },
                { f, f, f, f, f, f }, { f, f, f, f, f, f } };
        final boolean[][] glider = { { f, f, t, f, f, f }, { t, f, t, f, f, f },
                { f, t, t, f, f, f }, { f, f, f, f, f, f },
                { f, f, f, f, f, f }, { f, f, f, f, f, f } };
        boolean[][] grid = { { f, f, f, f, f, f }, { f, f, f, f, f, f },
                { f, f, f, f, f, f }, { f, f, f, f, f, f },
                { f, f, f, f, f, f }, { f, f, f, f, f, f } }; // empty grid
        final boolean[][][] options = { beacon, blinker, toad, glider };
        boolean validOption = false;

        out.println("WELCOME TO THE GAME OF LIFE (V1)");
        out.println("What starting condition would you like?");
        out.println("Options: beacon, blinker, toad, glider");
        out.print("Input: ");
        String userOption = in.nextLine();

        for (int i = 0; i < optionNames.length; i++) {
            if (optionNames[i].compareTo(userOption) == 0) {
                grid = options[i];
                validOption = true;
            }
        }

        while (!validOption) {
            out.print("Not a valid option! Try again: ");
            userOption = in.nextLine();

            for (int i = 0; i < optionNames.length; i++) {
                if (optionNames[i].compareTo(userOption) == 0) {
                    grid = options[i];
                    validOption = true;
                }
            }
        }
        return grid;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     * @throws InterruptedException
     *             uses this to "animate" in the output screen
     */
    public static void main(String[] args) throws InterruptedException {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        final int gameLength = 25;

        boolean[][] grid = askInput(out, in);
        printBoard(out, grid);
        for (int i = 0; i < gameLength; i++) {
            tickGame(grid);
            printBoard(out, grid);
            Thread.sleep(SLEEP);
        }

        in.close();
        out.close();
    }

}
