package de.schornyy.listener;

import de.schornyy.Troniacraft;
import de.schornyy.api.job.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        if(p.hasPermission("Troniacraft.job.edit")) {
            if(e.getLines() != null) {
                if(e.getLine(0).equalsIgnoreCase("[job]")) {
                    if(Job.getJobbyName(e.getLine(1)) != null) {

                        int i = 0;
                        for(String lines : Troniacraft.getInstance().jobConfig.getStoredSignLayout()) {
                            if(lines != null) {
                                String out = lines;
                                if(lines.contains("%job%")) {
                                    out = lines.replaceAll("%job%", Job.getJobbyName(e.getLine(i)).getName());
                                }
                                p.sendMessage(out);
                                e.setLine(i, out);
                                i++;
                            }
                        }

                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}
