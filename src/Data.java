public class Data {
    private static final double[] Y = {3, -3, -1, -4, 1, 0, -1, -4, 3};
    private static final double[] X = {-3, 0, -2, 5, 2, 0, 4, -5, 3};

    public static double getX(int i) {
        return X[i];
    }

    public static double getY(int i) {
        return Y[i];
    }

    public static int getCountOfCoordinates() {
        return X.length;
    }
}
