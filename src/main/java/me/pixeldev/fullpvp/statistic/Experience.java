package me.pixeldev.fullpvp.statistic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Experience {

    private final Kills current;
    private int to;

    public Experience() {
        this(new Kills(), 100);
    }

    public Experience(Kills current, int to) {
        this.current = current;
        this.to = to;
    }

}