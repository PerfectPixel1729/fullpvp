package me.pixeldev.fullpvp.statistic;

public class Level implements Statistic<Integer> {

    private int level;

    public Level() {
        this(0);
    }

    public Level(int level) {
        this.level = level;
    }

    @Override
    public Integer get() {
        return level;
    }

    @Override
    public void add(Integer integer) {
        level += integer;
    }

    @Override
    public void remove(Integer integer) {
        level -= integer;
    }

    @Override
    public void set(Integer integer) {
        level = integer;
    }

}