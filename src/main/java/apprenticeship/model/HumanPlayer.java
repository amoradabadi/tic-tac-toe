package apprenticeship.model;


import apprenticeship.Board;
import apprenticeship.error.QuitException;
import apprenticeship.scan.CellScanner;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final CellScanner cellScanner;

    public HumanPlayer(Scanner scanner, String marker, Board board) {
        super(marker);
        this.cellScanner = new CellScanner(scanner, board);
    }

    @Override
    public Cell getNextMove() throws QuitException {
        return cellScanner.getCell();
    }
}
