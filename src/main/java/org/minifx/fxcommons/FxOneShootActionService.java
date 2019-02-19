/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import java.time.Duration;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * JavaFx {@link Service} that executes the specified {@link Runnable} after the timeout. This is one shoot action. To
 * reset and reuse the {@link Service}, call {@link Service#restart()}. The specified action will run on the JavaFx
 * thread by using {@link Platform#runLater(Runnable)}.
 * 
 * @author acalia
 */
public final class FxOneShootActionService extends Service<Void> {
    private final Duration timeout;
    private final Runnable action;

    /** 
     * Create a {@link FxOneShootActionService} that executes the specified {@link Runnable} on the JavaFx thread after
     * the timeout.
     * @param action the runnable to run as the action
     * @param timeout the time to wait before the action is run
     */
    public FxOneShootActionService(Runnable action, Duration timeout) {
        this.action = action;
        this.timeout = timeout;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(timeout.toMillis());
                Platform.runLater(action);
                return null;
            }
        };
    }
}