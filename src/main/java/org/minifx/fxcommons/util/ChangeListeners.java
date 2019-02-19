package org.minifx.fxcommons.util;

import javafx.beans.value.ChangeListener;

import java.util.function.Consumer;

public final class ChangeListeners {

    public static <T> ChangeListener<T> onChange(Consumer<T> consumer) {
        return (obs, oldValue, newValue) -> consumer.accept(newValue);
    }

    private ChangeListeners() {
        /* static methods */
    }
}
