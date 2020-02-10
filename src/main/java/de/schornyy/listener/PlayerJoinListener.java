package de.schornyy.listener;

import de.schornyy.api.player.TroniacraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        TroniacraftPlayer troniacraftPlayer = new TroniacraftPlayer(p);
        troniacraftPlayer.load();
    }
}
