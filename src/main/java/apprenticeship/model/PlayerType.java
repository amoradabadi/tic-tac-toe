package apprenticeship.model;

import java.util.Arrays;
import java.util.Optional;

public enum PlayerType {
    HUMAN("h"), COMPUTER("c");

    private final String value;

    PlayerType(String value) {
        this.value = value;
    }

    public static Optional<PlayerType> getValue(String value) {
        return Arrays.stream(PlayerType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst();
    }

}
