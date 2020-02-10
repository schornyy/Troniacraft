package de.schornyy.listener;

import de.schornyy.Troniacraft;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.schmied.PlayerSchmieded;
import de.schornyy.api.schmied.Schmied;
import de.schornyy.utils.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

import java.util.Arrays;

public class PlayerInteractInventoryListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) throws NullPointerException{
        Player p = (Player)e.getWhoClicked();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);

        if(e.getInventory().getType() == InventoryType.ANVIL) {
            AnvilInventory anvilInventory = (AnvilInventory) e.getInventory();

            if(e.getSlot() == 2) {
                if(anvilInventory.getItem(2) != null) {
                    p.getInventory().addItem(anvilInventory.getItem(2));
                    anvilInventory.clear();
                }
            }

        }


        if(p.getOpenInventory().getTitle().equalsIgnoreCase("§eSchmied")) {
            if(troniacraftPlayer.getStoredPlayerSchmieded().size() != 0) {
                for(PlayerSchmieded schmieded : troniacraftPlayer.getStoredPlayerSchmieded()) {
                    if(schmieded == null) return;
                        if (Arrays.equals(schmieded.getInventory().getContents(), p.getOpenInventory().getTopInventory().getContents())) {
                            if(e.getSlot() != GuiBuilder.starter && e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                                e.setCancelled(true);
                            }
                            if(e.getSlot() == GuiBuilder.starter) {
                                p.getInventory().addItem(e.getCurrentItem());
                                p.closeInventory();
                                troniacraftPlayer.getStoredPlayerSchmieded().remove(schmieded);
                                p.sendMessage(Troniacraft.getInstance().config.Player_collect_Item);
                            }
                        return;
                    }
                }
            }

                if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.LIME_DYE) {
                    e.setCancelled(true);
                    Schmied s = null;
                    for(Schmied schmied : Schmied.getStoredBlackSmiths()) {
                        if(schmied == null) continue;
                        if(Arrays.equals(p.getOpenInventory().getTopInventory().getContents(), schmied.getInventory().getContents())) {
                            s = schmied;
                        }
                    }

                    if(s != null) {
                        if(s.getAcessableJob() != null) {
                            if(troniacraftPlayer.getJob() != null) {
                                if(troniacraftPlayer.getJob().getName().equalsIgnoreCase(s.getAcessableJob())) {
                                    p.getOpenInventory().getTopInventory().setItem(GuiBuilder.starter, null);
                                    PlayerSchmieded playerSchmieded = new PlayerSchmieded(p, s, troniacraftPlayer.getGetLatestClickedLocation(), p.getOpenInventory().getTopInventory());
                                    playerSchmieded.startSmith();
                                    troniacraftPlayer.getStoredPlayerSchmieded().add(playerSchmieded);
                                    p.closeInventory();
                                    p.sendMessage(Troniacraft.getInstance().config.Brue_start);
                                } else {
                                    p.sendMessage(Troniacraft.getInstance().config.Player_has_wrong_Job);
                                }
                            }
                        }
                    }

                }

            if(e.getClickedInventory() != null && e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                if(Arrays.stream(GuiBuilder.slots).anyMatch(i -> i == e.getSlot())) {
                    e.setCancelled(true);
                    return;
                }
            }

        }

    }
}
