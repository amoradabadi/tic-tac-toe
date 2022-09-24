package apprenticeship.model;

import apprenticeship.Board;
import apprenticeship.error.QuitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HumanPlayerTest {

    private Scanner scanner;
    private Board board;

    @BeforeEach
    void setUp() {
        scanner = mock(Scanner.class);
        board = mock(Board.class);
    }

    @Test
    void whenSelectAValidCell_shouldReturnCell() throws QuitException {
        when(scanner.next()).thenReturn("1,1");

        HumanPlayer humanPlayer = new HumanPlayer(scanner, "X", board);
        assertEquals(new Cell(1, 1), humanPlayer.getNextMove());
    }

}