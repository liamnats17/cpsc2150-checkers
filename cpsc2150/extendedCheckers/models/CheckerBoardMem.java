package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import java.util.*;

/**
 * CheckerBoard class that implements ICheckerBoard. Implements a checkerboard using a 2D array of characters
 * to serve as the checkerboard
 *
 * @invariant a player cannot exist on a BLACK_TILE
 * @invariant player count cannot exceed starting count nor go negative
 * @invariant pieces must stay within the bounds of the board
 * @invariant when a piece is moved, a EMPTY_POS should be placed at the starting position
 */
public class CheckerBoardMem extends AbsCheckerBoard {
    private Map<Character, ArrayList<BoardPosition>> board;
    private HashMap<Character, Integer> pieceCount;
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
    private final char PLAYER_ONE, PLAYER_TWO;
    private final int COL_NUM, ROW_NUM;

    /**
     * Constructor for the CheckerBoardMem object.
     *
     * @param aDimension the dimension size for the board
     * @param aPlayerOne the character representing Player One
     * @param aPlayerTwo the character representing Player Two
     * @pre 8 <= aDimension <= 16 and aDimension is even; aPlayerOne and aPlayerTwo must be differing single characters
     * @post board is a aDimensionxaDimension grid initialized with correct starting pieces;
     *       pieceCount maps each player to (aDimension * playableRows) / 4;
     *       viableDirections maps each player to correct directions (standard or king)
     */
    public CheckerBoardMem(int aDimension, char aPlayerOne, char aPlayerTwo) {
        PLAYER_ONE = aPlayerOne;
        PLAYER_TWO = aPlayerTwo;

        ROW_NUM = aDimension;
        COL_NUM = aDimension;

        board = new HashMap<>();
        board.put(PLAYER_ONE, new ArrayList<>());
        board.put(PLAYER_TWO, new ArrayList<>());

        pieceCount = new HashMap<>();

        int playableRows = aDimension - 2;
        int startingCount = (aDimension * playableRows) / 4;
        pieceCount.put(PLAYER_ONE, startingCount);
        pieceCount.put(PLAYER_TWO, startingCount);

        viableDirections = new HashMap<>();
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
                        board.get(PLAYER_ONE).add(new BoardPosition(r, c));
                    } else if (r >= aDimension - playableRows / 2) {
                        board.get(PLAYER_TWO).add(new BoardPosition(r, c));
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
        for (Map.Entry<Character, ArrayList<BoardPosition>> entry : board.entrySet()) {
            if (entry.getValue().remove(pos)) {
                break;
            }
        }

        // Add the position to the correct player's list
        board.computeIfAbsent(player, k -> new ArrayList<>()).add(pos);
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        for (Map.Entry<Character, ArrayList<BoardPosition>> entry : board.entrySet()) {
            if (entry.getValue().contains(pos)) {
                return entry.getKey();
            }
        }
        if ((pos.getRow() + pos.getColumn()) % 2 != 0) {
            return '*';
        }
        return ' ';
    }

    @Override
    public int getDimensions() {
        return ROW_NUM;
    }
}