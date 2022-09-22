package apprenticeship.model;


import apprenticeship.Board;
import apprenticeship.error.QuitException;
import apprenticeship.scan.CellScanner;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final CellScanner cellScanner;
    private final Board board;

    public HumanPlayer(Scanner scanner, Marker marker, Board board) {
        super(marker);
        this.board = board;
        this.cellScanner = new CellScanner(scanner);
    }

    @Override
    public Cell getNextMove() throws QuitException {
        return cellScanner.getCell(board);
    }
}
