package apprenticeship.model;


import apprenticeship.Board;

import static apprenticeship.Constants.BOARD_MAX;

public class ComputerPlayer extends Player {
    private final Board board;

    public ComputerPlayer(Marker marker, Board board) {
        super(marker);
        this.board = board;
    }

    @Override
    public Cell getNextMove() {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (this.board.isEmpty(i, j)) {
                    return new Cell(i + 1, j + 1);
                }
            }
        }
        return null;
    }

}
