package de.schornyy.commands;

import de.schornyy.Troniacraft;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCommand implements CommandExecutor, TabCompleter {

    /*
    item addModiefier <Modifier> <Slot> <amount>
    item removeModiefier <Modiefier> - X
    item setName <Name> - X
    item addEnchantment <Enchantment> <Level>
    item removeEnchantment <Enchantment> - X
    item addItemFlag <ItemFlag> - X
    item removeItemFlag <ItemFlag> - X
    item setDurability <amount> - X
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

    if (player.hasPermission("item.edit")){

        if(args.length == 2)  {
            String cmd = args[0];

            if(cmd.equalsIgnoreCase("setName")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(args[1].replaceAll("&", "§").replaceAll("_", " "));
                    item.setItemMeta(itemMeta);
                    player.setItemInHand(item);
                    //Item_setName
                } else {
                    //Player need Item in Hand
                }
            } else if(cmd.equalsIgnoreCase("addItemFlag")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.valueOf(args[1]));
                    item.setItemMeta(itemMeta);
                    player.setItemInHand(item);
                    //Item_addItemFlag
                } else {
                    //Player need Item in Hand
                }
            } else if(cmd.equalsIgnoreCase("removeItemFlag")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    if(itemMeta.getItemFlags().contains(ItemFlag.valueOf(args[1]))) {
                        itemMeta.removeItemFlags(ItemFlag.valueOf(args[1]));
                        item.setItemMeta(itemMeta);
                        player.setItemInHand(item);
                        //Item_removedItemFlag
                    } else {
                        //ItemdosnthaveFlag
                    }
                } else {
                    //Player need Item in Hand
                }
            } else if(cmd.equalsIgnoreCase("setDurability")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    item.setDurability(Short.valueOf(args[1]));
                    player.setItemInHand(item);
                    player.sendMessage("" + player.getItemInHand().getDurability());
                    //Item_setDurability
                } else {
                    //Player need Item in Hand
                }
            } else if(cmd.equalsIgnoreCase("removeEnchantment")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    if(item.getEnchantments().containsKey(Enchantment.getByName(args[1]))) {
                        item.removeEnchantment(Enchantment.getByName(args[1]));
                        player.setItemInHand(item);
                        //Item_removedEnchantment
                    } else {
                        //ItemdostHaveEnchantment
                    }
                } else {
                    //Player need Item in Hand
                }
            } else if(cmd.equalsIgnoreCase("removeModiefier")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    if(itemMeta.getAttributeModifiers(Attribute.valueOf(args[1]))!= null) {
                        itemMeta.removeAttributeModifier(Attribute.valueOf(args[1]));
                        item.setItemMeta(itemMeta);
                        player.setItemInHand(item);
                        //Item_removedModiefier
                    } else {
                        //ItemdostHaveEnchantment
                    }
                } else {
                    //Player need Item in Hand
                }
            }
        } else if(args.length == 3){
            String cmd = args[0];
            if(cmd.equalsIgnoreCase("addEnchantment")) {
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addEnchant(Enchantment.getByName(args[1]), Integer.valueOf(args[2]), true);
                    item.setItemMeta(itemMeta);
                    player.setItemInHand(item);
                    //Item_addEnchantment
                } else {
                    //Player need Item in Hand
                }
            }
          } else if (args.length == 4) {
            String cmd = args[0];

            if(args[0].equalsIgnoreCase("addModiefier")) {
                String modifier = args[1];
                String slot = args[2];
                int amount = Integer.valueOf(args[3]);
                if(player.getItemInHand() != null || player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), modifier.toLowerCase(), amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.valueOf(slot));

                    itemMeta.addAttributeModifier(Attribute.valueOf(modifier.toUpperCase()), attributeModifier);

                    item.setItemMeta(itemMeta);
                    player.setItemInHand(item);
                } else {
                    //Player need Item in Hand
                }
            }
            }

    } else {
      player.sendMessage(Troniacraft.getInstance().config.PlayerhasnoPermissions);
    }

        return false;
    }

        /*
    item addModiefier <Modifier> <Slot> <amount>
    item removeModiefier <Modiefier> - X
    item setName <Name> - X
    item addEnchantment <Enchantment> <Level>
    item removeEnchantment <Enchantment>
    item addItemFlag <ItemFlag> - X
    item removeItemFlag <ItemFlag> - X
    item setDurability <amount> - X
     */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();
        Player player = (Player)sender;


        if(sender.hasPermission("item.edit")) {
            if(args.length == 1) {
                tabs.add("addModiefier");
                tabs.add("removeModiefier");
                tabs.add("setName");
                tabs.add("addEnchantment");
                tabs.add("removeEnchantment");
                tabs.add("addItemFlag");
                tabs.add("removeItemFlag");
                tabs.add("setDurability");
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("removeModiefier")) {
                    if(player.getItemInHand() != null) {
                        ItemMeta itemMeta = player.getItemInHand().getItemMeta();
                        if(itemMeta.getAttributeModifiers() != null) {
                            for(Attribute attribute : itemMeta.getAttributeModifiers().keys()) {
                                tabs.add(attribute.name());
                            }
                        }
                    }
                } else if(args[0].equalsIgnoreCase("setName")) {
                    if(player.getItemInHand() != null) {
                        tabs.add("<Name>");
                    }
                }  else if(args[0].equalsIgnoreCase("addItemFlag")) {
                    if(player.getItemInHand() != null) {
                        for(ItemFlag itemFlag : ItemFlag.values()) {
                            tabs.add(itemFlag.name());
                        }
                    }
                }  else if(args[0].equalsIgnoreCase("removeItemFlag")) {
                    if(player.getItemInHand() != null) {
                        ItemMeta itemMeta = player.getItemInHand().getItemMeta();
                        if(itemMeta.getItemFlags() != null) {
                            itemMeta.getItemFlags().forEach(itemFlag -> {tabs.add(itemFlag.name());});
                        }
                    }
                }  else if(args[0].equalsIgnoreCase("setDurability")) {
                    if(player.getItemInHand() != null) {
                        tabs.add("<Zahl>");
                    }
                } else if(args[0].equalsIgnoreCase("removeEnchantment")) {
                    if(player.getItemInHand() != null) {
                        ItemMeta itemMeta = player.getItemInHand().getItemMeta();
                        if(itemMeta.getEnchants() != null) {
                            itemMeta.getEnchants().forEach((enchantment, integer) -> {
                                tabs.add(enchantment.getName());
                            });
                        }
                    }
                } else if(args[0].equalsIgnoreCase("addEnchantment")) {
                    if(player.getItemInHand() != null) {
                        ItemMeta itemMeta = player.getItemInHand().getItemMeta();
                        for (Enchantment enchantment : Enchantment.values()) {
                            tabs.add(enchantment.getName());
                        }
                    }
                } else if(args[0].equalsIgnoreCase("addModiefier")) {
                    if(player.getItemInHand() != null) {
                        for(Attribute attribute : Attribute.values()) {
                            tabs.add(attribute.name());
                        }
                    }
                }
            } else if(args.length == 3){
                if (args[0].equalsIgnoreCase("addEnchantment")) {
                    tabs.add(String.valueOf(Enchantment.getByName(args[1]).getMaxLevel()));
                } else if(args[0].equalsIgnoreCase("addModiefier")) {
                    for(EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        tabs.add(equipmentSlot.name());
                    }
                }
            } else if(args.length == 4) {
                if(args[0].equalsIgnoreCase("addModiefier")) {
                    tabs.add("<Zahl>");
                }
            }
        }

        return tabs;
    }
}
