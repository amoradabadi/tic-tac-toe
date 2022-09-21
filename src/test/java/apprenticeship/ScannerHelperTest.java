package apprenticeship;

import apprenticeship.error.QuitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScannerHelperTest {
    private Scanner scanner;
    private ScannerHelper scannerHelper;

    @BeforeEach
    void setUp() {
        scanner = mock(Scanner.class);
        scannerHelper = new ScannerHelper(scanner);
    }

    @Test
    void whenQ_shouldThrowException() {
        when(scanner.next()).thenReturn("q");

        assertThrows(QuitException.class, () -> scannerHelper.blockScannerAndGetUserInput());
    }

    @Test
    void whenAnythingOtherThanQ_shouldReturnValue() throws QuitException {
        when(scanner.next()).thenReturn("anything");

        assertEquals("anything", scannerHelper.blockScannerAndGetUserInput());
    }

}