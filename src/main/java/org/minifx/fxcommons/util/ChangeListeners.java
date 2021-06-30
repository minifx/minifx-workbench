package org.minifx.fxcommons.util;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;

public final class ChangeListeners {

    public static <T> ChangeListener<T> onChange(Consumer<T> consumer) {
        return (obs, oldValue, newValue) -> consumer.accept(newValue);
    }

    private ChangeListeners() {
        /* static methods */
    }
}
