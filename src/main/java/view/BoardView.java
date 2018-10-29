package view;

import model.Board;
import model.Cell;

import static java.lang.System.out;
import static model.Cell.Status.UNSELECTED;

public class BoardView {
    public void drawBoard(final Board board) {
        int x = 0;
        final int headerSize = board.getSize();

        out.print("  ");
        for (int i = 1; i <= headerSize; i++) {
            out.print("   " + i);
        }

        out.println();

        for (Cell[] column : board.getCells()) {
            x++;
            out.print(x < 10 ? x + "  | " : x + " | ");
            for (Cell cell : column) {
                out.print(cell.getStatus() == UNSELECTED ? "  | " : cell.getPlayer().getSymbol() + " | ");
            }
            out.println("  ");
        }
    }

    public void drawBoardSizeSelection() {
        out.println("Select size of board should be 3 to 10");
    }

    public void drawPlayerSelectPosition(final Integer number) {
        out.println("Select player" + number + "name");
    }
}
