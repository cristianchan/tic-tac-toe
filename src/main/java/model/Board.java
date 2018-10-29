package model;

public class Board {
    private final Integer size;
    private final Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    public Board(Integer size) {
        this.size = size;
        this.cells = new Cell[size][size];


        int y = 0;

        for (Cell[] columns : cells) {
            int x = 0;

            for (Cell cell : columns) {
                cell = new Cell(x, y);
                cells[x][y] = cell;
                x++;
            }

            y++;
        }
    }

    public Integer getSize() {
        return size;
    }
}
