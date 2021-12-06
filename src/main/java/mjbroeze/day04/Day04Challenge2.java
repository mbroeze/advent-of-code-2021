package mjbroeze.day04;

import java.io.IOException;
import java.util.Iterator;

/**
 * This is the same as challenge 1, but we don't want to upset the squid and are playing to lose.
 * What is the score of the last bingo board to win?
 */
public class Day04Challenge2 extends Day04Challenge1 {
    /**
     * This constructor loads the bingo numbers called, and the boards
     */
    public Day04Challenge2() throws IOException {
    }

    @Override
    public void solution() {
        int score = 0;
        for (int number: bingoNumbers) {
            for (int[][] board: bingoBoards) {
                // mark board
                markBoard(board, number);
                // check if it won
                if (checkBingoBoard(board)) {
                    // update the score if so
                    score = scoreBoard(board, number);
                }
            }

            // remove winning boards
            bingoBoards.removeIf(Day04Challenge2::checkBingoBoard);
        }

        System.out.println(score);
    }
}
