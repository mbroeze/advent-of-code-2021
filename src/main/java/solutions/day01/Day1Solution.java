package solutions.day01;

import solutions.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * We're checking the depth measurements of a submarine!
 */
public class Day1Solution extends AbstractSolution {
    public Day1Solution() {
        super(1);
    }

    /**
     * Given a sequence of numbers (depth measurements), determine how often consecutive elements increase.
     */
    public void firstChallenge() {
        // final count of how often the depth increases
        int depthIncreasingCount = 0;

        // load sequence
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            int prevDepth = Integer.parseInt(reader.readLine());
            int currentDepth;

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                currentDepth = Integer.parseInt(currentLine);

                if (prevDepth < currentDepth) {
                    depthIncreasingCount++;
                }

                prevDepth = currentDepth;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(depthIncreasingCount);
    }

    /**
     * Given a sequence of numbers (depth measurements), determine how often the rolling sum of
     * three consecutive measurements increases.
     *
     * Problem solution: This is exactly the same as checking if an element at index N is less than
     * the element at index N+3.
     */
    public void secondChallenge() {
        // final count of how often the rolling depth sum increases
        int depthIncreasingCount = 0;

        // load data
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            // load first three measurements into the deque
            Deque<Integer> rollingDepths = new ArrayDeque<>();
            for (int i = 0; i < 3; i++) {
                rollingDepths.addLast(Integer.parseInt(reader.readLine()));
            }

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // add the next measurement to the end of the deque
                rollingDepths.addLast(Integer.parseInt(currentLine));

                // if the first measurement is less than the last measurement, then so is the rolling sum
                // ((middle two measurements are the same)
                if (rollingDepths.pollFirst() < rollingDepths.peekLast()) {
                    depthIncreasingCount++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(depthIncreasingCount);
    }
}
