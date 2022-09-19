package apprenticeship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicTacToeTest {
    private static final String NEW_LINE = "\n";
    private static final String QUIT = "q" + NEW_LINE;
    private static final String EXIT = "Bye" + NEW_LINE;
    private static final String INSTRUCTIONS = "Welcome to Tic-Tac-Toe" + NEW_LINE + NEW_LINE + "To select a cell for your move enter the position of the row and the column, separated with ," + NEW_LINE + "Example: 1,3 puts the sign in the first row and third column." + NEW_LINE + NEW_LINE + "To begin, choose who starts the game by entering X or O." + NEW_LINE + "To exit the game press q" + NEW_LINE;

    private TicTacToe ticTacToe;
    private Scanner scanner;
    private ByteArrayOutputStream systemOut;

    @BeforeEach
    void setUp() {
        systemOut = new ByteArrayOutputStream();
        scanner = mock(Scanner.class);
        ticTacToe = new TicTacToe();
        System.setOut(new PrintStream(systemOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void whenBackDiagonalIsFull_playerXWin() {
        when(scanner.next()).thenReturn("X", "1,3", "1,2", "2,2", "2,1", "3,1");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenNoWinner_shouldDraw() {
        when(scanner.next()).thenReturn("X", "1,2", "1,1", "2,1", "1,3", "2,2", "2,3", "3,1", "3,2", "3,3");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains("No winner, game is draw."));
    }

    @Test
    void test_main() {
        System.setIn(new ByteArrayInputStream(QUIT.getBytes()));
        TicTacToe.main(new String[]{});
        assertEquals(INSTRUCTIONS + NEW_LINE + EXIT, systemOut.toString());
    }

}