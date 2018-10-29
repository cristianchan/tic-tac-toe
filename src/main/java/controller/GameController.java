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

            out.println("Select X position");
            String posX = bufferedReader.readLine();

            out.println("Select Y position");
            String posY = bufferedReader.readLine();

            o
            gameService.setPosition(game, Integer.parseInt(posY) - 1, Integer.parseInt(posX) - 1, player);

            turn++;

            if (turn > 2) {
                turn = 0;
            }
        }

    }
}
