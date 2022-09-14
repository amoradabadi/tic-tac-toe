package apprenticeship.scan;

import apprenticeship.Board;
import apprenticeship.ScannerHelper;
import apprenticeship.error.QuitException;
import apprenticeship.model.Cell;

import java.util.Optional;
import java.util.Scanner;

import static apprenticeship.Constants.*;

public class CellScanner extends ScannerHelper {

    private final Board board;

    public CellScanner(Scanner scanner, Board board) {
        super(scanner);
        this.board = board;
    }

    public Cell getCell() throws QuitException {
        Optional<Cell> optionalCell;
        do {
            String userInput = blockScannerAndGetUserInput();
            optionalCell = parseValue(userInput);
        } while (optionalCell.isEmpty());
        return optionalCell.get();
    }

    private Optional<Cell> parseValue(String userInput) {
        String[] dataArr = userInput.split(CELL_SEPARATOR_REGEX);
        if (dataArr.length != 2) {
            System.out.println(INVALID_CELL_VALUE);
            return Optional.empty();
        }

        Cell cell = new Cell(dataArr[0], dataArr[1]);

        if (cell.x() < BOARD_MIN || cell.y() < BOARD_MIN || cell.x() > BOARD_MAX || cell.y() > BOARD_MAX) {
            System.out.println(INVALID_CELL_VALUE);
            return Optional.empty();
        }
        if (board.isFull(cell)) {
            System.out.println(INVALID_CELL_FULL_VALUE);
            return Optional.empty();
        }
        return Optional.of(cell);
    }
}
