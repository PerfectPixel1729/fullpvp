package me.pixeldev.fullpvp.statistic;

public class Kills implements Statistic {

    private int kills;

    public Kills() {
        this(0);
    }

    public Kills(int kills) {
        this.kills = kills;
    }

    @Override
    public int get() {
        return kills;
    }

    @Override
    public void add(int integer) {
        kills += integer;
    }

    @Override
    public void remove(int integer) {
        kills -= integer;
    }

    @Override
    public void set(int integer) {
        kills = integer;
    }

}