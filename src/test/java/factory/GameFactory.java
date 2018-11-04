package factory;

import model.Board;
import model.Game;
import service.CpuPlayerService;
import service.HumanPlayerService;

import java.util.Properties;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public final class GameFactory {
    public static final Integer BOARD_SIZE = 5;
    public static final String PLAYER_NUMBER = "3";

    private GameFactory() {
    }

    public static Game getGame(final HumanPlayerService humanPlayerService, final CpuPlayerService cpuPlayerService) {
        return getGame(humanPlayerService, cpuPlayerService, randomAlphabetic(10), randomAlphabetic(10));
    }

    public static Game getGame(final HumanPlayerService humanPlayerService, final CpuPlayerService cpuPlayerService,
                               final String playerName, final String playerSymbol) {
        final Board board = new Board(BOARD_SIZE);
        final Properties properties = new Properties();
        properties.setProperty("player.number", PLAYER_NUMBER);
        properties.setProperty("player-one.name", playerName);
        properties.setProperty("player-one.symbol", playerSymbol);
        properties.setProperty("player-two.name", randomAlphabetic(10));
        properties.setProperty("player-two.symbol", randomAlphabetic(10));
        properties.setProperty("player-tree.name", randomAlphabetic(10));
        properties.setProperty("player-tree.symbol", randomAlphabetic(10));

        return new Game(board, properties, humanPlayerService, cpuPlayerService);
    }
}
