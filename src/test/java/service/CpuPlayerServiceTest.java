package service;


import model.Cell;
import model.Game;
import model.HumanPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Properties;

import static factory.GameFactory.getGame;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CpuPlayerServiceTest {
    private static final String PLAYER_NUMBER = "3";
    private static final Integer BOARD_SIZE = 5;

    @InjectMocks
    private CpuPlayerService subject;

    @Mock
    private Properties properties;

    @Mock
    private HumanPlayerService humanPlayerService;

    @Mock
    private GameService gameService;

    @Test
    public void getCPUCell_PlayerWinInNextMove_Cell() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posX = 0;

        final Game game = getGame(humanPlayerService, subject, playerName, playerSymbol);

        game.getPlayers()[0] = player;

        when(gameService.isPlayerWin(game, BOARD_SIZE - 1, posX, player)).thenReturn(true);

        for (int y = 0; y < game.getBoard().getSize() - 1; y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Cell cell = subject.getCpuCell(game);

        assertThat(cell, is(notNullValue()));
        assertThat(cell.getY(), is(BOARD_SIZE - 1));
        assertThat(cell.getX(), is(0));

        verify(gameService).setPosition(game, cell.getY(), cell.getX(), player);
        verify(gameService).unSetPosition(game, cell.getY(), cell.getX());
    }

    @Test
    public void getCPUCell_PlayerNotWinInNextMove_Cell() {
        final String playerName = randomAlphabetic(10);
        final String playerSymbol = randomAlphabetic(2);
        final HumanPlayer player = new HumanPlayer(playerName, playerSymbol, humanPlayerService);

        final Integer posX = 0;

        final Game game = getGame(humanPlayerService, subject, playerName, playerSymbol);

        for (int y = 0; y < game.getBoard().getSize() - 2; y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];
            cell.setSelected(player);
        }

        final Cell cell = subject.getCpuCell(game);

        assertThat(cell, is(notNullValue()));
        assertThat(cell.getY(), is(0));
        assertThat(cell.getX(), is(1));
    }
}
