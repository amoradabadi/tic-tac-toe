package apprenticeship;

public class Constants {

    public static final int BOARD_MAX = 3;
    public static final int BOARD_MIN = 1;
    public static final String NEW_LINE = "\n";
    public static final String PIPE = "|";
    public static final String CELL_SEPARATOR_REGEX = ",";
    public static final char EMPTY = ' ';
    public static final String EXIT = "q";
    public static final String INVALID_MARKER_VALUE = "Invalid value, To begin, choose who starts the game by entering %s or %s.%n";
    public static final String INVALID_PLAYER_TYPE_VALUE = "Invalid value, select c for computer and h for Human player";
    public static final String INVALID_CELL_VALUE = "Invalid value, format is x" + CELL_SEPARATOR_REGEX + "y where x and y should be between " + BOARD_MIN + " and " + BOARD_MAX;
    public static final String INVALID_CELL_FULL_VALUE = "Cell has already selected, choose another one";
    public static final String PLAYER_TEXT = "Player %s: %n";
    public static final String NO_WINNER_TEXT = "No winner, game is draw.";
    public static final String PLAYER_HAS_WON = "Player %s has won.%n";
    public static final String INSTRUCTIONS_TEXT = """
            Welcome to Tic-Tac-Toe
                            
            To choose a cell: i,j like 1,2
            To exit: enter q
            To begin: choose who starts the game, %s or %s.
            """;

    public static final String HUMAN_OR_COMPUTER = "Is '%s' a computer (c) or a Human (h) ?%n";
    public static final String BYE = "Bye";
    public static final String PLAYER_SELECTED = "Player '%s' selected %s%n";
}
