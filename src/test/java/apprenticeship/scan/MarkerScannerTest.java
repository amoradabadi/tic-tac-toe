package apprenticeship.scan;

import apprenticeship.error.QuitException;
import apprenticeship.model.Marker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_MARKER_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MarkerScannerTest {

    private Scanner scanner;
    private MarkerScanner markerScanner;
    private ByteArrayOutputStream systemOut;

    @BeforeEach
    void setUp() {
        systemOut = new ByteArrayOutputStream();
        scanner = mock(Scanner.class);
        markerScanner = new MarkerScanner(scanner);
        System.setOut(new PrintStream(systemOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "X", " X", "X ", " X "})
    void whenWithDifferentCase_shouldSelectX(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput);

        assertEquals(Marker.X, markerScanner.getMarker());
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenWithDifferentCase_shouldSelectO(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput);

        assertEquals(Marker.O, markerScanner.getMarker());
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenInvalidMarker_shouldReturnError(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput, "X");

        markerScanner.getMarker();

        assertTrue(systemOut.toString().contains(INVALID_MARKER_VALUE.formatted(Marker.X, Marker.O)));
    }

    @Test
    void whenQ_shouldThrowException() {
        when(scanner.next()).thenReturn("q");

        assertThrows(QuitException.class, () -> markerScanner.getMarker());
    }

}