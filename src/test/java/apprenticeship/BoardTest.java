package apprenticeship;

import apprenticeship.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private final Cell firstCell = new Cell("1", "1");
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void whenTableIsEmpty_shouldReturnTableString() {
        assertEquals("""
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+""", board.toTableString());
    }

    @Test
    void whenTableHasData_shouldReturnTableString() {
        board.setCellValue(firstCell, "X");

        assertEquals("""
                +---+---+---+
                | X |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+""", board.toTableString());
    }

    @Test
    void whenCellNotEmpty_shouldReturnFalse() {
        assertFalse(board.isFull(firstCell));
    }

    @Test
    void whenCellIsEmpty_shouldReturnTrue() {
        board.setCellValue(firstCell, "X");

        assertTrue(board.isFull(firstCell));
    }

    @Test
    void whenHasEmptySpace_shouldReturnTrue() {
        assertTrue(board.hasEmptySpace());
    }

    @Test
    void whenHasNotEmptySpace_shouldReturnTrue() {
        board.setCellValue(firstCell, "X");
        board.setCellValue(new Cell("1", "2"), "X");
        board.setCellValue(new Cell("1", "3"), "X");
        board.setCellValue(new Cell("2", "1"), "X");
        board.setCellValue(new Cell("2", "2"), "X");
        board.setCellValue(new Cell("2", "3"), "X");
        board.setCellValue(new Cell("3", "1"), "X");
        board.setCellValue(new Cell("3", "2"), "X");
        board.setCellValue(new Cell("3", "3"), "X");

        assertFalse(board.hasEmptySpace());
    }

    @Test
    void whenEqualBackDiagonal_returnTrue() {
        board.setCellValue(new Cell("1", "3"), "X");
        board.setCellValue(new Cell("2", "2"), "X");
        board.setCellValue(new Cell("3", "1"), "X");

        assertTrue(board.hasEqualBackDiagonal());
    }

    @Test
    void whenNotEqualBackDiagonal_returnFalse() {
        board.setCellValue(new Cell("1", "3"), "X");
        board.setCellValue(new Cell("2", "2"), "X");

        assertFalse(board.hasEqualBackDiagonal());
    }

    @Test
    void whenEqualDiagonal_returnTrue() {
        board.setCellValue(firstCell, "X");
        board.setCellValue(new Cell("2", "2"), "X");
        board.setCellValue(new Cell("3", "3"), "X");

        assertTrue(board.hasEqualDiagonal());
    }

    @Test
    void whenNotEqualDiagonal_returnFalse() {
        board.setCellValue(new Cell("2", "2"), "X");
        board.setCellValue(new Cell("3", "3"), "X");

        assertFalse(board.hasEqualDiagonal());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void whenEqualItemsInRow_returnTrue(String row) {
        board.setCellValue(new Cell(row, "1"), "X");
        board.setCellValue(new Cell(row, "2"), "X");
        board.setCellValue(new Cell(row, "3"), "X");

        assertTrue(board.hasEqualItemsInRowOrCol());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void whenNotEqualItemsInRow_returnTrue(String row) {
        board.setCellValue(new Cell(row, "1"), "X");
        board.setCellValue(new Cell(row, "2"), "X");

        assertFalse(board.hasEqualItemsInRowOrCol());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void whenEqualItemsInColumn_returnTrue(String col) {
        board.setCellValue(new Cell("1", col), "X");
        board.setCellValue(new Cell("2", col), "X");
        board.setCellValue(new Cell("3", col), "X");

        assertTrue(board.hasEqualItemsInRowOrCol());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void whenEqualItemsInColumn_returnFalse(String col) {
        board.setCellValue(new Cell("1", col), "X");
        board.setCellValue(new Cell("2", col), "X");

        assertFalse(board.hasEqualItemsInRowOrCol());
    }

}