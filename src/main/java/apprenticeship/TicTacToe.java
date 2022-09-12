package apprenticeship;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class TicTacToe {
    public static final String NEW_LINE = "\n";
    private static final String PIPE = "|";
    public final String CELL_SEPARATOR_REGEX = ",";
    public final char EMPTY = ' ';
    public char[][] board = new char[][]{
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY}
    };
    public Player[] player = new Player[2];

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        ticTacToe.startGame(scanner);
    }

    public Optional<Marker> getMarkerFromUserInput(Scanner scanner) {
        String next = scanner.next();
        return Marker.getValue(next.trim());
    }

    public Optional<Cell> getCellFromUserInput(Scanner scanner) {
        String next = scanner.next();
        String[] dataArr = next.split(CELL_SEPARATOR_REGEX);
        if (dataArr.length != 2) {
            return Optional.empty();
        }
        int x, y;
        try {
            x = Integer.parseInt(dataArr[0]);
            y = Integer.parseInt(dataArr[1]);
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
        if (x < 1 || y < 1 || x > this.board.length || y > this.board[0].length) {
            return Optional.empty();
        }
        return Optional.of(new Cell(x, y));
    }

    public String showInstructions() {
        return """
                Welcome to Tic-Tac-Toe
                                
                To select a cell for your move enter the position of the row and the column, separated with %s
                Example: 1%s3 puts the sign in the first row and third column.
                                
                To begin, choose who starts the game by entering %s or %s.
                To exit the game press ^C
                """.formatted(CELL_SEPARATOR_REGEX, CELL_SEPARATOR_REGEX, Marker.X, Marker.O);
    }

    public String printTable() {
        final String separatorRow = "+---+---+---+";
        final String cellFormat = "%-3s";
        StringBuilder sb = new StringBuilder();
        for (char[] chars : this.board) {
            sb.append(separatorRow).append(NEW_LINE);
            for (char aChar : chars) sb.append(PIPE).append(cellFormat.formatted(aChar));
            sb.append(PIPE).append(NEW_LINE);
        }
        sb.append(separatorRow);
        return sb.toString();
    }

    public void startGame(Scanner scanner) {
        print(showInstructions());
        Marker marker = getFirstPlayerMarker(scanner);
        initializePlayers(marker);
    }

    private void print(String text) {
        System.out.println(text);
    }

    private void initializePlayers(Marker firstPlayer) {
        this.player[0] = new Player(firstPlayer);
        this.player[1] = new Player(firstPlayer.next());
    }

    private Marker getFirstPlayerMarker(Scanner scanner) {
        Optional<Marker> optionalMarker;
        do {
            optionalMarker = getMarkerFromUserInput(scanner);
            if (optionalMarker.isEmpty()) {
                print("Invalid value, To begin, choose who starts the game by entering X or O.");
            }
        } while (optionalMarker.isEmpty());
        return optionalMarker.get();
    }


    enum Marker {
        X, O;

        public static Optional<Marker> getValue(String value) {
            return Arrays.stream(Marker.values())
                    .filter(marker -> marker.name().equalsIgnoreCase(value))
                    .findFirst();
        }

        public Marker next() {
            return this == O ? X : O;
        }
    }

    record Player(Marker marker) {
    }

    record Cell(int x, int y) {
    }
}