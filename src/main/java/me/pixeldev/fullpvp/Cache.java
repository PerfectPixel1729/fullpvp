package me.pixeldev.fullpvp;

import java.util.Map;
import java.util.Optional;

public interface Cache<K, V> {

    Map<K, V> get();

    default Optional<V> find(K key) {
        return Optional.ofNullable(get().get(key));
    }

    default void remove(K key) {
        get().remove(key);
    }

    default void add(K key, V value) {
        get().put(key, value);
    }

    default boolean exists(K key) {
        return get().containsKey(key);
    }

}