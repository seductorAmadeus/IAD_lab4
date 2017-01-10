package iad4lab;

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
    private static BufferedImage bufferedImage;
    private static Color colorOfPixel;
    private static HitsController hitsController;
    private static ShotsController shotsController;
    static {
        Point[] point = new Point[]{new Point(4, 4), new Point(3, 3), new Point(2, 2), new Point(1, 1), new Point(0, 0),
                new Point(-1, -1), new Point(-2, -2), new Point(-3, -3), new Point(-4, -4)};
        Collections.addAll(points, point);
    }

    public static HitsController getHitsController() {
        return hitsController;
    }

    public static void setMbeanHits(HitsController controller) {
        hitsController = controller;
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

    public static ShotsController getShotsController() {
        return shotsController;
    }

    public static void setShotsController(ShotsController shotsController) {
        Data.shotsController = shotsController;
    }
}
