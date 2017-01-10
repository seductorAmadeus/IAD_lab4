package iad4lab;

import javax.management.NotificationEmitter;

public interface HitsControllerMBean extends NotificationEmitter {

    int getDotsInArea();

    int getDotsOutOfArea();
    // TODO : add some method
}
