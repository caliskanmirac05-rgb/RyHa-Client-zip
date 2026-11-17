package com.ryha.client.event;

import com.ryha.client.util.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple synchronous event bus.
 * Listeners are stored per event type. Posting null is a no-op.
 */
public class EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <E extends Event> void register(Class<E> type, EventListener<E> listener) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> void unregister(Class<E> type, EventListener<E> listener) {
        List<EventListener<?>> list = listeners.get(type);
        if (list != null) list.remove(listener);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <E extends Event> void post(E event) {
        if (event == null) return;
        List<EventListener<?>> list = listeners.get(event.getClass());
        if (list == null || list.isEmpty()) return;
        for (EventListener l : new ArrayList<>(list)) {
            try {
                l.onEvent(event);
            } catch (Exception ex) {
                Logger.error("EventBus exception in listener for " + event.getClass().getSimpleName(), ex);
            }
        }
    }

    /** Register all annotated listener methods on an object (simple reflection approach). */
    public void registerObject(Object obj) {
        for (var method : obj.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Subscribe.class)) continue;
            if (method.getParameterCount() != 1) continue;
            Class<?> paramType = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(paramType)) continue;
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            Class<Event> eventType = (Class<Event>) paramType;
            register(eventType, event -> {
                try { method.invoke(obj, event); }
                catch (Exception ex) { Logger.error("EventBus reflection dispatch error", ex); }
            });
        }
    }
}
