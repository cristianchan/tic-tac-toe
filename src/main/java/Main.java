import controller.GameController;
import model.Board;
import model.Game;
import service.GameService;
import view.BoardView;

import java.io.IOException;

public class Main {
    public static void main(String... args) throws IOException {


        final BoardView boardView = new BoardView();
        final GameService gameService = new GameService();
        final GameController gameController = new GameController(boardView, gameService);
        final Integer boardSize = gameController.getGameConfiguration();
        final Board bord = new Board(boardSize);

        final Game game = new Game(bord);

        gameController.starGame(game);
    }
}
