package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.AfkCMD;
import io.github.WolfLeader116.Settings.CMDs.FlyCMD;
import io.github.WolfLeader116.Settings.CMDs.GamemodeCMD;
import io.github.WolfLeader116.Settings.CMDs.SettingsCMD;
import io.github.WolfLeader116.Settings.Tab.SettingsTabCompleter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Settings
extends JavaPlugin
implements Listener
{
	public static Settings plugin;

	public static Chat chat = null;
	public static Economy economy = null;

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
		return (chat != null);
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}

	@Override
	public void onEnable()
	{
		setupChat();
		setupEconomy();
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
		initializeScoreboard();
		if (this.getConfig().getString("news") == null) {
			this.saveDefaultConfig();
		}
		plugin = this;
	}

	@Override
	public void onDisable()
	{
		plugin = null;
	}
	
	public static Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	public static Objective objective = scoreboard.registerNewObjective("status", "dummy");

	public void initializeScoreboard() {
		objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
		scoreboard();
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
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboard();
		eplayer.setScoreboard(scoreboard);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		scoreboard();
	}

	public static Inventory createInventory(short slots, String name) {
		Inventory invent = Bukkit.createInventory(null, slots, name);
		return invent;
	}
	
	public static Inventory myInventory = createInventory((short) 9, ChatColor.DARK_BLUE + "Player Settings");

	static {
		myInventory.setItem(3, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
		myInventory.setItem(5, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		myInventory.setItem(3, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
		myInventory.setItem(5, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		if (inventory.getName().equals(myInventory.getName())) {
			if (clicked.getType() == Material.FEATHER) {
				player.chat("/settings set fly toggle");
				player.closeInventory();
			} else if (clicked.getType() == Material.BARRIER) {
				player.chat("/settings set afk toggle");
				player.closeInventory();
			}
		}
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
		scoreboard();
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = e.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
		}
		scoreboard();
	}

	public void scoreboard() {
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			int staff = 0;
			for (Player players : Bukkit.getServer().getOnlinePlayers()) {
				if(chat.playerInGroup("world", players, "helper") || chat.playerInGroup("world", players, "moderator") || chat.playerInGroup("world", players, "admin") || chat.playerInGroup("world", players, "headadmin") || chat.playerInGroup("world", players, "coowner") || chat.playerInGroup("world", players, "owner")) {
					staff = staff + 1;
				}
			}
			String latestnews = this.getConfig().getString("news");
			latestnews = latestnews.replaceAll("&", "§");
			makeScore(0, "Online Players:", Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
			makeBlank(-2);
			makeScore(-3, "Online Staff:", Integer.toString(staff));
			makeBlank(-5);
			makeScore(-6, "Money:", Double.toString(economy.getBalance(all)));
			makeBlank(-8);
			makeScore(-9, "News:", latestnews);
		}
	}
	
	public void makeBlank(int number) {
		Score scorename = objective.getScore("");
		scorename.setScore(number);
	}
	
	public void makeScore(int number, String name, String value) {
		Score scorename = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + name);
		scorename.setScore(number);
		Score scorevalue = objective.getScore(ChatColor.RED + value);
		scorevalue.setScore(number - 1);
	}

	public static ItemStack createItem(Material material, int amount, short data, String name) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

}
