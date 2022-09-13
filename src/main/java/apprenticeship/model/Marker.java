package apprenticeship.model;

import java.util.Arrays;
import java.util.Optional;

public enum Marker {
    X('X'), O('O');

    private final char value;

    Marker(char v) {
        this.value = v;
    }

    public static Optional<Marker> getValue(String value) {
        return Arrays.stream(Marker.values())
                .filter(marker -> marker.name().equalsIgnoreCase(value))
                .findFirst();
    }

    public char getValue() {
        return value;
    }

    public Marker next() {
        return this == O ? X : O;
    }
}
