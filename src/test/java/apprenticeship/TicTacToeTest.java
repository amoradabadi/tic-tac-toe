package apprenticeship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static apprenticeship.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicTacToeTest {
    public static final String INVALID_MARKER_DUPLICATE_VALUE = "Marker has already selected, choose another one";
    private static final String NEW_LINE = "\n";
    private static final String QUIT = "q" + NEW_LINE;
    private static final String EXIT = "Bye" + NEW_LINE;
    private static final String INSTRUCTIONS = """
            Welcome to Tic-Tac-Toe
                            
            To choose a cell: i,j like 1,2
            To exit: enter q
            """;
    private static final String CHOOSE_MARKER = "Choose a marker for player %d:" + NEW_LINE;
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
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,3", "1,2", "2,2", "2,1", "3,1");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().endsWith("Player 'X' has won."));
    }

    @Test
    void whenComputerVsComputer_firstPlayerWins() {
        when(scanner.next()).thenReturn("X", "c", "O", "c");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().endsWith("Player 'X' has won."));
    }

    @Test
    void whenNoWinner_shouldDraw() {
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,2", "1,1", "2,1", "1,3", "2,2", "2,3", "3,1", "3,2", "3,3");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains("No winner, game is draw."));
    }

    @Test
    void test_main() {
        System.setIn(new ByteArrayInputStream(QUIT.getBytes()));
        TicTacToe.main(new String[]{});
        assertEquals(INSTRUCTIONS + NEW_LINE + CHOOSE_MARKER.formatted(1) + EXIT, systemOut.toString());
    }

    @Test
    void whenInputLengthIsInvalid_shouldReturnCell() {
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,2,3", "1,2", "1,1", "2,1", "1,3", "2,2", "2,3", "3,1", "3,2", "3,3");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains(INVALID_CELL_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1", "1,0", "1,4", "4,1"})
    void whenInvalidCellRange_shouldReturnError(String userInput) {
        when(scanner.next()).thenReturn("X", "h", "O", "h", userInput, "q");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains(INVALID_CELL_VALUE));
    }

    @Test
    void whenBoardIsFull_shouldReturnCell() {
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,1", "1,1", "q");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains(INVALID_CELL_FULL_VALUE));
    }

    @Test
    void whenMarkerIsAlreadySelected_shouldReturnCell() {
        when(scanner.next()).thenReturn("X", "h", "X", "q");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains(INVALID_MARKER_DUPLICATE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "hh", "cc"})
    void whenInvalidPlayerType_shouldReturnError(String userInput) {
        when(scanner.next()).thenReturn("X", userInput, "q");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().contains(INVALID_PLAYER_TYPE_VALUE));
    }

    @Test
    void whenEqualItemsInRow_returnShouldWin() {
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,1", "2,1", "1,2", "3,3", "1,3");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().endsWith("Player 'X' has won."));
    }

    @Test
    void whenEqualItemsInCol_returnShouldWin() {
        when(scanner.next()).thenReturn("X", "h", "O", "h", "1,1", "2,2", "2,1", "3,3", "3,1");

        this.ticTacToe.start(scanner);

        assertTrue(systemOut.toString().endsWith("Player 'X' has won."));
    }

}