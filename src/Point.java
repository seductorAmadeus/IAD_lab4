import java.awt.*;

public class Point implements Comparable {
    private float x, y;
    private Color colorOfPoint;

    Point(float x, float y, Color colorOfPoint) {
        this.x = x;
        this.y = y;
        this.colorOfPoint = colorOfPoint;
    }

    public void changeColorOfPoint(Point point) {
        point.colorOfPoint = (point.colorOfPoint == Color.red) ? new Color(0x53F22C) : Color.red;
    }

    private Color getColorOfPoint(Point point) {
        return point.colorOfPoint;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public int compareTo(Object o) {
        float result;
        Point entry = (Point) o;
        result = entry.x - x;
        if (result != 0) {
            return (int) (result / Math.abs(result));
        } else return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(x).append(" ").append(y);
        return stringBuilder.toString();
    }
}