package de.schornyy.commands;

import de.schornyy.Troniacraft;
import de.schornyy.api.npc.NPC;
import de.schornyy.api.rasse.Rasse;
import de.schornyy.utils.MessageBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.hasPermission("troniacraft.npc.edit")) {
            switch (args.length) {
                case 1:
                    if(args[0].equalsIgnoreCase("list")) {
                        for(NPC npc : NPC.getStoredNPCs()) {
                            if(npc == null) continue;

                            TextComponent msg = new MessageBuilder(npc.getName() + "§f´s Info: ").getTextComponent();
                            TextComponent info = new MessageBuilder("§f[§bInfo§f]").addHover("§fName: " + npc.getName() + "\n"
                            + "§fEntityType: §6" + npc.getEntityType().getName() + "\n"
                            + "§fRasse: §6" + npc.getRasse() + "\n"
                            + "§fLocation: §eWorld§f: §6" + npc.getSpawnLocation().getWorld().getName()
                                    + " §eX§f: §6" + (int)npc.getSpawnLocation().getX()
                                    + " §eY§f: §6" + (int)npc.getSpawnLocation().getY()
                                    + " §eZ§f: §6" + (int)npc.getSpawnLocation().getZ()).getTextComponent();

                            player.spigot().sendMessage(msg, info);
                        }
                    }
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        NPC npc = new NPC(UUID.randomUUID().toString());
                        npc.create(player.getLocation(), args[1]);
                        player.sendMessage(Troniacraft.getInstance().config.Player_create_NPC);
                    } else if(args[1].equalsIgnoreCase("delete")) {
                        int id = Integer.valueOf(args[0]);
                        if(NPC.getNPCbyID(id) != null) {
                            NPC npc = NPC.getNPCbyID(id);
                            npc.delete();
                            player.sendMessage(Troniacraft.getInstance().config.Player_deleted_NPC);
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_dosnt_exists);
                        }
                    } else if (args[1].equalsIgnoreCase("setSpawnpoint")) {
                        int id = Integer.valueOf(args[0]);
                        if(NPC.getNPCbyID(id) != null) {
                            NPC npc = NPC.getNPCbyID(id);
                            npc.changeSpawnpoint(player.getLocation());
                           player.sendMessage(Troniacraft.getInstance().config.Player_setSpawn_NPC);
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_dosnt_exists);
                        }
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("setRasse")) {
                        int id = Integer.valueOf(args[0]);
                        if(NPC.getNPCbyID(id) != null) {
                            if(Rasse.getRassebyName(args[2]) != null) {
                                NPC npc = NPC.getNPCbyID(id);
                                npc.setRasse(args[2]);
                                //PlayersetNPCRasse
                                player.sendMessage(Troniacraft.getInstance().config.Player_setRasse_NPC);
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.Rasse_dosnt_exists);
                            }
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("setName")) {
                        int id = Integer.valueOf(args[0]);
                        if(NPC.getNPCbyID(id) != null) {
                            NPC npc = NPC.getNPCbyID(id);
                            npc.changeName(args[2]);
                            player.sendMessage(Troniacraft.getInstance().config.Player_setName_NPC);
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("setEntityType")) {
                        int id = Integer.valueOf(args[0]);
                        if(NPC.getNPCbyID(id) != null) {
                            if(EntityType.fromName(args[2]) != null) {
                                NPC npc = NPC.getNPCbyID(id);
                                npc.changeEntityType(EntityType.fromName(args[2]));
                                player.sendMessage(Troniacraft.getInstance().config.Player_setEntityType_NPC);
                            } else {
                                player.sendMessage(Troniacraft.getInstance().config.Entity_dosnt_exists);
                            }
                        } else {
                            player.sendMessage(Troniacraft.getInstance().config.NPC_dosnt_exists);
                        }
                    }
                    break;
            }
        } else {
      player.sendMessage(Troniacraft.getInstance().config.PlayerhasnoPermissions);
        }

        return false;
    }

       /*
    /npc list
    /npc create <name>
    /npc <id> delete
    /npc <id> setEntityType <Type>
    /npc <id> setSpawnpoint
    /npc <id> setName <Name>
    /npc <id> setRasse <Rasse>
     */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabs = new ArrayList<>();

        if(sender.hasPermission("Troniacraft.npc.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("list");
                    tabs.add("create");
                    NPC.getStoredNPCs().forEach(npc -> {
                        if(npc != null) {
                            tabs.add("" + npc.getId());
                        }
                    });
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("create")){
                        tabs.add("<Name>");
                    } else if(NPC.getNPCbyID(Integer.valueOf(args[0])) != null) {
                        tabs.add("delete");
                        tabs.add("setEntityType");
                        tabs.add("setSpawnpoint");
                        tabs.add("setName");
                        tabs.add("setRasse");
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("setEntityType")) {
                        for(EntityType entityType : EntityType.values()) {
                            tabs.add(entityType.toString());
                        }
                    } else if(args[1].equalsIgnoreCase("setName")) {
                        tabs.add("<Name>");
                    } else if(args[1].equalsIgnoreCase("setRasse")) {
                        Rasse.getStoredRassen().forEach(rasse -> tabs.add(rasse.getName()));
                    }
                    break;
            }
        }

        return tabs;
    }
}
