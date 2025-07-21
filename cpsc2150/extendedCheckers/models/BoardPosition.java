package cpsc2150.extendedCheckers.models;

/**
 * @invariant 0 <= row < 8 AND 0 <= column < 8
 */
public class BoardPosition
{
    /**
     * Row component of the BoardPosition
     */
    private int row;

    /**
     * Column component of the BoardPosition
     */
    private int column;
    
    /**
     * @param r the row of the position; must be in [0, 7]
     * @param c the column of the position; must be in [0, 7]
     * @pre 0 <= r < 8 AND 0 <= c < 8
     * @post row = r AND column = c
     */
    public BoardPosition(int aRow, int aCol) {
        /*
        Constructor for the BoardPosition object. This should set both the row and column instance variables to their
        respective parameters.
         */
    }
    
     /**
     * @return the row of the position
     * @post getRow = row
     */
    public int getRow() {
        /*
        Typical accessor for the row instance variable.
         */
    }

    /**
     * @return the column of the position
     * @post getColumn = column
     */
    public int getColumn() {
        /*
        Typical accessor for the column instance variable.
         */
    }

    /**
     * @param obj the object to compare to
     * @return true iff o is a BoardPosition with same row and column
     * @post equals = (o instanceof BoardPosition AND o.row == row AND o.column == column)
     */
    public boolean equals(Object obj) {
        /*
        returns true if this BoardPosition is equal to the parameter object. Two BoardPositions are equal if their row
        and column vlaues are the same.

        hint: it is intentional that this accepts a parameter of type Object. There is a way to check if that parameter
        Object just happens to be an instance of a BoardPosition.
         */
    }

    /**
     * @return the string representation in format "row,column"
     * @post toString = row + "," + column
     */
    public String toString() {
        /*
        returns a String representation of the BoardPosition in the format of "row,column"
         */
    }
}
