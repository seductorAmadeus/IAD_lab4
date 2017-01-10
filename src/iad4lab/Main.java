package iad4lab;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName nameHits = new ObjectName("iad4lab:type=HitsController");
        ObjectName nameShots = new ObjectName("iad4lab:type=ShotsController");

        HitsController mbeanHits = new HitsController(mainFrame.getGraphPanel());
        ShotsController mbeanShots = new ShotsController(mainFrame.getGraphPanel());

        mbs.registerMBean(mbeanHits, nameHits);
        mbs.registerMBean(mbeanShots, nameShots);

        Data.setMbeanHits(mbeanHits);


        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
