package io.github.WolfLeader116.Settings.CMDs;

import io.github.WolfLeader116.Settings.Config;
import io.github.WolfLeader116.Settings.Scoreboard;
import io.github.WolfLeader116.Settings.Settings;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCMD implements CommandExecutor {
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public String settings = "fly, afk";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		File configFile = new File(Settings.plugin.getDataFolder(), "config.yml");
		Config c = new Config("playerdata", Settings.plugin);
		if (Bukkit.getPluginManager().getPlugin("Music") != null) {
			settings = "fly, afk, music";
		}
		if (cmd.getName().equalsIgnoreCase("settings")) {
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					log.info("Use /settings help for more help.");
					log.info("Settings made by WolfLeader116.");
					log.info("===---Settings Info---===");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("thanks")) {
						log.info("Special thanks to olivervscreeper and SuperOmegaCow for helping out developing this plugin!");
					} else if (args[0].equalsIgnoreCase("news")) {
						String message = "";
						for(int i = 1; i < args.length; i++) {
							String arg = args[i] + " ";
							message = message + arg;
						}
						message = message.replaceAll("&", "§");
						Settings.plugin.getConfig().set("News", message);
						Settings.plugin.saveConfig();
						Scoreboard.scoreboard();
						log.info("Server news set to " + message);
					} else if (args[0].equalsIgnoreCase("reset")) {
						configFile.delete();
						Settings.plugin.saveDefaultConfig();
						Settings.plugin.reloadConfig();
						log.info("Reset the config!");
					} else if (args[0].equalsIgnoreCase("reload")) {
						Settings.plugin.reloadConfig();
						log.info("Reloaded the config!");
					} else if (args[0].equalsIgnoreCase("gui")) {
						log.info("You can't run this command!");
					} else if (args[0].equalsIgnoreCase("help")) {
						log.info("/spectator Spectator mode.");
						log.info("/adventure Adventure mode.");
						log.info("/creative Creative mode.");
						log.info("/survival Survival mode.");
						log.info("/gm Set gamemode.");
						log.info("/gamemode Set gamemode.");
						log.info("/settings reload Reloads the config.");
						log.info("/settings reset Resets the config.");
						log.info("/settings thanks Thank you!");
						log.info("/settings news <news> Sets the server news.");
						log.info("/settings set <setting> <value> <player> Sets a setting for the specified player.");
						log.info("/settings help Shows this page.");
						log.info("/settings Displays info on the plugin.");
						log.info("===---Settings Help---===");
					} else if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							log.info("You must enter a setting, value and player!");
						} else if (args.length == 2) {
							log.info("You must enter a value and player!");
						} else if (args.length == 3) {
							log.info("You must enter a player!");
						} else if (args.length >= 4) {
							if (Bukkit.getServer().getPlayer(args[3]) == null) {
								log.info("The specified player is not online!");
							} else if (args[1].equalsIgnoreCase("fly")) {
								Player player = Bukkit.getServer().getPlayer(args[3]);
								if (args[2].equalsIgnoreCase("true")) {
									c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
									c.save();
									Bukkit.getPlayer(args[3]).setAllowFlight(true);
									log.info(args[3] + "'s fly mode has been set to true");
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by the console");
								} else if (args[2].equalsIgnoreCase("false")) {
									c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
									c.save();
									Bukkit.getPlayer(args[3]).setAllowFlight(false);
									log.info(args[3] + "'s fly mode has been set to false");
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false by the console");
								} else if (args[2].equalsIgnoreCase("toggle")) {
									if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(false);
										log.info(args[3] + "'s fly mode has been set to false");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false by the console");
									} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(true);
										log.info(args[3] + "'s fly mode has been set to true");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by the console");
									} else {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(true);
										log.info(args[3] + "'s fly mode has been set to true");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by the console");
									}
								} else {
									log.info("The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
								}
							} else if (args[1].equalsIgnoreCase("afk")) {
								Player player = Bukkit.getServer().getPlayer(args[3]);
								if (args[2].equalsIgnoreCase("true")) {
									c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
									c.save();
									log.info(args[3] + " is now afk");
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");
								} else if (args[2].equalsIgnoreCase("false")) {
									c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
									c.save();
									log.info(args[3] + " is no longer afk");
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
								} else if (args[2].equalsIgnoreCase("toggle")) {
									if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										log.info(args[3] + " is no longer afk");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
									} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + " is now afk");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");
									} else {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + " is now afk");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");
									}
								} else {
									log.info("The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
								}
							} else if (args[1].equalsIgnoreCase("music")) {
								if (Bukkit.getPluginManager().getPlugin("Music") != null) {
									Player player = Bukkit.getServer().getPlayer(args[3]);
									if (args[2].equalsIgnoreCase("true")) {
										c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + "'s music mode has been set to true");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by the console");
									} else if (args[2].equalsIgnoreCase("false")) {
										c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										log.info(args[3] + "'s music mode has been set to false");
										player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false by the console");
									} else if (args[2].equalsIgnoreCase("toggle")) {
										if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											log.info(args[3] + "'s music mode has been set to false");
											player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false by the console");
										} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											log.info(args[3] + "'s music mode has been set to true");
											player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by the console");
										} else {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											log.info(args[3] + "'s music mode has been set to true");
											player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by the console");
										}
									} else {
										log.info("The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
									}
								} else {
									log.info("Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
								}
							} else {
								log.info("The setting " + args[1] + " does not exist. Possible values are " + settings + ".");
							}
						}
					} else {
						log.info("Unknown sub command! Use /settings help for commands!");
					}
				}
			}
			else if ((sender instanceof Player)) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.BLUE + "===---" + ChatColor.GOLD + "Settings Info" + ChatColor.BLUE + "---===");
					sender.sendMessage(ChatColor.AQUA + "Settings made by WolfLeader116.");
					sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.RED + "/settings help " + ChatColor.AQUA + "for more help.");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("thanks")) {
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Special thanks to olivervscreeper and SuperOmegaCow for helping out developing this plugin!");
					} else if (args[0].equalsIgnoreCase("news")) {
						if (sender.hasPermission("settings.news")) {
							String message = "";
							for(int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							message = message.replaceAll("&", "§");
							Settings.plugin.getConfig().set("News", message);
							Settings.plugin.saveConfig();
							Scoreboard.scoreboard();
							sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Server news set to " + message);
						} else {
							sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
						}
					} else if (args[0].equalsIgnoreCase("reset")) {
						configFile.delete();
						Settings.plugin.saveDefaultConfig();
						Settings.plugin.reloadConfig();
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Reset the config!");
					} else if (args[0].equalsIgnoreCase("reload")) {
						Settings.plugin.reloadConfig();
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Reloaded the config!");
					} else if (args[0].equalsIgnoreCase("gui")) {
						if (args.length == 1) {
							Player splayer = (Player) sender;
							splayer.openInventory(Settings.myInventory);
						}
					} else if (args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.BLUE + "===---" + ChatColor.GOLD + "Settings Help" + ChatColor.BLUE + "---===");
						sender.sendMessage(ChatColor.RED + "/settings " + ChatColor.AQUA + "Displays info on the plugin.");
						sender.sendMessage(ChatColor.RED + "/settings help " + ChatColor.AQUA + "Shows this page.");
						if (sender.hasPermission("settings.set.other")) {
							sender.sendMessage(ChatColor.RED + "/settings set <setting> <value> (player) " + ChatColor.AQUA + "Sets a setting for you or the specified player.");
							sender.sendMessage(ChatColor.RED + "/settings gui (player) " + ChatColor.AQUA + "Opens the settings GUI for you or the specified player.");
						} else if (sender.hasPermission("settings.set")) {
							sender.sendMessage(ChatColor.RED + "/settings set <setting> <value> " + ChatColor.AQUA + "Sets a setting for you.");
							sender.sendMessage(ChatColor.RED + "/settings gui " + ChatColor.AQUA + "Opens the settings GUI for you.");
						}
						if (sender.hasPermission("settings.news")) {
							sender.sendMessage(ChatColor.RED + "/settings news " + ChatColor.AQUA + "Sets the server news.");
						}
						sender.sendMessage(ChatColor.RED + "/settings thanks " + ChatColor.AQUA + "Thank you!");
						if (sender.hasPermission("settings.reset")) {
							sender.sendMessage(ChatColor.RED + "/settings reset " + ChatColor.AQUA + "Resets the config!");
						}
						if (sender.hasPermission("settings.reload")) {
							sender.sendMessage(ChatColor.RED + "/settings reload " + ChatColor.AQUA + "Reloads the config!");
						}
						sender.sendMessage(ChatColor.RED + "/gamemode " + ChatColor.AQUA + "Set gamemode.");
						sender.sendMessage(ChatColor.RED + "/gm " + ChatColor.AQUA + "Set gamemode.");
						sender.sendMessage(ChatColor.RED + "/survival " + ChatColor.AQUA + "Survival mode.");
						sender.sendMessage(ChatColor.RED + "/creative " + ChatColor.AQUA + "Creative mode.");
						sender.sendMessage(ChatColor.RED + "/adventure " + ChatColor.AQUA + "Adventure mode.");
						sender.sendMessage(ChatColor.RED + "/spectator " + ChatColor.AQUA + "Spectator mode.");
					} else if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You must enter a setting, value and player!");
						} else if (args.length == 2) {
							sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You must enter a value and player!");
						} else if (args.length == 3) {
							if (sender.hasPermission("settings.set")) {
								if (args[1].equalsIgnoreCase("fly")) {
									if (sender.hasPermission("settings.set.fly")) {
										String player = ((Player)sender).getName();
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
											c.save();
											Bukkit.getPlayer(player).setAllowFlight(true);
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
											c.save();
											Bukkit.getPlayer(player).setAllowFlight(false);
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(false);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false");
											} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(true);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true");
											} else {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(true);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true");
											}
										} else {
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
									}
								} else if (args[1].equalsIgnoreCase("afk")) {
									if (sender.hasPermission("settings.set.afk")) {
										String player = ((Player)sender).getName();
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
											c.save();
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your're now afk");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
											c.save();
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
											} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your're now afk");
											} else {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your're now afk");
											}
										} else {
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else if (args[1].equalsIgnoreCase("music")) {
										if (Bukkit.getPluginManager().getPlugin("Music") != null) {
											if (sender.hasPermission("settings.set.music")) {
												String player = ((Player)sender).getName();
												if (args[2].equalsIgnoreCase("true")) {
													c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
													c.save();
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true");
												} else if (args[2].equalsIgnoreCase("false")) {
													c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
													c.save();
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false");
												} else if (args[2].equalsIgnoreCase("toggle")) {
													if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(player).getUniqueId())) {
														c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false");
													} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(player).getUniqueId())) {
														c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true");
													} else {
														c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true");
													}
												} else {
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
												}
											}
										} else {
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
										}
									} else {
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
									}
								} else {
									sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
								}
							} else {
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
							}
						}
						else if (args.length >= 4) {
							if (sender.hasPermission("settings.set.other")) {
								if (Bukkit.getServer().getPlayer(args[3]) == null) {
									sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The specified player is not online!");
								} else if (args[1].equalsIgnoreCase("fly")) {
									if (sender.hasPermission("settings.set.other.fly")) {
										Player player = Bukkit.getServer().getPlayer(args[3]);
										String send = sender.getName();
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											Bukkit.getPlayer(args[3]).setAllowFlight(true);
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true");
											player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by " + ChatColor.RESET + send);
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											Bukkit.getPlayer(args[3]).setAllowFlight(false);
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to false");
											player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false by " + ChatColor.RESET + send);
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(false);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to false");
												player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to false by " + ChatColor.RESET + send);
											} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(true);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true");
												player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by " + ChatColor.RESET + send);
											} else {
												c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(true);
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true");
												player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your fly mode has been set to true by " + ChatColor.RESET + send);

											}
										}
									} else {
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
									}
								} else if (args[1].equalsIgnoreCase("afk")) {
									if (sender.hasPermission("settings.set.other.afk")) {
										Player aplayer = Bukkit.getServer().getPlayer(args[3]);
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + " is now afk");
											aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + " is no longer afk");
											aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + " is no longer afk");
												aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're no longer afk");
											} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + " is now afk");
												aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");
											} else {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + " is now afk");
												aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You're now afk");

											}
										} else {
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else if (args[1].equalsIgnoreCase("music")) {
										if (Bukkit.getPluginManager().getPlugin("Music") != null) {
											if (sender.hasPermission("settings.set.other.music")) {
												Player senda = (Player) sender;
												String send = senda.getName();
												Player aplayer = Bukkit.getServer().getPlayer(args[3]);
												if (args[2].equalsIgnoreCase("true")) {
													c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
													c.save();
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true");
													aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by " + ChatColor.RESET + send);
												} else if (args[2].equalsIgnoreCase("false")) {
													c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
													c.save();
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to false");
													aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false by " + ChatColor.RESET + send);
												} else if (args[2].equalsIgnoreCase("toggle")) {
													if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
														c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to false");
														aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to false by " + ChatColor.RESET + send);
													} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
														c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true");
														aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by " + ChatColor.RESET + send);
													} else {
														c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
														c.save();
														sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true");
														aplayer.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your music mode has been set to true by " + ChatColor.RESET + send);
													}
												} else {
													sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
												}
											}
										} else {
											sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
										}
									} else {
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
									}
								} else {
									sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
								}
							} else {
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!");
							}
						}
					} else {
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Unknown sub command! Use /settings help for commands!");
					}
				}
			}
		}
		return false;
	}
}
