package model;

import static model.Cell.Status.SELECTED;
import static model.Cell.Status.UNSELECTED;

public class Cell {
    private final Integer x;
    private final Integer y;
    private Status status;
    private Player player;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.status = UNSELECTED;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setSelected(final Player player) {
        this.status = SELECTED;
        this.player = player;
    }

    public void setUnSelected() {
        this.status = UNSELECTED;
        this.player = null;
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

