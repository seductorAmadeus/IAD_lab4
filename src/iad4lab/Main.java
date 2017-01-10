package iad4lab;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("iad4lab:type=Lab4");
        Lab4 mbean = new Lab4(mainFrame.getGraphPanel());
        mbs.registerMBean(mbean, name);
        Data.setLab4(mbean);
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
