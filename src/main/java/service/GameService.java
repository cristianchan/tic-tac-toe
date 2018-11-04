package service;

import model.Cell;
import model.Cell.Status;
import model.Game;
import model.Player;

import static model.Cell.Status.SELECTED;
import static model.Cell.Status.UNSELECTED;

public class GameService {
    public void setPosition(final Game game, final Integer y, final Integer x, final Player player) {
        game.getBoard().getCells()[y][x].setSelected(player);
    }

    public void unSetPosition(final Game game, final Integer y, final Integer x) {
        game.getBoard().getCells()[y][x].setUnSelected();
    }

    public boolean isSelectedCell(final Game game, final Integer y, final Integer x) {
        Cell cell = game.getBoard().getCells()[y][x];

        Status status = cell.getStatus();

        if (status == SELECTED) {
            return true;
        }

        return false;
    }

    public boolean isPlayerWin(final Game game, final Integer posY, final Integer posX, final Player player) {
        return isRowComplete(game, posY, player) || isColumnComplete(game, posX, player) ||
                isDiagonalAtZeroComplete(game, player) || isDiagonalAtBoarSizeComplete(game, player);
    }

    public boolean isDraw(final Game game) {
        final Cell[][] cells = game.getBoard().getCells();

        for (final Cell[] row : cells) {
            for (final Cell cell : row) {
                if (cell.getStatus() == UNSELECTED) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isRowComplete(final Game game, final Integer posY, final Player player) {
        Cell[] row = game.getBoard().getCells()[posY];

        for (final Cell cell : row) {
            if (cell.getStatus() == UNSELECTED || !player.getName().equals(cell.getPlayer().getName())) {
                return false;
            }
        }

        return true;
    }

    private boolean isColumnComplete(final Game game, final Integer posX, final Player player) {
        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][posX];

            if (cell.getStatus() == UNSELECTED || !player.getName().equals(cell.getPlayer().getName())) {
                return false;
            }
        }

        return true;
    }

    private boolean isDiagonalAtZeroComplete(final Game game, final Player player) {
        int x = 0;

        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][x];

            if (cell.getStatus() == UNSELECTED || !player.getName().equals(cell.getPlayer().getName())) {
                return false;
            }

            x++;
        }
        return true;
    }

    private boolean isDiagonalAtBoarSizeComplete(final Game game, final Player player) {
        int x = game.getBoard().getSize() - 1;

        for (int y = 0; y < game.getBoard().getSize(); y++) {
            final Cell cell = game.getBoard().getCells()[y][x];

            if (cell.getStatus() == UNSELECTED || !player.getName().equals(cell.getPlayer().getName())) {
                return false;
            }

            x--;
        }

        return true;
    }
}