import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Data {

    private static ArrayList<Point> points = new ArrayList<>();
    private static ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();

    static {
        Point[] point = new Point[]{new Point(4, 4), new Point(3, 3), new Point(2, 2), new Point(1, 1), new Point(0, 0),
                new Point(-1, -1), new Point(-2, -2), new Point(-3, -3), new Point(-4, -4)};
        Collections.addAll(points, point);
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
}
