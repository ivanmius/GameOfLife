import java.awt.event.ActionListener;

public interface GOLView extends ActionListener {
    void registerObserver(GOLController cntrlr);

    void updateBoard(Board b);
}
