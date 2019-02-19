package org.minifx.fxcommons.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Instant;

import static java.lang.String.format;
import static java.time.Duration.between;
import static java.time.Instant.now;

public final class AgingService extends Service<Void> {
    private static final int DEFAULT_AGING_INTERVAL = 1000;
    private Instant referenceTime;
    private final int agingInterval;

    public AgingService() {
        this(DEFAULT_AGING_INTERVAL);
    }

    public AgingService(int agingInterval) {
        this.agingInterval = agingInterval;
    }

    public void setReferenceTime(Instant referenceTime) {
        this.referenceTime = referenceTime;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("0 seconds ago");
                while (!isDone()) {
                    Thread.sleep(agingInterval);
                    long elapsedMillis = between(referenceTime, now()).toMillis();
                    String elapsedTime = DurationFormatUtils.formatDurationWords(elapsedMillis, true, true);
                    updateMessage(format("%s ago", elapsedTime));
                }
                return null;
            }
        };
    }
}