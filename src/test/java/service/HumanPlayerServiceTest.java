package service;

import javafx.util.Pair;
import model.Board;
import model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import view.BoardView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

import static factory.GameFactory.getGame;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class HumanPlayerServiceTest {
    private static final String ERROR_MESSAGE = "ERROR";

    @InjectMocks
    private HumanPlayerService subject;

    @Mock
    private CpuPlayerService cpuPlayerService;

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private BoardView boardView;

    @Test
    public void getPositions_ValidInput_Pair() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1,1");

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(1, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader).readLine();
    }

    @Test
    public void getPositions_PosXNumberGreaterThanZero_PrintNumberFormatError() throws IOException {
        when(bufferedReader.readLine()).thenReturn("0,1").thenReturn("2,1");
        when(boardView.getNumberGreaterThanZeroErrorMessage()).thenReturn(ERROR_MESSAGE);

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(2, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader, times(2)).readLine();
        verify(boardView).printNumberFormatError(ERROR_MESSAGE);
        verify(boardView).getNumberGreaterThanZeroErrorMessage();
    }

    @Test
    public void getPositions_PosYNumberBiggerThanBoardSize_PrintNumberFormatError() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1,6").thenReturn("2,1");
        when(boardView.getNumberBiggerThanBoardSizeMessage()).thenReturn(ERROR_MESSAGE);

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(2, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader, times(2)).readLine();
        verify(boardView).printNumberFormatError(ERROR_MESSAGE);
        verify(boardView).getNumberBiggerThanBoardSizeMessage();
    }

    @Test
    public void getPositions_PosYNotNumeric_PrintNumberFormatError() throws IOException {
        when(bufferedReader.readLine()).thenReturn("a,6").thenReturn("2,1");

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(2, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader, times(2)).readLine();
        verify(boardView).printNumberFormatError(anyString());
    }

    @Test
    public void getPositions_PosXNotNumeric_PrintNumberFormatError() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1,b").thenReturn("2,1");

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(2, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader, times(2)).readLine();
        verify(boardView).printNumberFormatError(anyString());
    }

    @Test
    public void getPositions_InvalidInput_TryTwice() throws IOException {
        when(bufferedReader.readLine()).thenReturn("abc49a").thenReturn("3,1");

        final Game game = getGame(subject, cpuPlayerService);
        final Pair<Integer, Integer> actualPosition = subject.getPositions(game);

        final Pair<Integer, Integer> expectedPosition = new Pair<>(3, 1);
        assertThat(actualPosition, is(expectedPosition));

        verify(bufferedReader, times(2)).readLine();
        verify(boardView).printInputFormatError();
    }
}