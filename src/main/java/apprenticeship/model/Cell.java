package apprenticeship.model;

public class Cell {
    private final int x;
    private final int y;

    public Cell(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

}
