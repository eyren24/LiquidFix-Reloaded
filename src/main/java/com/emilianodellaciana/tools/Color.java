package com.emilianodellaciana.tools;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Color {
    public static String chatColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String placeholder(Player player, String str) {
        return PlaceholderAPI.setPlaceholders(player, str);
    }
}
