import java.util.Random;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * First attempt at game of life.
 *
 * @author Ivan Stoychev
 */
public final class GameOfLifeV2 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private GameOfLifeV2() {
        // no code needed here
    }

    /**
     * Size of the board.
     */
    private static int boardSize;
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
    private static final int SLEEP = 250;

    /**
     * How long you see the initial state.
     */
    private static final long INIT_PAUSE = 1500;

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
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {

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
            if (checkRow >= boardSize) {
                checkRow = 0;
            } else if (checkRow < 0) {
                checkRow = boardSize - 1;
            }

            // Wrap the column
            if (checkColumn >= boardSize) {
                checkColumn = 0;
            } else if (checkColumn < 0) {
                checkColumn = boardSize - 1;
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
        final String[] optionNames = { "beacon", "blinker", "toad", "glider",
                "random" };
        final boolean f = false;
        final boolean t = true;

        // 6 x 6 grids
        final int basicObjects = 6;
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
                { f, f, f, f, f, f }, { f, f, f, f, f, f } }; // empty grid (for copying)

        boolean validOption = false;

        out.println("WELCOME TO THE GAME OF LIFE (V1)");
        out.print("What size board would you like?: ");
        boardSize = in.nextInteger();

        boolean[][] random = randomGrid();
        final boolean[][][] options = { beacon, blinker, toad, glider, random };

        out.println("What starting condition would you like?");
        if (boardSize == basicObjects) {
            out.println("Options: beacon, blinker, toad, glider, random");

        } else {
            out.println("Options: random");
        }

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
     * Generates a random grid using BOARD_SIZE class variable.
     *
     * @return a boolean[BOARD_SIZE][BOARS_SIZE] random grid
     */
    public static boolean[][] randomGrid() {
        Random randomNum = new Random();
        boolean[][] grid = new boolean[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j] = randomNum.nextBoolean();
            }
        }
        return grid;
    }

    /**
     * Asks whether to continue simulation.
     *
     * @param out
     *            the output stream
     * @param in
     *            the input stream
     * @param length
     *            The length of a set of ticks
     * @return boolean whether to continue
     */
    public static boolean askContinue(int length, SimpleWriter out,
            SimpleReader in) {
        out.println("(y or n) " + length + " more turns?: ");
        String goOn = in.nextLine();
        return goOn.compareTo("y") == 0;
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
        final int gameLength = 50;
        //out.println('\u259B' + '\u2598');
        boolean[][] grid = askInput(out, in);
        boolean keepRunning = true;
        printBoard(out, grid);
        Thread.sleep(INIT_PAUSE);

        while (keepRunning) {
            for (int i = 0; i < gameLength; i++) {
                tickGame(grid);
                printBoard(out, grid);
                Thread.sleep(SLEEP);
            }
            keepRunning = askContinue(gameLength, out, in);
        }

        in.close();
        out.close();
    }

}
