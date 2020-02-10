package de.schornyy.commands;

import de.schornyy.Troniacraft;
import de.schornyy.api.job.Job;
import de.schornyy.api.schmied.Schmied;
import de.schornyy.utils.MessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchmiedCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;

        /*
                0       1
        0       1       2
        schmied create <name>
        schmied delete <Name>
        schmied edit <Name>
        schmied setMidItem <name>
        schmied setEndItem <name>
        schmied addIngrediend <name>
        schmied removeIngrediend <Name>
        schmied setSmithTimeInMinutes <Name> <amount>
        schmied setSmithTimeInSeconds <Name> <amout>
        schmied setAcessableJob <Name> <Job>
         */

        if(p.hasPermission("schmied.edit")) {
            switch (args.length) {
                case 1:
                    if(args[0].equalsIgnoreCase("list")) {
                        Schmied.getStoredBlackSmiths().forEach(schmied -> {
                            TextComponent smith = new MessageBuilder("§f" + schmied.getName() + "§b | ").addHover("§fAcessableJob: §e" + schmied.getAcessableJob() + "\n" + "§fSchmiedzeit: §e" + schmied.getBrueTime()).getTextComponent();
                            TextComponent edit = new MessageBuilder("§6[§eEdit§6]").addHover("§aKlicken um das Rezept zu ändern!").addClick(ClickEvent.Action.RUN_COMMAND, "/schmied edit " + schmied.getName()).getTextComponent();
                            TextComponent delete = new MessageBuilder(" §4[§cDelete§4]").addHover("§cKlicken um das Rezept zu löschen!").addClick(ClickEvent.Action.RUN_COMMAND, "/schmied delete " + schmied.getName()).getTextComponent();
                            p.spigot().sendMessage(smith, edit, delete);
                        });
                    }
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("delete")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            Schmied schmied = Schmied.getSchmiedByName(args[1]);
                            schmied.deleteSchmied();
                            p.sendMessage(Troniacraft.getInstance().config.Smith_deleted);
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("edit")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            Schmied schmied = Schmied.getSchmiedByName(args[1]);
                            p.openInventory(schmied.getInventory());
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("setMidItem")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            if(p.getItemInHand() != null) {
                                Schmied schmied = Schmied.getSchmiedByName(args[1]);
                                schmied.setMidItem(p.getItemInHand());
                                p.sendMessage(Troniacraft.getInstance().config.Player_setMidItem);
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.NeedItemInHand);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("setEndItem")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            if(p.getItemInHand() != null) {
                                Schmied schmied = Schmied.getSchmiedByName(args[1]);
                                schmied.setEndItem(p.getItemInHand());
                                p.sendMessage(Troniacraft.getInstance().config.Player_setEndItem);
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.NeedItemInHand);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("addIngrediend")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            if(p.getItemInHand() != null) {
                                Schmied schmied = Schmied.getSchmiedByName(args[1]);
                                schmied.getIngrediens().add(p.getItemInHand());
                                p.sendMessage(Troniacraft.getInstance().config.Smith_addIngrediends);
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.NeedItemInHand);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    }else if(args[0].equalsIgnoreCase("removeIngrediend")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            if(p.getItemInHand() != null) {
                                if(Schmied.getSchmiedByName(args[1]).getIngrediens().contains(p.getItemInHand())) {
                                    Schmied schmied = Schmied.getSchmiedByName(args[1]);
                                    schmied.getIngrediens().remove(p.getItemInHand());
                                    p.sendMessage(Troniacraft.getInstance().config.Smith_removeIngrediends);
                                } else {
                                    p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_contains_Item);
                                }
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.NeedItemInHand);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("create")) {
                        if(Schmied.getSchmiedByName(args[1]) == null) {
                            Schmied schmied = new Schmied(args[1]);
                            schmied.create();
                            Schmied.getStoredBlackSmiths().add(schmied);
                            p.sendMessage(Troniacraft.getInstance().config.Smith_created);
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_Alreadyexists);
                        }
                    }
                    break;
                case 3:
                    if(args[0].equalsIgnoreCase("setAcessableJob")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            if(Job.getJobbyName(args[2]) != null) {
                                Schmied schmied = Schmied.getSchmiedByName(args[1]);
                                schmied.setAcessableJob(args[2]);
                                p.sendMessage(Troniacraft.getInstance().config.Smith_setAcessableJob.replaceAll("%job%", args[2]));
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("setSmithTimeInMinutes")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            int amount = 0;
                            try{
                                amount = Integer.valueOf(args[2]);
                            } catch (NumberFormatException e){
                                p.sendMessage(Troniacraft.getInstance().config.MustEnterNummber);
                            }
                            Schmied schmied = Schmied.getSchmiedByName(args[1]);
                            schmied.setBrueTime((amount * 60));
                            p.sendMessage(Troniacraft.getInstance().config.Smith_setBrueTime.replaceAll("%seconds%", String.valueOf((amount * 60))));
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("setSmithTimeInSeconds")) {
                        if(Schmied.getSchmiedByName(args[1]) != null) {
                            int amount = 0;
                            try{
                                amount = Integer.valueOf(args[2]);
                            } catch (NumberFormatException e){
                                p.sendMessage(Troniacraft.getInstance().config.MustEnterNummber);
                            }
                            Schmied schmied = Schmied.getSchmiedByName(args[1]);
                            schmied.setBrueTime(amount);
                            p.sendMessage(Troniacraft.getInstance().config.Smith_setBrueTime.replaceAll("%seconds%", String.valueOf(amount)));
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Smith_dosnt_exists);
                        }
                    }
                    break;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        /*
                0       1
        0       1       2
        schmied create <name>
        schmied delete <Name>
        schmied edit <Name>
        schmied setMidItem <name>
        schmied setEndItem <name>
        schmied addIngrediend <name>
        schmied removeIngrediend <Name>
        schmied setSmithTimeInMinutes <Name> <amount>
        schmied setSmithTimeInSeconds <Name> <amout>
        schmied setAcessableJob <Name> <Job>
         */

        if(sender.hasPermission("schmied.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("list");
                    tabs.add("create");
                    tabs.add("delete");
                    tabs.add("edit");
                    tabs.add("setMidItem");
                    tabs.add("setEndItem");
                    tabs.add("addIngrediend");
                    tabs.add("removeIngrediend");
                    tabs.add("setSmithTimeInMinutes");
                    tabs.add("setSmithTimeInSeconds");
                    tabs.add("setAcessableJob");
                    break;
                case 2:
                    Schmied.getStoredBlackSmiths().forEach(schmied -> tabs.add(schmied.getName()));
                    break;
                case 3:
                    if(args[0].equalsIgnoreCase("setAcessableJob")) {
                        Job.getStoredJobs().forEach(job -> tabs.add(job.getName()));
                    } else if(args[0].equalsIgnoreCase("setSmithTimeInSeconds")) {
                        tabs.add("<Sekunden>");
                    }  else if(args[0].equalsIgnoreCase("setSmithTimeInMinutes")) {
                        tabs.add("<Minuten>");
                    }
                    break;
            }
        }

        return tabs;
    }
}
