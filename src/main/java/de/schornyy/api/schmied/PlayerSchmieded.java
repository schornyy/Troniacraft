package de.schornyy.api.schmied;

import de.schornyy.Troniacraft;
import de.schornyy.api.player.TroniacraftPlayer;
import de.schornyy.utils.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSchmieded {

    private Player player;
    private Schmied schmied;
    private Location location;
    private Inventory inventory;
    private int geschmolzeneZeit, taskid;

    public PlayerSchmieded(Player player, Schmied schmied, Location location, Inventory inv) {
        this.player = player;
        this.inventory = inv;
        this.schmied = schmied;
        this.location = location;
        geschmolzeneZeit = 0;
        taskid = (int) (Math.random() * 2000);
    }

    public void startSmith() {
        taskid = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Troniacraft.getInstance(), () -> {
            if(geschmolzeneZeit() >= getSchmied().getBrueTime()) {
                Bukkit.getScheduler().cancelTask(getTaskid());
                getInventory().clear();
                GuiBuilder.setBlackSmithGui(getInventory());
                getInventory().setItem(GuiBuilder.starter, getSchmied().getMidItem());
                getPlayer().sendMessage(Troniacraft.getInstance().config.Brue_finished);
            } else {
                setGeschmolzeneZeiet(geschmolzeneZeit + 1);
            }
        },20,20);

    }

  public static Inventory playerSchmiededGetByName(
      String name, TroniacraftPlayer troniacraftPlayer) {
    if (troniacraftPlayer.getStoredPlayerSchmieded() != null) {
      for (PlayerSchmieded playerSchmieded : troniacraftPlayer.getStoredPlayerSchmieded()) {
        if (playerSchmieded == null) continue;
        if (name.equalsIgnoreCase(playerSchmieded.getSchmied().getName()))
          return playerSchmieded.getInventory();
      }
    }
    return null;
    }

    public int geschmolzeneZeit() {
        return geschmolzeneZeit;
    }

    public void setGeschmolzeneZeiet(int value) {
        this.geschmolzeneZeit = value;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public Schmied getSchmied() {
        return schmied;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSchmied(Schmied schmied) {
        this.schmied = schmied;
    }
}
