package me.pixeldev.fullpvp.statistic;

import java.text.DecimalFormat;

public interface KDR {

    Deaths getDeaths();

    Kills getKills();

    default String getKDR() {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");

        return (getDeaths().get() == 0) ? String.valueOf(getKills().get()) : decimalFormat.format(getKills().get() / getDeaths().get());
    }

}