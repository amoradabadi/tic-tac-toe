import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeTest {
    TicTacToe ticTacToe;

    @BeforeEach
    void dummy() {
        this.ticTacToe = new TicTacToe();
    }

    @Test
    void shouldShowInstructions() {
        assertEquals("Welcome to Tic-Tac-Toe\n" +
                        "\n" +
                        "To select a cell for your move enter the position of the row and the column, separated with an x.\n" +
                        "Example: 1x3 puts the sign in the first row and third column.\n" +
                        "\n" +
                        "To begin, choose who starts the game by entering X or O.\n" +
                        "To exit the game press ^C\n",
                this.ticTacToe.showInstructions());
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
        String userInput = "some\n" +
                "invalid\n" +
                "texts\n" +
                "X";
        Scanner scanner = new Scanner(userInput);

        this.ticTacToe.startGame(scanner);

        assertEquals(this.ticTacToe.player[0].marker(), TicTacToe.Marker.X);
        assertEquals(this.ticTacToe.player[1].marker(), TicTacToe.Marker.O);
    }

}