package usagibot.utils.version;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class VersionTask {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void updateScheduler() {
        final Runnable updater = new Runnable() {
            @Override
            public void run() {
                VersionUpdate.checkForUpdate();
                log.info("Checking for update...");
            }
        };

        // Check for an update every 12 hours
        final ScheduledFuture<?> updateHandle = scheduler.scheduleAtFixedRate(updater,1, 12, TimeUnit.HOURS);
    }
}