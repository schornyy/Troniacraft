package de.schornyy.listener;

import de.schornyy.Troniacraft;
import de.schornyy.api.npc.NPC;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.utils.MessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInteractEntityListener implements Listener {

    private ArrayList<Player> wait = new ArrayList<>();
    public static HashMap<Player, String> hasinvite = new HashMap<>();

    @EventHandler
    public  void onEntityInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(player);

        if(e.getRightClicked().getCustomName() != null) {
            for (NPC npc : NPC.getStoredNPCs()) {
                if (npc != null) {
                    if (e.getRightClicked().getCustomName().equalsIgnoreCase(npc.getName())) {
                        e.setCancelled(true);

                        Bukkit.getScheduler().runTaskLater(Troniacraft.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                wait.remove(player);
                            }
                        }, 20*2);

                        if(wait.contains(player)) {
                            return;
                        }

                        if(npc.getRasse() == null) {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_hasnt_Rasse);
                            wait.add(player);
                            return;
                        }

                        if(troniacraftPlayer.getRasse() != null) {
                            player.sendMessage(Troniacraft.getInstance().config.Player_has_Rasse);
                            wait.add(player);
                            return;
                        }

                        TextComponent msg = new MessageBuilder("§eWillst du den §6" + npc.getRasse() + "§e beitreten ?").getTextComponent();
                        TextComponent ja = new MessageBuilder("§f[§a§lJa§f]").addHover("§aKlicken um die Einladung abzulehnen").addClick(ClickEvent.Action.RUN_COMMAND, "/rasse accept").getTextComponent();
                        TextComponent nein = new MessageBuilder("§f[§c§lNein§f]").addHover("§cKlicken um die Einladung abzulehen").addClick(ClickEvent.Action.RUN_COMMAND, "/rasse deny").getTextComponent();

                        player.spigot().sendMessage(msg, ja, nein);
                        wait.add(player);
                        hasinvite.put(player, npc.getRasse());

                    }
                }
            }
        }


    }
}
