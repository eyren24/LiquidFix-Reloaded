package com.emilianodellaciana.commands;

import com.emilianodellaciana.LiquidFixReloaded;
import com.emilianodellaciana.tools.Color;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FixCommand implements CommandExecutor {
    private Economy economy;
    private FileConfiguration configuration;
    private Inventory inv = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        economy = LiquidFixReloaded.getEconomy();
        configuration = LiquidFixReloaded.istance.getConfig();
        if (!(sender instanceof Player)) {
            sender.sendMessage(LiquidFixReloaded.getPrefix() + ChatColor.RED + "Only player can execute this command!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("wrong-command"))));
            return false;
        }
        if (player.hasPermission("liquidfix.bypass")) {
            int countItems = 0;
            ItemStack[] itemStack = player.getInventory().getContents();
            for (ItemStack item : itemStack) {
                if (item != null) {
                    if (!LiquidFixReloaded.getMaterials().contains(item.getType())) {
                        if (item.getDurability() > 1)
                            countItems++;
                    }
                }
            }
            ItemStack[] contentArmor = player.getInventory().getArmorContents();
            for (ItemStack item : contentArmor) {
                if (item != null) {
                    if (item.getDurability() > 0) {
                        countItems++;
                    }
                }
            }
            if (countItems > 0) {
                createInventory();
                player.openInventory(inv);
            } else {
                player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("no-item-repairable"))));
            }
            return true;
        }
        if (economy != null) {
            if (!economy.has(player, configuration.getDouble("fix-prince"))) {
                player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("insufficient-found"))));
                return false;
            }
        }
        int countItems = 0;
        ItemStack[] itemStack = player.getInventory().getContents();
        for (ItemStack item : itemStack) {
            if (item != null) {
                if (!LiquidFixReloaded.getMaterials().contains(item.getType())) {
                    if (item.getDurability() > 1)
                        countItems++;
                }
            }
        }
        ItemStack[] contentArmor = player.getInventory().getArmorContents();
        for (ItemStack item : contentArmor) {
            if (item != null) {
                if (item.getDurability() > 0) {
                    countItems++;
                }
            }
        }
        if (countItems > 0) {
            createInventory();
            player.openInventory(inv);
        } else {
            player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("no-item-repairable"))));
        }
        return false;
    }

    private void createInventory() {
        inv = Bukkit.createInventory(null, configuration.getInt("menu.size"), Color.chatColor(configuration.getString("menu.title")));
        initItems();
    }

    private void initItems() {
        Set<String> keys = configuration.getConfigurationSection("items").getKeys(false);
        for (String key : keys) {
            for (int i = 0; i < configuration.getConfigurationSection("items").getKeys(false).size(); i++) {
                String[] stringList = configuration.getStringList("items." + key + ".lore").toArray(new String[0]);
                inv.setItem(configuration.getInt("items." + key + ".position") - 1, createItem(configuration.getString("items." + key + ".item"), configuration.getString("items." + key + ".owner"), (short) configuration.getInt("items." + key + ".durability"), configuration.getString("items." + key + ".name"), stringList));
            }
        }

    }

    private ItemStack createItem(final String material, final String owner, final short durability, final String name, final String... lores) {
        if (Material.getMaterial(material) == Material.SKULL_ITEM) {
            ItemStack item = new ItemStack(Material.getMaterial(material), 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(owner);
            meta.setDisplayName(Color.chatColor(name));
            List<String> list = new ArrayList<>();
            for (String lore : lores)
                list.add(Color.chatColor(lore));
            meta.setLore(list);
            item.setItemMeta(meta);
            return item;
        }
        ItemStack item = new ItemStack(Material.getMaterial(material), 1, durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.chatColor(name));
        List<String> list = new ArrayList<>();
        for (String lore : lores)
            list.add(Color.chatColor(lore));
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }
}
