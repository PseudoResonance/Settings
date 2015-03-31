package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.SettingsCMD;
import io.github.WolfLeader116.Settings.Tab.SettingsTabCompleter;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Settings
  extends JavaPlugin
  implements Listener
{
  public static Settings plugin;
  
  public void onEnable()
  {
    getCommand("settings").setExecutor(new SettingsCMD());
    getCommand("settings").setTabCompleter(new SettingsTabCompleter());
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    plugin = this;
  }
  
  public void onDisable()
  {
    plugin = null;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    Config c = new Config("playerdata", plugin);
    String player = e.getPlayer().getName();
    if (c.getConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) != null)
    {
      if (c.getConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) == "'true'") {
        Bukkit.getPlayer(player).setAllowFlight(true);
      }
    }
    else if (c.getConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) == null)
    {
      c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), "'false'");
      c.save();
    }
  }
}
