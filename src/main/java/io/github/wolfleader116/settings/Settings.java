package io.github.wolfleader116.settings;

import io.github.wolfleader116.settings.commands.AfkC;
import io.github.wolfleader116.settings.commands.EffectsC;
import io.github.wolfleader116.settings.commands.FlyC;
import io.github.wolfleader116.settings.commands.GamemodeC;
import io.github.wolfleader116.settings.commands.MusicC;
import io.github.wolfleader116.settings.commands.ScoreboardC;
import io.github.wolfleader116.settings.commands.SettingsC;
import io.github.wolfleader116.settings.tabcompleters.GamemodeTC;
import io.github.wolfleader116.settings.tabcompleters.SettingsTC;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.io.File;
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

public class Settings extends JavaPlugin implements Listener {
	
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
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig();
		if (this.getConfig().getInt("Version") != 4) {
			File conf = new File(this.getDataFolder(), "config.yml");
			final String news = this.getConfig().getString("News");
			final String scoreboard = this.getConfig().getString("Scoreboard");
			final String servername = this.getConfig().getString("ServerName");
			conf.delete();
			this.saveDefaultConfig();
			this.saveConfig();
			this.reloadConfig();
			this.getConfig().set("News", news);
			this.getConfig().set("Scoreboard", scoreboard);
			this.getConfig().set("ServerName", servername);
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		setupChat();
		setupEconomy();
		initializeCommands();
		initializeTabCompleters();
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	private void initializeCommands() {
		getCommand("settings").setExecutor(new SettingsC());
		getCommand("gamemode").setExecutor(new GamemodeC());
		getCommand("survival").setExecutor(new GamemodeC());
		getCommand("creative").setExecutor(new GamemodeC());
		getCommand("adventure").setExecutor(new GamemodeC());
		getCommand("spectator").setExecutor(new GamemodeC());
		getCommand("fly").setExecutor(new FlyC());
		getCommand("afk").setExecutor(new AfkC());
		getCommand("music").setExecutor(new MusicC());
		getCommand("effects").setExecutor(new EffectsC());
		getCommand("scoreboard").setExecutor(new ScoreboardC());
	}

	private void initializeTabCompleters() {
		getCommand("settings").setTabCompleter(new SettingsTC());
		getCommand("gamemode").setTabCompleter(new GamemodeTC());
	}
	
	public static String songName = "None";
	
	public static void setCurrentSong(String name) {
		songName = name;
		Scoreboard.scoreboard();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Config c = new Config("playerdata", Settings.plugin);
		String player = e.getPlayer().getName();
		Player eplayer = e.getPlayer();
		Scoreboard.scoreboard();
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
		this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				Scoreboard.scoreboard();
			}
		}, 20);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = event.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			WolfAPI.message("You're no longer afk", player, "Settings");
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Config c = new Config("playerdata", Settings.plugin);
		Player player = event.getPlayer();
		if (c.getConfig().getBoolean("afk." + player.getUniqueId().toString())) {
			c.getConfig().set("afk." + player.getUniqueId().toString(), false);
			c.save();
			WolfAPI.message("You're no longer afk", player, "Settings");
		}
	}

	public static Inventory createInventory(short slots, String name) {
		Inventory invent = Bukkit.createInventory(null, slots, name);
		return invent;
	}
	
	public static Inventory myInventory = createInventory((short) 9, ChatColor.DARK_BLUE + "Player Settings");

	static {
		if ((Bukkit.getPluginManager().getPlugin("Utils") != null) && (Bukkit.getPluginManager().getPlugin("PlayerEffects") != null)) {
			myInventory.setItem(0, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(2, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(4, createItem(Material.RECORD_12, 1, (short) 0, ChatColor.BLUE + "Toggle Music Mode"));
			myInventory.setItem(6, createItem(Material.NETHER_STAR, 1, (short) 0, ChatColor.BLUE + "Toggle Effects Mode"));
			myInventory.setItem(8, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else if (Bukkit.getPluginManager().getPlugin("Utils") != null) {
			myInventory.setItem(1, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(3, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(5, createItem(Material.RECORD_12, 1, (short) 0, ChatColor.BLUE + "Toggle Music Mode"));
			myInventory.setItem(7, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else if (Bukkit.getPluginManager().getPlugin("PlayerEffects") != null) {
			myInventory.setItem(1, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(3, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(5, createItem(Material.NETHER_STAR, 1, (short) 0, ChatColor.BLUE + "Toggle Effects Mode"));
			myInventory.setItem(7, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else {
			myInventory.setItem(2, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(4, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(6, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if ((Bukkit.getPluginManager().getPlugin("Utils") != null) && (Bukkit.getPluginManager().getPlugin("PlayerEffects") != null)) {
			myInventory.setItem(0, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(2, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(4, createItem(Material.RECORD_12, 1, (short) 0, ChatColor.BLUE + "Toggle Music Mode"));
			myInventory.setItem(6, createItem(Material.NETHER_STAR, 1, (short) 0, ChatColor.BLUE + "Toggle Effects Mode"));
			myInventory.setItem(8, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else if (Bukkit.getPluginManager().getPlugin("Utils") != null) {
			myInventory.setItem(1, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(3, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(5, createItem(Material.RECORD_12, 1, (short) 0, ChatColor.BLUE + "Toggle Music Mode"));
			myInventory.setItem(7, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else if (Bukkit.getPluginManager().getPlugin("PlayerEffects") != null) {
			myInventory.setItem(1, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(3, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(5, createItem(Material.NETHER_STAR, 1, (short) 0, ChatColor.BLUE + "Toggle Effects Mode"));
			myInventory.setItem(7, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		} else {
			myInventory.setItem(2, createItem(Material.FEATHER, 1, (short) 0, ChatColor.BLUE + "Toggle Flight Mode"));
			myInventory.setItem(4, createItem(Material.BARRIER, 1, (short) 0, ChatColor.BLUE + "Toggle AFK Mode"));
			myInventory.setItem(6, createItem(Material.SIGN, 1, (short) 0, ChatColor.BLUE + "Toggle Scoreboard Visibility"));
		}
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		if (inventory.getName().equals(myInventory.getName())) {
			if (clicked.getType() == Material.FEATHER) {
				player.chat("/settings set fly toggle");
				player.closeInventory();
			} else if (clicked.getType() == Material.BARRIER) {
				player.chat("/settings set afk toggle");
				player.closeInventory();
			} else if (clicked.getType() == Material.RECORD_12) {
				player.chat("/settings set music toggle");
				player.closeInventory();
			} else if (clicked.getType() == Material.NETHER_STAR) {
				player.chat("/settings set effects toggle");
				player.closeInventory();
			} else if (clicked.getType() == Material.SIGN) {
				player.chat("/settings set scoreboard toggle");
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

}
