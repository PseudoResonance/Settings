package io.github.WolfLeader116.Settings;

import io.github.wolfleader116.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Scoreboard {
	
	public static void scoreboard() {
		for (Player online : Bukkit.getServer().getOnlinePlayers()) {
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
			playersname.setScore(-4);
			Score moneyvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Economy.getMoney(online.getName()));
			playersvalue.setScore(-5);
			Score newsname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "News:");
			newsname.setScore(-6);
			Score newsvalue = objective.getScore(ChatColor.RED + latestnews);
			newsvalue.setScore(-7);
			online.setScoreboard(scoreboard);
		}
	}
	
	public static void scoreboard(String playername) {
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
			playersname.setScore(-4);
			Score moneyvalue = objective.getScore(ChatColor.RED + "" + ChatColor.GRAY + "" + ChatColor.RED + Economy.getMoney(Bukkit.getServer().getPlayer(playername).getName()));
			playersvalue.setScore(-5);
			Score newsname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "News:");
			newsname.setScore(-6);
			Score newsvalue = objective.getScore(ChatColor.RED + latestnews);
			newsvalue.setScore(-7);
			Bukkit.getServer().getPlayer(playername).setScoreboard(scoreboard);
	}
}
