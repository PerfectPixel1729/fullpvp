package me.pixeldev.fullpvp.scoreboard.entry;

import java.util.List;

public interface Entry {

    void setFrames(List<String> entries);

    String getLastFrame();

    String getNextFrame();

    String getCurrentFrame();

    boolean isEmpty();

    long getTickEntry();

}