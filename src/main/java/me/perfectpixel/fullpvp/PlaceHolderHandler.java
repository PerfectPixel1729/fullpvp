package me.perfectpixel.fullpvp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.OfflinePlayer;

public class PlaceHolderHandler extends PlaceholderExpansion {

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "fullpvp";
    }

    @Override
    public String getAuthor() {
        return "PerfectPixel";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        return "";
    }

}