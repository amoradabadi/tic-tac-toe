package apprenticeship.scan;

import apprenticeship.enums.PlayerType;
import apprenticeship.error.QuitException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static apprenticeship.Constants.INVALID_PLAYER_TYPE_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerMarkerScannerTest {

    private Scanner scanner;
    private PlayerMarkerScanner playerMarkerScanner;
    private ByteArrayOutputStream systemOut;

    @BeforeEach
    void setUp() {
        systemOut = new ByteArrayOutputStream();
        scanner = mock(Scanner.class);
        playerMarkerScanner = new PlayerMarkerScanner(scanner);
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

        assertEquals(userInput.trim(), playerMarkerScanner.getMarker(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"o", "O", " O", "O ", " O "})
    void whenWithDifferentCase_shouldSelectO(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput);

        assertEquals(userInput.trim(), playerMarkerScanner.getMarker(""));
    }

    @Test
    void whenQ_shouldThrowException() {
        when(scanner.next()).thenReturn("q");

        assertThrows(QuitException.class, () -> playerMarkerScanner.getMarker(""));
    }


    @ParameterizedTest
    @ValueSource(strings = {"h", "H", " H", "H ", " H "})
    void whenWithDifferentCase_playerTypeShouldSelectH(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput);

        assertEquals(PlayerType.HUMAN, playerMarkerScanner.getPlayerType(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"c", "C", " C", "C ", " C "})
    void whenWithDifferentCase_playerTypeShouldSelectC(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput);

        assertEquals(PlayerType.COMPUTER, playerMarkerScanner.getPlayerType(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"anything", "XX", "XO"})
    void whenInvalidPlayerType_shouldReturnError(String userInput) throws QuitException {
        when(scanner.next()).thenReturn(userInput, "h");

        playerMarkerScanner.getPlayerType("");

        assertTrue(systemOut.toString().contains(INVALID_PLAYER_TYPE_VALUE));
    }

    @Test
    void whenQinPlayerType_shouldThrowException() {
        when(scanner.next()).thenReturn("q");

        assertThrows(QuitException.class, () -> playerMarkerScanner.getPlayerType(""));
    }

}