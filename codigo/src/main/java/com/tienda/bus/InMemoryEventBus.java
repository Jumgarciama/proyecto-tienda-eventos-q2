package com.tienda.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class InMemoryEventBus implements EventBus {
    private final Map<Class<?>, List<Consumer<?>>> subscribers = new HashMap<>();

    @Override
    public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void publish(Object event) {
        List<Consumer<?>> handlers = subscribers.getOrDefault(event.getClass(), List.of());
        for (Consumer<?> handler : handlers) {
            ((Consumer<Object>) handler).accept(event);
        }
    }
}
