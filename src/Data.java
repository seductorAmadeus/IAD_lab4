import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Data {

    private static JSpinner spinner;
    private static JButton button;
    private static ArrayList<Point> points = new ArrayList<>();
    private static ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();
    private static ArrayList<Point> pointsOnGraph = new ArrayList<>();
    private static BufferedImage bufferedImage;
    private static Color colorOfPixel;

    static {
        Point[] point = new Point[]{new Point(4, 4, Color.red), new Point(3, 3, Color.red), new Point(2, 2, Color.red), new Point(1, 1, Color.red), new Point(0, 0, Color.red),
                new Point(-1, -1, Color.red), new Point(-2, -2, Color.red), new Point(-3, -3, Color.red), new Point(-4, -4, Color.red)};
        Collections.addAll(points, point);
    }

    public static ArrayList<Point> getPointsOnGraph() {
        return pointsOnGraph;
    }

    public static void setPointsOnGraph(Point point) {
        pointsOnGraph.add(point);
    }

    public static Color getColorOfPixel() {
        return colorOfPixel;
    }

    public static void setColorOfPixel(Color pixelColor) {
        colorOfPixel = pixelColor;
    }

    public static BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public static void setBufferedImage(BufferedImage image) {
        bufferedImage = image;
    }

    public static double getX(int i) {
        return points.get(i).getX();
    }

    public static void setCheckBox(int i, JCheckBox checkBox) {
        checkBoxesList.add(i, checkBox);
    }

    public static JCheckBox getCheckBox(int i) {
        return checkBoxesList.get(i);
    }

    public static double getY(int i) {
        return points.get(i).getY();
    }

    public static int getCountOfCoordinates() {
        return points.size();
    }

    public static JSpinner getSpinner() {
        return spinner;
    }

    public static void setSpinner(JSpinner spinner) {
        Data.spinner = spinner;
    }

    public static JButton getButton() {
        return button;
    }

    public static void setButton(JButton button) {
        Data.button = button;
    }
}
