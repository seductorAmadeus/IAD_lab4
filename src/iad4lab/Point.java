package iad4lab;

public class Point implements Comparable {
    private float x, y;

    Point(float x, float y) {
        this.x = x;
        this.y = y;
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