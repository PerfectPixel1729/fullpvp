package me.perfectpixel.fullpvp;

import java.util.Map;
import java.util.Optional;

public interface Cache<K, V> {

    Map<K, V> get();

    Optional<V> find(K key);

    void remove(K key);

    void add(K key, V value);

    boolean exists(K key);

}