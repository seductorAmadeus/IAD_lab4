package iad4lab;

import javax.management.NotificationEmitter;

public interface Lab4MBean extends NotificationEmitter {

    int getDotsInArea();

    int getDotsOutOfArea();
    // TODO : add some method
}
