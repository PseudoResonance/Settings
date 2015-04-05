package io.github.WolfLeader116.Settings;

import io.github.WolfLeader116.Settings.CMDs.AfkCMD;
import io.github.WolfLeader116.Settings.CMDs.FlyCMD;
import io.github.WolfLeader116.Settings.CMDs.GamemodeCMD;
import io.github.WolfLeader116.Settings.CMDs.SettingsCMD;
import io.github.WolfLeader116.Settings.Tab.SettingsTabCompleter;
import net.milkbowl.vault.chat.Chat;

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

public class Settings extends JavaPlugin implements Listener {

	public static Chat chat = null;

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
		return (chat != null);
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupChat();
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
		scoreboard();
		if (this.getConfig().getString("news") == null) {
			this.saveDefaultConfig();
		}
		plugin = this;
	}

	@Override
	public void onDisable() {
		plugin = null;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Config c = new Config("playerdata", Settings.plugin);
		String player = e.getPlayer().getName();
		Player eplayer = e.getPlayer();
		scoreboard();
		if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
			Bukkit.getPlayer(player).setAllowFlight(true);
			if (!(eplayer.isOnGround())) {
				eplayer.setFlying(true);
			}
		} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
			c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
			c.save();
			Bukkit.getPlayer(player).setAllowFlight(false);
		} else {
			c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
			c.save();
			Bukkit.getPlayer(player).setAllowFlight(false);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		scoreboard();
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = event.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = event.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
		}
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

	public static ItemStack createItem(Material material, int amount, short data, String name) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	public static Objective objective = scoreboard.registerNewObjective("status", "dummy");

	public static void scoreboard() {
		objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		int staff = 0;
		for (Player players : Bukkit.getServer().getOnlinePlayers()) {
			if(Settings.chat.playerInGroup("world", players, "helper") || Settings.chat.playerInGroup("world", players, "moderator") || Settings.chat.playerInGroup("world", players, "admin") || Settings.chat.playerInGroup("world", players, "headadmin") || Settings.chat.playerInGroup("world", players, "coowner") || Settings.chat.playerInGroup("world", players, "owner")) {
				staff = staff + 1;
			}
		}
		String latestnews = Settings.plugin.getConfig().getString("news");
		latestnews = latestnews.replaceAll("&", "§");
		makeScore(0, "Online Players:", Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
		makeScore(-2, "Online Staff:", Integer.toString(staff));
		makeScore(-4, "News:", latestnews);
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			all.setScoreboard(scoreboard);
		}
	}

	public static void makeScore(int number, String name, String value) {
		Score scorename = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + name);
		scorename.setScore(number);
		Score scorevalue = objective.getScore(ChatColor.RED + value);
		scorevalue.setScore(number - 1);
	}
	
	public static Settings plugin;
}
