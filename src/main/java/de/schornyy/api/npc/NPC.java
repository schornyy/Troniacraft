package de.schornyy.api.npc;

import de.schornyy.api.rasse.Rasse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {

    public File file;
    public FileConfiguration cfg;

    private String uuid, name, rasse;
    private EntityType entityType;
    private LivingEntity npc;
    private int id;
    private Location spawnLocation;

    private static List<NPC> storedNPCs = new ArrayList<>();

    public NPC(String uuid) {
        this.uuid = uuid;
        spawnLocation = null;
        setRasse(null);
        file = new File("plugins/Troniacraft/NPCs/" + uuid + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        id = (storedNPCs.size() + 1);
    }

    public static void loadAllNPCs() {
        File file = new File("plugins/Troniacraft/NPCs/");

        if(file.listFiles() != null) {
            for(File files : file.listFiles()) {
                if(files == null) return;

                NPC npc = new NPC(files.getName().replace(".yml", ""));
                npc.load();
                npc.spawn();
            }
        }
    }

    public void create(Location location, String Name) {
        setSpawnLocation(location);
        setEntityType(EntityType.VILLAGER);
        setName(Name.replaceAll("&","§"));
        save();
        spawn();
        getStoredNPCs().add(this);
    }

    public void  changeSpawnpoint(Location location) {
        setSpawnLocation(location);
        despawn();
        spawn();
        save();
    }

    public void changeEntityType(EntityType entityType) {
        setEntityType(entityType);
        despawn();
        spawn();
        save();
    }

    public void changeName(String name) {
        setName(name.replaceAll("&", "§"));
        getNpc().setCustomName(getName());
        save();
    }

    public void delete() {
        getNpc().remove();
        getFile().delete();
        getStoredNPCs().remove(this);
    }

    public void despawn() {
        getNpc().remove();
        setNpc(null);
    }

    public void spawn() {
        Entity entity = getSpawnLocation().getWorld().spawnEntity(getSpawnLocation(), getEntityType());
        entity.setCustomName(getName());
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setAI(false);
        setNpc(livingEntity);
    }

    public static void saveAllStoredNPCs() {
    for (NPC npc : getStoredNPCs()) {
      npc.save();
      npc.despawn();
      getStoredNPCs().remove(npc);
        }
    }

    public void load() {
        setName(getCfg().getString("Name").replaceAll("&", "§"));
        setEntityType(EntityType.fromName(getCfg().getString("EntityType")));
        setId(getCfg().getInt("ID"));
        if(getCfg().getString("Rasse") != null) {
            setRasse(getCfg().getString("Rasse"));
        }
        setSpawnLocation(new Location(Bukkit.getWorld(getCfg().getString("Spawn.World")),
            getCfg().getDouble("Spawn.x"),
            getCfg().getDouble("Spawn.y"),
            getCfg().getDouble("Spawn.z")));
        storedNPCs.add(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void save() {
        getCfg().set("Name", getName().replaceAll("§", "&"));
        if(getRasse() != null) {
            getCfg().set("Rasse", getRasse());
        }
        getCfg().set("EntityType", getEntityType().getName());
        getCfg().set("ID", getId());

        getCfg().set("Spawn.World", getSpawnLocation().getWorld().getName());
        getCfg().set("Spawn.x", getSpawnLocation().getX());
        getCfg().set("Spawn.y", getSpawnLocation().getY());
        getCfg().set("Spawn.z", getSpawnLocation().getZ());

        saveConfig();
    }

    public static NPC getNPCbyID(int id) {
        for(NPC npc : getStoredNPCs()) {
            if(npc == null) continue;
            if(npc.getId() == id) return npc;
        }
        return null;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public  void saveConfig() {
        try{
            getCfg().save(getFile());
        } catch (IOException e){}
    }

    public int getId() {
        return id;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public LivingEntity getNpc() {
        return npc;
    }

    public void setNpc(LivingEntity npc) {
        this.npc = npc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRasse(String rasse) {
        this.rasse = rasse;
    }

    public Rasse getRasenOBJ() {
        return Rasse.getRassebyName(getRasse());
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getUuid() {
        return uuid;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getRasse() {
        return rasse;
    }

    public static List<NPC> getStoredNPCs() {
        return storedNPCs;
    }
}
