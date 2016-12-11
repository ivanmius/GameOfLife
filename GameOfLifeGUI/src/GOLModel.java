public interface GOLModel {
    Board getBoard();

    void clearBoard();

    void toggleCell(Cell cell);
}
