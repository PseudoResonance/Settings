package io.github.WolfLeader116.Settings;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config
{
  private final Plugin PLUGIN;
  private final String FILENAME;
  private final File FOLDER;
  private FileConfiguration config;
  private File configFile;
  
  public Config(String filename, Settings instance)
  {
    if (!filename.endsWith(".yml")) {
      filename = filename + ".yml";
    }
    this.FILENAME = filename;
    this.PLUGIN = instance;
    this.FOLDER = this.PLUGIN.getDataFolder();
    this.config = null;
    this.configFile = null;
    reload();
  }
  
  public Config(File folder, String filename, Settings instance)
  {
    if (!filename.endsWith(".yml")) {
      filename = filename + ".yml";
    }
    this.FILENAME = filename;
    this.PLUGIN = instance;
    this.FOLDER = folder;
    this.config = null;
    this.configFile = null;
    reload();
  }
  
  public FileConfiguration getConfig()
  {
    if (this.config == null) {
      reload();
    }
    return this.config;
  }
  
  public void reload()
  {
    if (!this.FOLDER.exists()) {
      try
      {
        if (this.FOLDER.mkdir()) {
          this.PLUGIN.getLogger().log(Level.INFO, 
            "Folder " + this.FOLDER.getName() + " created.");
        } else {
          this.PLUGIN.getLogger().log(
            Level.WARNING, 
            "Unable to create folder " + this.FOLDER.getName() + 
            ".");
        }
      }
      catch (Exception localException) {}
    }
    this.configFile = new File(this.FOLDER, this.FILENAME);
    if (!this.configFile.exists()) {
      try
      {
        this.configFile.createNewFile();
      }
      catch (IOException localIOException) {}
    }
    this.config = YamlConfiguration.loadConfiguration(this.configFile);
  }
  
  public void saveDefaultConfig()
  {
    if (this.configFile == null) {
      this.configFile = new File(this.PLUGIN.getDataFolder(), this.FILENAME);
    }
    if (!this.configFile.exists()) {
      this.PLUGIN.saveResource(this.FILENAME, false);
    }
  }
  
  public void save()
  {
    if ((this.config == null) || (this.configFile == null)) {
      return;
    }
    try
    {
      getConfig().save(this.configFile);
    }
    catch (IOException ex)
    {
      this.PLUGIN.getLogger().log(Level.WARNING, 
        "Could not save config to " + this.configFile.getName(), ex);
    }
  }
  
  public void set(String path, Object o)
  {
    getConfig().set(path, o);
  }
  
  public void setLocation(String path, Location l)
  {
    getConfig().set(path + ".w", l.getWorld().getName());
    getConfig().set(path + ".x", Double.valueOf(l.getX()));
    getConfig().set(path + ".y", Double.valueOf(l.getY()));
    getConfig().set(path + ".z", Double.valueOf(l.getZ()));
    getConfig().set(path + ".yaw", Float.valueOf(l.getYaw()));
    getConfig().set(path + ".pitch", Float.valueOf(l.getPitch()));
    save();
  }
  
  public Location getLocation(String path)
  {
    Location l = new Location(Bukkit.getWorld(getConfig().getString(
      path + ".w")), getConfig().getDouble(path + ".x"), getConfig()
      .getDouble(path + ".y"), getConfig().getDouble(path + ".z"), 
      Float.parseFloat(getConfig().getDouble(new StringBuilder(String.valueOf(path)).append(".yaw").toString())), 
      Float.parseFloat(getConfig().getDouble(new StringBuilder(String.valueOf(path)).append(".pitch").toString())));
    return l;
  }
}
