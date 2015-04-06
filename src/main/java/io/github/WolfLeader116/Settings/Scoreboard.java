package io.github.WolfLeader116.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Scoreboard {
	
	public static void scoreboard() {
		org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("status", "dummy");
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
		Score playersname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Players:");
		playersname.setScore(0);
		Score playersvalue = objective.getScore(ChatColor.RED + Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
		playersvalue.setScore(-1);
		Score staffname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Online Staff:");
		staffname.setScore(-2);
		Score staffvalue = objective.getScore(ChatColor.RED + Integer.toString(staff));
		staffvalue.setScore(-3);
		Score newsname = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "News:");
		newsname.setScore(-4);
		Score newsvalue = objective.getScore(ChatColor.RED + latestnews);
		newsvalue.setScore(-5);
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			all.setScoreboard(scoreboard);
		}
	}
}
