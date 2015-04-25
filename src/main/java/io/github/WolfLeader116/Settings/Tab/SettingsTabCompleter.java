package io.github.WolfLeader116.Settings.Tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SettingsTabCompleter implements TabCompleter {
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("settings")) {
			ArrayList<String> possible = new ArrayList<String>();
			if (args.length == 1) {
				ArrayList<String> subs = new ArrayList<String>();
				subs.add("help");
				subs.add("set");
				subs.add("news");
				subs.add("thanks");
				subs.add("reset");
				subs.add("reload");
				if (!(args[0].equals(""))) {
					for (String sub : subs) {
						if (sub.toLowerCase().startsWith(args[0])) {
							possible.add(sub);
						}
					}
				} else {
					for (String sub : subs) {
						possible.add(sub);
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					ArrayList<String> subs = new ArrayList<String>();
					subs.add("fly");
					subs.add("afk");
					subs.add("music");
					if (!(args[1].equals(""))) {
						for (String sub : subs) {
							if (sub.toLowerCase().startsWith(args[1])) {
								possible.add(sub);
							}
						}
					} else {
						for (String sub : subs) {
							possible.add(sub);
						}
					}
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("set")) {
					if (args[1].equalsIgnoreCase("fly")) {
						ArrayList<String> subs = new ArrayList<String>();
						subs.add("true");
						subs.add("false");
						subs.add("toggle");
						if (!(args[2].equals(""))) {
							for (String sub : subs) {
								if (sub.toLowerCase().startsWith(args[2])) {
									possible.add(sub);
								}
							}
						} else {
							for (String sub : subs) {
								possible.add(sub);
							}
						}
					} else if (args[1].equalsIgnoreCase("afk")) {
						ArrayList<String> subs = new ArrayList<String>();
						subs.add("true");
						subs.add("false");
						subs.add("toggle");
						if (!(args[2].equals(""))) {
							for (String sub : subs) {
								if (sub.toLowerCase().startsWith(args[2])) {
									possible.add(sub);
								}
							}
						} else {
							for (String sub : subs) {
								possible.add(sub);
							}
						}
					} else if (args[1].equalsIgnoreCase("music")) {
						ArrayList<String> subs = new ArrayList<String>();
						subs.add("true");
						subs.add("false");
						subs.add("toggle");
						if (!(args[2].equals(""))) {
							for (String sub : subs) {
								if (sub.toLowerCase().startsWith(args[2])) {
									possible.add(sub);
								}
							}
						} else {
							for (String sub : subs) {
								possible.add(sub);
							}
						}
					}
				}
			} else if (args.length == 4) {
				if (args[0].equalsIgnoreCase("set")) {
					if (args[1].equalsIgnoreCase("fly") || args[1].equalsIgnoreCase("afk") || args[1].equalsIgnoreCase("music")) {
						if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("toggle")) {
							if (!(args[3].equals(""))) {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									String sub = p.getName();
									if (sub.toLowerCase().startsWith(args[3])) {
										possible.add(sub);
									}
								}
							} else {
								for (Player p : Bukkit.getServer().getOnlinePlayers()) {
									String sub = p.getName();
									possible.add(sub);
								}
							}
						}
					}
				}
			}
			Collections.sort(possible);
			return possible;
		}
		return null;
	}
}
