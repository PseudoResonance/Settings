package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.SettingsCMD;
import io.github.WolfLeader116.Settings.Tab.SettingsTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Settings
  extends JavaPlugin
  implements Listener
{
  public static Settings plugin;
  
  public Config c = new Config(this);
  
  ScoreboardManager manager = Bukkit.getScoreboardManager();
  Scoreboard board = manager.getNewScoreboard();
  Objective objective = board.registerNewObjective("status", "dummy");
  
  public void onEnable()
  {
    getCommand("settings").setExecutor(new SettingsCMD());
    getCommand("settings").setTabCompleter(new SettingsTabCompleter());
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
    onlineplayers();
    /*onlinestaff();*/
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
    Player eplayer = e.getPlayer();
    eplayer.setScoreboard(board);
    onlineplayers();
    /*onlinestaff();*/
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

  public void onlineplayers() {
	Score players = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Players:");
	players.setScore(Bukkit.getServer().getOnlinePlayers().size());
  }

  /*public void onlinestaff() {
	Score staff = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Staff:");
	staff.setScore(Bukkit.getServer().getOnlinePlayers().size());
  }*/
  
  public static Inventory myInventory = Bukkit.createInventory(null, 9, "My custom Inventory!");
  
  static {
	ItemStack feather = new ItemStack(Material.FEATHER);
	ItemMeta meta = feather.getItemMeta();
	meta.setDisplayName(ChatColor.BLUE + "Toggle Flight Mode");
	feather.setItemMeta(meta);
    myInventory.setItem(4, feather);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    ItemStack feather = new ItemStack(Material.FEATHER);
    ItemMeta meta = feather.getItemMeta();
	meta.setDisplayName(ChatColor.BLUE + "Toggle Flight Mode");
	feather.setItemMeta(meta);
	Player player = (Player) event.getWhoClicked();
	ItemStack clicked = event.getCurrentItem();
	Inventory inventory = event.getInventory();
    if (inventory.getName().equals(myInventory.getName())) {
      if (clicked.getType() == Material.FEATHER) {
    	event.setCancelled(true);
    	player.closeInventory();
    	player.getInventory().setItem(4, feather);
      }
    }
  }
  
  public void feather() {
  }
}
