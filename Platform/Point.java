package Platform;

public class Point {
    private int x;
    private int y;

    public Point() {
        x = 0;
        y = 0;
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double calculateDistance(Point otherPoint) {
        double xDistance = Math.pow((otherPoint.x - this.x), 2);
        double yDistance = Math.pow((otherPoint.y - this.y), 2);
        return Math.sqrt(xDistance + yDistance);
    }

    public static void main(String[] args) {
        Point point0 = new Point();
        Point point1 = new Point(20, 50);
        System.out.println(point0.calculateDistance(point1));
    }
}
