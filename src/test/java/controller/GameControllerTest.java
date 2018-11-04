package controller;


import javafx.util.Pair;
import model.CpuPlayer;
import model.Game;
import model.HumanPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.CpuPlayerService;
import service.GameService;
import service.HumanPlayerService;
import view.BoardView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Random;

import static factory.GameFactory.getGame;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    private static final Integer BOARD_SIZE = 5;

    @InjectMocks
    private GameController subject;

    @Mock
    private BoardView boardView;

    @Mock
    private GameService gameService;

    @Mock
    private Properties properties;

    @Mock
    private HumanPlayerService humanPlayerService;

    @Mock
    private CpuPlayerService cpuPlayerService;

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
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService, playerName, playerSymbol);
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;

        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);
        when(humanPlayerService.getPositions(game)).thenReturn(new Pair<>(posX, posY));

        final Game gameFinished = subject.starGame(game, 0);

        assertEquals(gameFinished, game);

        verify(boardView, times(2)).printBoard(game.getBoard());
        verify(boardView).printWinMessage(playerName);
    }

    @Test
    public void startGame_PersonTurn_GameSetPositionDraw() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService, playerName, playerSymbol);
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;

        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(false);
        when(gameService.isDraw(game)).thenReturn(true);
        when(humanPlayerService.getPositions(game)).thenReturn(new Pair<>(posX, posY));

        final Game gameFinished = subject.starGame(game, 0);

        assertEquals(gameFinished, game);

        verify(boardView).printDrawMessage();
        verify(boardView, times(2)).printBoard(game.getBoard());
    }

    @Test
    public void startGame_CPUTurn_GameRestartTurnSetPositionCPUWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final CpuPlayer player = new CpuPlayer(playerName, playerSymbol, cpuPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService, playerName, playerSymbol);
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[2] = player;

        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);
        when(cpuPlayerService.getCpuCell(game)).thenReturn(game.getBoard().getCells()[posY - 1][posX - 1]);

        final Game gameFinished = subject.starGame(game, 2);

        assertEquals(gameFinished, game);

        verify(boardView).printWinMessage(playerName);
        verify(boardView, times(2)).printBoard(game.getBoard());
    }

    @Test
    public void startGame_PersonTurnCellSelected_SetPositionPersonWin() throws IOException {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService, playerName, playerSymbol);
        final Integer posX = random.nextInt(BOARD_SIZE) + 1;
        final Integer posY = random.nextInt(BOARD_SIZE) + 1;

        game.getPlayers()[0] = player;

        when(gameService.isSelectedCell(game, posY - 1, posX - 1)).thenReturn(true).thenReturn(false);
        when(gameService.isPlayerWin(game, posY - 1, posX - 1, player)).thenReturn(true);
        when(humanPlayerService.getPositions(game)).thenReturn(new Pair<>(posX, posY));

        final Game gameFinished = subject.starGame(game, 0);

        assertEquals(gameFinished, game);

        verify(boardView).printWinMessage(playerName);
        verify(boardView, times(3)).printBoard(game.getBoard());
    }
}
