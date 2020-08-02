package me.pixeldev.fullpvp.scoreboard;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class Frame {

    private final String prefix;
    private final String name;
    private final String suffix;

    public static Frame of(String string) {
        Frame line;

        String one = "";
        String mid = "";
        String end = "";

        if (string.length() < 16) {
            mid = string;
        } else if (string.length() < 32) {
            one = string.substring(0, 15);
            mid = ChatColor.getLastColors(one) + string.substring(15);
        } else if (string.length() < 48) {
            one = string.substring(0, 15);
            mid = ChatColor.getLastColors(one) + string.substring(15, 31);
            end = ChatColor.getLastColors(mid) + string.substring(31);
        }

        return of(one, mid, end);
    }

}