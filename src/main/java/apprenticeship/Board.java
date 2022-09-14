package apprenticeship;

import apprenticeship.model.Cell;

import static apprenticeship.Constants.*;
import static java.util.stream.IntStream.range;

public class Board {
    private final char[][] board = new char[][]{
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY}
    };

    public void setCellValue(Cell cell, char value) {
        this.board[cell.x() - 1][cell.y() - 1] = value;
    }

    public String toTableString() {
        final String separatorRow = "+---+---+---+";
        StringBuilder sb = new StringBuilder();
        for (char[] chars : this.board) {
            sb.append(separatorRow).append(NEW_LINE);
            for (char aChar : chars) sb.append(PIPE).append(" %s ".formatted(aChar));
            sb.append(PIPE).append(NEW_LINE);
        }
        sb.append(separatorRow);
        return sb.toString();
    }

    public boolean isFull(Cell cell) {
        return board[cell.x() - 1][cell.y() - 1] != EMPTY;
    }

    public boolean hasEmptySpace() {
        return range(0, BOARD_MAX)
                .anyMatch(i -> range(0, BOARD_MAX)
                        .anyMatch(j -> this.isEmpty(i, j)));
    }

    public boolean isEmpty(int i, int j) {
        return this.board[i][j] == EMPTY;
    }

    public boolean hasEqualBackDiagonal() {
        int col = BOARD_MAX - 1;
        char first = this.getCellValue(0, col);
        for (int i = 0; i < BOARD_MAX; i++, col--) {
            if (first == EMPTY || this.getCellValue(i, col) != first) {
                return false;
            }
        }
        return true;
    }

    private char getCellValue(int i, int j) {
        return this.board[i][j];
    }

    public boolean hasEqualDiagonal() {
        char first = this.getCellValue(0, 0);
        if (first == EMPTY) {
            return false;
        }
        return range(0, BOARD_MAX).allMatch(i -> this.getCellValue(i, i) == first);
    }

    public boolean hasEqualItemsInRow() {
        for (int row = 0; row < BOARD_MAX; row++) {
            char first = this.getCellValue(row, 0);
            if (first != EMPTY && hasSameCharsInRow(first, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInRow(char first, int row) {
        return range(0, BOARD_MAX).noneMatch(col -> this.getCellValue(row, col) != first);
    }

    public boolean hasEqualItemsInColumn() {
        for (int col = 0; col < BOARD_MAX; col++) {
            char first = this.getCellValue(0, col);
            if (first != EMPTY && hasSameCharsInCol(first, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInCol(char first, int col) {
        return range(0, BOARD_MAX).noneMatch(row -> this.getCellValue(row, col) != first);
    }
}
