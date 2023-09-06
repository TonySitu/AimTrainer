

public class Main {
    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        ClockModel clockModel = new ClockModel();
        View view = new View();

        gameModel.addObserver(view);
        clockModel.addObserver(view);

        Presenter presenter = new Presenter(gameModel, clockModel, view);
        view.setPresenter(presenter);
        view.update(gameModel, null);
    }
}