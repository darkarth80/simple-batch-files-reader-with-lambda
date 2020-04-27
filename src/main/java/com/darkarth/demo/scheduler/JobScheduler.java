package com.darkarth.demo.scheduler;

import java.util.Date;

import com.darkarth.demo.processor.FileProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);

    @Autowired
    private FileProcessor fp;

    @Scheduled(fixedRateString="${app.conf.scheduler.retryJobFixedRate}")
    public void sendPendingTrails() {
        fp.processFiles();
        LOGGER.debug("Executing processPendingMessages at {}", new Date());
    }

}