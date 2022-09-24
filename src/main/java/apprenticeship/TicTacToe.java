package apprenticeship;

import apprenticeship.enums.GameStatus;
import apprenticeship.enums.PlayerType;
import apprenticeship.error.QuitException;
import apprenticeship.model.Cell;
import apprenticeship.model.ComputerPlayer;
import apprenticeship.model.HumanPlayer;
import apprenticeship.model.Player;
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
        System.out.println(INSTRUCTIONS_TEXT);
        try {
            initializePlayers(scanner);
            System.out.println(this.board.toTableString());
            getUserInputAndMove();
        } catch (QuitException e) {
            System.out.println(BYE);
        }
    }


    private void initializePlayers(Scanner scanner) throws QuitException {
        PlayerMarkerScanner playerMarkerScanner = new PlayerMarkerScanner(scanner);
        for (int i = 0; i < this.players.length; i++) {
            String marker = playerMarkerScanner.getMarker(CHOOSE_A_MARKER_FOR_PLAYER.formatted(i + 1));
            PlayerType playerType = playerMarkerScanner.getPlayerType(HUMAN_OR_COMPUTER.formatted(marker));
            this.players[i] = createPlayer(scanner, marker, playerType);
            this.board.setMarkerLength(marker.length());
        }
    }

    private void getUserInputAndMove() throws QuitException {
        int round = 0;
        GameStatus status = GameStatus.IN_PROGRESS;
        while (status == GameStatus.IN_PROGRESS) {
            Player player = getCurrentPlayer(round++);
            playerNextMove(player);
            System.out.println(this.board.toTableString());
            status = checkStatus();
            printWinnerOrDraw(status, player);
        }
    }

    private void playerNextMove(Player player) throws QuitException {
        Cell cell = player.getNextMove();
        System.out.printf(PLAYER_SELECTED, player.getMarker(), cell);
        this.board.setCellValue(cell, player.getMarker());
    }

    private Player createPlayer(Scanner scanner, String marker, PlayerType playerType) {
        return switch (playerType) {
            case HUMAN -> new HumanPlayer(scanner, marker, this.board);
            case COMPUTER -> new ComputerPlayer(marker, this.board);
        };
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