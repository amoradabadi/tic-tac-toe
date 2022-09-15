package apprenticeship.scan;

import apprenticeship.error.QuitException;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static apprenticeship.Constants.INVALID_MARKER_DUPLICATE_VALUE;

public class CustomMarkerScanner extends ScannerHelper {
    private final Set<String> set = new HashSet<>();

    public CustomMarkerScanner(Scanner scanner) {
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


}
