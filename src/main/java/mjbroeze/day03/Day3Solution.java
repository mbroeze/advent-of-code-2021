package mjbroeze.day03;

import mjbroeze.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.IntStream;

/**
 * The submarine has been making some odd creaking noises, so you ask it to produce a diagnostic report just in case.
 *
 * <p>The diagnostic report (your puzzle input) consists of a list of binary numbers which, when decoded properly,
 * can tell you many useful things about the conditions of the submarine.</p>
 */
public class Day3Solution extends AbstractSolution {
    public Day3Solution() {
        super(3);
    }

    /**
     * The first parameter to check is the power consumption.
     * <p>You need to use the binary numbers in the diagnostic report to generate two new binary numbers (called the gamma
     * rate and the epsilon rate). The power consumption can then be found by multiplying the gamma rate by the epsilon rate.</p>
     *
     * <p>Each bit in the gamma rate can be determined by finding the most common bit in the corresponding position of all
     * numbers in the diagnostic report. The most common bit in the Nth row is the Nth bit of the gamma rate</p>
     *
     * <p>Similarly, the Nth epsilon bit is the least common bit in the Nth row.</p>
     *
     * <p>Calculate the power consumption (in base 10), which is equal to the product of the epsilon and gamma rates.</p>
     */
    public void firstChallenge() {
        final int NUMBER_OF_BITS = 12;
        int gammaRate = 0;
        int epsilonRate = 0;

        try (BufferedReader reader = getResourceAsBufferedReader()) {
            // this array will hold the value counts: init elements to zero
            // increment bitCount[N} for a 1 at index N, similarly decrement for a zero
            int[] bitCount = new int[NUMBER_OF_BITS];

            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < bitCount.length; idx++) {
                    char bit = line.charAt(idx);
                    if (bit == '0') {
                        bitCount[idx]--;
                    } else if (bit == '1') {
                        bitCount[idx]++;
                    } else {
                        throw new IllegalArgumentException("unrecognized character found at line " + line);
                    }
                }
            }

            // if count at Nth index is positive, then Nth bit is 1, else 0
            int[] gammaBits = Arrays.stream(bitCount).map(count -> count > 0 ? 1 : 0).toArray();

            /* calculate the values of gamma and epsilon by iterating over each of the 'place values' of each digit in
             * the binary number represented by gammaBits */
            for (int idx = 0; idx < gammaBits.length; idx++) {
                int bit = gammaBits[idx];
                int exponent = gammaBits.length - idx - 1;
                int placeValue = (int) Math.pow(2, exponent);

                if (bit == 1) { // add the placeValue to gammaRate
                    gammaRate += placeValue;
                } else { // the corresponding epsilon bit is 1, so add placeValue to epsilonRate
                    epsilonRate += placeValue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(gammaRate * epsilonRate);
    }
}
