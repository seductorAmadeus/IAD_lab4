import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphPanel extends JPanel implements Runnable, ActionListener {
    private final int DEFAULT_GRAPH_HEIGHT = 470;
    private final int DEFAULT_GRAPH_WIDTH = 470;
    private boolean flag;
    private double x, y;
    private int radius = 5;
    private int step;
    private int green, red;
    private int stepPast = 25;
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

    public void setRadius(int radius) {
        this.radius = radius;
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
        setSize(DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT);
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        this.setBackground(new Color(0xFF, 248, 116));

        graphic.setColor(colorOfThePlotArea);
        graphic.fillArc(ArcData.X, ArcData.Y, ArcData.WIDTH, ArcData.HEIGHT, ArcData.START_ANGLE, ArcData.ARC_ANGLE);
        graphic.fillRect(RectData.X, RectData.Y, RectData.WIDTH, RectData.HEIGHT);
        graph.fillPolygon(PolygonPoints.X_POINTS, PolygonPoints.Y_POINTS, PolygonPoints.N_POINTS);

        graphic.setColor(new Color(0xFF0045));
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, DEFAULT_GRAPH_HEIGHT, DEFAULT_GRAPH_WIDTH / 2, 0); // Oy
        graphic.drawLine(0, DEFAULT_GRAPH_HEIGHT / 2, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2); // Ox
        // стрелки у Ox
        graphic.drawLine(DEFAULT_GRAPH_WIDTH - 10, DEFAULT_GRAPH_HEIGHT / 2 - 4, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2);
        graphic.drawLine(DEFAULT_GRAPH_WIDTH - 10, DEFAULT_GRAPH_HEIGHT / 2 + 4, DEFAULT_GRAPH_WIDTH, DEFAULT_GRAPH_HEIGHT / 2);
        // стрелки у Oy
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, 0, DEFAULT_GRAPH_WIDTH / 2 - 4, 10);
        graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2, 0, DEFAULT_GRAPH_WIDTH / 2 + 4, 10);

        graphic.drawString("0", DEFAULT_GRAPH_WIDTH / 2 + 2, DEFAULT_GRAPH_HEIGHT / 2 + 14);
        for (int i = 10; i >= 1; i--) {
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 - 3, DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 + 3);
            graphic.drawString("-" + i, DEFAULT_GRAPH_WIDTH / 2 - i * 20, DEFAULT_GRAPH_HEIGHT / 2 + 17);
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - 3, DEFAULT_GRAPH_HEIGHT / 2 - i * 20, DEFAULT_GRAPH_WIDTH / 2 + 3, DEFAULT_GRAPH_HEIGHT / 2 - i * 20);//цена деления
            graphic.drawString("" + i, DEFAULT_GRAPH_WIDTH / 2 + 17, DEFAULT_GRAPH_HEIGHT / 2 - i * 20 + 5);
            graphic.drawLine(DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20), DEFAULT_GRAPH_HEIGHT / 2 - 3, DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20), DEFAULT_GRAPH_HEIGHT / 2 + 3);            //цена деления
            graphic.drawString("" + i, DEFAULT_GRAPH_WIDTH - (DEFAULT_GRAPH_WIDTH / 2 - i * 20 + 5), DEFAULT_GRAPH_HEIGHT / 2 + 17);
            graphic.drawLine(DEFAULT_GRAPH_WIDTH / 2 - 3, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - 20 * i), DEFAULT_GRAPH_WIDTH / 2 + 3, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - i * 20));            //цена деления
            graphic.drawString("-" + i, DEFAULT_GRAPH_WIDTH / 2 + 17, DEFAULT_GRAPH_HEIGHT - (DEFAULT_GRAPH_HEIGHT / 2 - i * 20 - 5));
        }
        double x1;
        double y1;
        if (flag) {
            System.out.println(x + " " + y);
            x1 = (x - DEFAULT_GRAPH_WIDTH / 2) / stepPast;
            y1 = (-y + DEFAULT_GRAPH_HEIGHT / 2) / stepPast;
        } else {
            x1 = x;
            y1 = y;
        }
        boolean fcolor;
        if (((x1 <= this.radius & x1 >= 0) & (y1 <= this.radius & y1 >= 0) &
                ((Math.pow(x1, 2) + Math.pow(y1, 2) <= (Math.pow(this.radius, 2)))) || // изменить условие для окружности
                (x1 >= -this.radius & x1 <= 0 & y1 >= -this.radius & y1 <= 0) || // квадрат
                ((x1 >= -(double) this.radius / 2.0) & (x1 <= 0) & (y1 <= (double) this.radius / 2.0) & (y1 >= 0) & (y1 <= x1 + (double) this.radius / 2.0)))) {
            graphic.setColor(new Color(0x53F22C));
            green = 1;
            fcolor = red == 1;
            red = 0;
        } else {
            graphic.setColor(Color.red);
            red = 1;
            fcolor = green == 1;
            green = 0;
        }
        if (fcolor) {
            begin();
        }
        double x2;
        double y2;
        if (flag) {
            x2 = x1 * step + DEFAULT_GRAPH_WIDTH / 2;
            y2 = -y1 * step + DEFAULT_GRAPH_HEIGHT / 2;
            x = x2;
            y = y2;
        } else {
            x2 = DEFAULT_GRAPH_WIDTH / 2 + x * step;
            y2 = (DEFAULT_GRAPH_HEIGHT / 2) - y * step;
        }
        if (count > 2)
            graphic.fillOval((int) x2 - 2, (int) y2 - 2, 4, 4);
        stepPast = step;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 245; i = i + 1) {
                colorOfThePlotArea = new Color(0, i, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 0; i <= 245; i++) {
                colorOfThePlotArea = new Color(i, 245, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 255; i >= 220; i--) {
                colorOfThePlotArea = new Color(245, 245, i);
                repaint();
                Thread.sleep(5);
            }
            Thread.sleep(200);
            for (int i = 220; i <= 255; i++) {
                colorOfThePlotArea = new Color(245, 245, i);
                repaint();
                Thread.sleep(10);
            }
            for (int i = 245; i >= 0; i--) {
                colorOfThePlotArea = new Color(i, 245, 255);
                repaint();
                Thread.sleep(5);
            }
            for (int i = 245; i >= 0; i--) {
                colorOfThePlotArea = new Color(0, i, 255);
                repaint();
                Thread.sleep(10);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void begin() {
        new Thread(this).start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.setFlag(false);
        if (!this.getFlag()) {
            this.setY((int) getY());
        }
        this.repaint();
    }


}
