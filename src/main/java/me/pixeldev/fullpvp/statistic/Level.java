package me.pixeldev.fullpvp.statistic;

public class Level implements Statistic {

    private int level;

    public Level() {
        this(0);
    }

    public Level(int level) {
        this.level = level;
    }

    @Override
    public int get() {
        return level;
    }

    @Override
    public void add(int integer) {
        level += integer;
    }

    @Override
    public void remove(int integer) {
        level -= integer;
    }

    @Override
    public void set(int integer) {
        level = integer;
    }

}