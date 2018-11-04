package view;

import model.Board;
import model.HumanPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.HumanPlayerService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BoardViewTest {
    private static final Integer BOARD_SIZE = 5;

    private BoardView subject;

    private ByteArrayOutputStream outContent;
    private Random random;

    @Mock
    private HumanPlayerService humanPlayerService;


    @Before
    public void setUp() {
        random = new Random();
        subject = new BoardView();
        outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void drawBoard_EmptyBoard_DrawBoard() {
        final Board board = new Board(BOARD_SIZE);

        final String boardDraw = "     1   2   3   4   5\n" +
                "1  |   |   |   |   |   |   \n" +
                "2  |   |   |   |   |   |   \n" +
                "3  |   |   |   |   |   |   \n" +
                "4  |   |   |   |   |   |   \n" +
                "5  |   |   |   |   |   |   \n";

        subject.printBoard(board);

        assertThat(boardDraw, is(outContent.toString()));
    }

    @Test
    public void drawBoard_CellSelected_DrawBoard() {
        final Board board = new Board(BOARD_SIZE);
        final String name = randomAlphabetic(10);
        final Integer posX = 2;
        final Integer posY = 2;
        final HumanPlayer player = new HumanPlayer(name, "X", humanPlayerService);

        board.getCells()[posY][posX].setSelected(player);

        final String boardDraw = "     1   2   3   4   5\n" +
                "1  |   |   |   |   |   |   \n" +
                "2  |   |   |   |   |   |   \n" +
                "3  |   |   | X |   |   |   \n" +
                "4  |   |   |   |   |   |   \n" +
                "5  |   |   |   |   |   |   \n";

        subject.printBoard(board);

        assertThat(boardDraw, is(outContent.toString()));
    }

    @Test
    public void printDrawMessage_Call_PrintMessage() {
        subject.printDrawMessage();

        assertThat(outContent.toString(), is("\nSo sad!!!!! is a draw\n"));
    }

    @Test
    public void printWinMessage_Call_PrintMessage() {
        final String playerName = randomAlphabetic(10);

        subject.printWinMessage(playerName);

        assertThat(outContent.toString(), is("\nCongrats!! " + playerName + " Win\n"));
    }

    @Test
    public void printSelectPositionMessage_Call_PrintMessage() {
        final String playerName = randomAlphabetic(10);

        subject.printSelectPositionMessage(playerName);

        assertThat(outContent.toString(), is("\n" + playerName + " Select X,Y position\n"));
    }

    @Test
    public void printInputFormatError_Call_PrintMessage() {
        subject.printInputFormatError();

        assertThat(outContent.toString(), is("\nInvalid  input format try again\n"));
    }

    @Test
    public void printNumberFormatError_Call_PrintMessage() {
        final String error = randomAlphabetic(10);

        subject.printNumberFormatError(error);

        assertThat(outContent.toString(), is("\nInvalid number " + error + " try again\n"));
    }

    @Test
    public void getNumberGreaterThanZeroErrorMessage_Call_ReturnMessage() {
        final String message = subject.getNumberGreaterThanZeroErrorMessage();

        assertThat(message, is("The number should by greater than 0"));
    }

    @Test
    public void getNumberBiggerThanBoardSizeMessage_Call_ReturnMessage() {
        final String message = subject.getNumberBiggerThanBoardSizeMessage();

        assertThat(message, is("Is bigger than board size"));
    }
}
