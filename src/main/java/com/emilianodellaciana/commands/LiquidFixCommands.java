package com.emilianodellaciana.commands;

import com.emilianodellaciana.LiquidFixReloaded;
import com.emilianodellaciana.tools.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class LiquidFixCommands implements CommandExecutor {
    private FileConfiguration configuration;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        configuration = LiquidFixReloaded.istance.getConfig();
        if (!sender.hasPermission("liquidfix.admin")) {
            sender.sendMessage(Color.chatColor(configuration.getString("permission-denied")));
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(Color.chatColor(configuration.getString("&cPlease use &4/liquidfix reload")));
        }
        LiquidFixReloaded.istance.reloadConfig();
        LiquidFixReloaded.setPrefix(LiquidFixReloaded.istance.getConfig().getString("prefix"));
        sender.sendMessage(Color.chatColor(LiquidFixReloaded.getPrefix() + configuration.getString("&aReloaded complete!")));
        return false;
    }
}
