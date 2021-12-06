package mjbroeze.day03;

import mjbroeze.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Next, you should verify the life support rating, which can be determined by multiplying the oxygen generator
 * rating by the CO2 scrubber rating.
 *
 * <p>Both the oxygen generator rating and the CO2 scrubber rating are values that can be found in your diagnostic
 * report - finding them is the tricky part. Both values are located using a similar process that involves filtering
 * out values until only one remains. Before searching for either rating value, start with the full list of binary
 * numbers from your diagnostic report and consider just the first bit of those numbers.</p>
 *
 * <p>Then: </p>
 * <li>
 *     <ul>
 *         Keep only numbers selected by the bit criteria for the type of rating value for which you are searching.
 *         Discard numbers which do not match the bit criteria.
 *     </ul>
 *     <ul>
 *         If you only have one number left, stop; this is the rating value for which you are searching.
 *     </ul>
 *     <ul>
 *         Otherwise, repeat the process, considering the next bit to the right.
 *     </ul>
 * </li>
 *
 * <p>The bit criteria depends on which type of rating value you want to find:</p>
 * <li>
 *     <ul>
 *         To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position,
 *         and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a
 *         1 in the position being considered.
 *     </ul>
 *     <ul>
 *         To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position, and
 *         keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 0 in
 *         the position being considered.
 *     </ul>
 * </li>
 *
 * <p>Use the binary numbers in your diagnostic report to calculate the oxygen generator rating and CO2 scrubber
 * rating, then multiply them together. What is the life support rating of the submarine? (Be sure to represent your
 * answer in decimal, not binary.)</p>
 */
public class Day03Solution2 extends AbstractSolution {
    private final static int NUMBER_OF_BITS = 12;

    public Day03Solution2() {
        super(3);
    }

    public void solution() {
        // load the data
        List<int[]> bitList = loadBitArray();

        // call the recursive functions to filter data, and return an array of ints where each element represents a bit value
        int[] oxygenRatingBits = filterOxygenData(bitList, 0);
        int[] carbonRatingBits = filterCarbonData(bitList, 0);

        // convert the ratings to decimal
        int oxygenRating = bitArrayToDecimal(oxygenRatingBits);
        int carbonRating = bitArrayToDecimal(carbonRatingBits);

        // return the answer
        System.out.println(oxygenRating * carbonRating);
    }

    /** Load data into list */
    private List<int[]> loadBitArray() {
        List<int[]> bitArrayList = new ArrayList<>();
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                int[] bitArray = parseBitString(nextLine);
                bitArrayList.add(bitArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitArrayList;
    }

    /** Finds the most common bit in the array at the given place value */
    private static int mostCommonBit(List<int[]> bitList, int placeIndex){
        int count = 0;
        for (int[] bits: bitList) {
            count += bits[placeIndex] == 1 ? +1 : -1;
        }

        if (count > 0) {
            return 1;
        } if (count < 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * A recursive function that calculates the most common bit for a given place value,
     * filters all observations that don't pass the oxygen criteria, and calls itself
     * with an incremented placeIndex. Returns a bitArray when there is only one element
     * left in the list.
     */
    private static int[] filterOxygenData(List<int[]> bitlist, int placeIndex) {
        if (bitlist.size() == 1) {
            return bitlist.get(0);
        }

        int mostCommonBit = mostCommonBit(bitlist, placeIndex) == 0 ? 0 : 1;
        List<int[]> newBitList = bitlist.stream()
                .filter(bits -> bits[placeIndex] == mostCommonBit)
                .collect(Collectors.toList());

        return filterOxygenData(newBitList, placeIndex + 1);
    }

    /**
     * A recursive function that calculates the most common bit for a given place value,
     * filters all observations that don't pass the carbon criteria, and calls itself
     * with an incremented placeIndex. Returns a bitArray when there is only one element
     * left in the list.
     */
    private static int[] filterCarbonData(List<int[]> bitlist, int placeIndex) {
        if (bitlist.size() == 1) {
            return bitlist.get(0);
        }

        int mostCommonBit = mostCommonBit(bitlist, placeIndex) == 0 ? 1 : 0;
        List<int[]> newBitList = bitlist.stream()
                .filter(bits -> bits[placeIndex] == mostCommonBit)
                .collect(Collectors.toList());

        return filterCarbonData(newBitList, placeIndex + 1);
    }

    /** Loads a string of 1s and 0s into an array of boolean */
    private static int[] parseBitString(String bitString) {
        int[] bitArray = new int[bitString.length()];
        for (int idx = 0; idx < bitArray.length; idx++) {
            bitArray[idx] = bitString.charAt(idx) == '1' ? 1 : 0;
        }
        return bitArray;
    }

    /** Converts a bit array to a decimal integer. Low indexes are high place values */
    private static int bitArrayToDecimal(int[] bits) {
        int sum = 0;
        for (int idx = 0; idx < bits.length; idx++) {
            if (bits[idx] == 1) {
                sum += (int) Math.pow(2, bits.length - 1 - idx);
            }
        }
        return sum;
    }

    /** Prints a bit array for gutchecks and debugging */
    private static String bitArrayToString(int[] bits) {
        String[] chars = new String[bits.length];
        for (int idx = 0; idx < bits.length; idx++) {
            chars[idx] = bits[idx] == 1 ? "1" : "0";
        }
        return String.join("", chars);
    }
}
