package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.CheckerBoardMem;
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
        System.out.println("Welcome to Checkers!");

        System.out.print("Player 1, enter your piece: ");
        String input1 = scan.nextLine();
        while (input1.length() != 1 || !Character.isLowerCase(input1.charAt(0))) {
            System.out.print("Please enter only a single lowercase letter ");
            input1 = scan.nextLine();
        }
        char p1 = input1.charAt(0);

        System.out.print("Player 1, enter your piece: ");
        String input2 = scan.nextLine();
        while (input2.length() != 1 || !Character.isLowerCase(input1.charAt(0))) {
            System.out.print("Please enter only a single lowercase letter ");
            input2 = scan.nextLine();
        }
        char p2 = input2.charAt(0);

        char choice;
        while (true) {
            System.out.print("Do you want a fast game (F/f) or a memory efficient game (M/m)? ");
            choice = scan.nextLine().toUpperCase().charAt(0);
            if (choice != 'F' && choice != 'M') {
                System.out.println("Invalid game type. Please enter F or M. ");

            } else {
                break;
            }
        }

        int dimension;
        while (true) {
            System.out.print("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number: ");
            dimension = scan.nextInt();
            if (dimension >= 8 && dimension <= 16 && dimension % 2 == 0) {
                break;
            } else {
                System.out.println("Invalid board dimension. Try again. ");
            }
        }

        ICheckerBoard board;
        if (choice == 'M') {
            board = new CheckerBoardMem(dimension, p1, p2);
        } else {
            board = new CheckerBoard(dimension, p1, p2);
        }
        System.out.println(board);

        char currentPlayer = p1;
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
                    System.out.println("No valid piece at that position. Try again. ");
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
                        System.out.println("That direction is not valid for this piece. Try again. ");
                    } else {
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction. Try again. ");
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
                        throw new IllegalArgumentException("No opponent to jump over in that direction. ");
                    }
                    if (board.whatsAtPos(endPos) != ' ') {
                        throw new IllegalArgumentException("The landing position is occupied. ");
                    }
                    end = board.jumpPiece(start, dir);
                } else {
                    int rowEnd = start.getRow() + ((dir == DirectionEnum.NE || dir == DirectionEnum.NW) ? -1 : 1);
                    int colEnd = start.getColumn() + ((dir == DirectionEnum.NE || dir == DirectionEnum.SE) ? 1 : -1);
                    BoardPosition endPos = new BoardPosition(rowEnd, colEnd);
                    if (board.whatsAtPos(endPos) != ' ') {
                        throw new IllegalArgumentException("Destination is already occupied. ");
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
