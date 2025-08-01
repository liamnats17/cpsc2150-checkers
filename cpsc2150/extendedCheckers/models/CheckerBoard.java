package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * CheckerBoard class that implements ICheckerBoard. Implements a checkerboard using a 2D array of characters
 * to serve as the checkerboard
 *
 * @invariant a player cannot exist on a BLACK_TILE
 * @invariant player count cannot exceed starting count nor go negative
 * @invariant pieces must stay within the bounds of the board
 * @invariant when a piece is moved, a EMPTY_POS should be placed at the starting position
 */
public class CheckerBoard extends AbsCheckerBoard
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
    private final char PLAYER_ONE;
    private final char PLAYER_TWO;
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';

    private final int ROW_NUM;
    private final int COL_NUM;

    /**
     * Constructor for the CheckerBoard object.
     *
     * @param aDimension the dimension size for the board
     * @param aPlayerOne the character representing Player One
     * @param aPlayerTwo the character representing Player Two
     * @pre 8 <= aDimension <= 16 and aDimension is even; aPlayerOne and aPlayerTwo must be differing single letters
     * @post board is a aDimensionxaDimension grid initialized with correct starting pieces;
     *       pieceCount maps each player to (aDimension * playableRows) / 4;
     *       viableDirections maps each player to correct directions (standard or king)
     */
    public CheckerBoard(int aDimension, char aPlayerOne, char aPlayerTwo) {
        PLAYER_ONE = aPlayerOne;
        PLAYER_TWO = aPlayerTwo;

        ROW_NUM = aDimension;
        COL_NUM = aDimension;

        board = new char[aDimension][aDimension];
        pieceCount = new HashMap<>();
        viableDirections = new HashMap<>();

        for (int r = 0; r < aDimension; r++) {
            for (int c = 0; c < aDimension; c++) {
                board[r][c] = ((r + c) % 2 == 1) ? BLACK_TILE : EMPTY_POS;
            }
        }

        int playableRows = aDimension - 2;
        int startingCount = (aDimension * playableRows) / 4;
        pieceCount.put(PLAYER_ONE, startingCount);
        pieceCount.put(PLAYER_TWO, startingCount);

        ArrayList<DirectionEnum> p1Dirs = new ArrayList<>(List.of(DirectionEnum.SE, DirectionEnum.SW));
        ArrayList<DirectionEnum> p2Dirs = new ArrayList<>(List.of(DirectionEnum.NE, DirectionEnum.NW));
        ArrayList<DirectionEnum> kingDirs = new ArrayList<>(List.of(DirectionEnum.SE, DirectionEnum.SW, DirectionEnum.NE, DirectionEnum.NW));

        viableDirections.put(PLAYER_ONE, p1Dirs);
        viableDirections.put(Character.toUpperCase(PLAYER_ONE), kingDirs);
        viableDirections.put(PLAYER_TWO, p2Dirs);
        viableDirections.put(Character.toUpperCase(PLAYER_TWO), kingDirs);

        for (int r = 0; r < aDimension; r++) {
            for (int c = 0; c < aDimension; c++) {
                if ((r + c) % 2 != 1) {
                    if (r < playableRows / 2) {
                        board[r][c] = PLAYER_ONE;
                    } else if (r >= aDimension - playableRows / 2) {
                        board[r][c] = PLAYER_TWO;
                    }
                }
            }
        }
    }

    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    @Override
    public char getPlayerOne() {
        return PLAYER_ONE;
    }

    @Override
    public char getPlayerTwo() {
        return PLAYER_TWO;
    }

    @Override
    public void placePiece(BoardPosition pos, char player) {
        board[pos.getRow()][pos.getColumn()] = player;
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }

    @Override
    public int getDimensions() {
        return ROW_NUM;
    }
}
