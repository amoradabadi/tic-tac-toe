package apprenticeship.model;


import apprenticeship.error.QuitException;

public class ComputerPlayer extends Player {
    public ComputerPlayer(Marker marker) {
        super(marker, PlayerType.COMPUTER);
    }

    @Override
    public Cell getNextMove() throws QuitException {
        return null;
    }
}
