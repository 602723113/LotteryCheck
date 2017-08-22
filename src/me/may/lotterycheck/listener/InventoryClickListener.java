package me.may.lotterycheck.listener;

import me.may.lotterycheck.Entry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getRawSlot() < 0) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getInventory().getType().equals(InventoryType.DISPENSER) || e.getInventory().getType().equals(InventoryType.DROPPER)) {
            Player player = (Player) e.getWhoClicked();
            List<Entity> entityList = player.getNearbyEntities(8, 0, 8);
            if (entityList.isEmpty()) {
                return;
            }
            for (Entity entity : entityList) {
                if (entity instanceof Player) {
                    player.sendMessage(Entry.getInstance().eightBlock);
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(Entry.getInstance().invName)) {
            e.setCancelled(true);
        }
    }
}
