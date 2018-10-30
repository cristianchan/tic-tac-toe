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

    public Integer getGameConfiguration() throws IOException {
        boardView.drawBoardSizeSelection();
        String boardSize;
        boardSize = bufferedReader.readLine();

        try {
            final Integer size = Integer.parseInt(boardSize);

            if (size < 3 || size > 10) {
                throw new NumberFormatException("Invalid range of size");
            }

            return size;
        } catch (final NumberFormatException exception) {
            out.println("You most be enter a valid number");
        }

        return getGameConfiguration();
    }

    public void starGame(final Game game) throws IOException {
        int turn = 0;
        while (true) {

            boardView.drawBoard(game.getBoard());

            final Player player = game.getPlayers()[turn];
            final Integer posX = getPosX(game);
            final Integer posY = getPosY(game);

            gameService.setPosition(game, posY - 1, posX - 1, player);

            turn++;

            if (turn > 2) {
                turn = 0;
            }
        }
    }

    private Integer getPosX(final Game game) throws IOException {
        out.println();
        out.println("Select X position");

        String posX = bufferedReader.readLine();
        if (!isValidNumber(posX, game)) {
            return getPosX(game);
        }

        return Integer.parseInt(posX);
    }

    private Integer getPosY(final Game game) throws IOException {
        out.println();
        out.println("Select Y position");

        String posY = bufferedReader.readLine();
        if (!isValidNumber(posY, game)) {
            return getPosY(game);
        }

        return Integer.parseInt(posY);
    }

    private boolean isValidNumber(final String number, final Game game) {
        try {
            final Integer numberToValidate = Integer.parseInt(number);

            if (numberToValidate > game.getBoard().getSize()) {
                throw new NumberFormatException("Is bigger than board size");
            }

            return true;
        } catch (final NumberFormatException exception) {
            boardView.drawBoard(game.getBoard());

            out.println();
            out.println("Invalid number " + exception.getMessage() + " try again");

            return false;
        }
    }
}
