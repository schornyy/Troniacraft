package de.schornyy;

import de.schornyy.api.Config;
import de.schornyy.api.job.Job;
import de.schornyy.api.job.JobConfig;
import de.schornyy.api.npc.NPC;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.api.rasse.Rasse;
import de.schornyy.api.schmied.Schmied;
import de.schornyy.commands.*;
import de.schornyy.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Troniacraft extends JavaPlugin {

    private static Troniacraft instance;
    public Config config;
    public JobConfig jobConfig;

    @Override
    public void onEnable() {
        loadInits();
        loadCommands();
        loadListener();
    }

    @Override
    public void onDisable() {
        disableInits();
    }

    private void loadInits(){
        instance = this;
        config = new Config();
        jobConfig = new JobConfig();
        Rasse.loadRassen();
        NPC.loadAllNPCs();
        TroniacraftPlayer.loadAllTroniacraftPlayer();
        Job.loadAllJobs();
        Schmied.loadAllBlacksmith();
    }

    private void disableInits() {
        Rasse.saveAllStoredRassen();
        NPC.saveAllStoredNPCs();
        TroniacraftPlayer.saveAllTroniacraftPlayer();
        Job.saveAllJobs();
        Schmied.saveAllBlacksmith();
    }

    private void loadCommands() {
        getCommand("rasse").setExecutor(new RassenCommand());
        getCommand("npc").setExecutor(new NPCCommand());
        getCommand("job").setExecutor(new JobCommand());
        getCommand("schmied").setExecutor(new SchmiedCommand());
        getCommand("item").setExecutor(new ItemCommand());

        getCommand("rasse").setTabCompleter(new RassenCommand());
        getCommand("npc").setTabCompleter(new NPCCommand());
        getCommand("job").setTabCompleter(new JobCommand());
        getCommand("schmied").setTabCompleter(new SchmiedCommand());
        getCommand("item").setTabCompleter(new ItemCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
        pluginManager.registerEvents(new SignChangeListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerCraftListener(), this);
        pluginManager.registerEvents(new PlayerInteractInventoryListener(), this);
        pluginManager.registerEvents(new AnvilPreParedListener(), this);
    }

    public static Troniacraft getInstance() {
        return instance;
    }
}
