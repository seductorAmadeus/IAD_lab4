package iad4lab;

import javax.management.NotificationBroadcasterSupport;
import java.awt.geom.Point2D;
import java.util.Vector;

public class ShotsController extends NotificationBroadcasterSupport implements ShotsControllerMBean {

    private GraphPanel graphPanel;
    private Vector<Point2D> points;

    public ShotsController(GraphPanel panel) {
        graphPanel = panel;
        points = graphPanel.getPoints();
    }

    public int getAllShots() {
        return 0;
    }

    public int getGoodShots() {
        return 0;
    }


}
