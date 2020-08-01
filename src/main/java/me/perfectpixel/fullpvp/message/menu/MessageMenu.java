package me.perfectpixel.fullpvp.message.menu;

import java.util.List;

public interface MessageMenu {

    String getString(String key);

    String getTitle(String keyMenu);

    String getItemName(String keyMenu, String keyItem);

    List<String> getItemLore(String keyMenu, String keyItem);

}