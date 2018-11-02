package controller;

import model.Cell;
import model.Game;
import model.Player;
import service.GameService;
import view.BoardView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static java.lang.System.in;
import static java.lang.System.out;

public class GameController {
    private final BoardView boardView;
    private final BufferedReader bufferedReader;
    private final GameService gameService;

    public GameController(BoardView boardView, GameService gameService, BufferedReader bufferedReader) {
        this.boardView = boardView;
        this.gameService = gameService;
        this.bufferedReader = bufferedReader;
    }

    public Game starGame(final Game game, Integer turn, final Properties properties) throws IOException {
        Boolean winnerExist = false;

        while (!winnerExist) {
            final Integer posX;
            final Integer posY;
            final Player player = game.getPlayers()[turn];

            if (turn != 2) {
                boardView.drawBoard(game.getBoard());
                posX = getPosX(game, player) - 1;
                posY = getPosY(game, player) - 1;
            } else {
                final Cell cpuCell = gameService.getCpuCell(game, properties, player);
                posX = cpuCell.getX();
                posY = cpuCell.getY();
            }


            if (gameService.isSelectedCell(game, posY, posX)) {
                return starGame(game, turn, properties);
            }

            gameService.setPosition(game, posY, posX, player);

            if (gameService.isPlayerWin(game, posY, posX, player)) {
                out.println();
                out.println("Congrats!! " + player.getName() + " Win");
                out.println();
                boardView.drawBoard(game.getBoard());
                winnerExist = true;
            }

            if (gameService.isDraw(game)) {
                out.println();
                out.println("So sad!!!!! is a draw");
                out.println();
                boardView.drawBoard(game.getBoard());
                winnerExist = true;
            }

            turn++;
            if (turn > game.getPlayers().length - 1) {
                turn = 0;
            }
        }

        return game;
    }


    private Integer getPosX(final Game game, final Player player) throws IOException {
        out.println();
        out.println(player.getName() + " select X position");

        String posX = bufferedReader.readLine();

        if (!gameService.isValidNumber(posX, game)) {
            boardView.drawBoard(game.getBoard());

            return getPosX(game, player);
        }

        return Integer.parseInt(posX);
    }

    private Integer getPosY(final Game game, final Player player) throws IOException {
        out.println();
        out.println(player.getName() + " select Y position");

        String posY = bufferedReader.readLine();

        if (!gameService.isValidNumber(posY, game)) {
            boardView.drawBoard(game.getBoard());

            return getPosY(game, player);
        }

        return Integer.parseInt(posY);
    }
}
