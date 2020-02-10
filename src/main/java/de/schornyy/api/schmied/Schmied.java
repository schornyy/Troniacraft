package de.schornyy.api.schmied;

import de.schornyy.utils.GuiBuilder;
import de.schornyy.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Schmied {

    private File file;
    private FileConfiguration cfg;

    private String name, acessableJob;
    private int id, brueTime;
    private Inventory inventory;
    private ItemStack midItem, endItem;
    private List<ItemStack> ingrediens;

    private static ArrayList<Schmied> storedBlackSmiths = new ArrayList<>();

    public Schmied(String name) {
        this.name = name;
        this.ingrediens = new ArrayList<>();
        file = new File("plugins/Troniacraft/Schmied/" + name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        setName(getCfg().getString("Name"));
        setAcessableJob(getCfg().getString("AcessableJob"));
        setBrueTime(getCfg().getInt("BrueTime"));
        setEndItem(getCfg().getItemStack("EndItem"));
        setMidItem(getCfg().getItemStack("MidItem"));
        setId(getCfg().getInt("ID"));

        Inventory inv = Bukkit.createInventory(null, 27, "§eSchmied");

        if(getCfg().isSet("Inventory.")) {
            for(String items : getCfg().getConfigurationSection("Inventory.").getKeys(false)) {
                if(items != null) {
                    inv.setItem(Integer.valueOf(items), getCfg().getItemStack("Inventory." + items));
                }
            }
        }

        if(getCfg().isSet("Ingrediens.")) {
            for(String ingr : getCfg().getConfigurationSection("Ingrediens.").getKeys(false)) {
                if(ingr == null) continue;
                getIngrediens().add(getCfg().getItemStack("Ingrediens." + ingr));
            }
        }

        GuiBuilder.setBlackSmithGui(inv);
        setInventory(inv);
        getStoredBlackSmiths().add(this);
    }

    public void deleteSchmied() {
        getStoredBlackSmiths().remove(this);
        getFile().delete();
    }

    public static void loadAllBlacksmith() {
        File file = new File("plugins/Troniacraft/Schmied/");

        if(file.exists() && file.listFiles() != null) {
            for(File files : file.listFiles()) {
                if(files != null) {
                    Schmied schmied = new Schmied(files.getName().replaceAll(".yml", ""));
                    schmied.load();
                }
            }
        }
    }

    public static void  saveAllBlacksmith() {
        for(Schmied schmied : getStoredBlackSmiths()) {
            if(schmied != null) {
                schmied.save();
            }
        }
    }

    public void create() {
        setAcessableJob("Default");
        setId((getStoredBlackSmiths().size() + 1));
        setBrueTime(60);
        setMidItem(new ItemBuilder(Material.STICK).setName("§eDeveloped by Schornyy").Build());
        setEndItem(new ItemBuilder(Material.BEACON).setName("§eDeveloped by Schornyy").Build());
        Inventory inv = Bukkit.createInventory(null, 27, "§eSchmied");
        inv.setItem(10, new ItemBuilder(Material.STICK).Build());
        getIngrediens().add(new ItemBuilder(Material.IRON_INGOT, 2).Build());
        getIngrediens().add(new ItemBuilder(Material.WOODEN_PICKAXE).Build());
        GuiBuilder.setBlackSmithGui(inv);
        setInventory(inv);
    }

    public static Schmied getSchmiedByName(String name) {
        for(Schmied schmied : getStoredBlackSmiths()) {
            if(schmied != null) {
                if(schmied.getName().equalsIgnoreCase(name)) return schmied;
            }
        }
        return null;
    }

    public static Inventory getSchmiedInventorybyName(String name) {
        for(Schmied schmied : getStoredBlackSmiths()) {
            if(schmied != null) {
                if(schmied.getName().equalsIgnoreCase(name)) {
                    return schmied.getInventory();
                }
            }
        }
        return null;
    }

    public static Schmied getSchmiedByMidItemName(String itemName) {
        for(Schmied schmied : getStoredBlackSmiths()) {
            if(schmied == null) continue;
            if(schmied.getMidItem().getItemMeta().getDisplayName().equalsIgnoreCase(itemName)) return schmied;
        }
        return null;
    }

    public void save() {
        getCfg().set("Name", getName());
        getCfg().set("AcessableJob", getAcessableJob());
        getCfg().set("BrueTime", getBrueTime());
        getCfg().set("ID", getId());
        getCfg().set("EndItem", getEndItem());
        getCfg().set("MidItem", getMidItem());

        for(int i = 0; i < getInventory().getSize();i++) {
              if (getInventory().getItem(i) != null
                    && getInventory().getItem(i).getType() != Material.AIR) {
                  getCfg().set("Inventory." + i, getInventory().getItem(i));
              }
        }

        int d = 0;
        for(ItemStack itemStack : getIngrediens()) {
            if(itemStack == null) return;
            getCfg().set("Ingrediens." + d, itemStack);
        }

        try{
            getCfg().save(getFile());
        }catch (IOException e){}

    }

    public List<ItemStack> getIngrediens() {
        return ingrediens;
    }

    public void setIngrediens(List<ItemStack> ingrediens) {
        this.ingrediens = ingrediens;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMidItem(ItemStack midItem) {
        this.midItem = midItem;
    }

    public void setEndItem(ItemStack endItem) {
        this.endItem = endItem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAcessableJob(String acessableJob) {
        this.acessableJob = acessableJob;
    }

    public void setBrueTime(int brueTime) {
        this.brueTime = brueTime;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public ItemStack getMidItem() {
        ItemStack item = midItem;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (ItemStack ingredien : getIngrediens()) {
            if(ingredien == null) continue;
            lore.add(ingredien.getType().name() + ":" + ingredien.getAmount());
        }

    itemMeta.setLore(lore);
    item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack getEndItem() {
        return endItem;
    }

    public String getName() {
        return name;
    }

    public int getBrueTime() {
        return brueTime;
    }

    public static ArrayList<Schmied> getStoredBlackSmiths() {
        return storedBlackSmiths;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getAcessableJob() {
        return acessableJob;
    }
}
