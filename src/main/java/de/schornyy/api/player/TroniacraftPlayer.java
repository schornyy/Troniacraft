package de.schornyy.api.player;

import de.schornyy.Troniacraft;
import de.schornyy.api.job.Job;
import de.schornyy.api.rasse.Rasse;
import de.schornyy.api.schmied.PlayerSchmieded;
import de.schornyy.api.schmied.Schmied;
import de.schornyy.utils.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TroniacraftPlayer {

    private File file;
    private FileConfiguration cfg;

    private Player player;
    private Rasse rasse;
    private Job job;
    private Location getLatestClickedLocation = null;
    private int PlayerBrueds;
    private List<PlayerSchmieded> storedPlayerSchmieded;

    private static HashMap<Player, TroniacraftPlayer> storedTroniacraftPlayer = new HashMap<>();

    public TroniacraftPlayer(Player player) {
        this.player = player;
        storedPlayerSchmieded = new ArrayList<>();
        file = new File("plugins/Troniacraft/Player/" + player.getName() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public static void loadAllTroniacraftPlayer() {
        for(Player all : Bukkit .getOnlinePlayers()) {
            if(all == null) continue;
            TroniacraftPlayer troniacraftPlayer = new TroniacraftPlayer(all);
            troniacraftPlayer.load();
        }
    }

    public static void saveAllTroniacraftPlayer() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all == null) continue;
            TroniacraftPlayer troniacraftPlayer = getStoredTroniacraftPlayer().get(all);
            troniacraftPlayer.save();
        }
    }

    public void save() {
        if(getRasse() != null) {
            getCfg().set("Rasse", getRasse().getName());
        } else {
            if(getCfg().get("Rasse") != null) {
                getCfg().set("Rasse", null);
            }
        }

        if(!getJob().getName().equalsIgnoreCase(Troniacraft.getInstance().jobConfig.getDefaultJob())) {
            getCfg().set("Job", getJob().getName());
        }

        if(getStoredPlayerSchmieded().size() != 0) {
            getCfg().set("PlayerBrues", getStoredPlayerSchmieded().size());
            int i = 0;
            for(PlayerSchmieded playerSchmieded : getStoredPlayerSchmieded()) {
                if(playerSchmieded != null) {
                    getCfg().set("PlayerSchmieded." + i + ".BruedTime", playerSchmieded.geschmolzeneZeit());
                    getCfg().set("PlayerSchmieded." + i + ".Schmied", playerSchmieded.getSchmied().getName());
                    getCfg().set("PlayerSchmieded." + i + ".Location.World", playerSchmieded.getLocation().getWorld().getName());
                    getCfg().set("PlayerSchmieded." + i + ".Location.x", playerSchmieded.getLocation().getX());
                    getCfg().set("PlayerSchmieded." + i + ".Location.y", playerSchmieded.getLocation().getY());
                    getCfg().set("PlayerSchmieded." + i + ".Location.z", playerSchmieded.getLocation().getZ());
                    for(int x = 0; x < playerSchmieded.getInventory().getSize();x++) {
                        if(playerSchmieded.getInventory().getItem(x) != null && playerSchmieded.getInventory().getItem(x).getType() != Material.AIR) {
                            getCfg().set("PlayerSchmieded." + i + ".Inventory." + x, playerSchmieded.getInventory().getItem(x));
                        }
                    }
                }
            }
        } else {
            getCfg().set("PlayerBrues", 0);
        }

        try{
            getCfg().save(getFile());
        }catch (IOException e) {}
    }

    public void load() {
        if(getFile().exists()) {
            if(getCfg().isSet("PlayerSchmieded.")) {
                for(int i = 0; i< getCfg().getInt("PlayerBrues");i++) {
                    String path = "PlayerSchmieded." + i + ".";
                    int bruedtime = getCfg().getInt(path + "BruedTime");
                    Schmied schmied = Schmied.getSchmiedByName(getCfg().getString(path + "Schmied"));
                    Location location = new Location(Bukkit.getWorld(getCfg().getString(path + "Location.World")),
                            getCfg().getDouble(path + "Location.x"),
                            getCfg().getDouble(path + "Location.y"),
                            getCfg().getDouble(path + "Location.z"));
                    Inventory inv = Bukkit.createInventory(null, 27, "§eSchmied");
                    GuiBuilder.setBlackSmithGui(inv);
                    for(String slots : getCfg().getConfigurationSection(path + "Inventory.").getKeys(false)) {
                        if(slots == null) continue;
                        inv.setItem(Integer.valueOf(slots), getCfg().getItemStack(path + "Inventory." + slots));
                    }
                    PlayerSchmieded playerSchmieded = new PlayerSchmieded(getPlayer(), schmied, location, inv);
                    playerSchmieded.setGeschmolzeneZeiet(bruedtime);
                    playerSchmieded.startSmith();
                    getStoredPlayerSchmieded().add(playerSchmieded);
                }
            }

            if(getCfg().getString("Rasse") != null) {
                setRasse(Rasse.getRassebyName(getCfg().getString("Rasse")));
                getPlayer().setMaxHealth((getRasse().getMaxHealth() * 2));

                getRasse().getPotionEffects().forEach(effect -> getPlayer().addPotionEffect(effect));

            } else {
                setRasse(null);
            }

            if(getCfg().getString("Job") != null) {
                setJob(Job.getJobbyName(getCfg().getString("Job")));
            } else {
                setJob(Job.getJobbyName(Troniacraft.getInstance().jobConfig.getDefaultJob()));
            }

        } else {
            setRasse(null);
            setJob(Job.getJobbyName(Troniacraft.getInstance().jobConfig.getDefaultJob()));
        }
        storedTroniacraftPlayer.put(getPlayer(), this);
    }

    public static HashMap<Player, TroniacraftPlayer> getStoredTroniacraftPlayer() {
        return storedTroniacraftPlayer;
    }

    public void setRasse(Rasse rasse) {
        this.rasse = rasse;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public Rasse getRasse() {
        return rasse;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getGetLatestClickedLocation() {
        return getLatestClickedLocation;
    }

    public void setGetLatestClickedLocation(Location getLatestClickedLocation) {
        this.getLatestClickedLocation = getLatestClickedLocation;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getPlayerBrueds() {
        return PlayerBrueds;
    }

    public List<PlayerSchmieded> getStoredPlayerSchmieded() {
        return storedPlayerSchmieded;
    }

    public void setPlayerBrueds(int playerBrueds) {
        PlayerBrueds = playerBrueds;
    }

    public void setStoredPlayerSchmieded(List<PlayerSchmieded> storedPlayerSchmieded) {
        this.storedPlayerSchmieded = storedPlayerSchmieded;
    }
}
