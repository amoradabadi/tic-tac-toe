import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class TicTacToe {
    public static final String NEW_LINE = "\n";
    private static final String PIPE = "|";
    public final char EMPTY = ' ';
    public char[][] board = new char[][]{
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY}
    };
    public Player[] player = new Player[2];

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe");
    }

    public Optional<Marker> getMarkerFromUserInput(Scanner scanner) {
        String next = scanner.next();
        return Marker.getValue(next.trim());
    }

    public String showInstructions() {
        return """
                Welcome to Tic-Tac-Toe
                                
                To select a cell for your move enter the position of the row and the column, separated with an x.
                Example: 1x3 puts the sign in the first row and third column.
                                
                To begin, choose who starts the game by entering X or O.
                To exit the game press ^C
                """;
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
        showInstructions();
        Marker marker = getFirstPlayerMarker(scanner);
        initializePlayers(marker);
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
                System.out.println("Invalid value, To begin, choose who starts the game by entering X or O.");
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
}