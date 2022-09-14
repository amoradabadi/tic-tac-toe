package apprenticeship.model;


import apprenticeship.error.QuitException;

public abstract class Player {
    private final Marker marker;
    private final PlayerType playerType;

    public Player(Marker marker, PlayerType playerType) {
        this.marker = marker;
        this.playerType = playerType;
    }

    public Marker getMarker() {
        return marker;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public abstract Cell getNextMove() throws QuitException;

}
