package de.schornyy.api.job;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job {

    private File file;
    private FileConfiguration cfg;

    private String name;
    private List<Material> storedBlacklistofUseableBlocks, storedBlacklistofUnusableCraftings;
    private List<ItemStack> storedBlacklistofcustomCrafts;

    public static ArrayList<Job> storedJobs = new ArrayList<>();

    public Job(String name) {
        file = new File("plugins/Troniacraft/Jobs/" + name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        this.name = name;
        storedBlacklistofcustomCrafts = new ArrayList<>();
        storedBlacklistofUnusableCraftings = new ArrayList<>();
        storedBlacklistofUseableBlocks = new ArrayList<>();
    }

    public void createDefaults() {
        if(!getFile().exists()) {

            getCfg().set("Name", getName());

            try{
                getCfg().save(getFile());
            } catch (IOException e){}

        }
    }

    public static void loadAllJobs() {
        File file = new File("plugins/Troniacraft/Jobs/");

        for(File files : file.listFiles()) {
            if(files != null) {
                if(!files.getName().contains("JobConfig")) {
                    Job job = new Job(files.getName().replaceAll(".yml", ""));
                    job.load();
                }
            }
        }
    }

    public static void saveAllJobs() {
        for(Job job : getStoredJobs()) {
            if(job != null) {
                job.save();
            }
        }
    }

    public void load() {
        setName(getCfg().getString("Name"));

        if(getCfg().isSet("UnusableBlocks")) {
            for(String types : getCfg().getStringList("UnusableBlocks")) {
                if(types != null) {
                    getStoredBlacklistofUseableBlocks().add(Material.getMaterial(types));
                }
            }
        }

        if(getCfg().isSet("UnusableCraftings")) {
            for(String types : getCfg().getStringList("UnusableCraftings")) {
                if(types != null) {
                    getStoredBlacklistofUnusableCraftings().add(Material.getMaterial(types));
                }
            }
        }

        if(getCfg().isSet("UnusablecustomCraftings")) {
            for(String id : getCfg().getConfigurationSection("UnusablecustomCraftings.").getKeys(false)) {
                if(id != null) {
                    getStoredBlacklistofcustomCrafts().add(getCfg().getItemStack("UnusablecustomCraftings." + id));
                }
            }
        }
        getStoredJobs().add(this);
    }

    public void save() {
        getCfg().set("Name", getName());
        List<String> materialTypes = new ArrayList<>();
        List<String> materialTypes2 = new ArrayList<>();

        for(Material material : getStoredBlacklistofUnusableCraftings()) {
            if(material != null) {
                materialTypes.add(material.name());
            }
        }

        for(Material material : getStoredBlacklistofUseableBlocks()) {
            if(material != null) {
                materialTypes2.add(material.name());
            }
        }

        getCfg().set("UnusableCraftings", materialTypes);
        getCfg().set("UnusableBlocks", materialTypes2);

        for(int i = 0; i < getStoredBlacklistofcustomCrafts().size();i++) {
            getCfg().set("UnusablecustomCraftings." + i, getStoredBlacklistofcustomCrafts().get(i));
        }

        try{
            getCfg().save(getFile());
        }catch (IOException e){}

    }

    public void delete() {
        getFile().delete();
        getStoredJobs().remove(this);
    }

    public static Job getJobbyName(String name) {
        for(Job jobs : getStoredJobs()) {
            if(jobs != null) {
                if(jobs.getName().equalsIgnoreCase(name)) return jobs;
            }
        }
        return null;
     }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Job> getStoredJobs() {
        return storedJobs;
    }

    public List<ItemStack> getStoredBlacklistofcustomCrafts() {
        return storedBlacklistofcustomCrafts;
    }

    public List<Material> getStoredBlacklistofUnusableCraftings() {
        return storedBlacklistofUnusableCraftings;
    }

    public List<Material> getStoredBlacklistofUseableBlocks() {
        return storedBlacklistofUseableBlocks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStoredBlacklistofcustomCrafts(List<ItemStack> storedBlacklistofcustomCrafts) {
        this.storedBlacklistofcustomCrafts = storedBlacklistofcustomCrafts;
    }

    public void setStoredBlacklistofUnusableCraftings(List<Material> storedBlacklistofUnusableCraftings) {
        this.storedBlacklistofUnusableCraftings = storedBlacklistofUnusableCraftings;
    }

    public void setStoredBlacklistofUseableBlocks(List<Material> storedBlacklistofUseableBlocks) {
        this.storedBlacklistofUseableBlocks = storedBlacklistofUseableBlocks;
    }

    public static void setStoredJobs(ArrayList<Job> storedJobs) {
        Job.storedJobs = storedJobs;
    }
}
