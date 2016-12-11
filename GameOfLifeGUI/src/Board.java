import components.map.Map;

public class Board {
    // decide how to represent board
    private double minX, minY, maxX, maxY;
    private Map<Cell, Boolean> board;

    public Board() {

    }

    public double getWidth() {
        return this.maxX - this.minX;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public boolean cellState(Cell c) {
        return this.board.hasKey(c);
    }
}
