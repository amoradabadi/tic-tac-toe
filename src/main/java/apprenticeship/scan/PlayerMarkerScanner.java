package apprenticeship.scan;

import apprenticeship.enums.PlayerType;
import apprenticeship.error.QuitException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static apprenticeship.Constants.*;

public class PlayerMarkerScanner extends ScannerHelper {
    private final Set<String> set = new HashSet<>();

    public PlayerMarkerScanner(Scanner scanner) {
        super(scanner);
    }

    public String getMarker(String text) throws QuitException {
        System.out.println(text);
        boolean exists;
        String userInput;
        do {
            userInput = blockScannerAndGetUserInput();
            String finalUserInput = userInput;
            exists = set.stream().anyMatch(s -> s.equalsIgnoreCase(finalUserInput)); // case in-sensitive
            if (exists) {
                System.out.println(INVALID_MARKER_DUPLICATE_VALUE);
            }
        } while (exists);
        set.add(userInput);
        return userInput;
    }

    public PlayerType getPlayerType(String text) throws QuitException {
        System.out.println(text);
        Optional<PlayerType> optional;
        do {
            String userInput = blockScannerAndGetUserInput();
            optional = PlayerType.getValue(userInput);
            if (optional.isEmpty()) {
                System.out.println(INVALID_PLAYER_TYPE_VALUE + NEW_LINE);
            }
        } while (optional.isEmpty());
        return optional.get();
    }


}