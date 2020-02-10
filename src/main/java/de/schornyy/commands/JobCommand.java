package de.schornyy.commands;

import de.schornyy.Troniacraft;
import de.schornyy.api.job.Job;
import de.schornyy.api.player.TroniacraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JobCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;

        if(p.hasPermission("Troniacraft.job.edit")) {
            switch (args.length) {
                case 1:
                    if(args[0].equalsIgnoreCase("list")) {
                        for(Job job : Job.getStoredJobs()) {
                            if(job != null) {
                                p.sendMessage(job.getName());
                            }
                        }
                    }
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        if(Job.getJobbyName(args[1]) == null) {
                            Job job = new Job(args[1]);
                            job.createDefaults();
                            job.load();
                            p.sendMessage(Troniacraft.getInstance().config.Job_created);
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_already_exists);
                        }
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            if(TroniacraftPlayer.getStoredTroniacraftPlayer().get(Bukkit.getPlayer(args[1])).getJob().getName().equalsIgnoreCase(Troniacraft.getInstance().jobConfig.getDefaultJob())){
                                //Playerhasdefualtjob
                                p.sendMessage(Troniacraft.getInstance().config.Player_has_default_Job);
                                return true;
                            }

                            TroniacraftPlayer target = TroniacraftPlayer.getStoredTroniacraftPlayer().get(Bukkit.getPlayer(args[1]));
                            target.setJob(Job.getJobbyName(Troniacraft.getInstance().jobConfig.getDefaultJob()));
                            p.sendMessage(Troniacraft.getInstance().config.Player_set_Player_default_job);

                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Player_isnt_Online);
                        }

                    } else if(args[1].equalsIgnoreCase("delete")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            Job job = Job.getJobbyName(args[0]);
                            job.delete();
                            p.sendMessage(Troniacraft.getInstance().config.Job_deleted);

                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableCustomCraftings")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(p.getItemInHand() != null) {
                                Job job = Job.getJobbyName(args[0]);
                                if(job.getStoredBlacklistofcustomCrafts().contains(p.getItemInHand())) {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_already_containsitem);
                                    return true;
                                }
                                job.getStoredBlacklistofcustomCrafts().add(p.getItemInHand());
                                p.sendMessage(Troniacraft.getInstance().config.Job_additemtoBlacklist);
                                job.save();
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_NedditemtoaddtoBlacklist);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableCraftings")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(p.getItemInHand() != null) {
                                Job job = Job.getJobbyName(args[0]);
                                if(job.getStoredBlacklistofUnusableCraftings().contains(p.getItemInHand().getType())) {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_already_containsitem);
                                    return true;
                                }
                                job.getStoredBlacklistofUnusableCraftings().add(p.getItemInHand().getType());
                                job.save();
                                p.sendMessage(Troniacraft.getInstance().config.Job_additemtoBlacklist);

                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_NedditemtoaddtoBlacklist);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableBlocks")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(p.getItemInHand() != null) {
                                Job job = Job.getJobbyName(args[0]);
                                if(job.getStoredBlacklistofUseableBlocks().contains(p.getItemInHand().getType())) {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_already_containsitem);
                                    return true;
                                }
                                job.getStoredBlacklistofUseableBlocks().add(p.getItemInHand().getType());
                                job.save();
                                p.sendMessage(Troniacraft.getInstance().config.Job_additemtoBlacklist);

                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_NedditemtoaddtoBlacklist);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("setJob")) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            if (Job.getJobbyName(args[2]) != null) {
                                TroniacraftPlayer troniacraftPlayer = TroniacraftPlayer.getStoredTroniacraftPlayer().get(Bukkit.getPlayer(args[0]));
                                troniacraftPlayer.setJob(Job.getJobbyName(args[2]));
                                p.sendMessage(Troniacraft.getInstance().config.Player_set_Player_Job.replaceAll("%player%", troniacraftPlayer.getPlayer().getName()));
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Player_isnt_Online);
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableCraftings")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(Material.getMaterial(args[2]) != null) {
                                if(Job.getJobbyName(args[0]).getStoredBlacklistofUnusableCraftings().contains(Material.getMaterial(args[2]))) {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_already_containsitem);
                                    return true;
                                }
                                Job.getJobbyName(args[0]).getStoredBlacklistofUnusableCraftings().add(Material.getMaterial(args[2]));
                                Job.getJobbyName(args[0]).save();

                                p.sendMessage(Troniacraft.getInstance().config.Job_additemtoBlacklist);

                            } else{
                             p.sendMessage(Troniacraft.getInstance().config.Material_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableBlocks")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(Material.getMaterial(args[2]) != null) {
                                if(Job.getJobbyName(args[0]).getStoredBlacklistofUseableBlocks().contains(Material.getMaterial(args[2]))) {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_already_containsitem);
                                    return true;
                                }
                                Job.getJobbyName(args[0]).getStoredBlacklistofUseableBlocks().add(Material.getMaterial(args[2]));
                                Job.getJobbyName(args[0]).save();

                                p.sendMessage(Troniacraft.getInstance().config.Job_additemtoBlacklist);

                            } else{
                                p.sendMessage(Troniacraft.getInstance().config.Material_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableBlocks")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(Material.getMaterial(args[2]) != null) {
                                if(Job.getJobbyName(args[0]).getStoredBlacklistofUseableBlocks().contains(Material.getMaterial(args[2]))) {

                                    Job job = Job.getJobbyName(args[0]);
                                    job.getStoredBlacklistofUseableBlocks().remove(Material.getMaterial(args[2]));
                                    job.save();
                                    p.sendMessage(Troniacraft.getInstance().config.Job_removed_item_from_Blacklist);

                                } else {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_containsitem);
                                }
                            } else{
                                p.sendMessage(Troniacraft.getInstance().config.Material_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableCraftings")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            if(Material.getMaterial(args[2]) != null) {
                                if(Job.getJobbyName(args[0]).getStoredBlacklistofUnusableCraftings().contains(Material.getMaterial(args[2]))) {

                                    Job job = Job.getJobbyName(args[0]);
                                    job.getStoredBlacklistofUnusableCraftings().remove(Material.getMaterial(args[2]));
                                    job.save();
                                    p.sendMessage(Troniacraft.getInstance().config.Job_removed_item_from_Blacklist);

                                } else {
                                    p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_containsitem);
                                }
                            } else{
                                p.sendMessage(Troniacraft.getInstance().config.Material_dosnt_exists);
                            }
                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableCustomCraftings")) {
                        if(Job.getJobbyName(args[0]) != null) {
                            String name = args[2];
                            ItemStack itemStack = null;
                            int slot = 0;
                            for(ItemStack itemStack1 : Job.getJobbyName(args[0]).getStoredBlacklistofcustomCrafts()) {
                                if(itemStack1 != null) {
                                    if(itemStack1.getItemMeta() != null) {
                                        if(itemStack1.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
                                            itemStack = itemStack1;
                                            
                                        }
                                    }
                                    slot++;
                                }
                            }

                            if(itemStack != null) {
                                Job.getJobbyName(args[0]).getStoredBlacklistofcustomCrafts().remove(slot);
                                Job.getJobbyName(args[0]).save();
                                p.sendMessage(Troniacraft.getInstance().config.Job_removed_item_from_Blacklist);
                            } else {
                                p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_containsitem);
                            }

                        } else {
                            p.sendMessage(Troniacraft.getInstance().config.Job_dosnt_exists);
                        }
                    }
                    break;
            }
        }

        return false;
    }

            /*   0     1       2          3
             1     2       3          4
        job create <Name>
        job <Name> delete
        job delete <Player>
        job <Player> setJob <Job>
        job <Name> addUnusableBlocks | <material>
        job <Name> addUnusablecustomCraftings
        job <Name> addUnusableCraftings | <material>
        job <Name> removeUnusableBlocks <Material>
        job <Name> removeUnusableCraftings <Material>
        job <Name> removeUnusableCustomCraftings <Name>

         */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabs = new ArrayList<>();

        if(sender.hasPermission("Troniacraft.job.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("create");
                    tabs.add("delete");
                    Job.getStoredJobs().forEach(job -> tabs.add(job.getName()));
                    Bukkit.getOnlinePlayers().forEach(all -> tabs.add(all.getName()));
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        tabs.add("<Name>");
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        Bukkit.getOnlinePlayers().forEach(all -> tabs.add(all.getName()));
                    } else if(Job.getJobbyName(args[0]) != null) {
                        tabs.add("delete");
                        tabs.add("addUnusableBlocks");
                        tabs.add("addUnusableCraftings");
                        tabs.add("removeUnusableBlocks");
                        tabs.add("removeUnusableCraftings");
                        tabs.add("removeUnusableCustomCraftings");
                        tabs.add("addUnusablecustomCraftings");
                    } else if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                        tabs.add("setJob");
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("addUnusableBlocks")) {
                        for(Material material : Material.values()) {
                            tabs.add(material.name());
                        }
                    } else if(args[1].equalsIgnoreCase("addUnusableCraftings")) {
                        for(Material material : Material.values()) {
                            tabs.add(material.name());
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableBlocks")) {
                        for(Material material : Job.getJobbyName(args[0]).getStoredBlacklistofUseableBlocks()){
                            if(material != null) {
                                tabs.add(material.name());
                            }
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableCraftings")) {
                        for(Material material : Job.getJobbyName(args[0]).getStoredBlacklistofUnusableCraftings()){
                            if(material != null) {
                                tabs.add(material.name());
                            }
                        }
                    } else if(args[1].equalsIgnoreCase("removeUnusableCustomCraftings")) {
                        for(ItemStack itemStack : Job.getJobbyName(args[0]).getStoredBlacklistofcustomCrafts()) {
                            if(itemStack != null) {
                                tabs.add(itemStack.getItemMeta().getDisplayName());
                            }
                        }
                    } else if(args[1].equalsIgnoreCase("setJob")) {
                        Job.getStoredJobs().forEach(job -> tabs.add(job.getName()));
                    }
                    break;
            }
        }

        return tabs;
    }
}
