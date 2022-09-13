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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeTest {
    private static final String NEW_LINE = "\n";
    private static final String QUIT = "q" + NEW_LINE;
    private static final String EXIT = "Bye" + NEW_LINE;
    private static final String INSTRUCTIONS = "Welcome to Tic-Tac-Toe" +
            NEW_LINE +
            NEW_LINE +
            "To select a cell for your move enter the position of the row and the column, separated with ," + NEW_LINE +
            "Example: 1,3 puts the sign in the first row and third column." + NEW_LINE +
            NEW_LINE +
            "To begin, choose who starts the game by entering X or O." + NEW_LINE +
            "To exit the game press q" + NEW_LINE;
    private static final String ERROR_INVALID_VALUE_FORMAT = "Invalid value, format is x,y where x and y should be between 1 and 3" + NEW_LINE;
    private static final String ERROR_INVALID_VALUE = "Invalid value, To begin, choose who starts the game by entering X or O." + NEW_LINE;
    private static final String PLAYER_X = "Player X: ";
    private static final String PLAYER_O = "Player O: ";
    private static final String EMPTY_TABLE = """
            +---+---+---+
            |   |   |   |
            +---+---+---+
            |   |   |   |
            +---+---+---+
            |   |   |   |
            +---+---+---+""";
    private static final String NON_EMPTY_TABLE = """
            +---+---+---+
            | X |   |   |
            +---+---+---+
            |   |   |   |
            +---+---+---+
            |   |   |   |
            +---+---+---+""";

    private TicTacToe ticTacToe;
    private ByteArrayOutputStream SYSTEM_OUT;

    @BeforeEach
    void setup() {
        SYSTEM_OUT = new ByteArrayOutputStream();
        System.setOut(new PrintStream(SYSTEM_OUT));
        this.ticTacToe = new TicTacToe();
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "X", " X", "X ", " X "})
    void whenWithDifferentCase_shouldSelectX(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_X + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenWithDifferentCase_shouldSelectO(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_O + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenInvalidMarker_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                ERROR_INVALID_VALUE +
                EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1", "1,0", "1,4", "4,1"})
    void whenInvalidCellRange_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner("X" + NEW_LINE +
                userInput + NEW_LINE
                + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_X + NEW_LINE +
                ERROR_INVALID_VALUE_FORMAT +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenInvalidCell_shouldReturnError() {
        String userInput = "X" + NEW_LINE +
                "anything" + NEW_LINE +
                QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_X + NEW_LINE +
                ERROR_INVALID_VALUE_FORMAT +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerO() {
        String userInput = "O" + NEW_LINE + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_O + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerX() {
        String userInput = "X" + NEW_LINE + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_X + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenCellAlreadySelected_shouldReturnError() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                QUIT;

        Scanner scanner = new Scanner(userInput);
        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                EMPTY_TABLE + NEW_LINE +
                PLAYER_X + NEW_LINE +
                NON_EMPTY_TABLE + NEW_LINE +
                PLAYER_O + NEW_LINE +
                "Cell has already selected, choose another one" + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenFirstRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "1,2" + NEW_LINE + // X
                "2,3" + NEW_LINE + // O
                "1,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));

    }

    @Test
    void whenSecondRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "2,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenThirdRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "3,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "3,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }


    @Test
    void whenFirstColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "2,3" + NEW_LINE + // O
                "2,1" + NEW_LINE + // X
                "2,2" + NEW_LINE + // O
                "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenSecondColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "2,1" + NEW_LINE + // X
                "3,1" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "3,2" + NEW_LINE + // O
                "2,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenThirdColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "3,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "3,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenDiagonalIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenBackDiagonalIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE +
                "1,3" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player X has won." + NEW_LINE));
    }

    @Test
    void whenNoWinner_shouldDraw() {
        String userInput = "X" + NEW_LINE +
                "1,2" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "2,1" + NEW_LINE + // X
                "1,3" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "2,3" + NEW_LINE + // O
                "3,1" + NEW_LINE + // X
                "3,2" + NEW_LINE + // O
                "3,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("No winner, game is draw." + NEW_LINE));
    }

    @Test
    void test_main() {
        System.setIn(new ByteArrayInputStream("q\n".getBytes()));
        TicTacToe.main(new String[]{});
        assertEquals(INSTRUCTIONS + NEW_LINE + EXIT, SYSTEM_OUT.toString());
    }

}