package com.emilianodellaciana.placeholders;

import com.emilianodellaciana.LiquidFixReloaded;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiquidFixPlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "liquidfix";
    }

    @Override
    public @NotNull String getAuthor() {
        return "zSuperSettingsYT";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return null;
        if (params.equalsIgnoreCase("fix_prince")) {
            return String.valueOf(LiquidFixReloaded.istance.getConfig().getDouble("fix-prince"));
        }
        return super.onPlaceholderRequest(player, params);
    }
}
