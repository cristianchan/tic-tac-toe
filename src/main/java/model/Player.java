package model;

import java.io.IOException;

public abstract class Player {
    private final String name;
    private final String symbol;

    protected Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public abstract Cell getCell(final Game game) throws IOException;

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

}
