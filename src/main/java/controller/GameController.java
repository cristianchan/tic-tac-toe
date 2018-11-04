package controller;

import model.Cell;
import model.Game;
import model.Player;
import service.GameService;
import view.BoardView;

import java.io.IOException;

import static java.lang.System.out;

public class GameController {
    private final BoardView boardView;
    private final GameService gameService;

    public GameController(BoardView boardView, GameService gameService) {
        this.boardView = boardView;
        this.gameService = gameService;
    }

    public Game starGame(final Game game, Integer turn) throws IOException {
        Boolean winnerExist = false;

        while (!winnerExist) {
            out.println();
            boardView.printBoard(game.getBoard());

            final Player player = game.getPlayers()[turn];

            boardView.printSelectPositionMessage(player.getName());

            final Cell selectedCell = player.getCell(game);

            final Integer posX = selectedCell.getX();
            final Integer posY = selectedCell.getY();

            if (gameService.isSelectedCell(game, posY, posX)) {
                boardView.printCellSelectedMessage();
                return starGame(game, turn);
            }

            gameService.setPosition(game, posY, posX, player);

            if (gameService.isPlayerWin(game, posY, posX, player)) {
                boardView.printWinMessage(player.getName());
                boardView.printBoard(game.getBoard());
                winnerExist = true;
            } else if (gameService.isDraw(game)) {
                boardView.printDrawMessage();
                boardView.printBoard(game.getBoard());
                winnerExist = true;
            }

            turn++;
            if (turn > game.getPlayers().length - 1) {
                turn = 0;
            }
        }

        return game;
    }
}
