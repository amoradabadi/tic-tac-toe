package apprenticeship;

import apprenticeship.model.Cell;

import java.util.stream.IntStream;

import static apprenticeship.Constants.*;

public class Board {
    private final String[][] board = new String[][]{
            new String[]{EMPTY, EMPTY, EMPTY},
            new String[]{EMPTY, EMPTY, EMPTY},
            new String[]{EMPTY, EMPTY, EMPTY}
    };
    private int markerLength = 1;

    public void setCellValue(Cell cell, String value) {
        this.board[cell.x() - 1][cell.y() - 1] = value;
    }

    public String toTableString() {
        final String middle = "-+-";
        final String left = "+-";
        final String right = "-+";
        final String dash = "-";
        final String separatorRow = left + dash.repeat(markerLength) + middle + dash.repeat(markerLength) + middle + dash.repeat(markerLength) + right;
        StringBuilder sb = new StringBuilder();
        for (String[] rows : this.board) {
            sb.append(separatorRow).append(NEW_LINE);
            for (String text : rows) sb.append(PIPE).append(padCenter(text));
            sb.append(PIPE).append(NEW_LINE);
        }
        sb.append(separatorRow);
        return sb.toString();
    }

    private String padCenter(String text) {
        int maxWidth = markerLength + 2;
        int textLen = text.length();
        int len = textLen + (maxWidth - textLen) / 2;
        return rightPad(leftPad(text, len), maxWidth);
    }

    private static String rightPad(String text, int len) {
        return String.format("%-" + len + "s", text);
    }

    private static String leftPad(String text, int len) {
        return String.format("%" + len + "s", text);
    }

    public boolean isFull(Cell cell) {
        return !board[cell.x() - 1][cell.y() - 1].equals(EMPTY);
    }

    public boolean hasEmptySpace() {
        return IntStream.range(0, BOARD_MAX)
                .anyMatch(i -> IntStream.range(0, BOARD_MAX)
                        .anyMatch(j -> this.isEmpty(i, j)));
    }

    public boolean isEmpty(int i, int j) {
        return this.board[i][j].equals(EMPTY);
    }

    public boolean hasEqualBackDiagonal() {
        int col = BOARD_MAX - 1;
        String first = this.getCellValue(0, col);
        if (first.equals(EMPTY)) {
            return false;
        }
        for (int i = 0; i < BOARD_MAX; i++, col--) {
            if (!this.getCellValue(i, col).equals(first)) {
                return false;
            }
        }
        return true;
    }

    private String getCellValue(int i, int j) {
        return this.board[i][j];
    }

    public boolean hasEqualDiagonal() {
        String first = this.getCellValue(0, 0);
        if (first.equals(EMPTY)) {
            return false;
        }
        return IntStream.range(0, BOARD_MAX).allMatch(i -> this.getCellValue(i, i).equals(first));
    }

    public boolean hasEqualItemsInRow() {
        for (int row = 0; row < BOARD_MAX; row++) {
            String first = this.getCellValue(row, 0);
            if (!first.equals(EMPTY) && hasSameCharsInRow(first, row)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInRow(String first, int row) {
        return IntStream.range(0, BOARD_MAX).allMatch(col -> this.getCellValue(row, col).equals(first));
    }

    public boolean hasEqualItemsInColumn() {
        for (int col = 0; col < BOARD_MAX; col++) {
            String first = this.getCellValue(0, col);
            if (!first.equals(EMPTY) && hasSameCharsInCol(first, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsInCol(String first, int col) {
        return IntStream.range(0, BOARD_MAX).allMatch(row -> this.getCellValue(row, col).equals(first));
    }

    public void setMarkerLength(int markerLength) {
        this.markerLength = Math.max(this.markerLength, markerLength);
    }

}
