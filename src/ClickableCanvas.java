import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class ClickableCanvas extends JPanel {
    private final int DEFAULT_GRAPH_HEIGHT = 470;
    private final int DEFAULT_GRAPH_WIDTH = 470;
    private final int coef = 3;
    JTextField output;
    private int Width, Height;
    private volatile double stepX, stepY;
    private Graphics2D graphic;
    private volatile int R = 5, mode;
    private Rectangle2D rectangle;
    private Arc2D arc;
    private Polygon polygon;
    private Point2D.Double savedPoint;
    private ThreadLocal<Integer> alpha;
    private volatile Vector<Point2D> Noktas;
    private ThreadLocal<Point2D.Double> axis = null, real;

    ClickableCanvas() {
        this.setPreferredSize(new Dimension(470, 260));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Noktas = new Vector<Point2D>();
        this.setBackground(Color.white);
        axis = new ThreadLocal<Point2D.Double>();
        alpha = new ThreadLocal<Integer>();
        real = new ThreadLocal<Point2D.Double>();
        mode = 1;
    }


    @Override
    protected synchronized void paintComponent(Graphics graph) {
        graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        if (axis.get() == null) {
            if (mode == 1) {
                this.Width = this.getWidth();
                this.Height = this.getHeight();
                double radX = this.Width / coef, radY = this.Height / coef;
                this.stepX = radX / this.R;
                this.stepY = radY / this.R;
                this.setBackground(Color.white);

                int XNokts[] = {(int) (this.Width / 2 - radX / 2), this.Width / 2, this.Width / 2};
                int YNokts[] = {this.Height / 2, this.Height / 2, (int) (this.Height / 2 + radY / 2)};
                /*  Заполнение фигур    */
                graphic.setColor(Color.black);
                rectangle = new Rectangle2D.Double(this.Width / 2 - radX, this.Height / 2 - radY, radX, radY);
                graphic.fill(rectangle);
                arc = new Arc2D.Double(radX / 2, radY / 2, 2 * radX, 2 * radY, 0, 90, Arc2D.PIE);
                graphic.fill(arc);

                polygon = new Polygon(XNokts, YNokts, 3);
                graphic.fill(polygon);

                graphic.setColor(Color.red);
                /*  Заполнение осей координат  */
                graphic.drawLine(this.Width / 2, 0, this.Width / 2, this.Height);
                graphic.drawLine(0, this.Height / 2, this.Width, this.Height / 2);
                graphic.drawString("0", this.Width / 2 + 2, this.Height / 2 + 10);
                graphic.drawString("X", this.Width - 10, this.Height / 2 - 15);
                graphic.drawString("Y", this.Width / 2 + 10, 15);
                /*  Отрисовка 'стрелочек'   */
                graphic.drawLine(this.Width / 2, 0, this.Width / 2 + 7, 7);
                graphic.drawLine(this.Width / 2, 0, this.Width / 2 - 7, 7);
                graphic.drawLine(this.Width, this.Height / 2, this.Width - 7, this.Height / 2 - 7);
                graphic.drawLine(this.Width, this.Height / 2, this.Width - 7, this.Height / 2 + 7);

                /*  Нанесение координат */
                graphic.drawLine(this.Width / 2 - 4, this.Height / 2 + (int) radY, this.Width / 2 + 4, this.Height / 2 + (int) radY);
                graphic.drawString("-R", this.Width / 2 + 10, this.Height / 2 + (int) radY + 5);
                graphic.drawLine(this.Width / 2 - 4, this.Height / 2 + (int) radY / 2, this.Width / 2 + 4, this.Height / 2 + (int) radY / 2);
                graphic.drawString("-R/2", this.Width / 2 + 10, this.Height / 2 + (int) radY / 2 + 5);
                graphic.drawLine(this.Width / 2 - 4, this.Height / 2 - (int) radY / 2, this.Width / 2 + 4, this.Height / 2 - (int) radY / 2);
                graphic.drawString("R/2", this.Width / 2 + 10, this.Height / 2 - (int) radY / 2 + 5);
                graphic.drawLine(this.Width / 2 - 4, this.Height / 2 - (int) radY, this.Width / 2 + 4, this.Height / 2 - (int) radY);
                graphic.drawString("R", this.Width / 2 + 10, this.Height / 2 - (int) radY + 5);

                graphic.drawLine(this.Width / 2 - (int) radX, this.Height / 2 - 4, this.Width / 2 - (int) radX, this.Height / 2 + 4);
                graphic.drawString("-R", this.Width / 2 - (int) radX + 5, this.Height / 2 + 10);
                graphic.drawLine(this.Width / 2 - (int) radX / 2, this.Height / 2 - 4, this.Width / 2 - (int) radX / 2, this.Height / 2 + 4);
                graphic.drawString("-R/2", this.Width / 2 - (int) radX / 2 + 5, this.Height / 2 + 10);
                graphic.drawLine(this.Width / 2 + (int) radX / 2, this.Height / 2 - 4, this.Width / 2 + (int) radX / 2, this.Height / 2 + 4);
                graphic.drawString("R/2", this.Width / 2 + (int) radX / 2 + 5, this.Height / 2 + 10);
                graphic.drawLine(this.Width / 2 + (int) radX, this.Height / 2 - 4, this.Width / 2 + (int) radX, this.Height / 2 + 4);
                graphic.drawString("R", this.Width / 2 + (int) radX + 5, this.Height / 2 + 10);

                for (Point2D nokta : Noktas) {
                    Point2D realpoint = Coordinates(nokta);
                    if (rectangle.contains(realpoint) || polygon.contains(realpoint) || arc.contains(realpoint)) {
                        graphic.setColor(Color.green);
                    } else {
                        graphic.setColor(Color.BLACK);
                    }
                    int x = (int) realpoint.getX(), y = (int) realpoint.getY();
                    graphic.fillRect(x, y, 5, 5);
                }
            } else {
                Point2D realpoint = Coordinates(Noktas.lastElement());
                if (rectangle.contains(realpoint) || polygon.contains(realpoint) || arc.contains(realpoint)) {
                    graphic.setColor(Color.green);
                } else {
                    graphic.setColor(Color.BLACK);
                }
                int x = (int) realpoint.getX(), y = (int) realpoint.getY();
                graphic.fillRect(x, y, 5, 5);
            }
        } else {
            if (alpha.get() != null) {
                Color black = new Color(0, 0, 0, alpha.get());
                graphic.setColor(black);
                graphic.fillRect((int) real.get().getX(), (int) real.get().getY(), 5, 5);
            }

        }
    }

    private synchronized Point2D.Double Coordinates(Point2D point2D) {
        double x, y;
        x = Width / 2 + point2D.getX() * stepX;
        y = Height / 2 - point2D.getY() * stepY;
        return new Point2D.Double(x, y);
    }

    private void AnimatedPaint() {
        int n = 255;
        int step = (3000 / n) + 1;
        int x, y;
        for (alpha.set(0); alpha.get() <= n; alpha.set(alpha.get() + 1)) {
            try {
                synchronized (this) {
                    real.set(Coordinates(axis.get()));
                    Point2D.Double test = real.get();
                    if (rectangle.contains(test) || polygon.contains(test) || arc.contains(test)) {
                        break;
                    }
                    x = (int) test.getX();
                    y = (int) test.getY();
                    this.paintImmediately(x, y, 5, 5);
                }
                Thread.sleep(step);
            } catch (InterruptedException e) {
                System.out.println("Not gonna happen");
            }
        }
        Noktas.add(axis.get());
        axis.set(null);
    }

    synchronized void addPointAxes(double x, double y) {
        savedPoint = new Point2D.Double(x, y);
        Point2D.Double point = Coordinates(savedPoint);
        String s = "X =" + savedPoint.getX() + " Y = " + savedPoint.getY();
        output.setText(s);
        if (!rectangle.contains(point) && !polygon.contains(point) && !arc.contains(point)) {
            Thread animation = new Thread(new Runnable() {
                @Override
                public void run() {
                    axis.set((Point2D.Double) savedPoint.clone());
                    AnimatedPaint();
                    synchronized (this) {
                        mode = 1;
                        repaint();
                    }
                }
            });
            animation.setDaemon(true);
            animation.start();

        } else {
            synchronized (this) {
                Noktas.add(savedPoint);
                mode = 2;
                repaint((int) point.getX(), (int) point.getY(), 5, 5);
            }
        }
    }

    public synchronized void addPointCoordinates(double x, double y) {
        addPointAxes((x - Width / 2) / stepX, -(y - Height / 2) / stepY);
    }

    public void EventSpinner(JSpinner source) {
        synchronized (this) {
            this.R = (Integer) (source.getValue());
            this.stepX = this.Width / (coef * this.R);
            this.stepY = this.Height / (coef * this.R);
            mode = 1;
            repaint();
        }
    }

}
