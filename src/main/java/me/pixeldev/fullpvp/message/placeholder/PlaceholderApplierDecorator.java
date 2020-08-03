package me.pixeldev.fullpvp.message.placeholder;

import team.unnamed.inject.Inject;

import org.bukkit.entity.Player;

public class PlaceholderApplierDecorator implements PlaceholderApplier {

    @Inject
    private StatisticPlaceholderApplier statisticPlaceholderApplier;

    @Override
    public String setPlaceHolders(Player player, String text) {
        return statisticPlaceholderApplier.setPlaceHolders(player, text)
                .replace("%player_name%", player.getName());
    }

}