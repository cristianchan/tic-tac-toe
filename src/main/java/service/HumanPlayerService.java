package service;

import javafx.util.Pair;
import model.Game;
import view.BoardView;

import java.io.BufferedReader;

public class HumanPlayerService {
    private final BufferedReader bufferedReader;
    private final BoardView boardView;

    public HumanPlayerService(final BufferedReader bufferedReader,
                              final BoardView boardView) {
        this.bufferedReader = bufferedReader;
        this.boardView = boardView;
    }

    public Pair<Integer, Integer> getPositions(final Game game) {
        try {
            String[] positions = bufferedReader.readLine().split(",");

            final String posX = positions[0];
            final String posY = positions[1];

            if (!isValidNumber(posX, game) || !isValidNumber(posY, game)) {
                return getPositions(game);
            }

            return new Pair<>(Integer.parseInt(posX), Integer.parseInt(posY));
        } catch (final Exception exception) {
            boardView.printInputFormatError();

            return getPositions(game);
        }


    }

    private boolean isValidNumber(final String number, final Game game) {
        try {
            final Integer numberToValidate = Integer.parseInt(number);

            if (numberToValidate < 1) {
                throw new NumberFormatException(boardView.getNumberGreaterThanZeroErrorMessage());
            }

            if (numberToValidate > game.getBoard().getSize()) {
                throw new NumberFormatException(boardView.getNumberBiggerThanBoardSizeMessage());
            }

            return true;
        } catch (final NumberFormatException exception) {
            boardView.printNumberFormatError(exception.getMessage());

            return false;
        }
    }
}
