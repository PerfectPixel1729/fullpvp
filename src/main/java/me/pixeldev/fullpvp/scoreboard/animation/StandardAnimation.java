package me.pixeldev.fullpvp.scoreboard.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StandardAnimation {

    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    private final String text;
    private final String base;
    private final String effect;

    private final ScoreAnimation animation;

    public int getTotalAnimation() {
        return fadeIn + stay + fadeOut;
    }

}