package model;

import javafx.util.Pair;
import service.HumanPlayerService;

public class HumanPlayer extends Player {
    final HumanPlayerService humanPlayerService;

    public HumanPlayer(final String name,
                       final String symbol,
                       final HumanPlayerService humanPlayerService) {
        super(name, symbol);
        this.humanPlayerService = humanPlayerService;
    }


    @Override
    public Cell getCell(final Game game) {
        final Pair<Integer, Integer> positions = humanPlayerService.getPositions(game);

        final Integer posX = positions.getKey() - 1;
        final Integer posY = positions.getValue() - 1;

        return game.getBoard().getCells()[posY][posX];
    }
}
