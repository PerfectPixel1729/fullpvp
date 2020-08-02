package me.pixeldev.fullpvp.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreboardCache {

    List<ScoreboardAPI> getScoreboards();

    default void removeScore(Player player) { }

    default void getScoreboard(Player player) {}

}