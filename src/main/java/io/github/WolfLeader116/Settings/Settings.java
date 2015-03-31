package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.SettingsCMD;
import io.github.WolfLeader116.Settings.Tab.SettingsTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Settings
  extends JavaPlugin
  implements Listener
{
  public static Settings plugin;
  
  public Config c = new Config(this);
  
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
    String player = e.getPlayer().getName();
    if (c.getCustomConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) != null)
    {
      if (c.getCustomConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) == "'true'") {
        Bukkit.getPlayer(player).setAllowFlight(true);
      }
    }
    else if (c.getCustomConfig().getString("fly." + Bukkit.getPlayer(player).getUniqueId().toString()) == null)
    {
      c.getCustomConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), "'false'");
      c.saveCustomConfig();
    }
  }
}
