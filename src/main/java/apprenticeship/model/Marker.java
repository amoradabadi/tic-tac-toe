package apprenticeship.model;

import java.util.Arrays;
import java.util.Optional;

public enum Marker {
    X('x'), O('o');

    private final char value;

    Marker(char v) {
        this.value = v;
    }

    public static Optional<Marker> getValue(String value) {
        return Arrays.stream(Marker.values())
                .filter(marker -> value.equalsIgnoreCase(String.valueOf(marker.value)))
                .findFirst();
    }

    public char getValue() {
        return value;
    }

    public Marker next() {
        return this == O ? X : O;
    }
}
