package de.schornyy.listener;

import de.schornyy.Troniacraft;
import de.schornyy.api.job.Job;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.schmied.PlayerSchmieded;
import de.schornyy.api.schmied.Schmied;
import de.schornyy.utils.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            troniacraftPlayer.setGetLatestClickedLocation(e.getClickedBlock().getLocation());
            if(troniacraftPlayer.getJob().getStoredBlacklistofUseableBlocks().size() != 0) {
                if(troniacraftPlayer.getJob().getStoredBlacklistofUseableBlocks().contains(e.getClickedBlock().getType())) {
                    e.setCancelled(true);
                    p.sendMessage(Troniacraft.getInstance().config.Player_cant_use_Block);
                    return;
                }
            }

            if(e.getClickedBlock().getType() == Material.SMITHING_TABLE) {
                e.setCancelled(true);
                if (troniacraftPlayer.getStoredPlayerSchmieded().size() != 0) {
                    Schmied schmied = null;
                    Inventory inv = null;
                    for (PlayerSchmieded schmieded : troniacraftPlayer.getStoredPlayerSchmieded()) {
                        if (schmieded == null) return;
                        if(schmieded.getLocation().equals(e.getClickedBlock().getLocation())) {
                            schmied = schmieded.getSchmied();
                            inv = schmieded.getInventory();
                        }
                    }

                    if(schmied != null) {
                        p.openInventory(inv);
                    }
                } else {
                    Inventory inv = Bukkit.createInventory(null, 27, "§eSchmied");
                    GuiBuilder.setBlackSmithGui(inv);
                    p.openInventory(inv);
                }
            }
            if(block.getType().name().contains("_SIGN")) {
                Sign s = (Sign) block.getState();

                if(s.getLine(0).equalsIgnoreCase(Troniacraft.getInstance().jobConfig.getStoredSignLayout().get(0))) {
                    if(!troniacraftPlayer.getJob().getName().equalsIgnoreCase(Troniacraft.getInstance().jobConfig.getDefaultJob())) {
                        p.sendMessage(Troniacraft.getInstance().config.Player_has_job);
                        return;
                    }

                    for(String lines : s.getLines()) {
                        if(lines != null) {
                            if (Job.getJobbyName(ChatColor.stripColor(lines)) != null) {
                                troniacraftPlayer.setJob(Job.getJobbyName(ChatColor.stripColor(lines)));
                                p.sendMessage(Troniacraft.getInstance().config.Player_joined_job);
                                break;
                            }
                        }
                    }
                }
            }

        }


    }
}
