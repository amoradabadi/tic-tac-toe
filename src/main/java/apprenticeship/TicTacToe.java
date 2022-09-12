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

    public void startGame(Scanner scanner) {
        print(showInstructions());
        Marker marker = getPlayerMarker(scanner);
        initializePlayers(marker);
    }

    public Optional<Marker> getMarkerFromUserInput(Scanner scanner) {
        String next = scanner.next();
        return Marker.getValue(next.trim());
    }

    // TODO: fix this method, decouple
    public Optional<Cell> getCellFromUserInput(Scanner scanner) {
        String format_error = "Invalid value, format is x%sy where x and y should be more than one and less than %d"
                .formatted(CELL_SEPARATOR_REGEX, this.board.length);
        String next = scanner.next();
        String[] dataArr = next.split(CELL_SEPARATOR_REGEX);
        if (dataArr.length != 2) {
            print(format_error);
            return Optional.empty();
        }
        Cell cell;
        try {
            cell = new Cell(Integer.parseInt(dataArr[0]), Integer.parseInt(dataArr[1]));
        } catch (NumberFormatException ignored) {
            print(format_error);
            return Optional.empty();
        }
        if (cell.x < 1 || cell.y < 1 || cell.x > this.board.length || cell.y > this.board[0].length) {
            print(format_error);
            return Optional.empty();
        }
        if (this.board[cell.x - 1][cell.y - 1] != EMPTY) {
            print("Cell has already selected, choose another one");
            return Optional.empty();
        }
        return Optional.of(cell);
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

    private void print(String text) {
        System.out.println(text);
    }

    private void initializePlayers(Marker firstPlayer) {
        this.player[0] = new Player(firstPlayer);
        this.player[1] = new Player(firstPlayer.next());
    }

    private Marker getPlayerMarker(Scanner scanner) {
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
        X('X'), O('O');

        private final char value;

        Marker(char v) {
            this.value = v;
        }

        public static Optional<Marker> getValue(String value) {
            return Arrays.stream(Marker.values())
                    .filter(marker -> marker.name().equalsIgnoreCase(value))
                    .findFirst();
        }

        public char getValue() {
            return value;
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