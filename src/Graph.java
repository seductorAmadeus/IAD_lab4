import javax.swing.*;
import java.awt.*;

class Graph extends JPanel implements Runnable {
    /* доработать поля до private-ов!!!*/
    private static boolean flag;
    private static double x, y;
    int green, red;
    int radius = 4;
    int h = 450;
    int w = 450;
    int step;
    private int steppast = 25;
    private int count = 0;
    private int[] xPoints = new int[]{w / 2 - 100, w / 2, w / 2};
    private int[] yPoints = new int[]{100, 100, 300, 300};
    private Color color = Color.black;

    public  double getXCoordinate() {
        return x;
    }

    public  double getYCoordinate() {
        return y;
    }

    public static boolean getFlag() {
        return flag;
    }

    public static void setX(double x){
        Graph.x = x;
    }
    public static void setY(double y){
       Graph.y = y;
    }

    public static void setFlag(boolean flag){
        Graph.flag = flag;
    }

    @Override
    protected void paintComponent(Graphics graph) {
        count += 1;
        step = (int) 100 / radius;
        setSize(h, w);
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        this.setBackground(new Color(0xFF, 248, 116));
        /**
         * отрисовываем график. Переделать, пусть перерисовывается график, а не масштаб осей координат
         */
        graphic.setColor(color);
        graphic.fillArc(radius +121, radius +121, 200, 200, 0, 90); // вычислять через координаты
        graphic.fillRect(125, 225, 100, 100); // вычислять через координаты
        graph.fillPolygon(new int[]{125, 115 , 200}, new int[]{225, 350, 119}, 3 );

        graphic.setColor(new Color(0xFF0045));
        graphic.drawLine(w / 2, h, w / 2, 0);            //оси координат
        graphic.drawLine(w / 2, 0, w / 2 - 4, 10);
        graphic.drawLine(w / 2, 0, w / 2 + 4, 10);
        graphic.drawLine(0, h / 2, w, h / 2);
        graphic.drawLine(w - 10, h / 2 - 4, w, h / 2);
        graphic.drawLine(w - 10, h / 2 + 4, w, h / 2);

        graphic.drawString("0", w / 2 + 2, h / 2 + 14);
        for (int i = radius; i >= 1; i--) {
            graphic.drawLine(w / 2 - i * step, h / 2 - 3, w / 2 - i * step, h / 2 + 3);
            graphic.drawString("-" + i, w / 2 - i * step, h / 2 + 17);
            graphic.drawLine(w / 2 - 3, h / 2 - i * step, w / 2 + 3, h / 2 - i * step);            //цена деления
            graphic.drawString("" + i, w / 2 + 17, h / 2 - i * step + 5);
            graphic.drawLine(w - (w / 2 - i * step), h / 2 - 3, w - (w / 2 - i * step), h / 2 + 3);            //цена деления
            graphic.drawString("" + i, w - (w / 2 - i * step + 5), h / 2 + 17);
            graphic.drawLine(w / 2 - 3, h - (h / 2 - step * i), w / 2 + 3, h - (h / 2 - i * step));            //цена деления
            graphic.drawString("-" + i, w / 2 + 17, h - (h / 2 - i * step - 5));
        }
        double x1;
        double y1;
        if (flag) {
            x1 = (x - w / 2) / steppast;
            y1 = (-y + h / 2) / steppast;
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
            x2 = x1 * step + w / 2;
            y2 = -y1 * step + h / 2;
            x = x2;
            y = y2;
        } else {
            x2 = w / 2 + x * step;
            y2 = h / 2 - y * step;
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
