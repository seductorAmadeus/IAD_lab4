package iad4lab;

import javax.management.NotificationEmitter;

public interface ShotsControllerMBean extends NotificationEmitter {

    int getAllShots();

    int getGoodShots();

}
