package apprenticeship;

import apprenticeship.enums.GameStatus;
import apprenticeship.error.QuitException;
import apprenticeship.model.*;
import apprenticeship.scan.MarkerScanner;
import apprenticeship.scan.PlayerTypeScanner;

import java.util.Scanner;

import static apprenticeship.Constants.*;

public class TicTacToe {
    private final Board board = new Board();
    private final Player[] players = new Player[2];

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        ticTacToe.start(scanner);
    }

    public void start(Scanner scanner) {
        print(showInstructions());
        try {
            initializePlayers(scanner);
            print(this.board.toTableString());
            getUserInputAndMove();
        } catch (QuitException e) {
            print(BYE);
        }
    }

    private void print(String text) {
        System.out.println(text);
    }

    private String showInstructions() {
        return INSTRUCTIONS_TEXT.formatted(Marker.X.getValue(), Marker.O.getValue());
    }

    private void initializePlayers(Scanner scanner) throws QuitException {
        Marker firstPlayerMarker = new MarkerScanner(scanner).getMarker();
        PlayerTypeScanner playerTypeScanner = new PlayerTypeScanner(scanner);

        for (int i = 0; i < this.players.length; i++) {
            print(HUMAN_OR_COMPUTER.formatted(firstPlayerMarker.getValue()));
            PlayerType firstPlayerType = playerTypeScanner.getPlayerType();
            this.players[i] = switch (firstPlayerType) {
                case HUMAN -> new HumanPlayer(scanner, firstPlayerMarker, this.board);
                case COMPUTER -> new ComputerPlayer(firstPlayerMarker, this.board);
            };
            firstPlayerMarker = firstPlayerMarker.next();
        }
    }

    private void getUserInputAndMove() throws QuitException {
        int round = 0;
        GameStatus status = GameStatus.IN_PROGRESS;
        while (status == GameStatus.IN_PROGRESS) {
            Player player = getCurrentPlayer(round++);
            Cell cell = player.getNextMove();
            print(PLAYER_SELECTED.formatted(player.getMarker().getValue(), cell));
            this.board.setCellValue(cell, player.getMarker().getValue());
            print(this.board.toTableString());
            round++;
            status = checkStatus();
            printWinnerOrDraw(status, player);
        }
    }

    private Player getCurrentPlayer(int round) {
        Player player = players[round % 2];
        print(PLAYER_TEXT.formatted(player.getMarker()));
        return player;
    }

    private GameStatus checkStatus() {
        if (boardHasWinner()) return GameStatus.FINISHED_WINNER;
        else if (this.board.hasEmptySpace()) return GameStatus.IN_PROGRESS;
        else return GameStatus.FINISHED_DRAW;
    }

    private void printWinnerOrDraw(GameStatus status, Player player) {
        if (status == GameStatus.FINISHED_DRAW) {
            print(NO_WINNER_TEXT);
        } else if (status == GameStatus.FINISHED_WINNER) {
            print(PLAYER_HAS_WON.formatted(player.marker()));
        }
    }

    private boolean boardHasWinner() {
        return this.board.hasEqualItemsInRow() || this.board.hasEqualItemsInColumn() || this.board.hasEqualDiagonal() || this.board.hasEqualBackDiagonal();
    }

}