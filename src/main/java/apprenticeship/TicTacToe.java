package apprenticeship;

import apprenticeship.enums.GameStatus;
import apprenticeship.enums.PlayerType;
import apprenticeship.error.QuitException;
import apprenticeship.model.*;
import apprenticeship.scan.CustomMarkerScanner;
import apprenticeship.scan.PlayerTypeScanner;

import java.util.Scanner;

import static apprenticeship.Constants.*;

public class TicTacToe {
    private final Board board = new Board();
    private final Player[] players = new Player[2];
    private GameStatus status = GameStatus.IN_PROGRESS;

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        ticTacToe.start(scanner);
    }

    public void start(Scanner scanner) {
        print(INSTRUCTIONS_TEXT);
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

    private void initializePlayers(Scanner scanner) throws QuitException {
        CustomMarkerScanner markerScanner = new CustomMarkerScanner(scanner);
        PlayerTypeScanner playerTypeScanner = new PlayerTypeScanner(scanner);

        for (int i = 0; i < this.players.length; i++) {
            String marker = markerScanner.getMarker(CHOOSE_A_MARKER_FOR_PLAYER.formatted(i + 1));
            PlayerType playerType = playerTypeScanner.getPlayerType(HUMAN_OR_COMPUTER.formatted(marker));
            this.players[i] = createPlayer(scanner, marker, playerType);
            this.board.setMarkerLength(marker.length());
        }
    }

    private void getUserInputAndMove() throws QuitException {
        int round = 0;
        while (status == GameStatus.IN_PROGRESS) {
            Player player = getCurrentPlayer(round++);
            Cell cell = player.getNextMove();
            print(PLAYER_SELECTED.formatted(player.getMarker(), cell));
            this.board.setCellValue(cell, player.getMarker());
            print(this.board.toTableString());
            status = checkStatus();
            if (status == GameStatus.FINISHED_DRAW) {
                print(NO_WINNER_TEXT);
            } else if (status == GameStatus.FINISHED_WINNER) {
                print(PLAYER_HAS_WON.formatted(player.getMarker()));
            }
        }
    }

    private Player createPlayer(Scanner scanner, String marker, PlayerType playerType) {
        return switch (playerType) {
            case HUMAN -> new HumanPlayer(scanner, marker, this.board);
            case COMPUTER -> new ComputerPlayer(marker, this.board);
        };
    }

    private Player getCurrentPlayer(int round) {
        Player player = players[round % 2];
        print(PLAYER_TEXT.formatted(player.getMarker()));
        return player;
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