public class Point implements Comparable {
    private float X, Y;

    Point(float X, float Y) {
        this.X = X;
        this.Y = Y;
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    @Override
    public int compareTo(Object o) {
        float result;
        Point entry = (Point) o;
        result = entry.X - X;
        if (result != 0) {
            return (int) (result / Math.abs(result));
        } else return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(X).append(" ").append(Y);
        return stringBuilder.toString();
    }
}