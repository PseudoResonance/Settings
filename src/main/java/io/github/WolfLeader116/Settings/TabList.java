package io.github.WolfLeader116.Settings;

import io.github.wolfleader116.chat.ChatPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcsg.double0negative.tabapi.TabAPI;

public class TabList {
	
	public static void tablist() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			TabAPI.setPriority(Settings.plugin, p, 0);
			TabAPI.updatePlayer(p);
			TabAPI.setTabString(Settings.plugin, p, 0, 0, "§2----------" + TabAPI.nextNull(), 0);
			TabAPI.setTabString(Settings.plugin, p, 0, 1, "§2----------" + TabAPI.nextNull(), 0);
			TabAPI.setTabString(Settings.plugin, p, 0, 2, "§2----------" + TabAPI.nextNull(), 0);
			TabAPI.setTabString(Settings.plugin, p, 1, 0, "§6Online" + TabAPI.nextNull(), 5);
			TabAPI.setTabString(Settings.plugin, p, 1, 1, Settings.plugin.getConfig().getString("ServerName") + TabAPI.nextNull(), 5);
			TabAPI.setTabString(Settings.plugin, p, 1, 2, "§6Party" + TabAPI.nextNull(), 5);
			TabAPI.setTabString(Settings.plugin, p, 2, 0, "§2----------" + TabAPI.nextNull(), 0);
			TabAPI.setTabString(Settings.plugin, p, 2, 1, "§2----------" + TabAPI.nextNull(), 0);
			TabAPI.setTabString(Settings.plugin, p, 2, 2, "§2----------" + TabAPI.nextNull(), 0);
			int down = 3;
			int across = 0;
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				if (across == 2) {
					across = 0;
					down++;
				}
				if (down != 20) {
					TabAPI.setTabString(Settings.plugin, p, down, across, ChatPlugin.getNick(pl) + TabAPI.nextNull(), 3);
					across++;
				}
			}
			TabAPI.updatePlayer(p);
		}
	}
}
