import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class GraphPanel extends JPanel {
    private final int coefficient = 3;
    private int Width, Height;
    private volatile double stepX, stepY;
    private volatile int R = 5, mode;
    private Rectangle2D rectangle;
    private Arc2D arc;
    private Polygon polygon;
    private Point2D.Double savedPoint;
    private ThreadLocal<Integer> alpha;
    private volatile Vector<Point2D> Noktas;
    private ThreadLocal<Point2D.Double> axis = null, real;
    private Color colorOfThePlotArea = Color.black;
    private volatile boolean stateCursor = true;

    GraphPanel() {
        this.setPreferredSize(new Dimension(470, 260));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Noktas = new Vector<>();
        axis = new ThreadLocal<>();
        alpha = new ThreadLocal<>();
        real = new ThreadLocal<>();
        mode = 1;
    }

    public boolean getStateCuror() {
        return stateCursor;
    }

    public double getStepX() {
        return stepX;
    }

    public double getStepY() {
        return stepY;
    }

    @Override
    protected synchronized void paintComponent(Graphics graph) {
        Graphics2D graphic = (Graphics2D) graph;
        super.paintComponent(graphic);
        if (axis.get() == null) {
            if (1 == mode) {
                this.Width = this.getWidth();
                this.Height = this.getHeight();
                double radX = this.Width / coefficient, radY = this.Height / coefficient;
                this.stepX = radX / this.R;
                this.stepY = radY / this.R;
                this.setBackground(new Color(0xFF, 248, 116));

                /*  Заполнение фигур    */
                graphic.setColor(colorOfThePlotArea);
                rectangle = new Rectangle2D.Double(RectData.X, RectData.Y, RectData.WIDTH, RectData.HEIGHT);
                graphic.fill(rectangle);
                arc = new Arc2D.Double(radX / 2, radY / 2, 2 * radX, 2 * radY, 0, 90, Arc2D.PIE);
                graphic.fill(arc);

                polygon = new Polygon(PolygonData.X_POINTS, PolygonData.Y_POINTS, PolygonData.N_POINTS);
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
                        graphic.setColor(Color.RED);
                    }
                    int x = (int) realpoint.getX(), y = (int) realpoint.getY();
                    graphic.fillRect(x, y, 4, 4);
                }
            } else {
                Point2D realpoint = Coordinates(Noktas.lastElement());
                if (rectangle.contains(realpoint) || polygon.contains(realpoint) || arc.contains(realpoint)) {
                    graphic.setColor(Color.green);
                } else {
                    graphic.setColor(Color.RED);
                }
                int x = (int) realpoint.getX(), y = (int) realpoint.getY();
                graphic.fillRect(x, y, 4, 4);
            }
        } else {
            if (alpha.get() != null) {
                graphic.setColor(Color.red);
                graphic.fillRect((int) real.get().getX(), (int) real.get().getY(), 4, 4);
            }
        }
    }

    private synchronized Point2D.Double Coordinates(Point2D point2D) {
        double x, y;
        x = Width / 2 + point2D.getX() * stepX;
        y = Height / 2 - point2D.getY() * stepY;
        return new Point2D.Double(x, y);
    }

    private synchronized int getCountOfPointInArea() {
        int count = 0;
        for (Point2D nokta : Noktas) {
            Point2D realpoint = Coordinates(nokta);
            if (rectangle.contains(realpoint) || polygon.contains(realpoint) || arc.contains(realpoint)) {
                count++;
            }
        }
        return count;
    }

    private void AnimatedPaint() {
        int x, y;
        real.set(Coordinates(axis.get()));
        Point2D.Double test = real.get();
        if (rectangle.contains(test) || polygon.contains(test) || arc.contains(test)) {
            return;
        }
        x = (int) test.getX();
        y = (int) test.getY();
        this.paintImmediately(x, y, 4, 4);
        Noktas.add(axis.get());
        System.out.println(Noktas.size());
        axis.set(null);
    }

    private synchronized void drawGraph() {
        Thread animation = new Thread(new Runnable() {
            @Override
            public void run() {
                mode = 1;
                axis.set((Point2D.Double) savedPoint.clone());
                Data.getSpinner().setEnabled(false);
                Data.getButton().setEnabled(false);
                try {
                    stateCursor = false;
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
                    stateCursor = true;
                }
                synchronized (this) {
                    mode = 1;
                    repaint();
                }
            }
        });
        animation.setDaemon(true);
        animation.start();
    }

    private synchronized void addPointAxes(double x, double y) {
        savedPoint = new Point2D.Double(x, y);
        Point2D.Double point = Coordinates(savedPoint);

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
                repaint((int) point.getX(), (int) point.getY(), 4, 4);
            }
        }
    }

    public synchronized void addPointCoordinates(double x, double y) {
        addPointAxes((x - Width / 2) / stepX, -(y - Height / 2) / stepY);
    }

    public void EventSpinner(JSpinner source) {
        int countOfPointInArea = getCountOfPointInArea();
        synchronized (this) {
            this.R = (Integer) (source.getValue());
            this.stepX = this.Width / (coefficient * this.R);
            this.stepY = this.Height / (coefficient * this.R);
            mode = 1;
            repaint();
        }
        if (countOfPointInArea < getCountOfPointInArea()) {
            drawGraph();
        }
    }
}