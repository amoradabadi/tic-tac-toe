package apprenticeship;

import apprenticeship.enums.GameStatus;
import apprenticeship.error.QuitException;
import apprenticeship.model.Cell;
import apprenticeship.model.Marker;
import apprenticeship.model.Player;
import apprenticeship.scan.CellScanner;
import apprenticeship.scan.MarkerScanner;

import java.util.Scanner;

import static apprenticeship.Constants.*;

public class TicTacToe {
    private final Board board = new Board();
    private final Player[] players = new Player[2];
    private GameStatus status;

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        ticTacToe.start(scanner);
    }

    public void start(Scanner scanner) {
        print(showInstructions());
        try {
            Marker firstPlayerMarker = getPlayerMarker(scanner);
            initializePlayers(firstPlayerMarker);
            print(this.board.toTableString());
            getUserInputAndMove(scanner);
        } catch (QuitException e) {
            print("Bye");
        }
    }

    private void print(String text) {
        System.out.println(text);
    }

    private String showInstructions() {
        return INSTRUCTIONS_TEXT.formatted(CELL_SEPARATOR_REGEX, CELL_SEPARATOR_REGEX, Marker.X, Marker.O);
    }

    private Marker getPlayerMarker(Scanner scanner) throws QuitException {
        return new MarkerScanner(scanner).getMarker();
    }

    private void initializePlayers(Marker firstPlayerMarker) {
        this.players[0] = new Player(firstPlayerMarker);
        this.players[1] = new Player(firstPlayerMarker.next());
        this.status = GameStatus.IN_PROGRESS;
    }

    private void getUserInputAndMove(Scanner scanner) throws QuitException {
        int round = 0;
        while (status == GameStatus.IN_PROGRESS) {
            Player player = getCurrentPlayer(round);
            Cell cell = getPlayerMove(scanner);
            this.board.setCellValue(cell, player.marker().getValue());
            print(this.board.toTableString());
            round++;
            status = checkStatus();
            if (status == GameStatus.FINISHED_DRAW) {
                print(NO_WINNER_TEXT);
            } else if (status == GameStatus.FINISHED_WINNER) {
                print(PLAYER_HAS_WON.formatted(player.marker()));
            }
        }
    }

    private Player getCurrentPlayer(int round) {
        Player player = players[round % 2];
        print(PLAYER_TEXT.formatted(player.marker()));
        return player;
    }

    private Cell getPlayerMove(Scanner scanner) throws QuitException {
        return new CellScanner(scanner).getCell(this.board);
    }

    private GameStatus checkStatus() {
        if (this.board.hasEqualItemsInRow() || this.board.hasEqualItemsInColumn() || this.board.hasEqualDiagonal() || this.board.hasEqualBackDiagonal()) {
            return GameStatus.FINISHED_WINNER;
        } else if (this.board.hasEmptySpace()) {
            return GameStatus.IN_PROGRESS;
        } else {
            return GameStatus.FINISHED_DRAW;
        }
    }

}