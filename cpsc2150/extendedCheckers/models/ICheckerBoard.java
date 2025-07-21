package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ICheckerBoard is an interface for a standard checkerboard. This interface defines the basic behaviors of a checkerboard
 * such as moving and placing pieces, crowning and jumping, and accessors regarding board info and victory status.
 *
 * @initialization_ensures The board is set to the standard checkers starting position with 12 pieces for each player
 *
 *
 * @defines: A board of BOARD_DIMENSIONS x BOARD_DIMENSIONS containing pieces from players (self)
 *           A count of each players' pieces (piece count)
 *           A list of all standard viable moving directions a piece can make (viable directions)
 *
 * @constraints: The board size is always BOARD_DIMENSIONS x BOARD_DIMENSIONS.
 *               Each position contains either a player piece, is a non-playable space, or is empty.
 *               Limited to two players x's and o's
 */
public interface ICheckerBoard {

    /** The board is always 8x8 */
    public static final int BOARD_DIMENSIONS = 8;

    /**
     * Accessor for the viableDirections HashMap.
     *
     * @return mapping of each player to their list of viable movement directions
     * @pre none
     * @post getViableDirections = HashMap with player characters mapped to directions; self = #self;
     * piece count = #piece count; viable directions = #viable directions
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();

    /**
     * Accessor for the pieceCount HashMap.
     *
     * @return mapping of each player to number of pieces remaining
     * @pre none
     * @post getPieceCounts = HashMap with player characters mapped to piece counts; self = #self;
     * piece count = #piece count; viable directions = #viable directions
     */
    public HashMap<Character, Integer> getPieceCounts();

    /**
     * Returns a map of directions to characters located diagonally around a given board position
     * @param startingPos the origin position to scan around
     * @return map of DirectionEnum to character at the adjacent diagonal position
     * @pre startingPos is a valid board position
     * @post scanSurroundingPositions = HashMap of all valid surrounding diagonal positions with the piece located there;
     * self = #self; piece count = #piece count; viable directions = #viable directions
     */
    public HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos);

    /**
     * Places a player's checker on the board at the specified position.
     * @param pos The position to place the checker.
     * @param player The player's character (e.g., 'X' or 'O').
     * @pre pos is within board bounds
     * @post player checker is placed at pos; piece count updated if player piece was added; `
     *  viable directions = #viable directions
     *
     */
    public void placePiece(BoardPosition pos, char player);

    /**
     * Returns the character of the player at the specified position.
     * @param pos The position to check.
     * @return The player's character, or a space ' ' if empty
     * @pre pos is within board bounds
     * @post whatsAtPos = player character at pos or associated empty char or unplayable char; #self; piece count = #piece count
     * viable directions = #viable directions
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Moves a piece from startingPos in the given direction if valid.
     * @param startingPos The position of the piece to move.
     * @param dir The direction to move in.
     * @return the new BoardPosition after moving
     * @pre startingPos contains a player piece, and move in dir is valid
     * @post movePiece = new BoardPosition of moved piece; the piece is moved one space in dir, startingPos is now empty; piece count = #piece count
     * viable directions = #viable directions
     */
    public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir);

    /**
     * Jumps a piece from startingPos in the given direction if valid.
     * @param startingPos The position of the piece to jump.
     * @param dir The direction to jump in.
     * @return the new BoardPosition after the jump
     * @pre there is an opponent piece to jump over and a valid space to land
     * @post jumpPiece = new BoardPosition of jumping piece; the piece is moved two spaces in dir, opponent piece is removed; piece count of jumped player is decreased
     * viable directions = #viable directions
     */
    public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir);

    /**
     * Makes the piece at posOfPlayer a king.
     * @param posOfPlayer The position of the piece to crown.
     * @pre posOfPlayer is within bounds and contains a non-kinged player piece
     * @post the piece at posOfPlayer is now kinged; piece count = #piece count
     * viable directions = #viable directions
     *
     */
    default public void crownPiece(BoardPosition posOfPlayer) {
        char piece = whatsAtPos(posOfPlayer);
        if (Character.isLowerCase(piece)) {
            placePiece(posOfPlayer, Character.toUpperCase(piece));
        }
    }

    /**
     * Checks if the specified player has won.
     * @param player The player to check.
     * @return true if the player has no opponent pieces remaining on the board
     * @pre player is a valid character ('x', 'o', etc.)
     * @post checkPlayerWin = true if opponent pieces = 0; false if pieces > 0; self = #self; piece count = #piece count
     * viable directions = #viable directions
     */
    default public boolean checkPlayerWin(Character player) {
        char opponent = (player == 'x') ? 'o' : 'x';
        for (int r = 0; r < BOARD_DIMENSIONS; r++) {
            for (int c = 0; c < BOARD_DIMENSIONS; c++) {
                BoardPosition pos = new BoardPosition(r, c);
                if (whatsAtPos(pos) == opponent || whatsAtPos(pos) == Character.toUpperCase(opponent)) {
                    return false;
                }
            }
        }
        return true;
    }
}
