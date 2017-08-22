package me.may.lotterycheck;

import me.may.lotterycheck.listener.InventoryClickListener;
import me.may.lotterycheck.listener.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Entry extends JavaPlugin {

    private static Entry instance;
    public String invName;
    public ItemStack item;
    public String eightBlock;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        invName = getConfig().getString("Gui.Title").replaceAll("&", "§");
        eightBlock = getConfig().getString("8BlockCannotUse").replaceAll("&", "§");
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        item = new ItemStack(getConfig().getInt("Gui.Item.Material"), 1, (short) getConfig().getInt("Gui.Item.Data"));
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(getConfig().getString("Gui.Item.DisplayName").replaceAll("&", "§"));
        List<String> lore = getConfig().getStringList("Gui.Item.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        item.setItemMeta(im);
    }

    public static Entry getInstance() {
        return instance;
    }
}
