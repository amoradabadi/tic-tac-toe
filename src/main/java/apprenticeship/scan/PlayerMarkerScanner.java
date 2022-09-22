package apprenticeship.scan;

import apprenticeship.ScannerHelper;
import apprenticeship.error.QuitException;
import apprenticeship.model.Marker;
import apprenticeship.model.PlayerType;

import java.util.Optional;
import java.util.Scanner;

import static apprenticeship.Constants.*;

public class PlayerMarkerScanner extends ScannerHelper {
    public PlayerMarkerScanner(Scanner scanner) {
        super(scanner);
    }

    public Marker getMarker() throws QuitException {
        Optional<Marker> optionalMarker;
        do {
            String userInput = blockScannerAndGetUserInput();
            optionalMarker = Marker.getValue(userInput);
            if (optionalMarker.isEmpty()) {
                System.out.printf(INVALID_MARKER_VALUE, Marker.X, Marker.O);
            }
        } while (optionalMarker.isEmpty());
        return optionalMarker.get();
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
