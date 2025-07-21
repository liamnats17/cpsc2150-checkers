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
     * @param r the row of the position
     * @param c the column of the position
     * @pre 0 <= r < 8 AND 0 <= c < 8
     * @post row = r AND column = c
     */
    public BoardPosition(int r, int c) {
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
     * @return true if obj is a BoardPosition with same row and column
     * @post equals = (obj instance of BoardPosition AND obj.row == row AND obj.column == column)
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
