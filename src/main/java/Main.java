import config.GameConfiguration;
import controller.GameController;
import model.Board;
import model.Game;
import service.CpuPlayerService;
import service.GameService;
import service.HumanPlayerService;
import view.BoardView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

import static java.lang.System.in;

public class Main {
    public static void main(String... args) throws IOException {

        String filename = "config.properties";
        InputStream input = Main.class.getClassLoader().getResourceAsStream(filename);

        final Random random = new Random();
        final GameConfiguration gameConfiguration = new GameConfiguration(input);
        final Properties properties = gameConfiguration.getGameConfiguration();

        final String boardSize = properties.getProperty("board.size");
        final Board bord = new Board(Integer.parseInt(boardSize));
        final BoardView boardView = new BoardView();
        final GameService gameService = new GameService();

        final InputStreamReader inputStreamReader = new InputStreamReader(in);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        final GameController gameController = new GameController(boardView, gameService);
        final HumanPlayerService humanPlayerService = new HumanPlayerService(bufferedReader,boardView);
        final CpuPlayerService cpuPlayerService = new CpuPlayerService(gameService);
        final Game game = new Game(bord, properties, humanPlayerService, cpuPlayerService);

        gameController.starGame(game, random.nextInt(game.getPlayers().length));
    }
}
