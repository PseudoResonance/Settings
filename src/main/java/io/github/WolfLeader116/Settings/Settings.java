package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.AfkCMD;
import io.github.WolfLeader116.Settings.CMDs.FlyCMD;
import io.github.WolfLeader116.Settings.CMDs.GamemodeCMD;
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
import org.bukkit.event.player.PlayerMoveEvent;
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

	public void onEnable()
	{
		getCommand("settings").setExecutor(new SettingsCMD());
		getCommand("gamemode").setExecutor(new GamemodeCMD());
		getCommand("gm").setExecutor(new GamemodeCMD());
		getCommand("creative").setExecutor(new GamemodeCMD());
		getCommand("survival").setExecutor(new GamemodeCMD());
		getCommand("adventure").setExecutor(new GamemodeCMD());
		getCommand("spectator").setExecutor(new GamemodeCMD());
		getCommand("fly").setExecutor(new FlyCMD());
		getCommand("afk").setExecutor(new AfkCMD());
		getCommand("settings").setTabCompleter(new SettingsTabCompleter());
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("status", "dummy");
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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Config c = new Config("playerdata", Settings.plugin);
		String player = e.getPlayer().getName();
		Player eplayer = e.getPlayer();
		if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId()))
		{
			Bukkit.getPlayer(player).setAllowFlight(true);
			if (!(eplayer.isOnGround())) {
				eplayer.setFlying(true);
			}
		}
		else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId()))
		{
			c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
			c.save();
			Bukkit.getPlayer(player).setAllowFlight(false);
		} else {
			c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
			c.save();
			Bukkit.getPlayer(player).setAllowFlight(false);
		}
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("status", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
		eplayer.setScoreboard(board);
		onlineplayers();
		/*onlinestaff();*/
	}

	public void onlineplayers() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("status", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
		Score players = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Players:");
		players.setScore(Bukkit.getServer().getOnlinePlayers().size());
	}

	/*public void onlinestaff() {
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = manager.getNewScoreboard();
	Objective objective = board.registerNewObjective("status", "dummy");
	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
	Score staff = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Staff:");
	staff.setScore(Bukkit.getServer().getOnlinePlayers().size());
  }*/

	public static Inventory myInventory = Bukkit.createInventory(null, 9, ChatColor.DARK_BLUE + "Player Settings");

	static {
		ItemStack feather = new ItemStack(Material.FEATHER);
		ItemMeta meta = feather.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Toggle Flight Mode");
		feather.setItemMeta(meta);
		myInventory.setItem(4, feather);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack feather = new ItemStack(Material.FEATHER);
		ItemMeta meta = feather.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Toggle Flight Mode");
		feather.setItemMeta(meta);
		Player player = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		if (inventory.getName().equals(myInventory.getName())) {
			if (clicked.getType() == Material.FEATHER) {
				player.chat("/settings set fly toggle");
				player.closeInventory();
			}
		}
	}

	public void feather() {
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = e.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
		}
	}
}
