package apprenticeship;

public class Constants {

    public static final int BOARD_MAX = 3;
    public static final int BOARD_MIN = 1;
    public static final String NEW_LINE = "\n";
    public static final String PIPE = "|";
    public static final String CELL_SEPARATOR_REGEX = ",";
    public static final char EMPTY = ' ';
    public static final String INVALID_MARKER_VALUE = "Invalid value, To begin, choose who starts the game by entering %s or %s.%n";
    public static final String INVALID_CELL_VALUE = "Invalid value, format is x" + CELL_SEPARATOR_REGEX + "y where x and y should be between " + BOARD_MIN + " and " + BOARD_MAX;
    public static final String INVALID_CELL_FULL_VALUE = "Cell has already selected, choose another one";
    public static final String PLAYER_TEXT = "Player %s: ";
    public static final String NO_WINNER_TEXT = "No winner, game is draw.";
    public static final String PLAYER_HAS_WON = "Player %s has won.";
    public static final String INSTRUCTIONS_TEXT = """
            Welcome to Tic-Tac-Toe
                            
            To select a cell for your move enter the position of the row and the column, separated with %s
            Example: 1%s3 puts the sign in the first row and third column.
                            
            To begin, choose who starts the game by entering %s or %s.
            To exit the game press q
            """;
}
