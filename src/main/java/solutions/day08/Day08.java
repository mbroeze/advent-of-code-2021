package solutions.day08;

import solutions.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Each display shows four digits.
 *
 * Each digit is rendered to a display by passing a group of characters a,b,c,d,e,f,g
 * indicating which "line" of lights should be turns on.
 *
 *  0:      1:      2:      3:      4:
 *  aaaa    ....    aaaa    aaaa    ....
 * b    c  .    c  .    c  .    c  b    c
 * b    c  .    c  .    c  .    c  b    c
 *  ....    ....    dddd    dddd    dddd
 * e    f  .    f  e    .  .    f  .    f
 * e    f  .    f  e    .  .    f  .    f
 *  gggg    ....    gggg    gggg    ....
 *
 *   5:      6:      7:      8:      9:
 *  aaaa    aaaa    aaaa    aaaa    aaaa
 * b    .  b    .  .    c  b    c  b    c
 * b    .  b    .  .    c  b    c  b    c
 *  dddd    dddd    ....    dddd    dddd
 * .    f  e    f  .    f  e    f  .    f
 * .    f  e    f  .    f  e    f  .    f
 *  gggg    gggg    ....    gggg    gggg
 *
 *  Codes
 *  0: abcefg
 *  1: cf
 *  2: acdeg
 *  3: acdfg
 *  4: bcdf
 *  5: abdfg
 *  6: abdefg
 *  7: acf
 *  8: abcdefg
 *  9: abcdfg
 *
 *  The signals have been mixed up!
 *  - the signals a through g are mixed up
 *  - they're mixed up differently for each display
 *
 *  Puzzle Input
 *
 */
public class Day08 extends AbstractSolution {
    protected  final List<SignalOutput> readings = new LinkedList<>();

    public Day08() throws IOException {
        super(8);
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                readings.add(new SignalOutput(line));
            }
        }
    }

    /** Count how many times the digits 1,4,7,8 occur in the output */
    public void solution1() {
        int signalFoundCount = 0;
        // the numbers we are looking for
        int[] NUMBERS = { 1, 4, 7, 8 };
        // Represented as signals (sets of characters)
        DigitSignal[] DIGITAL_SIGNALS = DigitSignal.digits(NUMBERS);

        Iterator<SignalOutput> iter = readings.listIterator();
        while (iter.hasNext()) { // for each output sequence of signals
            for (Set<Character> output: iter.next().output) { // for each signal
                // check to see if the signal is one of the ones we are looking for
                boolean signalRecognized = Arrays.stream(DIGITAL_SIGNALS).anyMatch(SIGNAL -> SIGNAL.size() == output.size());
                if (signalRecognized) {
                    signalFoundCount++;
                }
            }
        }

        System.out.println(signalFoundCount);
    }

    /** The signals for the numerals 0-9. Signals are represented as a set of characters. */
    protected enum DigitSignal {
        D0("abcefg"), D1("cf"), D2("acdeg"), D3("acdfg"),
        D4("bcdf"), D5("abdfg"), D6("abdefg"), D7("acf"),
        D8("abcdefg"), D9("abcdfg");

        /** Returns a DigitalSignal corresponding to the digit argument */
        public static DigitSignal digit(int digit) {
            switch (digit) {
                case 0: return D0;
                case 1: return D1;
                case 2: return D2;
                case 3: return D3;
                case 4: return D4;
                case 5: return D5;
                case 6: return D6;
                case 7: return D7;
                case 8: return D8;
                case 9: return D9;
                default: throw new IndexOutOfBoundsException("The DigitSignal class is only defined for digits 0-9");
            }
        }

        /** Returns an array of DigitSignal */
        public static DigitSignal[] digits(int[] digits) {
            DigitSignal[] digitalSignals = new DigitSignal[digits.length];
            for (int idx = 0; idx < digits.length; idx++){
                digitalSignals[idx] = DigitSignal.digit(digits[idx]);
            }
            return digitalSignals;
        }

        /** The characters contained in the signal.*/
        Set<Character> charSet;

        /** Initializes charSet from a string */
        DigitSignal(String charString) {
            charSet = new HashSet<>();
            for (Character chara: charString.toCharArray()) {
                charSet.add(chara);
            }
        }

        /** Wrapper for quick access to size function of hashset */
        int size() {
            return charSet.size();
        }
    }

    /**
     * Represents a puzzle input line. The signal field array is the signals preceding the pipe separator
     * in the puzzle input, and the output field array is those after it.
     *
     * A signal is represented as a set of characters.
     */
    protected class SignalOutput {
        public final Set<Character>[] signal;
        public final Set<Character>[] output;

        /** Loads the signal/output readings from a raw string */
        public SignalOutput(String inputLine) {
            String[] inputLineArray = inputLine.split("\\|");
            String signalEntryString = inputLineArray[0].trim();
            String outputEntryString = inputLineArray[1].trim();

            signal = parseEntry(signalEntryString);
            output = parseEntry(outputEntryString);

        }

        /** Parses a string pattern e.g., "abeg" to return a set of characters */
        private Set<Character> parsePattern(String charString) {
            Set<Character> chars = new HashSet<>();

            for (char chara: charString.trim().toCharArray()) {
                chars.add(chara);
            }

            return chars;
        }

        /** Parses a space separated sequence of string patterns, e.g., "ab ceg acss"; returns an array of a set of characters */
        private Set<Character>[] parseEntry(String entryString) {
            String[] patternStrings = entryString.split(" ");
            Set<Character>[] patterns = new Set[patternStrings.length];
            for (int idx = 0; idx < patterns.length; idx++) {
                patterns[idx] = parsePattern(patternStrings[idx].trim());
            }
            return patterns;
        }

        /** Helper methord for SignalOutput printer */
        private void printCharSet(Set<Character> charSet) {
            charSet.forEach(System.out::print);
        }

        /** Helper method SIgnalOutput printer */
        private void printCharSetArray(Set<Character>[] charSetArray) {
            Arrays.stream(charSetArray).forEach(it ->{
                printCharSet(it);
                System.out.print(" ");
            });
        }

        /** Prints SignalOutput objects to console (formatted as puzzle input)*/
        public void print() {
            printCharSetArray(signal);
            System.out.print(" | ");
            printCharSetArray(output);
            System.out.println();
        }
    }

}
