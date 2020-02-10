package de.schornyy.listener;

import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.schmied.PlayerSchmieded;
import de.schornyy.utils.GuiBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.util.Arrays;

public class PlayerDragItemListener implements Listener {

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);

        if(!troniacraftPlayer.getStoredPlayerSchmieded().isEmpty()) {
            for(PlayerSchmieded playerSchmieded : troniacraftPlayer.getStoredPlayerSchmieded()) {
                if(playerSchmieded == null) continue;
                if(Arrays.equals(playerSchmieded.getInventory().getContents(), e.getInventory().getContents())) {
                    if(e.getOldCursor().equals(playerSchmieded.getSchmied().getMidItem())) {
                        troniacraftPlayer.getStoredPlayerSchmieded().remove(playerSchmieded);
                        p.sendMessage("§bItem entnohmen!");
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }


    }

}
