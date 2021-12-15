package solutions.day06;

import solutions.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Day06 extends AbstractSolution {
    int[] ages;

    public Day06() throws IOException {
        super(6);
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line = reader.readLine();
            ages = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
        }
    }

    public void solution(int days) {
        AgeBracket bracket = new AgeBracket(ages);
        bracket.nextNDays(days);
        System.out.println(bracket.countFish());
    }

    class AgeBracket {
        private final long[] ageCounts = new long[9];

        public AgeBracket(int[] ages) {
            for (int age: ages) {
                ageCounts[age]++;
            }
        }

        private void nextDay() {
            long ageZeroCount = ageCounts[0];
            System.arraycopy(ageCounts, 1, ageCounts, 0, ageCounts.length - 1);
            ageCounts[8] = ageZeroCount;
            ageCounts[6] += ageZeroCount;
        }

        public long countFish() {
            return Arrays.stream(ageCounts).reduce(0, (sum, count) -> sum += count);
        }

        public void nextNDays(int days) {
            for (int day = 0; day < days; day++) {
                nextDay();
            }
        }
    }
}
