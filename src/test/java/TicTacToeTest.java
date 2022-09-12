import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                        "To begin, choose who starts the game by entering X or O.\n",
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

}