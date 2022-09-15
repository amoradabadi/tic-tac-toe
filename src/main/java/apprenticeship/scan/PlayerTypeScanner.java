package apprenticeship.scan;

import apprenticeship.error.QuitException;
import apprenticeship.enums.PlayerType;

import java.util.Optional;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_PLAYER_TYPE_VALUE;

public class PlayerTypeScanner extends ScannerHelper {
    public PlayerTypeScanner(Scanner scanner) {
        super(scanner);
    }

    public PlayerType getPlayerType(String text) throws QuitException {
        System.out.println(text);
        Optional<PlayerType> optional;
        do {
            String userInput = blockScannerAndGetUserInput();
            optional = PlayerType.getValue(userInput);
            if (optional.isEmpty()) {
                System.out.println(INVALID_PLAYER_TYPE_VALUE);
            }
        } while (optional.isEmpty());
        return optional.get();
    }

}
