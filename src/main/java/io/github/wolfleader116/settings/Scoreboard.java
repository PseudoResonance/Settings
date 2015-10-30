package io.github.wolfleader116.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import io.github.wolfleader116.utils.Utils;

public class Scoreboard {

	private static Config c = new Config("playerdata", Settings.plugin);

	public static void scoreboard() {
		for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
			org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
			Objective objective = scoreboard.registerNewObjective("status", "dummy");
			String scoreboardname = Settings.plugin.getConfig().getString("Scoreboard");
			scoreboardname = scoreboardname.replaceAll("&", "§");
			objective.setDisplayName(scoreboardname);
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			int staff = 0;
			for (Player players : Bukkit.getServer().getOnlinePlayers()) {
				if(Settings.chat.playerInGroup("world", players, "helper") || Settings.chat.playerInGroup("world", players, "moderator") || Settings.chat.playerInGroup("world", players, "admin") || Settings.chat.playerInGroup("world", players, "headadmin") || Settings.chat.playerInGroup("world", players, "coowner") || Settings.chat.playerInGroup("world", players, "owner")) {
					staff = staff + 1;
				}
			}
			String latestnews = Settings.plugin.getConfig().getString("News");
			latestnews = latestnews.replaceAll("&", "§");
			Score playersname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Players:");
			playersname.setScore(0);
			Score playersvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
			playersvalue.setScore(-1);
			Score staffname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Staff:");
			staffname.setScore(-2);
			Score staffvalue = objective.getScore(ChatColor.RED + Integer.toString(staff));
			staffvalue.setScore(-3);
			Score moneyname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Money:");
			moneyname.setScore(-4);
			Score moneyvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Settings.economy.getBalance(online));
			moneyvalue.setScore(-5);
			Score newsname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "News:");
			newsname.setScore(-6);
			Score newsvalue = objective.getScore(ChatColor.RED + latestnews);
			newsvalue.setScore(-7);
			if (Utils.isMusic()) {
				Score songname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Current Song:");
				songname.setScore(-8);
				Score songvalue = objective.getScore(ChatColor.RED + Settings.songName);
				songvalue.setScore(-9);
			}
			online.setScoreboard(scoreboard);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Settings.plugin, new Runnable() {
				public void run() {
					if (c.getConfig().getBoolean("scoreboard." + online.getUniqueId().toString()) != true) {
						online.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					}
				}
			}, 600);
		}
	}

	public static void scoreboard(final String playername) {
		final Player player = Bukkit.getServer().getPlayer(playername);
		org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("status", "dummy");
		String scoreboardname = Settings.plugin.getConfig().getString("Scoreboard");
		scoreboardname = scoreboardname.replaceAll("&", "§");
		objective.setDisplayName(scoreboardname);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		int staff = 0;
		for (Player players : Bukkit.getServer().getOnlinePlayers()) {
			if(Settings.chat.playerInGroup("world", players, "helper") || Settings.chat.playerInGroup("world", players, "moderator") || Settings.chat.playerInGroup("world", players, "admin") || Settings.chat.playerInGroup("world", players, "headadmin") || Settings.chat.playerInGroup("world", players, "coowner") || Settings.chat.playerInGroup("world", players, "owner")) {
				staff = staff + 1;
			}
		}
		String latestnews = Settings.plugin.getConfig().getString("News");
		latestnews = latestnews.replaceAll("&", "§");
		Score playersname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Players:");
		playersname.setScore(0);
		Score playersvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
		playersvalue.setScore(-1);
		Score staffname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Staff:");
		staffname.setScore(-2);
		Score staffvalue = objective.getScore(ChatColor.RED + Integer.toString(staff));
		staffvalue.setScore(-3);
		Score moneyname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Money:");
		moneyname.setScore(-4);
		Score moneyvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Settings.economy.getBalance(player));
		moneyvalue.setScore(-5);
		Score newsname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "News:");
		newsname.setScore(-6);
		Score newsvalue = objective.getScore(ChatColor.RED + latestnews);
		newsvalue.setScore(-7);
		if (Utils.isMusic()) {
			Score songname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Current Song:");
			songname.setScore(-8);
			Score songvalue = objective.getScore(ChatColor.RED + Settings.songName);
			songvalue.setScore(-9);
		}
		player.setScoreboard(scoreboard);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Settings.plugin, new Runnable() {
			public void run() {
				if (c.getConfig().getBoolean("scoreboard." + player.getUniqueId().toString()) != true) {
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				}
			}
		}, 600);
	}
}
