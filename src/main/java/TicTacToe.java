public class TicTacToe {
    public static final String NEW_LINE = "\n";
    public final char EMPTY = ' ';

    public char[][] board = new char[][]{
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY},
            new char[]{EMPTY, EMPTY, EMPTY}
    };


    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe");
    }

    public String showInstructions() {
        return """
                Welcome to Tic-Tac-Toe
                                
                To select a cell for your move enter the position of the row and the column, separated with an x.
                Example: 1x3 puts the sign in the first row and third column.
                                
                To begin, choose who starts the game by entering X or O.
                """;
    }

    public String printTable() {
        final String separatorRow = "+---+---+---+";
        final String pipe = "|";
        StringBuilder sb = new StringBuilder();
        for (char[] chars : this.board) {
            sb.append(separatorRow).append(NEW_LINE);
            for (char aChar : chars) {
                sb.append(pipe).append("%-3s".formatted(aChar));
            }
            sb.append(pipe).append(NEW_LINE);
        }
        sb.append(separatorRow);
        return sb.toString();
    }
}