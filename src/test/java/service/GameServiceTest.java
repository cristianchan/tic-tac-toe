package service;

import model.Cell;
import model.Game;
import model.HumanPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static factory.GameFactory.getGame;
import static model.Cell.Status.SELECTED;
import static model.Cell.Status.UNSELECTED;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    private static final Integer BOARD_SIZE = 5;

    @InjectMocks
    private GameService subject;

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
    public void setPosition_CorrectInputData_SetPosition() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        subject.setPosition(game, posY, posX, player);

        assertThat(game.getBoard().getCells()[posY][posX].getStatus(), is(SELECTED));
        assertThat(game.getBoard().getCells()[posY][posX].getPlayer(), is(player));
    }

    @Test
    public void unSetPosition_CorrectInputData_unSetPosition() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        subject.unSetPosition(game, posY, posX);

        assertThat(game.getBoard().getCells()[posY][posX].getStatus(), is(UNSELECTED));
        assertThat(game.getBoard().getCells()[posY][posX].getPlayer(), is(nullValue()));
    }


    @Test
    public void isSelected_Selected_True() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        game.getBoard().getCells()[posY][posX].setSelected(player);

        final Boolean isSelected = subject.isSelectedCell(game, posY, posX);

        assertTrue(isSelected);
    }

    @Test
    public void isSelected_NotSelected_False() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        final Boolean isSelected = subject.isSelectedCell(game, posY, posX);

        assertFalse(isSelected);
    }

    @Test
    public void isPlayerWin_RowComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posY = random.nextInt(BOARD_SIZE);
        final Integer posX = random.nextInt(BOARD_SIZE);

        final Game game = getGame(humanPlayerService, cpuPlayerService);
        final Cell[] row = game.getBoard().getCells()[posY];

        for (final Cell cell : row) {
            cell.setSelected(player);
        }

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertTrue(isPlayerWin);
    }

    @Test
    public void isPlayerWin_ColumnComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posY = random.nextInt(BOARD_SIZE);
        final Integer posX = random.nextInt(BOARD_SIZE);

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertTrue(isPlayerWin);
    }

    @Test
    public void isPlayerWin_DiagonalAtZeroComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posY = 0;
        final Integer posX = 0;

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        int x = 0;
        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
            x++;
        }

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertTrue(isPlayerWin);
    }

    @Test
    public void isPlayerWin_DiagonalAtBoarSizeComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posY = 0;
        final Integer posX = 0;

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        int x = game.getBoard().getSize() - 1;
        ;
        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
            x--;
        }

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertTrue(isPlayerWin);
    }

    @Test
    public void isPlayerWin_NotWin_False() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posY = 0;
        final Integer posX = 0;

        final Game game = getGame(humanPlayerService, cpuPlayerService);

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertFalse(isPlayerWin);
    }
}
