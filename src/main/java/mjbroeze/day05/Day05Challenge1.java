package mjbroeze.day05;

import mjbroeze.base.AbstractSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Defines two inner classes Point (which is hashable), and Line (horizontal, vertical or diagonal. The Line class
 * method 'getPoints` returns a list of Points between the Points line.start and line.end (inclusive). We wish to
 * determine how many points are intersected by more than one line.
 *
 * To do this, we iterate through the lines, and add each of the points in that line to one of two Sets according to
 * the following rules:
 * 1. If the Point is in the Set foundTwice, nothing is done
 * 2. If the Point is in the Set foundOnce, then it is removed from that set, and added to foundTwice
 * 3. If the Point is in neither of those sets, it is added to foundOnce
 *
 * The number of points at which lines intersect is the size of foundTwice.
 */
public class Day05Challenge1 extends AbstractSolution {
    protected final List<Line> lines = new LinkedList<>();

    public Day05Challenge1() throws IOException {
        super(5);
        try (BufferedReader reader = getResourceAsBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(new Line(line));
            }
        }
    }

    public void solution() {
        // consider only the horizontal or vertical lines
        lines.removeIf(Line::isDiagonal);

        System.out.println(findOverLappingLines());
    }

    protected class Point {
        final int x;
        final int y;

       public Point(int x, int y) {
           this.x = x;
           this.y = y;
       }

        /**
         * Creates a Point from the String representation from the input file
         * @param pointInputString a String of the form 504,385
         */
        public Point(String pointInputString) {
            int[] coordinates = Arrays.stream(pointInputString.split(","))
                    .mapToInt(Integer::parseInt).toArray();
            this.x = coordinates[0];
            this.y = coordinates[1];
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            } if (object == null || object.getClass() != this.getClass()) {
                return false;
            }

            Point point = (Point) object;
            return point.x == this.x && point.y == this.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }

        @Override
        public String toString() {
            return this.x + "," + this.y;
        }
    }

    /**
     * A line, represented by two points. Contains a constructor for initializing from input file line.
     */
    protected class Line {
        final Point start;
        final Point end;

        /**
         * Creates a Line from a line from the input file
         * @param lineInputString a line from the input file, e.g., "810,691 -> 504,385"
         */
        public Line(String lineInputString) {
            String[] points = lineInputString.split(" -> ");
            this.start = new Point(points[0]);
            this.end = new Point(points[1]);
        }

        public boolean isHorizontal() {
            return start.y == end.y;
        }

        public boolean isVertical() {
            return start.x == end.x;
        }

        public boolean isDiagonal() {
            return !isHorizontal() && !isVertical();
        }

        private List<Point> getPoints() {
            List<Point> points = new LinkedList<>();

            int deltaX = end.x - start.x;
            int deltaY = end.y - start.y;

            int signDeltaX = deltaX < 0 ? -1 : 1;
            int signDeltaY = deltaY < 0 ? -1 : 1;

            int maxAbsDelta = Math.max(Math.abs(deltaX), Math.abs(deltaY));

            // if horizontal/vertical lines, then set sign of deltaY/X to zero
            if (deltaX == 0) { // is vertical line
                signDeltaX = 0;
            } else if (deltaY == 0) { // is horizontal line
                signDeltaY = 0;
            } // else is diagonal line

            for (int idx = 0; idx <= maxAbsDelta; idx++) {
                int pointX = start.x + idx * signDeltaX;
                int pointY = start.y + idx * signDeltaY;
                points.add(new Point(pointX, pointY));
            }

            return points;
        }

        @Override
        public String toString() {
            return start.toString() + " -> " + end.toString();
        }
    }

    protected int findOverLappingLines() {
        Set<Point> foundOnce = new HashSet<>();
        Set<Point> foundTwice = new HashSet<>();

        for (Line line : lines) {
            for (Point point: line.getPoints()) {
                if (!foundTwice.contains(point)) {
                    if (!foundOnce.contains(point)) {
                        foundOnce.add(point);
                    } else {
                        foundOnce.remove(point);
                        foundTwice.add(point);
                    }
                }
            }
        }

        return foundTwice.size();
    }
}
