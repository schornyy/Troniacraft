package de.schornyy.listener;

import de.schornyy.Troniacraft;
import de.schornyy.api.player.TroniacraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        Player p = (Player) e.getWhoClicked();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);

        if(troniacraftPlayer.getJob().getStoredBlacklistofUnusableCraftings().contains(e.getRecipe().getResult().getType())) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Troniacraft.getInstance().config.Player_cant_craft_item);
        }

        if(troniacraftPlayer.getJob().getStoredBlacklistofcustomCrafts().contains(e.getRecipe().getResult())) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Troniacraft.getInstance().config.Player_cant_craft_item);
        }


    }
}
