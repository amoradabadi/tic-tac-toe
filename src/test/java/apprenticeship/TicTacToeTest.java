package apprenticeship;

import apprenticeship.model.Cell;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
                                               "To exit: enter q" + NEW_LINE + NEW_LINE;
    private static final String ERROR_INVALID_VALUE_FORMAT = "Invalid value, format is i,j where i and j should be between 1 and 3" + NEW_LINE;
    private static final String PLAYER_INPUT = "Player '%s': " + NEW_LINE;
    private static final String ALREADY_SELECTED = "Cell has already selected, choose another one" + NEW_LINE;
    private static final String COMPUTER_HUMAN = "Is '%s' a computer (c) or a Human (h) ?" + NEW_LINE;
    private static final String CHOOSE_MARKER = "Choose a marker for player %d:" + NEW_LINE;
    private static final String MARKER_ALREADY_SELECTED = "Marker has already selected, choose another one" + NEW_LINE;
    private static final String INVALID_COMPUTER_HUMAN = "Invalid value, select c for computer and h for Human player" + NEW_LINE;

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
    @CsvSource({"x,o", "X,O", " X, O", "X ,O ", " X , O ", "x,O", "X,o"})
    void whenWithDifferentCase_shouldSelectPlayers(String p1, String p2) {
        Scanner scanner = new Scanner(p1 + NEW_LINE + SELECT_HUMAN +
                                      p2 + NEW_LINE + SELECT_HUMAN +
                                      QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman(p1) +
                     chooseMarker(2) +
                     computerOrHuman(p2) +
                     fillTable() +
                     playerInput(p1) +
                     EXIT, SYSTEM_OUT.toString());
    }

    private static String chooseMarker(int x) {
        return CHOOSE_MARKER.formatted(x);
    }

    private static String computerOrHuman(String player) {
        return COMPUTER_HUMAN.formatted(player);
    }

    private String fillTable() {
        return fillTable(new HashMap<>());
    }

    private static String playerInput(String player) {
        return PLAYER_INPUT.formatted(player);
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
    @CsvSource({"ab,cd"})
    void whenMoreThanOneChar_shouldSelectPlayers(String p1, String p2) {
        Scanner scanner = new Scanner(p1 + NEW_LINE + SELECT_HUMAN +
                                      p2 + NEW_LINE + SELECT_HUMAN +
                                      QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman(p1) +
                     chooseMarker(2) +
                     computerOrHuman(p2) +
                     fillBiggerTable(new HashMap<>()) +
                     playerInput(p1) +
                     EXIT, SYSTEM_OUT.toString());
    }

    private String fillBiggerTable(Map<Cell, String> map) {
        return "+----+----+----+\n" +
               "| " + fill(map, 1, 1) + "  | " + fill(map, 1, 2) + "  | " + fill(map, 1, 3) + "  |\n" +
               "+----+----+----+\n" +
               "| " + fill(map, 2, 1) + "  | " + fill(map, 2, 2) + "  | " + fill(map, 2, 3) + "  |\n" +
               "+----+----+----+\n" +
               "| " + fill(map, 3, 1) + "  | " + fill(map, 3, 2) + "  | " + fill(map, 3, 3) + "  |\n" +
               "+----+----+----+\n";
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1", "1,0", "1,4", "4,1", "1,2,3"})
    void whenInvalidCellRange_shouldReturnError(String userInput) {
        Scanner scanner = new Scanner("X" + NEW_LINE + SELECT_HUMAN +
                                      "O" + NEW_LINE + SELECT_HUMAN +
                                      userInput + NEW_LINE + QUIT);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("X") +
                     chooseMarker(2) +
                     computerOrHuman("O") +
                     fillTable() +
                     playerInput("X") +
                     ERROR_INVALID_VALUE_FORMAT +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerO() {
        String userInput = "O" + NEW_LINE + SELECT_HUMAN +
                           "X" + NEW_LINE + SELECT_HUMAN +
                           QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("O") +
                     chooseMarker(2) +
                     computerOrHuman("X") +
                     fillTable() +
                     playerInput("O") +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsAlreadySelected_shouldReturnError() {
        String userInput = "O" + NEW_LINE + SELECT_HUMAN +
                           "o" + NEW_LINE +
                           QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("O") +
                     chooseMarker(2) +
                     MARKER_ALREADY_SELECTED +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenComputerOrHumanIsWrong_shouldReturnError() {
        String userInput = "O" + NEW_LINE +
                           "x" + NEW_LINE +
                           QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("O") +
                     INVALID_COMPUTER_HUMAN +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenMarkerIsValid_shouldSelectPlayerX() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("X") +
                     chooseMarker(2) +
                     computerOrHuman("O") +
                     fillTable() +
                     playerInput("X") +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenCellAlreadySelected_shouldReturnError() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "1,1" + NEW_LINE + // X
                           "1,1" + NEW_LINE + // O
                           QUIT;

        Scanner scanner = new Scanner(userInput);
        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("X") +
                     chooseMarker(2) +
                     computerOrHuman("O") +
                     fillTable() +
                     playerInput("X") +
                     playerSelected("X", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "X")) +
                     playerInput("O") +
                     ALREADY_SELECTED +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenFirstRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "1,1" + NEW_LINE + // X
                           "2,1" + NEW_LINE + // O
                           "1,2" + NEW_LINE + // X
                           "2,3" + NEW_LINE + // O
                           "1,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));

    }

    @Test
    void whenSecondRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "2,1" + NEW_LINE + // X
                           "1,1" + NEW_LINE + // O
                           "2,2" + NEW_LINE + // X
                           "1,2" + NEW_LINE + // O
                           "2,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenThirdRowIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "3,1" + NEW_LINE + // X
                           "1,1" + NEW_LINE + // O
                           "3,2" + NEW_LINE + // X
                           "1,2" + NEW_LINE + // O
                           "3,3" + NEW_LINE; // X

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenFirstColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "1,1" + NEW_LINE + // X
                           "2,3" + NEW_LINE + // O
                           "2,1" + NEW_LINE + // X
                           "2,2" + NEW_LINE + // O
                           "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenSecondColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "2,1" + NEW_LINE + // X
                           "3,1" + NEW_LINE + // O
                           "2,2" + NEW_LINE + // X
                           "3,2" + NEW_LINE + // O
                           "2,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenThirdColIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "3,1" + NEW_LINE + // X
                           "1,1" + NEW_LINE + // O
                           "3,2" + NEW_LINE + // X
                           "1,2" + NEW_LINE + // O
                           "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenDiagonalIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "1,1" + NEW_LINE + // X
                           "1,2" + NEW_LINE + // O
                           "2,2" + NEW_LINE + // X
                           "2,1" + NEW_LINE + // O
                           "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenBackDiagonalIsFull_shouldWin() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           "1,3" + NEW_LINE + // X
                           "1,2" + NEW_LINE + // O
                           "2,2" + NEW_LINE + // X
                           "2,1" + NEW_LINE + // O
                           "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertTrue(SYSTEM_OUT.toString().endsWith("Player 'X' has won." + NEW_LINE));
    }

    @Test
    void whenNoWinner_shouldDraw() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_HUMAN +
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
        assertEquals(INSTRUCTIONS + chooseMarker(1) + EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenComputerIsPlayingAndBoardEmpty_shouldSelectTopLeft() {
        String userInput = "X" + NEW_LINE + SELECT_COMPUTER +
                           "O" + NEW_LINE + SELECT_HUMAN +
                           QUIT;

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("X") +
                     chooseMarker(2) +
                     computerOrHuman("O") +
                     fillTable() +
                     playerInput("X") +
                     playerSelected("X", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "X")) +
                     playerInput("O") +
                     EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenComputerIsPlayingAndBoardEmpty_shouldNotOverwrite() {
        String userInput = "X" + NEW_LINE + SELECT_HUMAN +
                           "O" + NEW_LINE + SELECT_COMPUTER +
                           "1,1" + NEW_LINE + // X
                           QUIT;

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.start(scanner);

        assertEquals(INSTRUCTIONS +
                     chooseMarker(1) +
                     computerOrHuman("X") +
                     chooseMarker(2) +
                     computerOrHuman("O") +
                     fillTable() +
                     playerInput("X") +
                     playerSelected("X", 1, 1) +
                     fillTable(Map.of(new Cell(1, 1), "X")) +
                     playerInput("O") +
                     playerSelected("O", 1, 2) +
                     fillTable(Map.of(new Cell(1, 1), "X", new Cell(1, 2), "O")) +
                     playerInput("X") +
                     EXIT, SYSTEM_OUT.toString());
    }

    private String playerSelected(String p, int i, int j) {
        return "Player '" + p + "' selected " + i + "," + j + NEW_LINE;
    }
}