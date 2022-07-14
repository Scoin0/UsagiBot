package usagibot.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RateLimit {

    private int freeTickets;

    public RateLimit(int rateLimitPerMinute) {
        freeTickets = rateLimitPerMinute;
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(() -> freeTickets = rateLimitPerMinute, 0, 1, TimeUnit.MINUTES);
    }

    private void checkTickets() {
        if(freeTickets == 0) {
            log.info("There are no tickets available: " + freeTickets);
        }
    }

    public void getOrWaitForTicket() {
        checkTickets();
        synchronized (this) {
            freeTickets--;
        }
    }
}
