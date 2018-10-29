package service;

import model.Game;
import model.Player;

public class GameService {
    public void setPosition(final Game game, final Integer x, final Integer y, final Player player) {
        game.getBoard().getCells()[x][y].setSelected(player);
    }
}