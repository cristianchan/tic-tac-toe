package model;

public class Cell {
    private final Integer x;
    private final Integer y;
    private Status status;
    private Player player;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.status = Status.UNSELECTED;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setSelected(final Player player) {
        this.status = Status.SELECTED;
        this.player = player;
    }

    public Status getStatus() {
        return status;
    }

    public Player getPlayer() {
        return player;
    }

    public enum Status {
        SELECTED, UNSELECTED
    }
}

