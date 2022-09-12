package apprenticeship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeTest {
    private static final String NEW_LINE = "\n";
    private static final String INSTRUCTIONS = "Welcome to Tic-Tac-Toe" +
            NEW_LINE +
            NEW_LINE +
            "To select a cell for your move enter the position of the row and the column, separated with ," + NEW_LINE +
            "Example: 1,3 puts the sign in the first row and third column." + NEW_LINE +
            NEW_LINE +
            "To begin, choose who starts the game by entering X or O." + NEW_LINE +
            "To exit the game press ^C" + NEW_LINE;
    private static final String ERROR_MARKER_LINE = "Invalid value, To begin, choose who starts the game by entering X or O.";
    private static final String ERROR_INVALID_VALUE_FORMAT = "Invalid value, format is x,y where x and y should be more than one and less than 3\n";
    private static final String ERROR_CELL_SELECTED = "Cell has already selected, choose another one\n";
    TicTacToe ticTacToe;
    ByteArrayOutputStream SYSTEM_OUT = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(SYSTEM_OUT));
        this.ticTacToe = new TicTacToe();
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void shouldShowInstructions() {
        assertEquals(INSTRUCTIONS, this.ticTacToe.showInstructions());
    }

    @Test
    void shouldHaveTheCorrectBoardLength() {
        assertEquals(3, this.ticTacToe.board.length);
        assertEquals(3, this.ticTacToe.board[0].length);
    }

    @Test
    void shouldInitializeBoardWithEmptyCharacter() {
        for (int i = 0; i < this.ticTacToe.board.length; i++)
            for (int j = 0; j < this.ticTacToe.board[i].length; j++)
                assertEquals(this.ticTacToe.EMPTY, this.ticTacToe.board[i][j]);
    }

    @Test
    void shouldPrintEmptyTable() {
        assertEquals(
                """
                        +---+---+---+
                        |   |   |   |
                        +---+---+---+
                        |   |   |   |
                        +---+---+---+
                        |   |   |   |
                        +---+---+---+""", this.ticTacToe.printTable());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "X", " X", "X ", " X "})
    void whenChoosingMarker_shouldReturnX(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Marker> markerOptional = this.ticTacToe.getMarkerFromUserInput(scanner);

        assertTrue(markerOptional.isPresent());
        assertEquals(TicTacToe.Marker.X, markerOptional.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenChoosingMarker_shouldReturnO(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Marker> markerOptional = this.ticTacToe.getMarkerFromUserInput(scanner);

        assertTrue(markerOptional.isPresent());
        assertEquals(TicTacToe.Marker.O, markerOptional.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Something else", "XX", "XO"})
    void whenChoosingMarkerIsInvalid_shouldReturnNull(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Marker> markerOptional = this.ticTacToe.getMarkerFromUserInput(scanner);

        assertTrue(markerOptional.isEmpty());
    }

    @Test
    void whenStartGame_shouldSetMarkerForX() {
        String userInput = "X";
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(this.ticTacToe.player[0].marker(), TicTacToe.Marker.X);
        assertEquals(this.ticTacToe.player[1].marker(), TicTacToe.Marker.O);
    }

    @Test
    void whenStartGame_shouldSetMarkerForO() {
        String userInput = "O";
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(this.ticTacToe.player[0].marker(), TicTacToe.Marker.O);
        assertEquals(this.ticTacToe.player[1].marker(), TicTacToe.Marker.X);
    }

    @Test
    void whenStartGame_shouldKeepAsking() {
        String userInput = """
                some
                invalid
                texts
                X
                """;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                        ERROR_MARKER_LINE + NEW_LINE +
                        ERROR_MARKER_LINE + NEW_LINE +
                        ERROR_MARKER_LINE + NEW_LINE,
                SYSTEM_OUT.toString());
        assertEquals(this.ticTacToe.player[0].marker(), TicTacToe.Marker.X);
        assertEquals(this.ticTacToe.player[1].marker(), TicTacToe.Marker.O);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,1", "1,2", "1,3",
            "2,1", "2,2", "2,3",
            "3,1", "3,2", "3,3",
    })
    void whenChoosingCell_shouldGetCell(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isPresent());
        String[] split = userInput.split(this.ticTacToe.CELL_SEPARATOR_REGEX);
        assertEquals(Integer.parseInt(split[0]), cellOptional.get().x());
        assertEquals(Integer.parseInt(split[1]), cellOptional.get().y());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1x2", "ax2", "0x2", "0x0",
            "4x1", "0x2", "11", "a",
            "1 2", "2X3", "1", "a,b"
    })
    void whenChoosingCell_shouldNotGetCell(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isEmpty());
        assertEquals(ERROR_INVALID_VALUE_FORMAT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0,0", "1,4", "4,1", "4,4"
    })
    void whenChoosingCell_shouldNotGetCellWithInvalidRange(String userInput) {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isEmpty());
        assertEquals(ERROR_INVALID_VALUE_FORMAT, SYSTEM_OUT.toString());
    }

    @Test
    void whenChoosingCell_shouldNotGetCellWithInvalidRange() {
        this.ticTacToe.board[0][0] = TicTacToe.Marker.X.getValue();
        Scanner scanner = new Scanner("1,1");
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isEmpty());
        assertEquals(ERROR_CELL_SELECTED, SYSTEM_OUT.toString());
    }


}