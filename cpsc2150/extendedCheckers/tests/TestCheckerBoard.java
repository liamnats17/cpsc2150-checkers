package cpsc2150.extendedCheckers.tests;

import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;

import static org.junit.Assert.*;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import org.junit.Test;

import java.util.Map;

public class TestCheckerBoard {
    
    // Helper to build expected board string
    private String arrayToString(char[][] board) {
        StringBuilder s = new StringBuilder("|  |");
        int numRows = board.length;
        int numCols = board[0].length;
        
        for (int i = 0; i < numCols; i++) {
            if (i < 10) {
                    s.append(" ").append(i).append("|");
            } else {
                s.append(i).append("|");
            }
        }
        s.append("\n");

        for (int i = 0; i < numRows; i++) {
            if (i < 10) {
                s.append("|").append(i).append(" ");
            } else {
                s.append("|").append(i);
            }

            for (int j = 0; j < numCols; j++) {
                s.append("|");
                char pos = board[i][j];
                if (pos != '*') {
                    s.append(pos).append(" ");
                } else {
                    s.append("* ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
  
  // Helper to build default 2d array representation 
    private void initializeDefaultBoard(char[][] board) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = (row + col) % 2 == 1 ? '*' : 'x';
            }
        }
        for (int row = 3; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = (row + col) % 2 == 1 ? '*' : ' ';
            }
      }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = (row + col) % 2 == 1 ? '*' : 'o';
            }
        }
    }

    // ------------------ Constructor Test ------------------
    @Test
    public void testCheckerBoard_Initialize() {
        CheckerBoard cb = new CheckerBoard();
        char[][] expected = new char[8][8];
        initializeDefaultBoard(expected);
        assertEquals(arrayToString(expected), cb.toString());
    }

    // ------------------ whatsAtPos Tests ------------------
    @Test
    public void testWhatsAtPos_MinRow_MinCol_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        assertEquals('x', cb.whatsAtPos(new BoardPosition(0, 0)));
    }

    @Test
    public void testWhatsAtPos_MaxRow_MaxCol() {
        CheckerBoard cb = new CheckerBoard();
        assertEquals('o', cb.whatsAtPos(new BoardPosition(7, 7)));
    }

    @Test
    public void testWhatsAtPos_Empty_Space() {
        CheckerBoard cb = new CheckerBoard();
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(3, 1)));
    }

    @Test
    public void testWhatsAtPos_Black_Tile() {
        CheckerBoard cb = new CheckerBoard();
        assertEquals('*', cb.whatsAtPos(new BoardPosition(0, 1)));
    }

    @Test
    public void testWhatsAtPos_After_Turn_1_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(2, 2), DirectionEnum.SE);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(3, 3)));
    }

    // ------------------ placePiece Tests ------------------
    @Test
    public void testPlacePiece_Empty_Tile_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        char[][] expected = new char[8][8];
        initializeDefaultBoard(expected);
        cb.placePiece(new BoardPosition(3, 1), 'x');
        expected[3][1] = 'x';
        assertEquals(arrayToString(expected), cb.toString());
    }

    @Test
    public void testPlacePiece_Occupied_Tile_Empty() {
        CheckerBoard cb = new CheckerBoard();
        char[][] expected = new char[8][8];
        initializeDefaultBoard(expected);
        cb.placePiece(new BoardPosition(5, 1), ' ');
        expected[5][1] = ' ';
        assertEquals(arrayToString(expected), cb.toString());
    }

    @Test
    public void testPlacePiece_Empty_Tile_Player_2() {
        CheckerBoard cb = new CheckerBoard();
        char[][] expected = new char[8][8];
        initializeDefaultBoard(expected);
        cb.placePiece(new BoardPosition(4, 6), 'o');
        expected[4][6] = 'o';
        assertEquals(arrayToString(expected), cb.toString());
    }

    @Test
    public void testPlacePiece_After_Turn_1_Player_2() {
        CheckerBoard cb = new CheckerBoard();
        char[][] expected = new char[8][8];
        initializeDefaultBoard(expected);
        cb.movePiece(new BoardPosition(2, 4), DirectionEnum.SE);
        expected[2][4] = ' ';
        expected[3][5] = 'x';
        cb.placePiece(new BoardPosition(2, 4), 'o');
        expected[2][4] = 'o';
        assertEquals(arrayToString(expected), cb.toString());
    }

    @Test
    public void testPlacePiece_After_Turn_2_Player_1() {
        /*CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(2, 4), DirectionEnum.SE);
        expected[2][4] = ' ';
        expected[3][5] = 'x';
        cb.movePiece(new BoardPosition(5, 7), DirectionEnum.NW);
        expected[5][7] = ' ';
        expected[4][6] = 'o';
        cb.placePiece(new BoardPosition(5, 7), 'x');
        expected[5][7] = 'x';
        assertEquals(arrayToString(expected), cb.toString());*/
    }

    // ------------------ getPieceCounts Test ------------------
    @Test
    public void testGetPieceCounts_InitialBoard() {
        CheckerBoard cb = new CheckerBoard();
        Map<Character, Integer> counts = cb.getPieceCounts();
        assertEquals((Integer) 12, counts.get('x'));
        assertEquals((Integer) 12, counts.get('o'));
    }

    // ------------------ getViableDirections Test ------------------
    @Test
    public void testGetViableDirections() {
        /*CheckerBoard cb = new CheckerBoard();
        Map<Character, List<DirectionEnum>> dirs = cb.getViableDirections();
        assertEquals(Arrays.asList(DirectionEnum.SE, DirectionEnum.SW), dirs.get('x'));
        assertEquals(Arrays.asList(DirectionEnum.NE, DirectionEnum.NW), dirs.get('o'));
        assertEquals(Arrays.asList(DirectionEnum.SE, DirectionEnum.SW, DirectionEnum.NE, DirectionEnum.NW), dirs.get('X'));
        assertEquals(Arrays.asList(DirectionEnum.SE, DirectionEnum.SW, DirectionEnum.NE, DirectionEnum.NW), dirs.get('O'));*/
    }

    // ------------------ checkPlayerWin Tests ------------------
    @Test
    public void testCheckPlayerWin_False_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        assertFalse(cb.checkPlayerWin('x'));
    }

    @Test
    public void testCheckPlayerWin_True_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col += 2) {
                cb.placePiece(new BoardPosition(row, col), ' ');
            }
        }
        assertTrue(cb.checkPlayerWin('x'));
    }

    // ------------------ crownPiece Tests ------------------
    @Test
    public void testCrownPiece_Back_Row_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        cb.placePiece(new BoardPosition(7, 1), 'x');
        cb.crownPiece(new BoardPosition(7, 1));
        assertEquals('X', cb.whatsAtPos(new BoardPosition(7, 1)));
    }

    @Test
    public void testCrownPiece_Middle_Board_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        cb.crownPiece(new BoardPosition(2, 6));
        assertEquals('X', cb.whatsAtPos(new BoardPosition(2, 6)));
    }

    @Test
    public void testCrownPiece_Back_Row_Player_2() {
        CheckerBoard cb = new CheckerBoard();
        cb.placePiece(new BoardPosition(0, 2), 'o');
        cb.crownPiece(new BoardPosition(0, 2));
        assertEquals('O', cb.whatsAtPos(new BoardPosition(0, 2)));
    }

    // ------------------ movePiece Tests ------------------
    @Test
    public void testMovePiece_Player_1_SE() {
        CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(2, 0), DirectionEnum.SE);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(3, 1)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(2, 0))); // Tests if starting position is now blank
    }

    @Test
    public void testMovePiece_Player_2_NE() {
        CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(5, 5), DirectionEnum.NE);
        assertEquals('o', cb.whatsAtPos(new BoardPosition(4, 6)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(5, 5)));
    }

    @Test
    public void testMovePiece_Crowned_SW() {
        CheckerBoard cb = new CheckerBoard();
        cb.placePiece(new BoardPosition(4, 4), 'X');
        cb.placePiece(new BoardPosition(5, 3), ' ');
        cb.movePiece(new BoardPosition(4, 4), DirectionEnum.SW);
        assertEquals('X', cb.whatsAtPos(new BoardPosition(5, 3)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(4, 4)));
    }

    // ------------------ jumpPiece Tests ------------------
    @Test
    public void testJumpPiece_Player_1_Jump_Player_2() {
        CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(2, 2), DirectionEnum.SE);
        cb.movePiece(new BoardPosition(5, 1), DirectionEnum.NE);
        cb.jumpPiece(new BoardPosition(3, 3), DirectionEnum.SW);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(5, 1)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(3, 3))); // Tests if starting position is now blank
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(4, 2))); // Tests if jumped position is now blank
    }

    @Test
    public void testJumpPiece_Player_2_Jump_Player_1() {
        CheckerBoard cb = new CheckerBoard();
        cb.movePiece(new BoardPosition(2, 2), DirectionEnum.SE);
        cb.movePiece(new BoardPosition(3, 3), DirectionEnum.SW);
        cb.jumpPiece(new BoardPosition(5, 3), DirectionEnum.NW);
        assertEquals('o', cb.whatsAtPos(new BoardPosition(3, 1)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(5, 3)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(4, 2)));
    }

    @Test
    public void testJumpPiece_Crowned_Backward() {
        CheckerBoard cb = new CheckerBoard();
        cb.placePiece(new BoardPosition(5, 5), 'X');
        cb.placePiece(new BoardPosition(2, 6), ' ');
        cb.movePiece(new BoardPosition(5, 7), DirectionEnum.NW);
        cb.jumpPiece(new BoardPosition(5, 5), DirectionEnum.NE);
        assertEquals('X', cb.whatsAtPos(new BoardPosition(3, 7)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(5, 5)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(4, 6)));
    }

    // ------------------ scanSurroundingPositions Tests ------------------
    @Test
    public void testScanSurroundingPositions_AllEmpty() {
        CheckerBoard cb = new CheckerBoard();
        cb.placePiece(new BoardPosition(4, 2), ' ');
        Map<DirectionEnum, Character> result = cb.scanSurroundingPositions(new BoardPosition(4, 2));
        assertEquals(' ', (char) result.get(DirectionEnum.SE));
        assertEquals(' ', (char) result.get(DirectionEnum.SW));
        assertEquals(' ', (char) result.get(DirectionEnum.NE));
        assertEquals(' ', (char) result.get(DirectionEnum.NW));
    }

    @Test
    public void testScanSurroundingPositions_AllOccupied() {
        CheckerBoard cb = new CheckerBoard();
        Map<DirectionEnum, Character> result = cb.scanSurroundingPositions(new BoardPosition(6, 2));
        assertEquals('o', (char) result.get(DirectionEnum.SE));
        assertEquals('o', (char) result.get(DirectionEnum.SW));
        assertEquals('o', (char) result.get(DirectionEnum.NE));
        assertEquals('o', (char) result.get(DirectionEnum.NW));
    }

    @Test
    public void testScanSurroundingPositions_BoardEdge() {
        CheckerBoard cb = new CheckerBoard();
        Map<DirectionEnum, Character> result = cb.scanSurroundingPositions(new BoardPosition(2, 0));
        assertEquals(' ', (char) result.get(DirectionEnum.SE));
        assertNull(result.get(DirectionEnum.SW));
        assertEquals('x', (char) result.get(DirectionEnum.NE));
        assertNull(result.get(DirectionEnum.NW));
    }
}

