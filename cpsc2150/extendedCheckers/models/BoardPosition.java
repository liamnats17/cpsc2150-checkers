package cpsc2150.extendedCheckers.models;

/**
 * @invariant Row and column must not be negative or <= board dimensions
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
     * Constructor for a BoardPosition object, initializes row and column to r and c respectively
     * @param r the row of the position
     * @param c the column of the position
     * @pre row and column must not be negative or >= board dimensions
     * @post row = r AND column = c
     */
    public BoardPosition(int r, int c) {
        column = c;
        row = r;
    }
    
     /**
      * Accessor for the row instance variable
     * @return the row of the position
     * @post getRow = row; row = #row; column = #column
     */
    public int getRow() {
        return row;
    }

    /**
     * Accessor for the column instance variable
     * @return the column of the position
     * @post getColumn = column; row = #row; column = #column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Equals method that checks to see if the passed obj is equal to the BoardPosition object
     * @param obj the object to compare to
     * @return true if obj is a BoardPosition with same row and column
     * @pre obj != null
     * @post equals = (obj instance of BoardPosition AND obj.row == row AND obj.column == column);
     * row = #row; column = #column
     */
    public boolean equals(Object obj) {
        if (obj instanceof BoardPosition) {
            BoardPosition other = (BoardPosition) obj;
            return this.row == other.row && this.column == other.column;
        }
        return false;
    }

    /**
     * Method that returns a combined string representation of the row and column variables
     * @return the string representation in format "row,column"
     * @post toString = row + "," + column; row = #row; column = #column
     */
    public String toString() {
        return row + ", " + column;
    }
}
