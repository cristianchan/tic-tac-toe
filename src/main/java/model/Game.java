package model;

import service.CpuPlayerService;
import service.HumanPlayerService;

import java.util.Properties;

public class Game {
    private final Board board;
    private final Player[] players;
    private final HumanPlayerService humanPlayerService;
    private final CpuPlayerService cpuPlayerService;

    public Game(final Board board, final Properties properties, HumanPlayerService humanPlayerService, CpuPlayerService cpuPlayerService) {
        this.board = board;
        this.players = new Player[Integer.parseInt(properties.getProperty("player.number"))];
        this.humanPlayerService = humanPlayerService;
        this.cpuPlayerService = cpuPlayerService;

        players[0] = new HumanPlayer(properties.getProperty("player-one.name"), properties.getProperty("player-one.symbol"), this.humanPlayerService);
        players[1] = new HumanPlayer(properties.getProperty("player-two.name"), properties.getProperty("player-two.symbol"), this.humanPlayerService);

        if (players.length > 2) {
            players[2] = new CpuPlayer(properties.getProperty("player-tree.name"), properties.getProperty("player-tree.symbol"), this.cpuPlayerService);
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }
}
