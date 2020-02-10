package de.schornyy.api.job;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobConfig {

    private File file;
    private FileConfiguration cfg;

    private List<String> storedSignLayout;
    private String defaultJob;

    public JobConfig() {
        file = new File("plugins/Troniacraft/Jobs/JobConfig.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        storedSignLayout = new ArrayList<>();
        defaultJob = null;
        createDefault();
        load();
        Job job = new Job("Default");
        job.createDefaults();
    }

    private void createDefault() {
        if(!getFile().exists()) {

            getCfg().set("DefaultJob", "Default");

            getCfg().set("SignLayout.1", "&f[&6Job&f]");
            getCfg().set("SignLayout.2", "&e%job%");

            try{
                getCfg().save(getFile());
            } catch (IOException e){}

        }
    }

    public void load() {
        setDefaultJob(getCfg().getString("DefaultJob"));

        if(getCfg().isSet("SignLayout.")) {
            for(String line : getCfg().getConfigurationSection("SignLayout.").getKeys(false)) {
                if(line != null) {
                    getStoredSignLayout().add(getCfg().getString("SignLayout." + line).replaceAll("&", "§"));
                }
            }
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public List<String> getStoredSignLayout() {
        return storedSignLayout;
    }

    public String getDefaultJob() {
        return defaultJob;
    }

    public void setDefaultJob(String defaultJob) {
        this.defaultJob = defaultJob;
    }

    public void setStoredSignLayout(List<String> storedSignLayout) {
        this.storedSignLayout = storedSignLayout;
    }

}
