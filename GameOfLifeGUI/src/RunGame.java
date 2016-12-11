public final class RunGame {
    private RunGame() {

    }

    public static void main(String[] args) {
        GOLModel model = new GOLModel1();
        GOLController ctrlr = new GOLController1();
        GOLView view = new GOLView1();

        view.registerObserver(ctrlr);
    }
}
