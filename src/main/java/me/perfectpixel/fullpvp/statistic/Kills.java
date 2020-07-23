package me.perfectpixel.fullpvp.statistic;

public class Kills implements Statistic<Integer> {

    private int kills;

    public Kills() {
        this(0);
    }

    public Kills(int kills) {
        this.kills = kills;
    }

    @Override
    public Integer get() {
        return kills;
    }

    @Override
    public void add(Integer integer) {
        kills += integer;
    }

    @Override
    public void remove(Integer integer) {
        kills -= integer;
    }

    @Override
    public void set(Integer integer) {
        kills = integer;
    }

}