package apprenticeship.model;


import apprenticeship.error.QuitException;

public abstract class Player {
    private final Marker marker;

    public Player(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public abstract Cell getNextMove() throws QuitException;

}
