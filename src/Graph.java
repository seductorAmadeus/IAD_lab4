import javax.swing.*;
import java.awt.*;

public class Graph extends JPanel implements Runnable {
    private static boolean flag;
    private static double x, y;
    private final int graphHeight = 450;
    private final int graphWidth = 450;
    private static int radius = 4;
    private int step;
    private int green, red;
    private int steppast = 25;
    private int count = 0;
    private Color color = Color.black;

    public static boolean getFlag() {
        return flag;
    }

    public static void setRadius(int r) {
        radius = r;
    }

    public static void setFlag(boolean flag) {
        Graph.flag = flag;
    }

    public static void setX(double x) {
        Graph.x = x;
    }

    public static void setY(double y) {
        Graph.y = y;
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public int getGraphWidth() {
        return graphWidth;
    }

    public int getStep() {
        return step;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public double getXCoordinate() {
        return x;
    }

    public double getYCoordinate() {
        return y;
    }

    @Override
    protected void paintComponent(Graphics graph) {
        count += 1;
        step = 100 / radius;
        setSize(graphWidth, graphHeight);
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        this.setBackground(new Color(0xFF, 248, 116));
        /**
         * отрисовываем график. Переделать, пусть перерисовывается график, а не масштаб осей координат
         */
        graphic.setColor(color);
        graphic.fillArc(radius + 121, radius + 121, 200, 200, 0, 90); // вычислять через координаты
        graphic.fillRect(125, 225, 100, 100); // вычислять через координаты
        graph.fillPolygon(new int[]{175, 225, 225}, new int[]{225, 225, 175}, 3);

        graphic.setColor(new Color(0xFF0045));
        graphic.drawLine(graphWidth / 2, graphHeight, graphWidth / 2, 0);            //оси координат
        graphic.drawLine(graphWidth / 2, 0, graphWidth / 2 - 4, 10);
        graphic.drawLine(graphWidth / 2, 0, graphWidth / 2 + 4, 10);
        graphic.drawLine(0, graphHeight / 2, graphWidth, graphHeight / 2);
        graphic.drawLine(graphWidth - 10, graphHeight / 2 - 4, graphWidth, graphHeight / 2);
        graphic.drawLine(graphWidth - 10, graphHeight / 2 + 4, graphWidth, graphHeight / 2);

        graphic.drawString("0", graphWidth / 2 + 2, graphHeight / 2 + 14);
        for (int i = radius; i >= 1; i--) {
            graphic.drawLine(graphWidth / 2 - i * step, graphHeight / 2 - 3, graphWidth / 2 - i * step, graphHeight / 2 + 3);
            graphic.drawString("-" + i, graphWidth / 2 - i * step, graphHeight / 2 + 17);
            graphic.drawLine(graphWidth / 2 - 3, graphHeight / 2 - i * step, graphWidth / 2 + 3, graphHeight / 2 - i * step);            //цена деления
            graphic.drawString("" + i, graphWidth / 2 + 17, graphHeight / 2 - i * step + 5);
            graphic.drawLine(graphWidth - (graphWidth / 2 - i * step), graphHeight / 2 - 3, graphWidth - (graphWidth / 2 - i * step), graphHeight / 2 + 3);            //цена деления
            graphic.drawString("" + i, graphWidth - (graphWidth / 2 - i * step + 5), graphHeight / 2 + 17);
            graphic.drawLine(graphWidth / 2 - 3, graphHeight - (graphHeight / 2 - step * i), graphWidth / 2 + 3, graphHeight - (graphHeight / 2 - i * step));            //цена деления
            graphic.drawString("-" + i, graphWidth / 2 + 17, graphHeight - (graphHeight / 2 - i * step - 5));
        }
        double x1;
        double y1;
        if (flag) {
            x1 = (x - graphWidth / 2) / steppast;
            y1 = (-y + graphHeight / 2) / steppast;
        } else {
            x1 = x;
            y1 = y;
        }
        boolean fcolor;
        if ((x1 <= radius & x1 >= 0 & ((y1 >= -radius & y1 <= 0) || (Math.pow(x1, 2) + Math.pow(y1, 2) <= Math.pow(radius, 2) / 4))) || (x1 >= -radius & x1 <= 0 & y1 >= -radius & y1 <= 0 & x1 >= -radius - y1)) {
            graphic.setColor(Color.green);
            green = 1;
            if (red == 1) fcolor = true;
            else fcolor = false;
            red = 0;
        } else {
            graphic.setColor(Color.red);
            red = 1;
            if (green == 1) fcolor = true;
            else fcolor = false;
            green = 0;
        }
        if (fcolor) {
            begin();
        }
        double x2;
        double y2;
        if (flag) {
            x2 = x1 * step + graphWidth / 2;
            y2 = -y1 * step + graphHeight / 2;
            x = x2;
            y = y2;
        } else {
            x2 = graphWidth / 2 + x * step;
            y2 = (graphHeight / 2) - y * step;
        }
        if (count > 2)
            graphic.fillOval((int) x2 - 2, (int) y2 - 2, 4, 4);
        steppast = step;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 245; i = i + 1) {
                color = new Color(0, i, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 0; i <= 245; i++) {
                color = new Color(i, 245, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 255; i >= 220; i--) {
                color = new Color(245, 245, i);
                repaint();
                Thread.sleep(5);
            }
            Thread.sleep(200);
            for (int i = 220; i <= 255; i++) {
                color = new Color(245, 245, i);
                repaint();
                Thread.sleep(10);
            }
            for (int i = 245; i >= 0; i--) {
                color = new Color(i, 245, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 245; i >= 0; i--) {
                color = new Color(0, i, 255);
                repaint();
                Thread.sleep(10);
            }
        } catch (Exception e) {
        }
    }

    private void begin() {
        new Thread(this).start();
    }
}
