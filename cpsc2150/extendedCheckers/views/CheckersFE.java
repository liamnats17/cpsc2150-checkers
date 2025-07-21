package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.Scanner;

public class CheckersFE
{
    /**
     * Main method to control the overall game flow.
     *
     * @param args Command-line arguments (unused)
     * @pre none
     * @post Game is initialized, alternates players each turn, and restarts if users agree after win
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ICheckerBoard board = new CheckerBoard();
        char currentPlayer = 'x';

        System.out.println("Welcome to Checkers!");
        System.out.println(board);

        while (true) {
            System.out.println("Player " + currentPlayer + "'s turn");
            BoardPosition start;
            while (true) {
                System.out.print("Enter the row and column of the piece to move (e.g., 2 3): ");
                int startRow = scan.nextInt();
                int startCol = scan.nextInt();
                start = new BoardPosition(startRow, startCol);
                char piece = board.whatsAtPos(start);
                if (Character.toLowerCase(piece) != currentPlayer) {
                    System.out.println("No valid piece at that position. Try again.");
                } else {
                    break;
                }
            }

            DirectionEnum dir;
            while (true) {
                System.out.print("Enter direction (NE, NW, SE, SW): ");
                try {
                    dir = DirectionEnum.valueOf(scan.next().toUpperCase());
                    ArrayList<DirectionEnum> dirs = board.getViableDirections().get(board.whatsAtPos(start));
                    if (!dirs.contains(dir)) {
                        System.out.println("That direction is not valid for this piece. Try again.");
                    } else {
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction. Try again.");
                }
            }

            System.out.print("Move or Jump? (m/j): ");
            char action = scan.next().toLowerCase().charAt(0);

            try {
                BoardPosition end;
                if (action == 'j') {
                    int rowMid = start.getRow() + ((dir == DirectionEnum.NE || dir == DirectionEnum.NW) ? -1 : 1);
                    int colMid = start.getColumn() + ((dir == DirectionEnum.NE || dir == DirectionEnum.SE) ? 1 : -1);
                    int rowEnd = start.getRow() + 2 * ((dir == DirectionEnum.NE || dir == DirectionEnum.NW) ? -1 : 1);
                    int colEnd = start.getColumn() + 2 * ((dir == DirectionEnum.NE || dir == DirectionEnum.SE) ? 1 : -1);
                    BoardPosition mid = new BoardPosition(rowMid, colMid);
                    BoardPosition endPos = new BoardPosition(rowEnd, colEnd);

                    char middle = board.whatsAtPos(mid);
                    if (middle == ' ' || Character.toLowerCase(middle) == currentPlayer) {
                        throw new IllegalArgumentException("No opponent to jump over in that direction.");
                    }
                    if (board.whatsAtPos(endPos) != ' ') {
                        throw new IllegalArgumentException("The landing position is occupied.");
                    }
                    end = board.jumpPiece(start, dir);
                } else {
                    int rowEnd = start.getRow() + ((dir == DirectionEnum.NE || dir == DirectionEnum.NW) ? -1 : 1);
                    int colEnd = start.getColumn() + ((dir == DirectionEnum.NE || dir == DirectionEnum.SE) ? 1 : -1);
                    BoardPosition endPos = new BoardPosition(rowEnd, colEnd);
                    if (board.whatsAtPos(endPos) != ' ') {
                        throw new IllegalArgumentException("Destination is already occupied.");
                    }
                    end = board.movePiece(start, dir);
                }

                if ((currentPlayer == 'x' && end.getRow() == 7) || (currentPlayer == 'o' && end.getRow() == 0)) {
                    board.crownPiece(end);
                }

                System.out.println(board);

                if (board.checkPlayerWin(currentPlayer)) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }

                currentPlayer = (currentPlayer == 'x') ? 'o' : 'x';
            } catch (Exception e) {
                System.out.println("Invalid move: " + e.getMessage());
            }
        }
    }
}
