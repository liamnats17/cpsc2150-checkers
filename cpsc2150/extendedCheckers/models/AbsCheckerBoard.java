package cpsc2150.extendedCheckers.models;

/**
 * Abstract class to implement toString for all ICheckerBoard implementations.
 */
public abstract class AbsCheckerBoard implements ICheckerBoard {

    private int VARIABLE_DIMENSIONS;

    /**
     * Converts the board to a string representation.
     * Uses the getPieceAtPos method from the interface.
     *
     * @return A string representing the current state of the board.
     * @post returns the str string itself
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        // Print header row
        str.append("|  ");
        for (int col = 0; col < getDimensions(); col++) {
            str.append("| ").append(col);
        }
        str.append("|\n");

        // Print board rows
        for (int row = 0; row < getDimensions(); row++) {
            str.append("|").append(row).append(" ");
            for (int col = 0; col < getDimensions(); col++) {
                BoardPosition pos = new BoardPosition(row, col);
                char piece = whatsAtPos(pos);
                if (piece == ' ') {
                    if ((row + col) % 2 == 0) {
                        str.append("|").append(' ').append(" ");
                    } else {
                        str.append("|").append('*').append(" ");
                    }
                } else {
                    str.append("|").append(whatsAtPos(pos)).append(" ");
                }
            }
            str.append("|\n");
        }

        return str.toString();
    }
}