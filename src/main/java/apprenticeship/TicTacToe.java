package apprenticeship;

import apprenticeship.enums.GameStatus;
import apprenticeship.error.QuitException;
import apprenticeship.model.*;
import apprenticeship.scan.PlayerMarkerScanner;

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
        System.out.println(showInstructions());
        try {
            initializePlayers(scanner);
            System.out.println(this.board.toTableString());
            getUserInputAndMove();
        } catch (QuitException e) {
            System.out.println(BYE);
        }
    }

    private String showInstructions() {
        return INSTRUCTIONS_TEXT.formatted(Marker.X.getValue(), Marker.O.getValue());
    }

    private void initializePlayers(Scanner scanner) throws QuitException {
        PlayerMarkerScanner playerMarkerScanner = new PlayerMarkerScanner(scanner);
        Marker firstPlayerMarker = playerMarkerScanner.getMarker();

        for (int i = 0; i < this.players.length; i++) {
            System.out.printf(HUMAN_OR_COMPUTER, firstPlayerMarker.getValue());
            PlayerType firstPlayerType = playerMarkerScanner.getPlayerType();
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
            System.out.printf(PLAYER_SELECTED, player.getMarker().getValue(), cell);
            this.board.setCellValue(cell, player.getMarker().getValue());
            System.out.println(this.board.toTableString());
            status = checkStatus();
            printWinnerOrDraw(status, player);
        }
    }

    private Player getCurrentPlayer(int round) {
        Player player = players[round % 2];
        System.out.printf(PLAYER_TEXT, player.getMarker());
        return player;
    }

    private GameStatus checkStatus() {
        if (boardHasWinner()) return GameStatus.FINISHED_WINNER;
        else if (this.board.hasEmptySpace()) return GameStatus.IN_PROGRESS;
        else return GameStatus.FINISHED_DRAW;
    }

    private void printWinnerOrDraw(GameStatus status, Player player) {
        if (status == GameStatus.FINISHED_DRAW) {
            System.out.println(NO_WINNER_TEXT);
        } else if (status == GameStatus.FINISHED_WINNER) {
            System.out.printf(PLAYER_HAS_WON, player.getMarker());
        }
    }

    private boolean boardHasWinner() {
        return this.board.hasEqualItemsInRowOrCol() || this.board.hasEqualDiagonal() || this.board.hasEqualBackDiagonal();
    }

}