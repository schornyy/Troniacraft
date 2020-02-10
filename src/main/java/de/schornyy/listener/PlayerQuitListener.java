package de.schornyy.listener;

import de.schornyy.api.player.TroniacraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(p);
        troniacraftPlayer.save();
    }
}
