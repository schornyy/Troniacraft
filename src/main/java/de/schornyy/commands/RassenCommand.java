package de.schornyy.commands;

import de.schornyy.Troniacraft;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.rasse.Rasse;
import de.schornyy.listener.PlayerInteractEntityListener;
import de.schornyy.utils.MessageBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RassenCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.hasPermission("Troniacraft.rasse.edit")) {
            switch (args.length) {
                case 1:
                    if(args[0].equalsIgnoreCase("list")) {
                        for(Rasse rasse : Rasse.getStoredRassen()) {
                            if(rasse != null) {

                                TextComponent msg = new MessageBuilder(rasse.getName() + "´s Info: ").getTextComponent();
                                String pe = "";
                                for(PotionEffect effect : rasse.getPotionEffects()) {
                                    if(effect == null) continue;
                                    pe += effect.getType().getName() + "§e:" + effect.getAmplifier() + " §6§l| ";
                                }
                                TextComponent info = new MessageBuilder("§f[§bInfo§f]").addHover("§fName: §e" + rasse.getName() + "\n"
                                        + "§fMaxhealth: §e" + rasse.getMaxHealth() + "\n" + "§fEffecte: §e" + pe).getTextComponent();
                                player.spigot().sendMessage(msg, info);
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("accept")) {
                        if(PlayerInteractEntityListener.hasinvite.containsKey(player)) {
                            Rasse rasse = Rasse.getRassebyName(PlayerInteractEntityListener.hasinvite.get(player));
                            TroniacraftPlayer.getStoredTroniacraftPlayer().get(player).setRasse(rasse);
                            player.sendMessage(Troniacraft.getInstance().config.Player_joined_Rasse);
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.Player_hasnt_Invite);
                        }
                    } else if(args[0].equalsIgnoreCase("deny")) {
                        if(PlayerInteractEntityListener.hasinvite.containsKey(player)) {
                            PlayerInteractEntityListener.hasinvite.remove(player);
                            player.sendMessage(Troniacraft.getInstance().config.Player_denyed_Invite);
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.Player_hasnt_Invite);
                        }
                    }
                    break;
                case 2:

                    if(args[0].equalsIgnoreCase("create")) {
                        if(Rasse.getStoredRassen().contains(Rasse.getRassebyName(args[1]))) {
                            player.sendMessage(Troniacraft.getInstance().config.Rasse_exists);
                            return true;
                        }

                        Rasse rasse = new Rasse(args[1]);
                        rasse.save();
                        rasse.load();
                        player.sendMessage(Troniacraft.getInstance().config.Rasse_created);
                    } else if(args[1].equalsIgnoreCase("delete")) {
                        if(Bukkit.getPlayer(args[0]) == null) {
                            player.sendMessage(Troniacraft.getInstance().config.Player_isnt_Online);
                            return true;
                        } else {
                            TroniacraftPlayer target = TroniacraftPlayer.getStoredTroniacraftPlayer().get(Bukkit.getPlayer(args[0]));
                            if(target.getRasse() != null) {
                                target.setRasse(null);
                                target.save();
                                target.load();
                               player.sendMessage(Troniacraft.getInstance().config.Player_deleted_Player_Rasse.replaceAll("%player%", target.getPlayer().getName()));
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.Player_dosnt_have_Rasse);
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        if(Rasse.getRassebyName(args[1]) == null) {
                            player.sendMessage(Troniacraft.getInstance().config.Rasse_exists);
                            return true;
                        }
                        Rasse rasse = Rasse.getRassebyName(args[1]);
                        rasse.delete();
                        player.sendMessage(Troniacraft.getInstance().config.Rasse_deleted);
                    }

                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("setRasse")) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            if(Rasse.getRassebyName(args[2]) != null) {
                                TroniacraftPlayer target = TroniacraftPlayer.getStoredTroniacraftPlayer().get(Bukkit.getPlayer(args[0]));
                                target.setRasse(Rasse.getRassebyName(args[2]));
                                target.save();
                                target.load();
                                player.sendMessage(Troniacraft.getInstance().config.Player_set_Player_Rasse.replaceAll("%player%", target.getPlayer().getName()));
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.Rasse_dosnt_exists);
                            }
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.Player_isnt_Online);
                        }
                    } else if(args[1].equalsIgnoreCase("removePotionEffect")) {
                        if(Rasse.getRassebyName(args[0]) != null) {
                            if(PotionEffectType.getByName(args[2]) != null) {
                                Rasse rasse = Rasse.getRassebyName(args[0]);
                                int i = 0;
                                for(PotionEffect potionEffectType : rasse.getPotionEffects()) {
                                    if(potionEffectType == null) continue;
                                    if(potionEffectType.getType() == PotionEffectType.getByName(args[2])) {
                                        rasse.getPotionEffects().remove(i);
                                    }
                                    i++;
                                }
                                player.sendMessage(Troniacraft.getInstance().config.Rasse_removed_Effect);
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.PotionEffect_dosnt_exists);
                            }
                        } else {
                           player.sendMessage(Troniacraft.getInstance().config.Rasse_dosnt_exists);
                        }
                    }
                    break;
                case 4:
                    if(args[1].equalsIgnoreCase("addPotionEffect")) {
                        if(Rasse.getRassebyName(args[0]) != null) {
                            if(PotionEffectType.getByName(args[2]) != null) {
                                PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(args[2]), Integer.MAX_VALUE, Integer.valueOf(args[3]));
                                Rasse.getRassebyName(args[0]).getPotionEffects().add(potionEffect);
                                player.sendMessage(Troniacraft.getInstance().config.Potion_effect_added);
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.PotionEffect_dosnt_exists);
                            }
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.Rasse_dosnt_exists);
                        }
                    }
                    break;
            }
        } else {
            player.sendMessage(Troniacraft.getInstance().config.PlayerhasnoPermissions);
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabs = new ArrayList<>();
        Player player = (Player)sender;
           /*
    /rasse <Spieler> setRasse <Rasse>
    /rasse <Spieler> delete
    /rasse create <Name>
    /rasse <Name> addPotionEffect <Type> <Level>
    /rasse <Name> removePotionEffect <Type>
    /rasse delete <Name>
    /rasse list
     */

        if(player.hasPermission("Troniacraft.rasse.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("list");
                    tabs.add("delete");
                    tabs.add("create");
                    Bukkit.getOnlinePlayers().forEach(all -> tabs.add(all.getName()));
                    Rasse.getStoredRassen().forEach(rasse -> tabs.add(rasse.getName()));
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("delete")) {
                        Rasse.getStoredRassen().forEach(rasse -> tabs.add(rasse.getName()));
                    } else if(Bukkit.getPlayer(args[0]) != null) {
                        tabs.add("delete");
                        tabs.add("setRasse");
                    } else if(Rasse.getRassebyName(args[0]) != null) {
                        tabs.add("addPotionEffect");
                        tabs.add("removePotionEffect");
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("removePotionEffect")) {
                        if(Rasse.getRassebyName(args[0]) != null){
              Rasse.getRassebyName(args[0])
                  .getPotionEffects()
                  .forEach(effect -> tabs.add(effect.getType().getName()));
                        }
                    } else if(args[1].equalsIgnoreCase("setRasse")) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            Rasse.getStoredRassen().forEach(rasse -> tabs.add(rasse.getName()));
                        }
                    } else if(args[1].equalsIgnoreCase("addPotionEffect")) {
                        if(Rasse.getRassebyName(args[0]) != null) {
                            for(PotionEffectType effectType : PotionEffectType.values()) {
                                tabs.add(effectType.getName());
                            }
                        }
                    }
                    break;
            }
        }

        return tabs;
    }
}
