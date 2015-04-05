package io.github.WolfLeader116.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Scoreboard {
	
	public static org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	public static Objective objective = scoreboard.registerNewObjective("status", "dummy");

	public static void initializeScoreboard() {
		objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Marvel " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Craft " + ChatColor.RED + "" + ChatColor.BOLD + "Status");
		scoreboard();
	}
	
	public static void scoreboard() {
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			int staff = 0;
			for (Player players : Bukkit.getServer().getOnlinePlayers()) {
				if(Settings.chat.playerInGroup("world", players, "helper") || Settings.chat.playerInGroup("world", players, "moderator") || Settings.chat.playerInGroup("world", players, "admin") || Settings.chat.playerInGroup("world", players, "headadmin") || Settings.chat.playerInGroup("world", players, "coowner") || Settings.chat.playerInGroup("world", players, "owner")) {
					staff = staff + 1;
				}
			}
			String latestnews = Settings.plugin.getConfig().getString("news");
			latestnews = latestnews.replaceAll("&", "§");
			makeScore(0, "Online Staff:", Integer.toString(staff));
			makeScore(-2, "Money:", Double.toString(Settings.economy.getBalance(all)));
			makeScore(-4, "News:", latestnews);
		}
	}
		
		public static void makeBlank(int number) {
			Score scorename = objective.getScore("");
			scorename.setScore(number);
		}
		
		public static void makeScore(int number, String name, String value) {
			Score scorename = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + name);
			scorename.setScore(number);
			Score scorevalue = objective.getScore(ChatColor.RED + value);
			scorevalue.setScore(number - 1);
		}
}
