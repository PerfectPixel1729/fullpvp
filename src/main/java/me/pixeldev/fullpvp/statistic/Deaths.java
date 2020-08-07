package me.pixeldev.fullpvp.statistic;

public class Deaths implements Statistic {

    private int deaths;

    public Deaths() {
        this(0);
    }

    public Deaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public int get() {
        return deaths;
    }

    @Override
    public void add(int integer) {
        deaths += integer;
    }

    @Override
    public void remove(int integer) {
        deaths -= integer;
    }

    @Override
    public void set(int integer) {
        deaths = integer;
    }

}