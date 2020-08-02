package me.pixeldev.fullpvp.scoreboard.objective;

import me.pixeldev.fullpvp.scoreboard.entry.DefaultEntry;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.List;

public interface ScoreboardObjective {

    void setDisplaySlot(DisplaySlot displaySlot);

    void setAllContent(List<DefaultEntry> content);

    void setTitle(DefaultEntry title);

    void setLine(int line, DefaultEntry entry);

    void updateTitle();

    void deleteScoreboard();

    void updateScoreboard();

}