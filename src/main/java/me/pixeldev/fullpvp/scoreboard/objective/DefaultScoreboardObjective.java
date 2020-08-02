package me.pixeldev.fullpvp.scoreboard.objective;

import gnu.trove.map.hash.TIntObjectHashMap;

import me.pixeldev.fullpvp.scoreboard.Frame;
import me.pixeldev.fullpvp.scoreboard.entry.DefaultEntry;

import me.yushust.inject.process.annotation.Singleton;

import org.apache.commons.lang.RandomStringUtils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

@Singleton
public class DefaultScoreboardObjective implements ScoreboardObjective {

    private final Scoreboard scoreboard;

    private final Set<DefaultEntry> entries = new HashSet<>();
    private final TIntObjectHashMap<DefaultEntry> scoreboardContent = new TIntObjectHashMap<>();
    private final Map<String, String> placeholderLastValues = new HashMap<>();

    private final Objective current;
    private DisplaySlot currentDisplaySlot;

    private final Player player;
    private DefaultEntry title;

    private DefaultScoreboardObjective(Player player, Scoreboard scoreboard) {
        this.player = player;
        this.scoreboard = scoreboard;

        current = scoreboard.registerNewObjective("SbObj", "dummy");

        current.setDisplaySlot(DisplaySlot.SIDEBAR);
        currentDisplaySlot = DisplaySlot.SIDEBAR;

        String randomTitle = RandomStringUtils.random(5);

        title = DefaultEntry.of(randomTitle, 1, Collections.singletonList(randomTitle));
    }

    public DefaultScoreboardObjective build(Player player, Scoreboard scoreboard) {
        return new DefaultScoreboardObjective(player, scoreboard);
    }

    @Override
    public void setDisplaySlot(DisplaySlot displaySlot) {
        if (currentDisplaySlot == null || displaySlot != currentDisplaySlot) {
            currentDisplaySlot = displaySlot;

            current.setDisplaySlot(displaySlot);
        }
    }

    @Override
    public void setAllContent(List<DefaultEntry> content) {
        int i;

        if (content.size() <= scoreboardContent.size()) {
            for (i = content.size(); i < scoreboardContent.size(); i++) {
                entries.add(scoreboardContent.get(i));

                scoreboardContent.remove(i);
            }
        }

        i = 0;

        scoreboardContent.clear();

        for (DefaultEntry entry : content) {
            setLine(content.size() - i++, entry);
        }
    }

    @Override
    public void setTitle(DefaultEntry title) {
        this.title = title;
    }

    @Override
    public void setLine(int line, DefaultEntry entry) {
        DefaultEntry value = scoreboardContent.get(line);

        if (value != null && !entry.equals(value)) {
            entries.add(entry);
        }

        scoreboardContent.put(line, entry);
    }

    @Override
    public void updateTitle() {
        if (title == null || title.isEmpty()) {
            return;
        }

        title.getTickEntry();

        if (title.getCurrentTick() % title.getUpdateTicks() > 0) {
            return;
        }

        Frame oldFrame = Frame.of(title.getCurrentFrame());
        Frame frame = Frame.of(title.getNextFrame());

        if (oldFrame.equals(frame) && title.isDisplayed()) {
            return;
        }

        title.setDisplayed(true);
        current.setDisplayName(frame.getPrefix() + frame.getName() + frame.getSuffix());
    }

    @Override
    public void deleteScoreboard() {
        current.unregister();
        entries.clear();
        scoreboardContent.clear();

        title = null;
    }

    @Override
    public void updateScoreboard() {
        if (current == null || scoreboardContent.isEmpty()) {
            return;
        }

        scoreboardContent.forEachEntry((index, entry) -> {
            if (entry == null) {
                return true;
            }

            entry.getTickEntry();

            if (entry.getCurrentTick() % entry.getUpdateTicks() > 0 && entry.isDisplayed()) {
                return true;
            }

            String frameString = entry.getNextFrame();

            Frame frame = Frame.of(frameString);

            Team team = scoreboard.getTeam(entry.getEntryName());

            if (team == null) {
                team = scoreboard.registerNewTeam(entry.getEntryName());
            }

            if (!team.hasEntry(frame.getName())) {
                team.addEntry(frame.getName());
            }

            team.setDisplayName(frame.getName());

            team.setSuffix(frame.getSuffix());
            team.setPrefix(frame.getPrefix());

            current.getScore(frame.getName()).setScore(index + 1);

            entry.setDisplayed(true);

            return true;
        });
    }

    private void removeEntriesFromScoreboard() {
        //TODO
    }
}