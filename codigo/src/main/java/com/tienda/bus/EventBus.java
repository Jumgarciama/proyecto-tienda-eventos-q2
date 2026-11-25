package com.tienda.bus;

import java.util.function.Consumer;

public interface EventBus {
    <T> void subscribe(Class<T> eventType, Consumer<T> handler);
    void publish(Object event);
}
