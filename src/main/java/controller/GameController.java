package controller;

import model.Game;
import model.Player;
import service.GameService;
import view.BoardView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static java.lang.System.out;

public class GameController {
    private final BoardView boardView;
    private final InputStreamReader inputStreamReader;
    private final BufferedReader bufferedReader;
    private final GameService gameService;

    public GameController(BoardView boardView, GameService gameService) {
        this.boardView = boardView;
        this.gameService = gameService;
        this.inputStreamReader = new InputStreamReader(in);
        this.bufferedReader = new BufferedReader(this.inputStreamReader);
    }

    public Game starGame(final Game game, Integer turn) throws IOException {
        Boolean winnerExist = false;

        while (!winnerExist) {
            boardView.drawBoard(game.getBoard());

            final Player player = game.getPlayers()[turn];
            final Integer posX = getPosX(game, player);
            final Integer posY = getPosY(game, player);

            if (gameService.isSelectedCell(game, posY - 1, posX - 1)) {
                return starGame(game, turn);
            }
            gameService.setPosition(game, posY - 1, posX - 1, player);

            if (gameService.isPlayerWin(game, posY, posX, player)) {
                out.println("Congrats!! " + player.getName() + "Win");
                out.println();
                boardView.drawBoard(game.getBoard());
                winnerExist = true;
            }

            turn++;
            if (turn > 2) {
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
