package apprenticeship.model;

import apprenticeship.Board;
import apprenticeship.error.QuitException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ComputerPlayerTest {

    @Test
    void whenBoardEmpty_shouldSelectTopLeft() throws QuitException {
        Board board = new Board();
        ComputerPlayer computerPlayer = new ComputerPlayer(Marker.X, board);
        assertEquals(new Cell(1, 1), computerPlayer.getNextMove());
    }

    @Test
    void whenTopLeftIsFull_shouldSelectNext() throws QuitException {
        Board board = new Board();
        board.setCellValue(new Cell(1, 1), 'o');
        ComputerPlayer computerPlayer = new ComputerPlayer(Marker.X, board);
        assertEquals(new Cell(1, 2), computerPlayer.getNextMove());
    }

    @Test
    void whenBoardIsFull_shouldReturnNull() throws QuitException {
        Board board = new Board();
        board.setCellValue(new Cell(1, 1), 'x');
        board.setCellValue(new Cell(1, 2), 'o');
        board.setCellValue(new Cell(1, 3), 'o');
        board.setCellValue(new Cell(2, 1), 'o');
        board.setCellValue(new Cell(2, 2), 'x');
        board.setCellValue(new Cell(2, 3), 'o');
        board.setCellValue(new Cell(3, 1), 'x');
        board.setCellValue(new Cell(3, 2), 'x');
        board.setCellValue(new Cell(3, 3), 'o');
        ComputerPlayer computerPlayer = new ComputerPlayer(Marker.X, board);
        assertNull(computerPlayer.getNextMove());
    }

}