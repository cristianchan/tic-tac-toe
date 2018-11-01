package model;

import java.util.Properties;

public class Game {
    private final Board board;
    private final Player[] players;

    public Game(final Board board, final Properties properties) {
        this.board = board;
        this.players = new Player[Integer.parseInt(properties.getProperty("player.number"))];

        players[0] = new Player(properties.getProperty("player-one.name"), properties.getProperty("player-one.symbol"));
        players[1] = new Player(properties.getProperty("player-two.name"), properties.getProperty("player-two.symbol"));

        if (players.length > 2) {
            players[2] = new Player(properties.getProperty("player-tree.name"), properties.getProperty("player-tree.symbol"));
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }
}
