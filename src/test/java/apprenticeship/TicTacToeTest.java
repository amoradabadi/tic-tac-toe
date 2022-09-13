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
    private static final String ERROR_INVALID_VALUE_FORMAT = "Invalid value, format is x,y where x and y should be between one and 3" + NEW_LINE;
    private static final String ERROR_INVALID_VALUE = "Invalid value, To begin, choose who starts the game by entering X or O." + NEW_LINE;

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

    @Test
    void shouldShowInstructions() {
        assertEquals(INSTRUCTIONS, this.ticTacToe.showInstructions());
    }

    @Test
    void shouldPrintEmptyTable() {
        assertEquals(getTableOutput(), this.ticTacToe.drawTable());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "X", " X", "X ", " X "})
    void whenChoosingMarker_shouldReturnX(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                getTableOutput() + NEW_LINE +
                "Player X: " + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenChoosingMarker_shouldReturnO(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                getTableOutput() + NEW_LINE +
                "Player O: " + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenChoosingMarkerIsInvalid_shouldReturnNull(String userInput) {
        Scanner scanner = new Scanner(userInput + NEW_LINE + QUIT);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                ERROR_INVALID_VALUE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenChoosingMarkerIsInvalid_shouldReturnNull() {
        String userInput = "X" + NEW_LINE +
                "anything" + NEW_LINE +
                QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                getTableOutput() + NEW_LINE +
                "Player X: " + NEW_LINE +
                "Invalid value, format is x,y where x and y should be between one and 3" + NEW_LINE +
                "Player X: " + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenStartGame_shouldSetMarkerForO() {
        String userInput = "O" + NEW_LINE + QUIT;
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                getTableOutput() + NEW_LINE +
                "Player O: " + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    private String getTableOutput() {
        return """
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+""";
    }

    private String getTableOutput2() {
        return """
                +---+---+---+
                | X |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+
                |   |   |   |
                +---+---+---+""";
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,1", "1,2", "1,3",
            "2,1", "2,2", "2,3",
            "3,1", "3,2", "3,3",
    })
    void whenChoosingCell_shouldGetCell(String userInput) throws TicTacToe.QuitException {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isPresent());
        String[] split = userInput.split(",");
        assertEquals(Integer.parseInt(split[0]), cellOptional.get().x());
        assertEquals(Integer.parseInt(split[1]), cellOptional.get().y());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1x2", "ax2", "0x2", "0x0",
            "4x1", "0x2", "11", "a",
            "1 2", "2X3", "1", "a,b"
    })
    void whenChoosingCell_shouldNotGetCell(String userInput) throws TicTacToe.QuitException {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isEmpty());
        assertEquals(ERROR_INVALID_VALUE_FORMAT, SYSTEM_OUT.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0,0", "1,4", "4,1", "4,4"
    })
    void whenChoosingCell_shouldNotGetCellWithInvalidRange(String userInput) throws TicTacToe.QuitException {
        Scanner scanner = new Scanner(userInput);
        Optional<TicTacToe.Cell> cellOptional = this.ticTacToe.getCellFromUserInput(scanner);
        assertTrue(cellOptional.isEmpty());
        assertEquals(ERROR_INVALID_VALUE_FORMAT, SYSTEM_OUT.toString());
    }

    @Test
    void whenChoosingCell_shouldNotGetCellWhenAlreadySelected() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                QUIT;

        Scanner scanner = new Scanner(userInput);
        this.ticTacToe.startGame(scanner);

        assertEquals(INSTRUCTIONS + NEW_LINE +
                getTableOutput() + NEW_LINE +
                "Player X: " + NEW_LINE +
                getTableOutput2() + NEW_LINE +
                "Player O: " + NEW_LINE +
                "Cell has already selected, choose another one" + NEW_LINE +
                "Player O: " + NEW_LINE +
                EXIT, SYSTEM_OUT.toString());
    }

    @Test
    void whenFirstRowIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "1,2" + NEW_LINE + // X
                "2,3" + NEW_LINE + // O
                "1,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenSecondRowIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "2,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,3" + NEW_LINE;  // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenThirdRowIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "3,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "3,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X

        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }


    @Test
    void whenFirstColIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "2,3" + NEW_LINE + // O
                "2,1" + NEW_LINE + // X
                "2,2" + NEW_LINE + // O
                "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenSecondColIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "2,1" + NEW_LINE + // X
                "3,1" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "3,2" + NEW_LINE + // O
                "2,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenThirdColIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "3,1" + NEW_LINE + // X
                "1,1" + NEW_LINE + // O
                "3,2" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenDiagonalIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "1,1" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "3,3" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenBackDiagonalIsFull_checkStatusHasWinner() {
        String userInput = "X" + NEW_LINE +
                "1,3" + NEW_LINE + // X
                "1,2" + NEW_LINE + // O
                "2,2" + NEW_LINE + // X
                "2,1" + NEW_LINE + // O
                "3,1" + NEW_LINE; // X
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_WINNER, this.ticTacToe.checkStatus());
    }

    @Test
    void whenNoWinner_checkStatusIsDraw() {
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

        this.ticTacToe.startGame(scanner);

        assertEquals(TicTacToe.GameStatus.FINISHED_DRAW, this.ticTacToe.checkStatus());
    }

}