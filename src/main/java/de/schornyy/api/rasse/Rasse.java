package de.schornyy.api.rasse;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rasse {

    private File file;
    private FileConfiguration cfg;

    private String name;
    private List<PotionEffect> potionEffects;
    private int maxHealth;

    private static List<Rasse> storedRassen = new ArrayList<>();

    public Rasse(String name) {
        this.name = name;
        potionEffects = new ArrayList<>();
        maxHealth = 10;
        file = new File("plugins/Troniacraft/Rassen/" + name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        loadDefaults();
    }

    public void delete() {
        getFile().delete();
        storedRassen.remove(this);
    }

    public void save() {
        getCfg().set("Name", getName());
        getCfg().set("MaxHealth", getMaxHealth());
        int i = 0;
        if(getPotionEffects().size() != 0) {
            for(PotionEffect potionEffect : getPotionEffects()) {
                if(potionEffect != null) {
                    getCfg().set("Effect." + i + ".Type", potionEffect.getType().getName());
                    getCfg().set("Effect." + i + ".Level", potionEffect.getAmplifier());
                }
            }
        }
        saveConfig();
    }

    public static void saveAllStoredRassen() {
        if(getStoredRassen() != null || !getStoredRassen().isEmpty()) {
            Iterator<Rasse> r = getStoredRassen().iterator();
            r.forEachRemaining(rasse -> rasse.save());
        }
    }

    public void load() {
        this.name = getCfg().getString("Name");
        this.maxHealth = getCfg().getInt("MaxHealth");
        if(getCfg().isSet("Effect.")) {
            for(String i : getCfg().getConfigurationSection("Effect.").getKeys(false)) {
                if(i != null) {
                    getPotionEffects().add(new PotionEffect(PotionEffectType.getByName(getCfg().getString("Effect." + i + ".Type")), Integer.MAX_VALUE, getCfg().getInt("Effect." + i + ".Level")));
                }
            }
        }
        storedRassen.add(this);
    }

    private void loadDefaults() {
        if(new File("plugins/Troniacraft/Rassen/").listFiles() == null) {
            getCfg().set("Name", "Default");
            getCfg().set("MaxHealth", 10);

            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));

            int i = 0;
            for(PotionEffect potionEffect : potionEffects) {
                if(potionEffect != null) {
                    getCfg().set("Effect." + i + ".Type", potionEffect.getType().getName());
                    getCfg().set("Effect." + i + ".Level", potionEffect.getAmplifier());
                    i++;
                }
            }
            saveConfig();
        }
    }

    public static void loadRassen() {
        File file = new File("plugins/Troniacraft/Rassen/");

        if(file.listFiles() != null) {
            for(File files : file.listFiles()) {
                if(files != null) {
                    Rasse rasse = new Rasse(files.getName().replaceAll(".yml", ""));
                    rasse.load();
                }
            }
        } else {
            Rasse rasse = new Rasse("Default");
            loadRassen();
        }
    }

    public static Rasse getRassebyName(String name) {
        for(Rasse rasse : getStoredRassen()) {
            if(rasse != null) {
                if(rasse.getName().equalsIgnoreCase(name)) {
                    return rasse;
                }
            }
        }

        return null;
    }


    public boolean exists() {
        return getFile().exists();
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public void saveConfig() {
        try{
            getCfg().save(getFile());
        } catch (IOException e) {}
    }

    public static List<Rasse> getStoredRassen() {
        return storedRassen;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
