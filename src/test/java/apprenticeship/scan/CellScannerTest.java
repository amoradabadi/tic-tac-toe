package apprenticeship.scan;

import apprenticeship.Board;
import apprenticeship.error.QuitException;
import apprenticeship.model.Cell;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_CELL_FULL_VALUE;
import static apprenticeship.Constants.INVALID_CELL_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CellScannerTest {
    private Scanner scanner;
    private CellScanner cellScanner;
    private ByteArrayOutputStream systemOut;
    private Board board;

    @BeforeEach
    void setUp() {
        systemOut = new ByteArrayOutputStream();
        scanner = mock(Scanner.class);
        board = mock(Board.class);
        cellScanner = new CellScanner(scanner, board);
        System.setOut(new PrintStream(systemOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void whenQ_shouldThrowException() {
        when(scanner.next()).thenReturn("q");

        assertThrows(QuitException.class, () -> cellScanner.getCell());
    }

    @Test
    void whenInputIsValid_shouldReturnCell() throws QuitException {
        when(scanner.next()).thenReturn("1,1");
        when(board.isFull(new Cell("1", "1"))).thenReturn(false);

        assertEquals(new Cell("1", "1"), cellScanner.getCell());
    }

    @Test
    void whenInputLengthIsInvalid_shouldReturnCell() throws QuitException {
        when(scanner.next()).thenReturn("1,2,3", "1,1");
        when(board.isFull(new Cell("1", "1"))).thenReturn(false);

        cellScanner.getCell();

        assertTrue(systemOut.toString().contains(INVALID_CELL_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1", "1,0", "1,4", "4,1"})
    void whenInvalidCellRange_shouldReturnError(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput, "1,1");
        when(board.isFull(new Cell("1", "1"))).thenReturn(false);

        cellScanner.getCell();

        assertTrue(systemOut.toString().contains(INVALID_CELL_VALUE));
    }

    @Test
    void whenBoardIsFull_shouldReturnCell() throws QuitException {
        Cell cell = new Cell("1", "1");

        when(scanner.next()).thenReturn("1,1", "1,2");
        when(board.isFull(cell)).thenReturn(true);

        cellScanner.getCell();

        assertTrue(systemOut.toString().contains(INVALID_CELL_FULL_VALUE));
    }

}