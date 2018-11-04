package service;

import model.Cell;
import model.Game;
import model.Player;

import static java.util.Objects.isNull;
import static model.Cell.Status.UNSELECTED;

public class CpuPlayerService {
    private final GameService gameService;

    public CpuPlayerService(GameService gameService) {
        this.gameService = gameService;
    }

    public Cell getCpuCell(final Game game) {
        final Cell cell = getCPUPositionIfAPlayerWinInNextMove(game);
        if (isNull(cell)) {
            return getCPUPosition(game);
        }
        return cell;
    }

    private Cell getCPUPositionIfAPlayerWinInNextMove(final Game game) {
        final Cell[][] cells = game.getBoard().getCells();
        final Player[] players = new Player[]{
                game.getPlayers()[0], game.getPlayers()[1]
        };

        for (final Player verifyPlayer : players) {
            for (final Cell[] row : cells) {
                for (final Cell cell : row) {
                    if (cell.getStatus() == UNSELECTED) {
                        gameService.setPosition(game, cell.getY(), cell.getX(), verifyPlayer);
                        if (gameService.isPlayerWin(game, cell.getY(), cell.getX(), verifyPlayer)) {
                            gameService.unSetPosition(game, cell.getY(), cell.getX());
                            return game.getBoard().getCells()[cell.getY()][cell.getX()];
                        }
                        gameService.unSetPosition(game, cell.getY(), cell.getX());
                    }
                }
            }
        }
        return null;
    }

    private Cell getCPUPosition(final Game game) {
        final Cell[][] cells = game.getBoard().getCells();

        for (final Cell[] row : cells) {
            for (final Cell cell : row) {
                if (cell.getStatus() == UNSELECTED) {
                    return cell;
                }
            }
        }

        return null;
    }
}
