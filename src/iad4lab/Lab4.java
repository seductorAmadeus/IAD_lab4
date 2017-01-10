package iad4lab;

import javax.management.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Lab4 extends NotificationBroadcasterSupport implements Lab4MBean {

    private GraphPanel graphPanel;
    private Vector<Point2D> points;
    private boolean previousClickOutside = false;
    private int sequenceNumber = 0;
    private int outsideClickCounter = 0;
    private int missedClickCounter = 0;

    public Lab4(GraphPanel panel) {
        graphPanel = panel;
        points = graphPanel.getPoints();
    }

    public int getDotsOutOfArea() {
        int count = 0;
        for (Point2D nokta : points) {
            Point2D realPoint = graphPanel.getCoordinates(nokta);
            if (!graphPanel.getRectangle().contains(realPoint) && !graphPanel.getPolygon().contains(realPoint) && !graphPanel.getArc().contains(realPoint)) {
                count++;
            }
        }
        return count;
    }

    public int getDotsInArea() {
        int count = 0;
        for (Point2D nokta : points) {
            Point2D realPoint = graphPanel.getCoordinates(nokta);
            if (graphPanel.getRectangle().contains(realPoint) || graphPanel.getPolygon().contains(realPoint) || graphPanel.getArc().contains(realPoint)) {
                count++;
            }
        }
        return count;
    }

    public void checkForOutOfAreaInARow(Point2D.Double mark1) {
        if (!this.graphPanel.getPolygon().contains(mark1) && !this.graphPanel.getArc().contains(mark1) && !
                this.graphPanel.getRectangle().contains(mark1)) {
            if (previousClickOutside && outsideClickCounter < 1) {
                sendNotification(new Notification(
                        "opilab4.OpiLab.twoInARow",
                        this,
                        ++sequenceNumber,
                        "Two dots in a row out of area"
                ));
                outsideClickCounter++;
                missedClickCounter++;
            }
            if (missedClickCounter == 1) {
                missedClickCounter = 0;
                previousClickOutside = false;
                outsideClickCounter = 0;
                return;
            }
            previousClickOutside = true;
        } else {
            previousClickOutside = false;
            outsideClickCounter = 0;
        }
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"opilab4.OpiLab.twoInARow"},
                        Notification.class.getName(),
                        "User notification"
                )
        };
    }
}
