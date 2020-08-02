package me.pixeldev.fullpvp.statistic;

public interface Statistic<T> {

    T get();

    void add(T t);

    void remove(T t);

    void set(T t);

}