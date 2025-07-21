package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckerBoard
{
    /**
     * A 2D array of characters used to represent our checkerboard.
     */
    private char[][] board;

    /**
     * A HashMap, with a Character key and an Integer value, that is used to map a player's char to the number of
     * tokens that player still has left on the board.
     */
    private HashMap<Character, Integer> pieceCount;

    /**
     * A HashMap, with a Character key and an ArrayList of DirectionEnums value, used to map a player (and its king
     * representation) to the directions that player can viably move in. A non-kinged (standard) piece can only move
     * in the diagonal directions away from its starting position. A kinged piece can move in the same directions the
     * standard piece can move in plus the opposite directions the standard piece can move in.
     */
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
    public static final char PLAYER_ONE = 'x';
    public static final char PLAYER_TWO = 'o';
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';

    /*
    Standard Checkers starts with 8 rows and 8 columns. Both players begin with 12 pieces each.
     */
    public static final int ROW_NUM = 8;
    public static final int COL_NUM = 8;
    public static final int STARTING_COUNT = 12;

    /**
     * Constructor for the CheckerBoard object.
     *
     * @pre none
     * @post board is an 8x8 grid initialized with correct starting pieces;
     *       pieceCount maps each player to STARTING_COUNT;
     *       viableDirections maps each player to correct directions (standard or king)
     */
    public CheckerBoard() {
        /*
        Constructor for the CheckerBoard object. The constructor should initialize the three instance variables to
        a new data structure of their respective type. Furthermore, the constructor should use the pieceCount HashMap
        to map the starting count of tokens to each player, and use the viableDirections HashMap to map the players to
        their respective starting directions (SE and SW for player one, NE and NW for player two). Finally, the
        constructor should also initialize all the indices within the checkerboard to either a player char, an asterisk
        (a 'black, non-playable' position), or a space (the 'empty' position)
         */
    }

    /**
     * Accessor for the viableDirections HashMap.
     *
     * @return mapping of each player to their list of viable movement directions
     * @pre none
     * @post returns reference to viableDirections
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        /*
        Simple accessor for the viableDirections HashMap.
         */
    }

    /**
     * Accessor for the pieceCount HashMap.
     *
     * @return mapping of each player to number of pieces remaining
     * @pre none
     * @post returns reference to pieceCount
     */
    public HashMap<Character, Integer> getPieceCounts() {
        /*
        Simple accessor for the getPieceCounts HashMap
         */
    }

    /**
     * Places a player’s piece at the specified board position.
     *
     * @param pos BoardPosition to place the piece
     * @param player character representing the player ('x', 'o', etc.)
     * @pre pos != null AND 0 <= pos.row < 8 AND 0 <= pos.col < 8 AND pos != Non_Playable_Tile
     * @post board[pos.row][pos.col] == player
     */
    public void placePiece(BoardPosition pos, char player) {
        /*
        A "mutator" for the board 2D array. This should be used for setting a given 2D index within the board 2D array,
         given by the row and col of the parameter BoardPosition, equal to the char given by player.
         */
    }

    /**
     * Returns the character at a given board position.
     *
     * @param pos the BoardPosition to check
     * @return character at that position ('x', 'o', '*', ' ', etc.)
     * @pre pos != null AND 0 <= pos.row < 8 AND 0 <= pos.col < 8
     * @post returns the value of board[pos.row][pos.col]
     */
    public char whatsAtPos(BoardPosition pos) {
        /*
        an "accessor" for the board 2D array. Returns what is at the position given by the row and col of the BoardPosition
        parameter.
         */
    }

    /**
     * Determines if the specified player has won the game.
     *
     * @param player character representing the player
     * @return true if only that player has pieces remaining
     * @pre player in pieceCount
     * @post returns true iff all remaining pieces belong to player
     */
    public boolean checkPlayerWin(Character player) {
        /*
        returns true or false if a player, designated by the player parameter, has won the game of checkers. A player
        has won if all remaining pieces on the board belong to the player.
         */
    }

    /**
     * Crowns a piece by converting it to uppercase at the specified position.
     *
     * @param posOfPlayer position of the piece to crown
     * @pre posOfPlayer != null AND board[pos.row][pos.col] is a player's standard piece
     * @post board[pos.row][pos.col] == Character.toUpperCase(originalChar)
     */
    public void crownPiece(BoardPosition posOfPlayer) {
        /*
        "crowns" a piece by converting the char at the position on the board, given by the posOfPlayer parameter, to an
        uppercase equivalent of the char.
         */
    }

    /**
     * Moves a piece one tile in the specified direction.
     *
     * @param startingPos starting position of the piece
     * @param dir direction to move
     * @return new BoardPosition after move
     * @pre startingPos != null AND dir != null AND destination is valid and empty
     * @post board[startingPos] == EMPTY_POS, board[newPos] == piece; returns newPos
     */
    public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir) {
        /*
        A modified version of placePiece moves a piece on the board, located at the startingPos index, in the direction
        designated by the DirectionEnum dir. Remember, not only should a piece now appear at the moved location, but the
        position the piece moved from should now be empty.
         */
    }

    /**
     * Moves a piece by jumping over an opponent's piece.
     *
     * @param startingPos starting position of the piece
     * @param dir direction to jump
     * @return new BoardPosition after jump
     * @pre startingPos != null AND dir != null AND intermediate square has opponent AND landing square is empty
     * @post jumped piece is removed, count decremented; board[startingPos] == EMPTY_POS, board[newPos] == piece
     */
    public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir) {
        /*
        A modified version of movePiece that moves a piece by "jumping" an opponent player piece. When a player "jumps"
        an opponent, that player should move two positions in the direction passed in by dir parameter. Remember, not
        only should the piece now appear at the moved location, and the starting position should not be empty, but the
        position that was "jumped" should now also be empty.

        Furthermore, this method should remove 1 from the opponent's pieceCount.
         */
    }

    /**
     * Scans the surrounding positions and maps each direction to the character at that adjacent position.
     *
     * @param startingPos center position to scan from
     * @return HashMap of DirectionEnum to character at adjacent positions
     * @pre startingPos != null
     * @post returns map of up to 4 direction-to-char entries representing valid diagonal neighbors
     */
    public HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos) {
        /*
        "Scans" the indices surrounding the index given by the startingPos parameter. There are a few different ways
        we can use this method, so I won't specify any given one in this description. Still, know that this function
        should return a HashMap mapping the four DirectionEnums to the char located at that position in the respective
        direction.

        For example, the position DirectionEnum.SE of startingPos (2,2) is position (3,3). If position (3,3) is empty,
        this function would return a HashMap where DirectionEnum.SE is mapped to ' '. If position (3,3) contained a
        player, it would map DirectionEnum.SE to the char that represents that player.
         */
    }

    /**
     * Returns a formatted string representing the board layout.
     *
     * @return the 8x8 board in string format with headers for rows and columns
     * @pre none
     * @post string output accurately reflects current board state; no print calls are made
     */
    public String toString()
    {
        /*
        returns a String representation of the checkerboard with all the pieces on it and their current positions. there
        should be a "header" line to display all the column numbers and a "header column" that displays all the row
        numbers. In essence, it should look like this:

        |  | 0| 1| 2| 3| 4| 5| 6| 7|
        |0 |x |* |x |* |x |* |x |* |
        |1 |* |x |* |x |* |x |* |x |
        |2 |x |* |x |* |x |* |x |* |
        |3 |* |  |* |  |* |  |* |  |
        |4 |  |* |  |* |  |* |  |* |
        |5 |* |o |* |o |* |o |* |o |
        |6 |o |* |o |* |o |* |o |* |
        |7 |* |o |* |o |* |o |* |o |

        THIS FUNCTION DOES NOT PRINT TO THE CONSOLE OR MAKE ANY KIND OF SYSTEM.OUT.PRINTLN CALLS
         */
    }
}
