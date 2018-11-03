package view;

import model.Board;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BoardViewTest {
    private static final Integer BOARD_SIZE = 5;

    private BoardView subject;

    private ByteArrayOutputStream outContent;
    private Random random;

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

        subject.drawBoard(board);

        assertThat(boardDraw, is(outContent.toString()));
    }

    @Test
    public void drawBoard_CellSelected_DrawBoard() {
        final Board board = new Board(BOARD_SIZE);
        final String name = randomAlphabetic(10);
        final Integer posX = 2;
        final Integer posY = 2;
        final Player player = new Player(name, "X");

        board.getCells()[posY][posX].setSelected(player);

        final String boardDraw = "     1   2   3   4   5\n" +
                "1  |   |   |   |   |   |   \n" +
                "2  |   |   |   |   |   |   \n" +
                "3  |   |   | X |   |   |   \n" +
                "4  |   |   |   |   |   |   \n" +
                "5  |   |   |   |   |   |   \n";

        subject.drawBoard(board);

        assertThat(boardDraw, is(outContent.toString()));
    }
}
