package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CheckerBoard class that implements ICheckerBoard. Implements a checkerboard using a 2D array of characters
 * to serve as the checkerboard
 *
 * @invariant a player cannot exist on a BLACK_TILE
 * @invariant player count cannot exceed STARTING_COUNT nor go negative
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
     * @post board is a ROW_NUMxCOL_NUM grid initialized with correct starting pieces;
     *       pieceCount maps each player to STARTING_COUNT;
     *       viableDirections maps each player to correct directions (standard or king)
     */
    public CheckerBoard() {
        board = new char[ROW_NUM][COL_NUM];
        pieceCount = new HashMap<>();
        viableDirections = new HashMap<>();
        pieceCount.put(PLAYER_ONE, STARTING_COUNT);
        pieceCount.put(PLAYER_TWO, STARTING_COUNT);

        ArrayList<DirectionEnum> p1Dirs = new ArrayList<>();
        p1Dirs.add(DirectionEnum.SE);
        p1Dirs.add(DirectionEnum.SW);
        ArrayList<DirectionEnum> p2Dirs = new ArrayList<>();
        p2Dirs.add(DirectionEnum.NE);
        p2Dirs.add(DirectionEnum.NW);

        ArrayList<DirectionEnum> kingDirs = new ArrayList<>();
        kingDirs.add(DirectionEnum.SE);
        kingDirs.add(DirectionEnum.SW);
        kingDirs.add(DirectionEnum.NE);
        kingDirs.add(DirectionEnum.NW);

        viableDirections.put(PLAYER_ONE, p1Dirs);
        viableDirections.put(Character.toUpperCase(PLAYER_ONE), kingDirs);
        viableDirections.put(PLAYER_TWO, p2Dirs);
        viableDirections.put(Character.toUpperCase(PLAYER_TWO), kingDirs);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                if ((r + c) % 2 != 1) {
                    if (r < 3) {
                        board[r][c] = PLAYER_ONE;
                    } else if (r > 4) {
                        board[r][c] = PLAYER_TWO;
                    } else {
                        board[r][c] = EMPTY_POS;
                    }
                } else {
                    board[r][c] = BLACK_TILE;
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
    public void placePiece(BoardPosition pos, char player) {
        board[pos.getRow()][pos.getColumn()] = player;
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }
}
