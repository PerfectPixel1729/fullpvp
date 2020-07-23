package me.perfectpixel.fullpvp;

import java.util.Optional;
import java.util.Set;

public interface Storage<K, V> {

    Set<K> get();

    Optional<K> find(V v);

    Optional<K> findFromData(V v);

    void save(V v);

    void remove(V v);

    void add(K k);

}