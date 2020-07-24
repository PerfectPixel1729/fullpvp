package me.perfectpixel.fullpvp;

import java.util.Map;
import java.util.Optional;

public interface Storage<K, V> {

    Map<V, K> get();

    Optional<K> find(V v);

    Optional<K> findFromData(V v);

    void save(V v);

    void remove(V v);

    void add(V v, K k);

    void saveAll();

    void loadAll();

}