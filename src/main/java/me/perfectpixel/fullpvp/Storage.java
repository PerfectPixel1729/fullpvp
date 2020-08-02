package me.perfectpixel.fullpvp;

import java.util.Map;
import java.util.Optional;

public interface Storage<K, V> {

    Map<K, V> get();

    Optional<V> find(K key);

    Optional<V> findFromData(K key);

    void save(K key);

    void saveObject(K key, V value);

    void remove(K key);

    void add(K key, V value);

    void saveAll();

    void loadAll();

}