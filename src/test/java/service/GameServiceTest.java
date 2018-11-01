package service;

import model.Board;
import model.Cell;
import model.Game;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Properties;
import java.util.Random;

import static model.Cell.Status.SELECTED;
import static model.Cell.Status.UNSELECTED;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    private static final Integer BOARD_SIZE = 5;
    private static final String PLAYER_NUMBER = "3";

    private GameService subject;
    private Random random;

    @Mock
    private Properties properties;

    @Before
    public void setUp() {
        subject = new GameService();
        random = new Random();
    }

    @Test
    public void setPosition_CorrectInputData_SetPosition() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);


        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        subject.setPosition(game, posY, posX, player);

        assertThat(game.getBoard().getCells()[posY][posX].getStatus(), is(SELECTED));
        assertThat(game.getBoard().getCells()[posY][posX].getPlayer(), is(player));
    }

    @Test
    public void unSetPosition_CorrectInputData_unSetPosition() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        subject.unSetPosition(game, posY, posX);

        assertThat(game.getBoard().getCells()[posY][posX].getStatus(), is(UNSELECTED));
        assertThat(game.getBoard().getCells()[posY][posX].getPlayer(), is(nullValue()));
    }

    @Test
    public void isValidNumber_StringValue_False() {
        final String number = randomAlphabetic(10);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        final Boolean isValidNumber = subject.isValidNumber(number, game);

        assertFalse(isValidNumber);
    }

    @Test
    public void isValidNumber_NumberGraterThanBoardSize_False() {
        final Integer number = BOARD_SIZE + 1;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        final Boolean isValidNumber = subject.isValidNumber(String.valueOf(number), game);

        assertFalse(isValidNumber);
    }

    @Test
    public void isValidNumber_NumberLowerThanOne_False() {
        final Integer number = -1;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        final Boolean isValidNumber = subject.isValidNumber(String.valueOf(number), game);

        assertFalse(isValidNumber);
    }

    @Test
    public void isValidNumber_CorrectNumber_True() {
        final Integer number = 5;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        final Boolean isValidNumber = subject.isValidNumber(String.valueOf(number), game);

        assertTrue(isValidNumber);
    }

    @Test
    public void isSelected_Selected_True() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        game.getBoard().getCells()[posY][posX].setSelected(player);

        final Boolean isSelected = subject.isSelectedCell(game, posY, posX);

        assertTrue(isSelected);
    }

    @Test
    public void isSelected_NotSelected_False() {
        final Integer posX = random.nextInt(BOARD_SIZE);
        final Integer posY = random.nextInt(BOARD_SIZE);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));


        final Game game = getGame();

        final Boolean isSelected = subject.isSelectedCell(game, posY, posX);

        assertFalse(isSelected);
    }

    @Test
    public void isPlayerWin_RowComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        final Integer posY = random.nextInt(BOARD_SIZE);
        final Integer posX = random.nextInt(BOARD_SIZE);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();
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
        final Player player = new Player(playerName, playerSymbol);

        final Integer posY = random.nextInt(BOARD_SIZE);
        final Integer posX = random.nextInt(BOARD_SIZE);

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertTrue(isPlayerWin);
    }

    @Test
    public void isPlayerWin_DiagonalAtCeroComplete_True() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        final Integer posY = 0;
        final Integer posX = 0;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

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
        final Player player = new Player(playerName, playerSymbol);

        final Integer posY = 0;
        final Integer posX = 0;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

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
        final Player player = new Player(playerName, playerSymbol);

        final Integer posY = 0;
        final Integer posX = 0;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-one.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

        final Boolean isPlayerWin = subject.isPlayerWin(game, posY, posX, player);

        assertFalse(isPlayerWin);
    }

    @Test
    public void getCPUCell_PlayerWinInNextMove_Cell() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        final Integer posX = 0;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

        for (int y = 0; y < game.getBoard().getSize() - 1; y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Cell cell = subject.getCpuCell(game, properties, player);

        assertThat(cell, is(notNullValue()));
        assertThat(cell.getY(), is(BOARD_SIZE - 1));
        assertThat(cell.getX(), is(0));
    }

    @Test
    public void getCPUCell_PlayerNotWinInNextMove_Cell() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final Player player = new Player(playerName, playerSymbol);

        final Integer posX = 0;

        when(properties.getProperty("player.number")).thenReturn(PLAYER_NUMBER);

        when(properties.getProperty("player-one.name")).thenReturn(playerName);
        when(properties.getProperty("player-one.symbol")).thenReturn(playerSymbol);

        when(properties.getProperty("player-two.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-two.symbol")).thenReturn(randomAlphabetic(10));

        when(properties.getProperty("player-tree.name")).thenReturn(randomAlphabetic(10));
        when(properties.getProperty("player-tree.symbol")).thenReturn(randomAlphabetic(10));

        final Game game = getGame();

        for (int y = 0; y < game.getBoard().getSize() - 2; y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Cell cell = subject.getCpuCell(game, properties, player);

        assertThat(cell, is(notNullValue()));
        assertThat(cell.getY(), is(0));
        assertThat(cell.getX(), is(1));
    }

    private Game getGame() {
        final Board bord = new Board(BOARD_SIZE);
        final Game game = new Game(bord, properties);

        return game;
    }
}
