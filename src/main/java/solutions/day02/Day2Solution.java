package solutions.day02;

import solutions.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;

/** We're learning to drive a submarine! */
public class Day2Solution extends AbstractSolution {
    public Day2Solution() {
        super(2);
    }

    /**
     * What is the product of the distance the submarine moved forward, and the final depth.
     */
    public void firstChallenge() {
        int forwardDistance = 0;
        int depth = 0;

        // load sequence
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                // split line into e.g., {"forward", "8"}
                String[] data = line.split(" ");

                // first element is a direction
                String direction = data[0];
                // second element is a distance
                int distance = Integer.parseInt(data[1]);

                switch (direction) {
                    case "forward":
                        forwardDistance += distance;
                        break;
                    case "down":
                        depth += distance;
                        break;
                    case "up":
                        depth -= distance;
                        break;
                    default:
                        throw new IllegalArgumentException("unrecognized direction " + direction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(forwardDistance * depth);
    }

    /**
     * What is the product of the distance the submarine moved forward, and the final depth when
     * up/down changes the AIM, not the depth, and forward moves both horizontally and vertically.
     */
    public void secondChallenge() {
        int forwardDistance = 0;
        int depth = 0;
        int aim = 0;

        // load sequence
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                // split line into e.g., {"forward", "8"}
                String[] data = line.split(" ");

                // first element is a direction
                String direction = data[0];
                // second element is a distance
                int distance = Integer.parseInt(data[1]);

                switch (direction) {
                    case "forward":
                        forwardDistance += distance;
                        depth += aim * distance;
                        break;
                    case "down":
                        aim += distance;
                        break;
                    case "up":
                        aim -= distance;
                        break;
                    default:
                        throw new IllegalArgumentException("unrecognized direction " + direction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(forwardDistance * depth);
    }
}
