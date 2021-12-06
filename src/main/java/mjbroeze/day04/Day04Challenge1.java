package mjbroeze.day04;

import mjbroeze.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04Challenge1 extends AbstractSolution {
    protected static final int BINGO_BOARD_SIZE = 5;
    protected final int[] bingoNumbers;
    protected final List<int[][]> bingoBoards = new ArrayList<>();

    /** This constructor loads the bingo numbers called, and the boards */
    public Day04Challenge1() throws IOException {
        super(4);

        try (BufferedReader reader = getResourceAsBufferedReader()) {
            // load numbers called
            String bingoNumbersLine = reader.readLine();
            bingoNumbers = Arrays.stream(bingoNumbersLine.split(","))
                    .mapToInt(Integer::parseInt).toArray();

            // load boards
            String nextLine;
            // load the blank line
            while ((nextLine = reader.readLine()) != null) {
                int[][] board = new int[BINGO_BOARD_SIZE][BINGO_BOARD_SIZE];
                for (int boardRow = 0; boardRow < BINGO_BOARD_SIZE; boardRow++) {
                    nextLine = reader.readLine();
                    board[boardRow] = loadRow(nextLine);
                }
                bingoBoards.add(board);
            }
        }
    }

    public void solution() {
        int[] lastBoard;
        for (int number: bingoNumbers) {
            for (int[][] board: bingoBoards) {
                markBoard(board, number);

                if (checkBingoBoard(board)) {
                    System.out.println(scoreBoard(board, number));
                    return;
                }
            }
        }
    }

    /** Converts a whitespace separated string into an array of integers */
    private static int[] loadRow(String rowString) {
        return Arrays.stream(rowString.trim().replace("  ", " ")
                .split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    /** Helper method; Checks if an array of tiles is a bingo */
    private static boolean isBingo(int[] squares) {
        return Arrays.stream(squares).mapToObj(it -> it == -1)
                .reduce(true, (isBingo, ele) -> isBingo && ele);
    }

    /** Checks a board to determine if any rows are filled in */
    private static boolean checkCompleteRows(int[][] board) {
        for (int[] row: board) {
            if (isBingo(row)) {
                return true;
            }
        }
        return false;
    }

    /** Checks a board to determine if any cols are filled in */
    private static boolean checkCompleteColumns(int[][] board) {
        for (int idx = 0; idx < BINGO_BOARD_SIZE; idx++) {
            int[] column = new int[BINGO_BOARD_SIZE];
            for (int jdx = 0; jdx < BINGO_BOARD_SIZE; jdx++) {
                column[jdx] = board[jdx][idx];
            }
            if (isBingo(column)) {
                return true;
            }
        }
        return false;
    }

    /** Checks a board to see if any diagonals are filled in */
    private static boolean checkCompleteDiagonals(int[][] board) {
        int[] forwardDiagonal = new int[BINGO_BOARD_SIZE];
        int[] backDiagonal = new int[BINGO_BOARD_SIZE];

        for (int idx = 0; idx < BINGO_BOARD_SIZE; idx++) {
            forwardDiagonal[idx] = board[idx][idx];
            backDiagonal[idx] = board[BINGO_BOARD_SIZE - 1 - idx][idx];
        }

        return isBingo(forwardDiagonal) || isBingo(backDiagonal);
    }

    /** Checks a board to see if its a bingo */
    protected static boolean checkBingoBoard(int[][] board) {
        return checkCompleteRows(board) || checkCompleteColumns(board) || checkCompleteDiagonals(board);
    }

    /** Marks all instances of the called number on the board by -1 */
    protected static void markBoard(int[][] board, int number) {
        for (int idx = 0; idx < BINGO_BOARD_SIZE; idx++) {
            for (int jdx = 0; jdx < BINGO_BOARD_SIZE; jdx++) {
                if (board[idx][jdx] == number) {
                    board[idx][jdx] = -1;
                }
            }
        }
    }

    /** Calculates the score of a board */
    protected static int scoreBoard(int [][] board, int number) {
        int score = 0;
        for (int[] row: board) {
            for (int tileNumber: row) {
                if (tileNumber != -1) {
                    score += tileNumber;
                }
            }
        }
        return score * number;
    }
}
