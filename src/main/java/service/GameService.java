package service;

import model.Cell;
import model.Cell.Status;
import model.Game;
import model.Player;

import java.util.Properties;

import static java.lang.System.out;
import static java.util.Objects.isNull;
import static model.Cell.Status.SELECTED;
import static model.Cell.Status.UNSELECTED;

public class GameService {
    public void setPosition(final Game game, final Integer y, final Integer x, final Player player) {
        game.getBoard().getCells()[y][x].setSelected(player);
    }

    public void unSetPosition(final Game game, final Integer y, final Integer x) {
        game.getBoard().getCells()[y][x].setUnSelected();
    }

    public boolean isValidNumber(final String number, final Game game) {
        try {
            final Integer numberToValidate = Integer.parseInt(number);

            if (numberToValidate < 1) {
                throw new NumberFormatException("The number should by grater than 0");
            }

            if (numberToValidate > game.getBoard().getSize()) {
                throw new NumberFormatException("Is bigger than board size");
            }

            return true;
        } catch (final NumberFormatException exception) {
            out.println();
            out.println("Invalid number " + exception.getMessage() + " try again");
            out.println();
            return false;
        }
    }

    public boolean isSelectedCell(final Game game, final Integer y, final Integer x) {
        Cell cell = game.getBoard().getCells()[y][x];

        Status status = cell.getStatus();

        if (status == SELECTED) {
            out.println();
            out.println("The cell is selected");
            out.println();
            return true;
        }

        return false;
    }

    public boolean isPlayerWin(final Game game, final Integer posY, final Integer posX, final Player player) {
        return isRowComplete(game, posY, player) || isColumnComplete(game, posX, player) ||
                isDiagonalAtCeroComplete(game, player) || isDiagonalAtBoarSizeComplete(game, player);
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

    public Cell getCpuCell(final Game game, final Properties properties, final Player player) {
        final Cell cell = getCPUPositionIfAPlayerWinInNextMove(game, properties);
        if (isNull(cell)) {
            return getCPUPosition(game);
        }
        return cell;
    }

    private Cell getCPUPositionIfAPlayerWinInNextMove(final Game game, final Properties properties) {
        final Cell[][] cells = game.getBoard().getCells();
        final Player[] players = new Player[]{
                game.getPlayers()[0], game.getPlayers()[1]
        };

        for (final Player verifyPlayer : players) {
            for (final Cell[] row : cells) {
                for (final Cell cell : row) {
                    if (cell.getStatus() == UNSELECTED) {
                        setPosition(game, cell.getY(), cell.getX(), verifyPlayer);
                        if (isPlayerWin(game, cell.getY(), cell.getX(), verifyPlayer)) {
                            unSetPosition(game, cell.getY(), cell.getX());
                            return game.getBoard().getCells()[cell.getY()][cell.getX()];
                        }
                        unSetPosition(game, cell.getY(), cell.getX());
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

    private boolean isDiagonalAtCeroComplete(final Game game, final Player player) {
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