package apprenticeship;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TicTacToe {
    private static final String NEW_LINE = "\n";
    private static final String PIPE = "|";
    private final String CELL_SEPARATOR_REGEX = ",";
    private final char EMPTY = ' ';
    private final char[][] board = new char[][]{
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY}
    };
    private final int BOARD_LENGTH = this.board.length;

    private final Player[] players = new Player[2];
    private GameStatus status;

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        ticTacToe.startGame(scanner);
    }

    public void startGame(Scanner scanner) {
        print(showInstructions());
        try {
            Marker marker = getPlayerMarker(scanner);
            initializePlayers(marker);
            print(drawTable());
            getUserInputs(scanner);
        } catch (QuitException e) {
            print("Bye");
        }
    }

    private void getUserInputs(Scanner scanner) throws QuitException {
        int round = 0;
        while (status == GameStatus.IN_PROGRESS) {
            Player player = players[round % 2];
            print("Player " + player.marker + ": ");
            Optional<Cell> cellOptional = getCellFromUserInput(scanner);
            if (cellOptional.isEmpty()) {
                continue;
            }
            Cell cell = cellOptional.get();
            this.board[cell.x - 1][cell.y - 1] = player.marker.getValue();
            print(drawTable());
            round++;
            status = checkStatus();
            if (status == GameStatus.FINISHED_DRAW) {
                print("No winner, game is draw.");
            } else if (status == GameStatus.FINISHED_WINNER) {
                print("Player " + player.marker + " has won.");
            }
        }
    }

    private Optional<Marker> getMarkerFromUserInput(Scanner scanner) throws QuitException {
        String next = scanner.next();
        if (next.trim().equalsIgnoreCase("q")) {
            throw new QuitException();
        }
        return Marker.getValue(next.trim());
    }

    // TODO: fix this method, decouple
    public Optional<Cell> getCellFromUserInput(Scanner scanner) throws QuitException {
        String format_error = "Invalid value, format is x%sy where x and y should be between one and %d"
                .formatted(CELL_SEPARATOR_REGEX, BOARD_LENGTH);
        String next = scanner.next();
        if (next.trim().equalsIgnoreCase("q")) {
            throw new QuitException();
        }
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
        if (cell.x < 1 || cell.y < 1 || cell.x > BOARD_LENGTH || cell.y > BOARD_LENGTH) {
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
                To exit the game press q
                """.formatted(CELL_SEPARATOR_REGEX, CELL_SEPARATOR_REGEX, Marker.X, Marker.O);
    }

    public String drawTable() {
        final String separatorRow = "+---+---+---+";
        final String cellFormat = " %s ";
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
        this.players[0] = new Player(firstPlayer);
        this.players[1] = new Player(firstPlayer.next());
        this.status = GameStatus.IN_PROGRESS;
    }

    private Marker getPlayerMarker(Scanner scanner) throws QuitException {
        Optional<Marker> optionalMarker;
        do {
            optionalMarker = getMarkerFromUserInput(scanner);
            if (optionalMarker.isEmpty()) {
                print("Invalid value, To begin, choose who starts the game by entering %s or %s.".formatted(Marker.X, Marker.O));
            }
        } while (optionalMarker.isEmpty());
        return optionalMarker.get();
    }

    public GameStatus checkStatus() {
        if (hasEqualItemsInRow() || hasEqualItemsInColumn() || hasEqualDiagonal() || hasEqualBackDiagonal()) {
            return GameStatus.FINISHED_WINNER;
        } else if (hasEmptySpace()) {
            return GameStatus.IN_PROGRESS;
        } else {
            return GameStatus.FINISHED_DRAW;
        }
    }

    private boolean hasEmptySpace() {
        return IntStream.range(0, BOARD_LENGTH)
                .anyMatch(i -> IntStream.range(0, BOARD_LENGTH)
                        .anyMatch(j -> this.board[i][j] == EMPTY));
    }

    private boolean hasEqualBackDiagonal() {
        int col = BOARD_LENGTH - 1;
        char first = this.board[0][col];
        if (first == EMPTY) {
            return false;
        }
        for (int i = 0; i < BOARD_LENGTH; i++, col--) {
            if (this.board[i][col] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean hasEqualDiagonal() {
        char first = this.board[0][0];
        if (first == EMPTY) {
            return false;
        }
        return IntStream.range(0, BOARD_LENGTH).noneMatch(i -> this.board[i][i] != first);
    }

    private boolean hasEqualItemsInRow() {
        for (int row = 0; row < BOARD_LENGTH; row++) {
            char first = this.board[row][0];
            if (first != EMPTY && hasSameCharsInRow(first, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInRow(char first, int row) {
        return IntStream.range(0, BOARD_LENGTH).noneMatch(col -> this.board[row][col] != first);
    }

    private boolean hasEqualItemsInColumn() {
        for (int col = 0; col < BOARD_LENGTH; col++) {
            char first = this.board[0][col];
            if (first != EMPTY && hasSameCharsInCol(first, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInCol(char first, int col) {
        return IntStream.range(0, BOARD_LENGTH).noneMatch(row -> this.board[row][col] != first);
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

    public enum GameStatus {
        IN_PROGRESS,
        FINISHED_DRAW,
        FINISHED_WINNER;
    }

    record Player(Marker marker) {
    }

    record Cell(int x, int y) {
    }

    static class QuitException extends Throwable {
    }
}