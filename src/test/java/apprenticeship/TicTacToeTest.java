package apprenticeship;

import apprenticeship.model.Cell;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeTest {
    private static final String NEW_LINE = "\n";
    private static final String QUIT = "q" + NEW_LINE;
    private static final String SELECT_HUMAN = "h" + NEW_LINE;
    private static final String SELECT_COMPUTER = "c" + NEW_LINE;
    private static final String EXIT = "Bye" + NEW_LINE;
    private static final String INSTRUCTIONS = "Welcome to Tic-Tac-Toe" + NEW_LINE + NEW_LINE +
                                               "To choose a cell: i,j like 1,2" + NEW_LINE +
                                               "To exit: enter q" + NEW_LINE +
                                               "To begin: choose who starts the game, x or o." + NEW_LINE + NEW_LINE;
    private static final String ERROR_INVALID_VALUE_FORMAT = "Invalid value, format is x,y where x and y should be between 1 and 3" + NEW_LINE;
    private static final String ERROR_INVALID_VALUE = "Invalid value, To begin, choose who starts the game by entering X or O." + NEW_LINE;
    private static final String ERROR_INVALID_PLAYER_VALUE = "Invalid value, select c for computer and h for Human player" + NEW_LINE;
    private static final String PLAYER_X_INPUT = "Player X: " + NEW_LINE;
    private static final String PLAYER_O_INPUT = "Player O: " + NEW_LINE;
    private static final String ALREADY_SELECTED = "Cell has already selected, choose another one" + NEW_LINE;

    private static final String COMPUTER_HUMAN_O = "Is 'o' a computer (c) or a Human (h) ?" + NEW_LINE;
    private static final String COMPUTER_HUMAN_X = "Is 'x' a computer (c) or a Human (h) ?" + NEW_LINE;

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
        Scanner scanner = new Scanner(userInput + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    private String fillTable() {
        return fillTable(new HashMap<>());
    }

    private String fillTable(Map<Cell, String> map) {
        return "+---+---+---+\n" +
               "| " + fill(map, 1, 1) + " | " + fill(map, 1, 2) + " | " + fill(map, 1, 3) + " |\n" +
               "+---+---+---+\n" +
               "| " + fill(map, 2, 1) + " | " + fill(map, 2, 2) + " | " + fill(map, 2, 3) + " |\n" +
               "+---+---+---+\n" +
               "| " + fill(map, 3, 1) + " | " + fill(map, 3, 2) + " | " + fill(map, 3, 3) + " |\n" +
               "+---+---+---+\n";
    }

    private static String fill(Map<Cell, String> map, int i, int j) {
        return map.getOrDefault(new Cell(i, j), " ");
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenWithDifferentCase_shouldSelectO(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + QUIT);

        this.ticTacToe.start(scanner);


        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_O +
                     COMPUTER_HUMAN_X +
                     fillTable() +
                     PLAYER_O_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenInvalidMarker_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + ERROR_INVALID_VALUE + EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1", "1,0", "1,4", "4,1"})
    void whenInvalidCellRange_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner("X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     ERROR_INVALID_VALUE_FORMAT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenInvalidCell_shouldReturnError() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + "anything" + NEW_LINE + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     ERROR_INVALID_VALUE_FORMAT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerO() {
        String userInput = "O" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_O +
                     COMPUTER_HUMAN_X +
                     fillTable() +
                     PLAYER_O_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerX() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenCellAlreadySelected_shouldReturnError() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
                           "1,1" + NEW_LINE + // X
                           "1,1" + NEW_LINE + // O
                           QUIT;

        Scanner scanner = new Scanner(userInput);
        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     playerSelected("x", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "x")) +
                     PLAYER_O_INPUT +
                     ALREADY_SELECTED +
                     EXIT, SYSTEM_OUT.toString());
    }

    private String playerSelected(String p, int i, int j) {
        return "Player '" + p + "' selected " + i + "," + j + NEW_LINE;
    }

    @Test
    void whenFirstRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_HUMAN +
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
        assertEquals(INSTRUCTIONS + EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenComputerIsPlayingAndBoardEmpty_shouldSelectTopLeft() {
        String userInput = "X" + NEW_LINE + SELECT_COMPUTER + SELECT_HUMAN + QUIT;

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     playerSelected("x", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "x")) +
                     PLAYER_O_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenComputerIsPlayingAndBoardEmpty_shouldNotOverwrite() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN + SELECT_COMPUTER
                           + "1,1" + NEW_LINE + // X
                           QUIT;

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     COMPUTER_HUMAN_X +
                     COMPUTER_HUMAN_O +
                     fillTable() +
                     PLAYER_X_INPUT +
                     playerSelected("x", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "x")) +
                     PLAYER_O_INPUT +
                     playerSelected("o", 1, 2) +
                     fillTable(Map.of(new Cell(1, 1), "x", new Cell(1, 2), "o")) +
                     PLAYER_X_INPUT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenInvalidPlayer_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner("X" + NEW_LINE + userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS + COMPUTER_HUMAN_X + ERROR_INVALID_PLAYER_VALUE + NEW_LINE + EXIT, SYSTEM_OUT.toString());
    }

}