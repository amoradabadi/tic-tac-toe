package apprenticeship.scan;


import apprenticeship.error.QuitException;

import java.util.Scanner;

import static apprenticeship.Constants.EXIT;

public class ScannerHelper {
    private final Scanner scanner;

    public ScannerHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String blockScannerAndGetUserInput() throws QuitException {
        String next = scanner.next();
        if (next.trim().equalsIgnoreCase(EXIT)) {
            throw new QuitException();
        }
        return next.trim();
    }

}
