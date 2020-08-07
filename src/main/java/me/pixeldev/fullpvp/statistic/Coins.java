package me.pixeldev.fullpvp.statistic;

public class Coins implements Statistic {

    private int coins;

    public Coins() {
        this(0);
    }

    public Coins(int coins) {
        this.coins = coins;
    }

    @Override
    public int get() {
        return coins;
    }

    @Override
    public void add(int integer) {
        coins += integer;
    }

    @Override
    public void remove(int integer) {
        coins -= integer;
    }

    @Override
    public void set(int integer) {
        coins = integer;
    }

    public boolean hasCoins() {
        return coins > 0;
    }

    public boolean hasEnoughCoins(int coins) {
        return this.coins >= coins;
    }

}