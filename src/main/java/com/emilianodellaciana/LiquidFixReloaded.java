package com.emilianodellaciana;

import com.emilianodellaciana.commands.FixCommand;
import com.emilianodellaciana.events.LiquidFixEvents;
import com.emilianodellaciana.placeholders.LiquidFixPlaceholders;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LiquidFixReloaded extends JavaPlugin {
    public static LiquidFixReloaded istance;
    private static Economy econ = null;
    private static String prefix = null;
    private static List<Material> materials = new ArrayList<>();

    public static Economy getEconomy() {
        return econ;
    }

    public static List<Material> getMaterials() {
        return materials;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        LiquidFixReloaded.prefix = prefix;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        istance = this;
        // get vault plugin
        if (getConfig().getBoolean("economy")) {
            if (!setupEconomy()) {
                getLogger().info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            }
        }
        // get placeholderapi plugin
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().info(String.format("[%s] - Disabled due to no PlaceholderAPI dependency found!", getDescription().getName()));
        }
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        init();
        new LiquidFixPlaceholders().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        prefix = getConfig().getString("prefix");
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("liquidfix").setExecutor(new FixCommand());
        getServer().getPluginManager().registerEvents(new LiquidFixEvents(), this);
        for (String s : getConfig().getStringList("banned-material")) {
            getLogger().info(s);
            materials.add(Material.getMaterial(s));
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
