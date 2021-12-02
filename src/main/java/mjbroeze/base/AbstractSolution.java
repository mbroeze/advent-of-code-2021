package mjbroeze.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An abstract class intended to be inherited by challenge solutions. Provides methods
 * for loading data. Data for day N is assumed to be in 'resources/dayN/input1.txt, where
 * N is a zero-padded two digit positive integer.
 */
public class AbstractSolution {
    private final int dayNumber;

    public AbstractSolution(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * Helper method for creating the path to the data input file relative to the resources dir
     * @return The relative path to the data input file from the resources dir
     */
    private String getResourcePath() {
        return String.format("day%02d/input.txt", dayNumber);
    }

    /**
     * Loads the challenge input data file as an InputStream.
     * @return The challenge input file as an InputStream
     * @throws IllegalArgumentException If the resource path is not found
     */
    public InputStream getResourceAsInputStream() throws IllegalArgumentException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(getResourcePath());

        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid resource path when loading input file from " + getResourcePath());
        }

        return inputStream;
    }

    /**
     * Loads the challenge input file as an InputStreamReader.
     * @return The challenge input file as an InputStreamReader
     */
    public InputStreamReader getResourceAsInputStreamReader() {
        return new InputStreamReader(getResourceAsInputStream());
    }

    /**
     * Loads the challenge input file as a BufferedReader.
     * Does not handle any I/O exceptions: the expectation is that this BufferedReader will be
     * opened (and automatically closed) in a try/catch block that catches IOException.
     * @return The challenge input file as a BufferedReader.
     * @throws IOException if an I/O exception occurs
     */
    public BufferedReader getResourceAsBufferedReader() throws IOException {
        return new BufferedReader(getResourceAsInputStreamReader());
    }
}
