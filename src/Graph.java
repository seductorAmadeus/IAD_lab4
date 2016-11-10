import javax.swing.*;
import java.awt.*;

class Graph extends JPanel implements Runnable {
    /* доработать поля до private-ов!!!*/
    static boolean flag;
    static double x, y;
    int green, red;
    int R = 4;
    int h = 350;
    int w = 350;
    int step;
    private int steppast = 25;
    private int count = 0;
    private int[] xPoints = new int[]{w / 2 - 100, w / 2, w / 2};
    private int[] yPoints = new int[]{h / 2, h / 2, h / 2 + 100};
    private Color color = Color.black;

    @Override
    protected void paintComponent(Graphics graph) {
        count += 1;
        step = (int) 100 / R;
        setSize(h, w);
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        this.setBackground(new Color(0xFF, 253, 136));
        /**
         * отрисовываем график. Переделать, пусть перерисовывается график, а не масштаб осей координат
         */
        graphic.setColor(color);
        graphic.fillArc(w / 2 - 50, h / 2 - 50, 100, 100, 0, 90);
        graphic.fillRect(w / 2, h / 2, 100, 100);
        graphic.fillPolygon(xPoints, yPoints, 3);

        graphic.setColor(Color.white);
        graphic.drawLine(w / 2, h, w / 2, 0);            //оси координат
        graphic.drawLine(w / 2, 0, w / 2 - 4, 10);
        graphic.drawLine(w / 2, 0, w / 2 + 4, 10);
        graphic.drawLine(0, h / 2, w, h / 2);
        graphic.drawLine(w - 10, h / 2 - 4, w, h / 2);
        graphic.drawLine(w - 10, h / 2 + 4, w, h / 2);

        graphic.drawString("0", w / 2 + 2, h / 2 + 14);
        for (int i = R; i >= 1; i--) {
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
        if (flag == true) {
            x1 = (double) (x - w / 2) / steppast;
            y1 = (double) (-y + h / 2) / steppast;
        } else {
            x1 = x;
            y1 = y;
        }
        boolean fcolor;
        if ((x1 <= R & x1 >= 0 & ((y1 >= -R & y1 <= 0) || (Math.pow(x1, 2) + Math.pow(y1, 2) <= Math.pow(R, 2) / 4))) || (x1 >= -R & x1 <= 0 & y1 >= -R & y1 <= 0 & x1 >= -R - y1)) {
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
        if (fcolor == true) {
            begin();
        }
        double x2;
        double y2;
        if (flag == true) {
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

    public void begin() {
        new Thread(this).start();
    }
}
