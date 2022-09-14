package apprenticeship.scan;

import apprenticeship.ScannerHelper;
import apprenticeship.error.QuitException;
import apprenticeship.model.PlayerType;

import java.util.Optional;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_PLAYER_TYPE_VALUE;
import static apprenticeship.Constants.NEW_LINE;

public class PlayerTypeScanner extends ScannerHelper {
    public PlayerTypeScanner(Scanner scanner) {
        super(scanner);
    }

    public PlayerType getPlayerType() throws QuitException {
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
