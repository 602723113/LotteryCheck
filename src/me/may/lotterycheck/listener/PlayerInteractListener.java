package me.may.lotterycheck.listener;

import com.google.common.collect.Lists;
import me.may.lotterycheck.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null || block.getType().equals(Material.AIR)) {
            return;
        }
        if (block.getType().equals(Material.DISPENSER) || block.getType().equals(Material.DROPPER)) {
            Player player = e.getPlayer();
            if (!player.isSneaking() || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                return;
            }
            List<ItemStack> items = Lists.newArrayList();
            if (block.getType().equals(Material.DISPENSER)) {
                Dispenser dispenser = (Dispenser) block.getState();
                for (ItemStack item : dispenser.getInventory().getContents()) {
                    if (item == null || item.getType().equals(Material.AIR)) {
                        continue;
                    }
                    items.add(item);
                }
            }

            if (block.getType().equals(Material.DROPPER)) {
                Dropper dropper = (Dropper) block.getState();
                for (ItemStack item : dropper.getInventory().getContents()) {
                    if (item == null || item.getType().equals(Material.AIR)) {
                        continue;
                    }
                    items.add(item);
                }
            }

            Inventory inv = Bukkit.createInventory(null, 5 * 9, Entry.getInstance().invName);
            int[] divide = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
            for (int i : divide) {
                inv.setItem(i, Entry.getInstance().item);
            }
            inv.addItem(items.toArray(new ItemStack[]{}));
            player.closeInventory();
            player.openInventory(inv);
            e.setCancelled(true);
        }
    }
}
