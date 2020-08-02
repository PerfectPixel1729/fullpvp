package me.pixeldev.fullpvp.scoreboard.entry;

import me.pixeldev.fullpvp.scoreboard.animation.ScoreAnimation;
import me.pixeldev.fullpvp.scoreboard.animation.StandardAnimation;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimatedEntry extends DefaultEntry {

    private final StandardAnimation standardAnimation;

    public AnimatedEntry(String entry) {
        super(UUID.randomUUID().toString().substring(0, 8), 1);

        standardAnimation = get(entry);
        setUpdateTicks(standardAnimation.getStay());
        setFrames(calculateFrames());
    }

    public List<String> calculateFrames() {
        List<String> frames = new ArrayList<>();

        String normalColor = standardAnimation.getBase();
        String startColor = standardAnimation.getEffect();
        String middleColor = standardAnimation.getEffect();
        String endColor = standardAnimation.getEffect();

        String text = standardAnimation.getText();

        int shineLength = standardAnimation.getStay();

        if (shineLength > text.length()) {
            shineLength = text.length() - 1;
        }

        int pauseFrames = standardAnimation.getFadeIn() + standardAnimation.getStay();
        int speed = 1;

        int index;

        for (index = 0; index < text.length() + shineLength; index += speed) {
            int startGlowIndex = Math.min(index - shineLength, 0);
            int midFlowIndex = Math.max(startGlowIndex + ((startGlowIndex > 0) ? 1 : 0), 0) + ((index - shineLength == 0) ? 1 : 0);

            String frameBuilder = normalColor + text.substring(0, startGlowIndex) +
                    startColor + text.substring(Math.min(Math.max(startGlowIndex, 0), startGlowIndex), Math.min(midFlowIndex, text.length())) +
                    middleColor + text.substring(midFlowIndex, Math.min(Math.max(index - 1, 0), text.length())) +
                    endColor + text.substring(Math.max(Math.min(index - 1, text.length()), 0), Math.min(index, text.length())) +
                    normalColor + text.substring(Math.min(index, text.length()));

            frames.add(ChatColor.translateAlternateColorCodes('&', frameBuilder));
        }

        for (index = 0; index < pauseFrames; index++) {
            frames.add(ChatColor.translateAlternateColorCodes('&', normalColor + text));
        }

        return frames;
    }

    public StandardAnimation get(String entry) {
        ScoreAnimation animation = ScoreAnimation.valueOf(entry.split(":")[0].replace("${", "").toUpperCase());

        String parameters = entry.split(":")[1].replace("}", "");
        String text = parameters.split("]")[2];

        parameters = parameters.replace(text, "");

        String[] parameter = parameters.split("]");
        String[] time = parameter[0].replace("[", "").replace("]", "").split(";");
        String[] colors = parameter[1].replace("[", "").replace("]", "").split(",");

        int fadeIn = Integer.parseInt(time[0]);
        int stay = Integer.parseInt(time[1]);
        int fadeOut = Integer.parseInt(time[2]);

        return new StandardAnimation(fadeIn, stay, fadeOut, text, colors[0], colors[1], animation);
    }

}