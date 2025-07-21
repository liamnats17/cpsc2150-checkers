package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * @post board is a 8x8 grid initialized with correct starting pieces;
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

            viableDirections.put(PLAYER_ONE, p1Dirs);
            viableDirections.put(PLAYER_TWO, p2Dirs);

            for (int r = 0; r < ROW_NUM; r++) {
                for (int c = 0; c < COL_NUM; c++) {
                    if ((r + c) % 2 == 1) {
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

    /**
     * Accessor for the viableDirections HashMap.
     *
     * @return mapping of each player to their list of viable movement directions
     * @pre none
     * @post returns reference to viableDirections
     */
    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Accessor for the pieceCount HashMap.
     *
     * @return mapping of each player to number of pieces remaining
     * @pre none
     * @post returns reference to pieceCount
     */
    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    /**
     * Places a player’s piece at the specified board position.
     *
     * @param pos BoardPosition to place the piece
     * @param player character representing the player ('x', 'o', etc.)
     * @pre pos != null AND 0 <= pos.row < 8 AND 0 <= pos.col < 8 AND pos != Non_Playable_Tile
     * @post board[pos.row][pos.col] == player
     */
    @Override
    public void placePiece(BoardPosition pos, char player) {
        board[pos.getRow()][pos.getColumn()] = player;
    }

    /**
     * Returns the character at a given board position.
     *
     * @param pos the BoardPosition to check
     * @return character at that position ('x', 'o', '*', ' ', etc.)
     * @pre pos != null AND 0 <= pos.row < 8 AND 0 <= pos.col < 8
     * @post returns the value of board[pos.row][pos.col]
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
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
    @Override
    public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir) {
        char piece = whatsAtPos(startingPos);
        int newRow = startingPos.getRow() + rowChange(dir);
        int newCol = startingPos.getColumn() + colChange(dir);

        BoardPosition dest = new BoardPosition(newRow, newCol);
        placePiece(dest, piece);
        placePiece(startingPos, EMPTY_POS);

        return dest;
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
    @Override
    public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir) {
        char piece = whatsAtPos(startingPos);
        int rowMid = startingPos.getRow() + rowChange(dir);
        int colMid = startingPos.getColumn() + colChange(dir);
        int rowEnd = startingPos.getRow() + 2 * rowChange(dir);
        int colEnd = startingPos.getColumn() + 2 * colChange(dir);

        BoardPosition mid = new BoardPosition(rowMid, colMid);
        BoardPosition end = new BoardPosition(rowEnd, colEnd);

        char opponent = whatsAtPos(mid);
        placePiece(mid, EMPTY_POS);
        pieceCount.put(Character.toLowerCase(opponent), pieceCount.get(Character.toLowerCase(opponent)) - 1);

        placePiece(end, piece);
        placePiece(startingPos, EMPTY_POS);

        return end;
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
        HashMap<DirectionEnum, Character> surroundings = new HashMap<>();

        for (DirectionEnum dir : DirectionEnum.values()) {
            int r = startingPos.getRow() + rowChange(dir);
            int c = startingPos.getColumn() + colChange(dir);
            if (r >= 0 && r < ROW_NUM && c >= 0 && c < COL_NUM) {
                surroundings.put(dir, board[r][c]);
            }
        }
        return surroundings;
    }

    /**
     * Helper method for when a piece is moving to determine the row position change
     *
     * @param dir direction a piece is moving
     * @pre dir is one of NE, NW, SW, SE
     * @return int (-1, 1, or 0) representing the row position change
     */
    private int rowChange(DirectionEnum dir) {
        switch (dir) {
            case NE:
            case NW:
                return -1;
            case SE:
            case SW:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Helper method for when a piece is moving to determine the column position change
     *
     * @param dir direction a piece is moving
     * @pre dir is one of NE, NW, SW, SE
     * @return int (-1, 1, or 0) representing the colum position change
     */
    private int colChange(DirectionEnum dir) {
        switch (dir) {
            case NE:
            case SE:
                return 1;
            case NW:
            case SW:
                return -1;
            default:
                return 0;
        }
    }
}
