package com.darkarth.demo.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);

    @Scheduled(cron="${app.conf.scheduler.retryJob}")
    public void sendPendingTrails() {
        LOGGER.debug("Executing processPendingMessages at {}", new Date());
    }

}