package me.pixeldev.fullpvp;

import java.util.Set;

public interface BasicManager<T> {

    Set<T> get();

    default void add(T t) {
        get().add(t);
    }

    default void remove(T t) {
        get().remove(t);
    }

    default boolean exists(T t) {
        return get().contains(t);
    }

    default void saveAll() {}

    default void loadAll() {}

}