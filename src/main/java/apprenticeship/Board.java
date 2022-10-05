package apprenticeship;

import apprenticeship.model.Cell;

import static apprenticeship.Constants.*;
import static java.util.stream.IntStream.range;

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
        final String dash = "-";
        final String separatorRow = "+-" + dash.repeat(markerLength) + middle + dash.repeat(markerLength) + middle + dash.repeat(markerLength) + "-+";
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
        String left = pad(text, len, false);
        return pad(left, maxWidth, true);
    }

    private static String pad(String text, int len, boolean isRight) {
        return String.format("%" + (isRight ? "-" : "") + len + "s", text);
    }

    public boolean isFull(Cell cell) {
        return !board[cell.x() - 1][cell.y() - 1].equals(EMPTY);
    }

    public boolean hasEmptySpace() {
        return range(0, BOARD_MAX)
                .anyMatch(i -> range(0, BOARD_MAX)
                        .anyMatch(j -> this.isEmpty(i, j)));
    }

    public boolean isEmpty(int i, int j) {
        return this.board[i][j].equals(EMPTY);
    }

    public boolean hasEqualBackDiagonal() {
        int col = BOARD_MAX - 1;
        String first = this.getCellValue(0, col);
        for (int i = 0; i < BOARD_MAX; i++, col--) {
            if (first.equals(EMPTY) || !this.getCellValue(i, col).equals(first)) {
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
        return range(0, BOARD_MAX).allMatch(i -> this.getCellValue(i, i).equals(first));
    }

    public boolean hasEqualItemsInRowOrCol() {
        for (int i = 0; i < BOARD_MAX; i++) {
            String firstRow = this.getCellValue(i, 0);
            String firstCol = this.getCellValue(0, i);

            if (!firstRow.equals(EMPTY) && hasSameCharsIn(firstRow, i, true)) {
                return true;
            }
            if (!firstCol.equals(EMPTY) && hasSameCharsIn(firstCol, i, false)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameCharsIn(String first, int index, boolean isRow) {
        return range(0, BOARD_MAX).allMatch(i -> isRow ? this.getCellValue(index, i).equals(first) : this.getCellValue(i, index).equals(first));
    }

    public void setMarkerLength(int markerLength) {
        this.markerLength = Math.max(this.markerLength, markerLength);
    }

}
