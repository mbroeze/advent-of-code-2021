package solutions.day07;

import solutions.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day07 extends AbstractSolution {
    private int[] positions;
    private int maxPosition;

    public Day07() throws IOException {
        super(7);
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line = reader.readLine();
            positions = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            maxPosition = Arrays.stream(positions).max().getAsInt();
        }
    }

    private int fuelRequired1(int endPos) {
        return Arrays.stream(positions).map(pos -> Math.abs(pos - endPos)).sum();
    }

    private int fuelRequired2(int endPos) {
        return Arrays.stream(positions).map(pos -> {
            int diff = Math.abs(pos - endPos);
            return diff * (diff + 1) / 2;
        }).sum();
    }

    public void solution() {
        int minPosition = positions[0];
        int minFuel = fuelRequired2(minPosition);

        for (int pos: positions) {
            int fuel = fuelRequired2(pos);
            if (fuel < minFuel) {
                minPosition = pos;
                minFuel = fuel;
            }
        }

        System.out.println("Min Position: " + minPosition);
        System.out.println("Min Fuel " + minFuel);
    }

    class PositionCounter {
        private int[] counter;

        public PositionCounter(int[] positions) {

        }
    }
}
