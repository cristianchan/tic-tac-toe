import config.GameConfiguration;
import controller.GameController;
import model.Board;
import model.Game;
import service.GameService;
import view.BoardView;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String... args) throws IOException {

        final GameConfiguration gameConfiguration = new GameConfiguration();
        final Properties properties = gameConfiguration.getGameConfiguration();

        final String boardSize = properties.getProperty("board.size");
        final Board bord = new Board(Integer.parseInt(boardSize));
        final BoardView boardView = new BoardView();
        final GameService gameService = new GameService();
        final GameController gameController = new GameController(boardView, gameService);
        final Game game = new Game(bord, properties);

        gameController.starGame(game, 0);
    }
}
