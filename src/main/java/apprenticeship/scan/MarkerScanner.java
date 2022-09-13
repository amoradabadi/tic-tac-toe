package apprenticeship.scan;

import apprenticeship.ScannerHelper;
import apprenticeship.error.QuitException;
import apprenticeship.model.Marker;

import java.util.Optional;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_MARKER_VALUE;

public class MarkerScanner extends ScannerHelper {
    public MarkerScanner(Scanner scanner) {
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

}
