package com.emilianodellaciana.events;

import com.emilianodellaciana.LiquidFixReloaded;
import com.emilianodellaciana.tools.Color;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class LiquidFixEvents implements Listener {
    private FileConfiguration configuration = LiquidFixReloaded.istance.getConfig();
    private Economy economy = LiquidFixReloaded.getEconomy();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(Color.chatColor(configuration.getString("menu.title")))) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null)
                return;
            Set<String> stringSet = configuration.getConfigurationSection("items").getKeys(false);
            for (String key : stringSet) {
                if (event.getSlot() == configuration.getInt("items." + key + ".position") - 1) {
                    for (String cmd : configuration.getStringList("items." + key + ".commands")) {
                        if (cmd.equalsIgnoreCase("[CANCEL]")) {
                            player.closeInventory();
                            return;
                        }
                        if (cmd.equalsIgnoreCase("[CONFIRM]")) {
                            if (economy != null)
                                if (!player.hasPermission("liquidfix.bypass")) {
                                    economy.withdrawPlayer(player, configuration.getDouble("fix-prince"));
                                    player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("pay-confirm-message"))));
                                }
                            ItemStack[] content = player.getInventory().getContents();
                            for (ItemStack item : content) {
                                if (item != null) {
                                    if (!LiquidFixReloaded.getMaterials().contains(item.getType())) {
                                        item.setDurability((short) 0);
                                    }
                                }
                            }
                            ItemStack[] armorContent = player.getInventory().getArmorContents();
                            for (ItemStack item : armorContent) {
                                if (item != null) {
                                    if (!LiquidFixReloaded.getMaterials().contains(item.getType())) {
                                        item.setDurability((short) 0);
                                    }
                                }
                            }
                            player.sendMessage(Color.chatColor(Color.placeholder(player, LiquidFixReloaded.getPrefix() + configuration.getString("fixed-items"))));
                            player.closeInventory();
                            return;
                        }
                        player.performCommand(cmd);
                    }
                    player.closeInventory();
                    break;
                }
            }
        }
    }
}
