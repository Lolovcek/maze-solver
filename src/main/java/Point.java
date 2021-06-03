public class Point {
    private int x;
    private int y;
    private int distance;
    private Point parent;
    public Point(int x, int y, int distance, Point parent) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public Point getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", distance='" + distance + '\'' +
                '}';
    }
}
