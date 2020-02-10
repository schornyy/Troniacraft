package de.schornyy.listener;

import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.schmied.Schmied;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AnvilPreParedListener implements Listener {

    @EventHandler
    public void onanvil(PrepareAnvilEvent e) {
        Player p = (Player) e.getView().getPlayer();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);

        if(e.getInventory().getItem(0) == null) {
            return;
        }

        if(e.getInventory().getItem(0).getItemMeta() != null) {
            if(Schmied.getSchmiedByMidItemName(e.getInventory().getItem(0).getItemMeta().getDisplayName()) != null) {
                Schmied schmied = Schmied.getSchmiedByMidItemName(e.getInventory().getItem(0).getItemMeta().getDisplayName());
                if(troniacraftPlayer.getJob().getName().equalsIgnoreCase(schmied.getAcessableJob())) {
                    if(e.getInventory().getItem(1) != null) {
                     ItemStack s1 = e.getInventory().getItem(0);
                     ItemStack s2 = e.getInventory().getItem(1);
                     boolean nextMidItem = false;
                     int line = 0;

                     if(s1.getItemMeta().getLore() != null || s1.getItemMeta().getLore().size() != 0) {
                         for(String lore : s1.getItemMeta().getLore()) {
                             if(lore.contains(s2.getType().name() + ":" + s2.getAmount())) {
                                 nextMidItem = true;
                                 break;
                             }
                             line++;
                         }

                         if(nextMidItem) {
                             ItemStack itemStack = s1.clone();
                             ItemMeta itemMeta = itemStack.getItemMeta();
                             List<String> lore = itemMeta.getLore();
                             lore.remove(line);
                             itemMeta.setLore(lore);
                             itemStack.setItemMeta(itemMeta);

                             if(lore.size() == 0 || lore == null) {
                                 e.setResult(schmied.getEndItem());
                             } else if(lore.size() != 0 || lore != null){
                                 e.setResult(itemStack);
                             }
                         }
                     }
                    }
                } else {
                    //Wrong Job
                }
            }
        }


    }
}
