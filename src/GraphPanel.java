import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphPanel extends JPanel {
    private final int DEFAULT_GRAPH_HEIGHT = 470;
    private final int DEFAULT_GRAPH_WIDTH = 470;
    private final Color colorOfBlackPixel = new Color(0, 0, 0);
    private boolean flag;
    private double x, y;
    private int radius = 5;
    private int count = 0;
    private Color colorOfThePlotArea = Color.black;

    GraphPanel() {
        this.setPreferredSize(new Dimension(470, 260));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getGraphHeight() {
        return DEFAULT_GRAPH_HEIGHT;
    }

    public int getGraphWidth() {
        return DEFAULT_GRAPH_WIDTH;
    }

    public double getXCoordinate() {
        return x;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getYCoordinate() {
        return y;
    }

    @Override
    protected void paintComponent(Graphics graph) {
        double x1, x2, y1, y2;
        boolean pointBelongs;
        count += 1;
        setSize(DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT);
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        this.setBackground(new Color(0xFF, 248, 116));

        drawGraphArea(graphic);
        drawAxes(graphic);

        if (flag) {
            x1 = (x - DEFAULT_GRAPH_WIDTH / 2) / 20;
            y1 = (-y + DEFAULT_GRAPH_HEIGHT / 2) / 20;
            x2 = x1 * 20 + DEFAULT_GRAPH_WIDTH / 2;
            y2 = -y1 * 20 + DEFAULT_GRAPH_HEIGHT / 2;
            x = x2;
            y = y2;
        } else {
            x1 = x;
            y1 = y;
            x2 = DEFAULT_GRAPH_WIDTH / 2 + x * 20;
            y2 = (DEFAULT_GRAPH_HEIGHT / 2) - y * 20;
        }

        if (pointBelongsToTheArea(x1, y1)) {
            graphic.setColor(new Color(0x53F22C));
            pointBelongs = true;
        } else {
            graphic.setColor(Color.red);
            pointBelongs = false;
        }

        if (count > 2) {
            Data.setPointsOnGraph(new Point((float) x2 - 2, (float) y2 - 2));
            ArrayList<Point> copyArrayList = Data.getPointsOnGraph();
            Iterator<Point> iterator = copyArrayList.iterator();
            do {
                Point point = iterator.next();
                System.out.println(point.toString());
                graphic.setColor(pointBelongsToTheArea((double) point.getX(), (double) point.getY()) ? new Color(0x53F22C) : Color.red);
                graphic.fillOval((int) point.getX(), (int) point.getY(), 4, 4);
            } while (iterator.hasNext());
        }

        if (pointBelongs) {
            Thread animation = new Thread(() -> {
                Data.getSpinner().setEnabled(false);
                Data.getButton().setEnabled(false);
                try {
                    for (int i = 0, j = 0, k = 0;
                         (i != 255) && (j != 245) && (k != 255);
                         i = (i + 1 <= 255) ? i + 1 : 255, j = (j + 1 <= 245) ? j + 1 : 245, k = (k + 1 <= 255) ? k + 1 : 255) {
                        colorOfThePlotArea = new Color(i, j, k);
                        repaint();
                        Thread.sleep(30);
                    }
                    for (int i = 255; i >= 220; i--) {
                        colorOfThePlotArea = new Color(i, 245, 255);
                        repaint();
                        Thread.sleep(100);
                    }
                    for (int i = 220, j = 255, k = 255;
                         (i >= 0) || (j >= 0) || (k >= 0);
                         i = (i - 1 >= 0) ? i - 1 : 0, j = (j - 1 >= 0) ? j - 1 : 0, k = (k - 1 >= 0) ? k - 1 : 0) {
                        colorOfThePlotArea = new Color(i, j, k);
                        repaint();
                        Thread.sleep(30);
                        if (i == 0 && k == 0 && j == 0) {
                            break;
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                } finally {
                    Data.getSpinner().setEnabled(true);
                    Data.getButton().setEnabled(true);
                }
            });
            animation.setDaemon(true);
            animation.start();
        }
    }

    private boolean pointBelongsToTheArea(double x, double y) {
        return colorOfBlackPixel.equals(Data.getColorOfPixel()) ||
                ((x <= this.radius & x >= 0) & (y <= this.radius & y >= 0) &
                        ((Math.pow(x, 2) + Math.pow(y, 2) <= (Math.pow(this.radius, 2)))) ||
                        (x >= -this.radius & x <= 0 & y >= -this.radius & y <= 0) ||
                        ((x >= -(double) this.radius / 2.0) & (x <= 0) & (y <= (double) this.radius / 2.0) & (y >= 0) & (y <= x + (double) this.radius / 2.0)));
    }

    private void drawAxes(Graphics2D graphic) {
        graphic.setColor(new Color(0xFF0045));
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, DEFAULT_GRAPH_HEIGHT, DEFAULT_GRAPH_WIDTH / 2, 0); // Oy
        graphic.drawLine(0, DEFAULT_GRAPH_HEIGHT / 2, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2); // Ox
        // Ox pointer
        graphic.drawLine(DEFAULT_GRAPH_WIDTH - 10, DEFAULT_GRAPH_HEIGHT / 2 - 4, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2);
        graphic.drawLine(DEFAULT_GRAPH_WIDTH - 10, DEFAULT_GRAPH_HEIGHT / 2 + 4, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2);
        // Oy pointer
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, 0, DEFAULT_GRAPH_WIDTH / 2 - 4, 10);
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, 0, DEFAULT_GRAPH_WIDTH / 2 + 4, 10);

        graphic.drawString("0", DEFAULT_GRAPH_WIDTH / 2 + 2, DEFAULT_GRAPH_HEIGHT / 2 + 14);
        for (int i = 10; i >= 1; i--) {
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 - 3, DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 + 3);
            graphic.drawString("-" + i, DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 + 17);
            // value of division
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - 3, DEFAULT_GRAPH_HEIGHT / 2 - i * 20, DEFAULT_GRAPH_WIDTH / 2 + 3, DEFAULT_GRAPH_HEIGHT / 2 - i * 20);
            graphic.drawString("" + i, DEFAULT_GRAPH_WIDTH / 2 + 17, DEFAULT_GRAPH_HEIGHT / 2 - i * 20 + 5);
            graphic.drawLine(DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20), DEFAULT_GRAPH_HEIGHT / 2 - 3, DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20), DEFAULT_GRAPH_HEIGHT / 2 + 3);
            graphic.drawString("" + i, DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20 + 5), DEFAULT_GRAPH_HEIGHT / 2 + 17);
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - 3, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - 20 * i), DEFAULT_GRAPH_WIDTH / 2 + 3, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - i * 20));
            graphic.drawString("-" + i, DEFAULT_GRAPH_WIDTH / 2 + 17, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - i * 20 - 5));
        }
    }

    private void drawGraphArea(Graphics2D graphic) {
        int arcValue = (5 - radius) * 20; // change the value of the radius on the constant
        graphic.setColor(colorOfThePlotArea);
        graphic.fillArc(ArcData.X + arcValue, ArcData.Y + arcValue, ArcData.WIDTH + arcValue * -2, ArcData.HEIGHT + arcValue * -2, ArcData.START_ANGLE, ArcData.ARC_ANGLE);
        graphic.fillRect(RectData.X + arcValue, RectData.Y, RectData.WIDTH - arcValue, RectData.HEIGHT - arcValue);
        graphic.fillPolygon(new int[]{PolygonPoints.X_POINTS[0] + arcValue / 2, PolygonPoints.X_POINTS[1], PolygonPoints.X_POINTS[2]}, new int[]{PolygonPoints.Y_POINTS[0], PolygonPoints.Y_POINTS[1], PolygonPoints.Y_POINTS[2] + arcValue / 2}, PolygonPoints.N_POINTS);
    }

}
