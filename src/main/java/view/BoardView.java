package view;

import model.Board;
import model.Cell;

import static java.lang.System.out;
import static model.Cell.Status.UNSELECTED;

public class BoardView {
    public void printBoard(final Board board) {
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

    public void printDrawMessage(){
        out.println();
        out.println("So sad!!!!! is a draw");
    }

    public void printWinMessage(final String playerName){
        out.println();
        out.println("Congrats!! " + playerName + " Win");
    }

    public void printSelectPositionMessage(final String playerName) {
        out.println();
        out.println(playerName + " Select X,Y position");
    }

    public void printCellSelectedMessage() {
        out.println();
        out.println("The cell is selected");
    }

    public void printInputFormatError() {
        out.println();
        out.println("Invalid  input format try again");
    }

    public void printNumberFormatError(final String error) {
        out.println();
        out.println("Invalid number " + error + " try again");
    }

    public String getNumberGreaterThanZeroErrorMessage() {
        return "The number should by greater than 0";
    }

    public String getNumberBiggerThanBoardSizeMessage() {
        return "Is bigger than board size";
    }
}
