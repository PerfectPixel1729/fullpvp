package me.pixeldev.fullpvp.statistic;

public class Coins implements Statistic<Integer> {

    private int coins;

    public Coins() {
        this(0);
    }

    public Coins(int coins) {
        this.coins = coins;
    }

    @Override
    public Integer get() {
        return coins;
    }

    @Override
    public void add(Integer integer) {
        coins += integer;
    }

    @Override
    public void remove(Integer integer) {
        coins -= integer;
    }

    @Override
    public void set(Integer integer) {
        coins = integer;
    }

    public boolean hasCoins() {
        return coins > 0;
    }

    public boolean hasEnoughCoins(Integer coins) {
        return this.coins >= coins;
    }

}