package controller;


import model.Board;
import model.Game;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.GameService;
import view.BoardView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    private static final String PLAYER_NUMBER = "3";
    private static final Integer BOARD_SIZE = 5;

    @InjectMocks
    private GameController subjet;

    @Mock
    private BoardView boardView;

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private GameService gameService;

    @Mock
    private Properties properties;

    private Random random;

    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() {
        random = new Random();
        outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void startGame_PersonTurn_GameSetPositionPlayerWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;

        when(bufferedReader.readLine()).thenReturn(posX.toString()).thenReturn(posY.toString());
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);
        when(gameService.isValidNumber(posX.toString(), game)).thenReturn(true);
        when(gameService.isValidNumber(posY.toString(), game)).thenReturn(true);

        final Game gameFinished = subjet.starGame(game, 0, properties);

        final String message = playerName + "select X position" +
                playerName + "select Y position" +
                "Congrats!! " + player.getName() + " Win";


        assertEquals(gameFinished, game);
        assertEquals(formatMessage(message), formatMessage(outContent.toString()));
        verify(boardView, times(2)).drawBoard(game.getBoard());
    }

    @Test
    public void startGame_PersonTurn_GameSetPositionDraw() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;

        when(bufferedReader.readLine()).thenReturn(posX.toString()).thenReturn(posY.toString());
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(false);
        when(gameService.isValidNumber(posX.toString(), game)).thenReturn(true);
        when(gameService.isValidNumber(posY.toString(), game)).thenReturn(true);
        when(gameService.isDraw(game)).thenReturn(true);

        final Game gameFinished = subjet.starGame(game, 0, properties);

        final String message = "So sad!!!!! is a draw";

        assertEquals(gameFinished, game);
        assertTrue(outContent.toString().contains(message));

        verify(boardView, times(2)).drawBoard(game.getBoard());
    }

    @Test
    public void startGame_CPUTurn_GameRestartTurnSetPositionCPUWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);


        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[2] = player;

        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);
        when(gameService.getCpuCell(game, properties, player)).thenReturn(game.getBoard().getCells()[posY - 1][posX - 1]);

        final Game gameFinished = subjet.starGame(game, 2, properties);

        final String message = "Congrats!! " + player.getName() + " Win";

        assertEquals(gameFinished, game);
        assertEquals(formatMessage(message), formatMessage(outContent.toString()));

        verify(boardView).drawBoard(game.getBoard());
    }

    @Test
    public void startGame_PersonTurnCellSelected_SetPositionPersonWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);


        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;


        when(bufferedReader.readLine()).thenReturn(posX.toString()).thenReturn(posY.toString())
                .thenReturn(posX.toString())
                .thenReturn(posY.toString());

        when(gameService.isValidNumber(posX.toString(), game)).thenReturn(true);
        when(gameService.isValidNumber(posY.toString(), game)).thenReturn(true);
        when(gameService.isSelectedCell(game, posY - 1, posX - 1)).thenReturn(true).thenReturn(false);
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);

        final Game gameFinished = subjet.starGame(game, 0, properties);

        final String messageWin = "Congrats!! " + player.getName() + " Win";

        assertEquals(gameFinished, game);
        assertTrue(outContent.toString().contains(messageWin));

        verify(boardView, times(3)).drawBoard(game.getBoard());
    }

    @Test
    public void startGame_PersonTurnInvalidPosXNumber_SetPositionPersonWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);


        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;


        when(bufferedReader.readLine()).thenReturn(posX.toString())
                .thenReturn(posX.toString())
                .thenReturn(posY.toString());

        when(gameService.isValidNumber(posX.toString(), game)).thenReturn(false).thenReturn(true);
        when(gameService.isValidNumber(posY.toString(), game)).thenReturn(true);
        when(gameService.isSelectedCell(game, posY - 1, posX - 1)).thenReturn(false);
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);

        final Game gameFinished = subjet.starGame(game, 0, properties);

        final String messageWin = "Congrats!! " + player.getName() + " Win";

        assertEquals(gameFinished, game);
        assertTrue(outContent.toString().contains(messageWin));
    }

    @Test
    public void startGame_PersonTurnInvalidPosYNumber_SetPositionPersonWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);


        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);
        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);
        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;


        when(bufferedReader.readLine()).thenReturn(posX.toString())
                .thenReturn(posY.toString())
                .thenReturn(posY.toString());

        when(gameService.isValidNumber(posX.toString(), game)).thenReturn(true);
        when(gameService.isValidNumber(posY.toString(), game)).thenReturn(false).thenReturn(true);
        when(gameService.isSelectedCell(game, posY - 1, posX - 1)).thenReturn(false);
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);

        final Game gameFinished = subjet.starGame(game, 0, properties);

        final String messageWin = "Congrats!! " + player.getName() + " Win";

        assertEquals(gameFinished, game);
        assertTrue(outContent.toString().contains(messageWin));
    }

    private Game getGame() {
        final Board bord = new Board(BOARD_SIZE);
        final Game game = new Game(bord, properties);

        return game;
    }

    private String formatMessage(final String string) {
        return string.replace("\n", "").replace("\r", "").replace(" ", "");
    }

}
