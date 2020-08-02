package me.pixeldev.fullpvp.scoreboard.entry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class DefaultEntry implements Entry {

    private final String entryName;
    private int updateTicks;
    private final Deque<String> frames;

    private volatile long currentTick;
    private volatile boolean displayed;

    public DefaultEntry(String entryName, int updateTicks) {
        this.entryName = entryName;
        this.updateTicks = updateTicks;

        frames = new LinkedList<>();
    }

    public DefaultEntry(String text) {
        entryName = UUID.randomUUID().toString().substring(0, 0);
        updateTicks = 20;
        frames = new LinkedList<>();

        frames.offer(text);
    }

    public DefaultEntry(String entryName, int updateTicks, String[] entries) {
        this.entryName = entryName;
        this.updateTicks = updateTicks;

        frames = new LinkedList<>();

        offerEntries(entries);
    }

    public DefaultEntry(String entryName, int updateTicks, List<String> entries) {
        this.entryName = entryName;
        this.updateTicks = updateTicks;

        frames = new LinkedList<>(entries);
    }

    private DefaultEntry(String entryName, int updateTicks, Deque<String> entries) {
        this.entryName = entryName;
        this.updateTicks = updateTicks;

        if (entries == null) {
            frames = new LinkedList<>();
        } else {
            frames = new LinkedList<>(entries);
        }
    }

    public static DefaultEntry of(String entryName, int updateTicks, List<String> entries) {
        return new DefaultEntry(entryName, updateTicks, entries);
    }

    public static DefaultEntry of(String entryName, int updateTicks, Deque<String> entries) {
        return new DefaultEntry(entryName, updateTicks, entries);
    }

    public static DefaultEntry of(String entryName, int updateTicks, String[] entries) {
        return new DefaultEntry(entryName, updateTicks, entries);
    }

    @Override
    public void setFrames(List<String> entries) {
        if (!frames.isEmpty()) {
            frames.clear();
        }

        offerEntries((String[]) entries.toArray());
    }

    @Override
    public String getLastFrame() {
        return frames.getLast();
    }

    @Override
    public String getNextFrame() {
        frames.offer(frames.poll());

        return frames.getFirst();
    }

    @Override
    public String getCurrentFrame() {
        return frames.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return frames.isEmpty();
    }

    @Override
    public long getTickEntry() {
        long actual = currentTick;

        actual++;

        currentTick = actual;

        return currentTick;
    }

    @Override
    public DefaultEntry clone() {
        return DefaultEntry.of(entryName, updateTicks, frames);
    }

    private void offerEntries(String[] entries) {
        for (String entry : entries) {
            if (entry == null) {
                frames.offer("");

                continue;
            }

            frames.offer(entry);
        }
    }

}