package me.pixeldev.fullpvp.statistic;

public class Deaths implements Statistic<Integer> {

    private int deaths;

    public Deaths() {
        this(0);
    }

    public Deaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public Integer get() {
        return deaths;
    }

    @Override
    public void add(Integer integer) {
        deaths += integer;
    }

    @Override
    public void remove(Integer integer) {
        deaths -= integer;
    }

    @Override
    public void set(Integer integer) {
        deaths = integer;
    }

}