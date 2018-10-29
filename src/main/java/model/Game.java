package model;

public class Game {
    private final Board board;
    private final Player[] players;

    public Game(Board board) {
        this.board = board;
        this.players = new Player[3];

        players[0] = new Player("Player 1", Player.Symbol.X);
        players[1] = new Player("Player 1", Player.Symbol.O);
        players[2] = new Player("Player 1", Player.Symbol.Z);
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }
}
