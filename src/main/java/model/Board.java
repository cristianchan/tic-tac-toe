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

        for (Cell[] row : cells) {
            int x = 0;

            for (Cell cell : row) {
                cell = new Cell(x, y);
                cells[y][x] = cell;
                x++;
            }

            y++;
        }
    }

    public Integer getSize() {
        return size;
    }
}
