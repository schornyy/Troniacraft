package de.schornyy.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class GuiBuilder {

    public static int[] slots = {0,4,5,6,7,8
            ,9,13,14,16,17
            ,18,22,23,24,25,26};
    public static int starter = 15;

    public static void setBlackSmithGui(Inventory inventory) {

        for (int i = 0;i < slots.length;i++) {
            inventory.setItem(slots[i], new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).Build());
        }

        inventory.setItem(starter, new ItemBuilder(Material.LIME_DYE).setName("§aStarten!").Build());

    }

}
