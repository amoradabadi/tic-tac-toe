package apprenticeship.model;


import apprenticeship.error.QuitException;

public abstract class Player {
    private final String marker;

    public Player(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }

    public abstract Cell getNextMove() throws QuitException;

}
