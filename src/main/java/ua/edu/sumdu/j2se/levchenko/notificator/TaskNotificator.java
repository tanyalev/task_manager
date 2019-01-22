package ua.edu.sumdu.j2se.levchenko.notificator;

import ua.edu.sumdu.j2se.levchenko.controller.NotificationObserverController;
import ua.edu.sumdu.j2se.levchenko.tasks.TaskList;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TaskNotificator extends Notificator {
    private final Repository repository;
    private final NotificationObserverController notificationObserver;

    public TaskNotificator(final Repository repository, NotificationObserverController notificationObserver) {
        this.repository = repository;
        this.notificationObserver = notificationObserver;
        setDaemon(true);
    }

    /*
    The following method allows you to check if we have incoming tasks.
    Showing the notification about incoming task.
     */
    @Override
    public void run() {
        long delay = 1000;

        while (true) {
            Calendar now = Calendar.getInstance();
            long nowInMillis = now.getTimeInMillis();
            Date afterDelay = new Date(nowInMillis + delay);

            TaskList tasks = repository.getTasksByPeriod(now.getTime(), afterDelay);
            notificationObserver.showNotification(tasks);

            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
