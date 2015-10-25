package io.github.wolfleader116.settings.commands;

import io.github.wolfleader116.settings.Config;
import io.github.wolfleader116.settings.Scoreboard;
import io.github.wolfleader116.settings.Settings;
import io.github.wolfleader116.wolfapi.WolfAPI;
import io.github.wolfleader116.wolfapi.Errors;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsC implements CommandExecutor {
	private static final Logger log = Logger.getLogger("Minecraft");

	public String settings = "fly, afk";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		File configFile = new File(Settings.plugin.getDataFolder(), "config.yml");
		Config c = new Config("playerdata", Settings.plugin);
		if (Bukkit.getPluginManager().getPlugin("Music") != null) {
			settings = "fly, afk, music, scoreboard";
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
									WolfAPI.message("Your fly mode has been set to true by the console", player, "Settings");
								} else if (args[2].equalsIgnoreCase("false")) {
									c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
									c.save();
									Bukkit.getPlayer(args[3]).setAllowFlight(false);
									log.info(args[3] + "'s fly mode has been set to false");
									WolfAPI.message("Your fly mode has been set to false by the console", player, "Settings");
								} else if (args[2].equalsIgnoreCase("toggle")) {
									if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(false);
										log.info(args[3] + "'s fly mode has been set to false");
										WolfAPI.message("Your fly mode has been set to false by the console", player, "Settings");
									} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(true);
										log.info(args[3] + "'s fly mode has been set to true");
										WolfAPI.message("Your fly mode has been set to true by the console", player, "Settings");
									} else {
										c.getConfig().set("fly." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										Bukkit.getPlayer(args[3]).setAllowFlight(true);
										log.info(args[3] + "'s fly mode has been set to true");
										WolfAPI.message("Your fly mode has been set to true by the console", player, "Settings");
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
									WolfAPI.message("You've been set as afk by the console", player, "Settings");
								} else if (args[2].equalsIgnoreCase("false")) {
									c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
									c.save();
									log.info(args[3] + " is no longer afk");
									WolfAPI.message("You've been set as not afk by the console", player, "Settings");
								} else if (args[2].equalsIgnoreCase("toggle")) {
									if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										log.info(args[3] + " is no longer afk");
										WolfAPI.message("You've been set as not afk by the console", player, "Settings");
									} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + " is now afk");
										WolfAPI.message("You've been set as afk by the console", player, "Settings");
									} else {
										c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + " is now afk");
										WolfAPI.message("You've been set as afk by the console", player, "Settings");
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
										WolfAPI.message("Your music mode has been set to true by the console", player, "Settings");
									} else if (args[2].equalsIgnoreCase("false")) {
										c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										log.info(args[3] + "'s music mode has been set to false");
										WolfAPI.message("Your music mode has been set to false by the console", player, "Settings");
									} else if (args[2].equalsIgnoreCase("toggle")) {
										if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											log.info(args[3] + "'s music mode has been set to false");
											WolfAPI.message("Your music mode has been set to false by the console", player, "Settings");
										} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											log.info(args[3] + "'s music mode has been set to true");
											WolfAPI.message("Your music mode has been set to true by the console", player, "Settings");
										} else {
											c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											log.info(args[3] + "'s music mode has been set to true");
											WolfAPI.message("Your music mode has been set to true by the console", player, "Settings");
										}
									} else {
										log.info("The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
									}
								} else {
									log.info("Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
								}
							} else if (args[1].equalsIgnoreCase("scoreboard")) {
								Player player = Bukkit.getServer().getPlayer(args[3]);
								if (args[2].equalsIgnoreCase("true")) {
									c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
									c.save();
									log.info(args[3] + "'s scoreboard visibility mode has been set to true");
									WolfAPI.message("Your scoreboard visibility mode has been set to true by the console", player, "Settings");
									Scoreboard.scoreboard(player.getName().toString());
								} else if (args[2].equalsIgnoreCase("false")) {
									c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
									c.save();
									log.info(args[3] + "'s scoreboard visibility mode has been set to false");
									WolfAPI.message("Your scoreboard visibility mode has been set to false by the console", player, "Settings");
									Scoreboard.scoreboard(player.getName().toString());
								} else if (args[2].equalsIgnoreCase("toggle")) {
									if (c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
										c.save();
										log.info(args[3] + "'s scoreboard visibility mode has been set to false");
										WolfAPI.message("Your scoreboard visibility mode has been set to false by the console", player, "Settings");
										Scoreboard.scoreboard(player.getName().toString());
									} else if (!c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId())) {
										c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + "'s scoreboard visibility mode has been set to true");
										WolfAPI.message("Your scoreboard visibility mode has been set to true by the console", player, "Settings");
										Scoreboard.scoreboard(player.getName().toString());
									} else {
										c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
										c.save();
										log.info(args[3] + "'s scoreboard visibility mode has been set to true");
										WolfAPI.message("Your scoreboard visibility mode has been set to true by the console", player, "Settings");
										Scoreboard.scoreboard(player.getName().toString());
									}
								} else {
									log.info("The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
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
				Player p = (Player) sender;
				if (args.length == 0) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Settings Info" + ChatColor.DARK_AQUA + "---===");
					sender.sendMessage(ChatColor.AQUA + "Settings made by WolfLeader116.");
					sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.RED + "/settings help " + ChatColor.AQUA + "for more help.");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("thanks")) {
						WolfAPI.message("Special thanks to olivervscreeper and SuperOmegaCow for helping out developing this plugin!", p, "Settings");
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
							WolfAPI.message("Server news set to " + message, p, "Settings");
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Settings");
						}
					} else if (args[0].equalsIgnoreCase("reset")) {
						configFile.delete();
						Settings.plugin.saveDefaultConfig();
						Settings.plugin.reloadConfig();
						WolfAPI.message("Reset the config!", p, "Settings");
					} else if (args[0].equalsIgnoreCase("reload")) {
						Settings.plugin.reloadConfig();
						WolfAPI.message("Reloaded the config!", p, "Settings");
					} else if (args[0].equalsIgnoreCase("gui")) {
						if (args.length == 1) {
							Player splayer = (Player) sender;
							splayer.openInventory(Settings.myInventory);
						}
					} else if (args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Settings Help" + ChatColor.DARK_AQUA + "---===");
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
						String player = ((Player)sender).getName();
						if (args.length == 1) {
							Errors.sendError(Errors.CUSTOM, p, "Settings", "You must enter a setting, value and player!");
						} else if (args.length == 2) {
							Errors.sendError(Errors.CUSTOM, p, "Settings", "You must enter a value and player!");
						} else if (args.length == 3) {
							if (sender.hasPermission("settings.set")) {
								if (args[1].equalsIgnoreCase("fly")) {
									if (sender.hasPermission("settings.set.fly")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
											c.save();
											Bukkit.getPlayer(player).setAllowFlight(true);
											WolfAPI.message("Your fly mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
											c.save();
											Bukkit.getPlayer(player).setAllowFlight(false);
											WolfAPI.message("Your fly mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(false);
												WolfAPI.message("Your fly mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
											} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(true);
												WolfAPI.message("Your fly mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
											} else {
												c.getConfig().set("fly." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(player).setAllowFlight(true);
												WolfAPI.message("Your fly mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
											}
										} else {
											Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, p, "Settings");
									}
								} else if (args[1].equalsIgnoreCase("afk")) {
									if (sender.hasPermission("settings.set.afk")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
											c.save();
											WolfAPI.message("You're now afk", Bukkit.getServer().getPlayer(player), "Settings");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
											c.save();
											WolfAPI.message("You're no longer afk", Bukkit.getServer().getPlayer(player), "Settings");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message("You're no longer afk", Bukkit.getServer().getPlayer(player), "Settings");
											} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message("You're now afk", Bukkit.getServer().getPlayer(player), "Settings");
											} else {
												c.getConfig().set("afk." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message("You're now afk", Bukkit.getServer().getPlayer(player), "Settings");
											}
										} else {
											Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, p, "Settings");
									}
								} else if (args[1].equalsIgnoreCase("music")) {
									if (Bukkit.getPluginManager().getPlugin("Music") != null) {
										if (sender.hasPermission("settings.set.music")) {
											if (args[2].equalsIgnoreCase("true")) {
												c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message("Your music mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
											} else if (args[2].equalsIgnoreCase("false")) {
												c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message("Your music mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
											} else if (args[2].equalsIgnoreCase("toggle")) {
												if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(player).getUniqueId())) {
													c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
													c.save();
													WolfAPI.message("Your music mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
												} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(player).getUniqueId())) {
													c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
													c.save();
													WolfAPI.message("Your music mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
												} else {
													c.getConfig().set("music." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
													c.save();;
													WolfAPI.message("Your music mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
												}
											} else {
												Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
											}
										} else {
											Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(player), "Settings");
										}
									} else {
										Errors.sendError(Errors.CUSTOM, p, "Settings", "Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
									}
								} else if (args[1].equalsIgnoreCase("scoreboard")) {
									if (sender.hasPermission("settings.set.scoreboard")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("scoreboard." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
											c.save();
											WolfAPI.message("Your scoreboard visibility mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
											Scoreboard.scoreboard(player);
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("scoreboard." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
											c.save();
											WolfAPI.message("Your scoreboard visibility mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
											Scoreboard.scoreboard(player);
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(player).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message("Your scoreboard visibility mode has been set to false", Bukkit.getServer().getPlayer(player), "Settings");
												Scoreboard.scoreboard(player);
											} else if (!c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(player).getUniqueId())) {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message("Your scoreboard visibility mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
												Scoreboard.scoreboard(player);
											} else {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(player).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message("Your scoreboard visibility mode has been set to true", Bukkit.getServer().getPlayer(player), "Settings");
												Scoreboard.scoreboard(player);
											}
										} else {
											Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(player), "Settings");
									}
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Settings", "The setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
								}
							} else {
								Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(player), "Settings");
							}
						} else if (args.length >= 4) {
							String send = sender.getName();
							if (sender.hasPermission("settings.set.other")) {
								if (Bukkit.getServer().getPlayer(args[3]) == null) {
									Errors.sendError(Errors.NOT_ONLINE, p, "Settings");
								} else if (args[1].equalsIgnoreCase("fly")) {
									Player target = Bukkit.getServer().getPlayer(args[3]);
									if (sender.hasPermission("settings.set.other.fly")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("fly." + target.getUniqueId().toString(), true);
											c.save();
											Bukkit.getPlayer(args[3]).setAllowFlight(true);
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true", p, "Settings");
											WolfAPI.message("Your fly mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("fly." + target.getUniqueId().toString(), false);
											c.save();
											Bukkit.getPlayer(args[3]).setAllowFlight(false);
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to false", p, "Settings");
											WolfAPI.message("Your fly mode has been set to false by " + ChatColor.RESET + send, target, "Settings");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("fly." + target.getUniqueId().toString(), false);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(false);
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to false", p, "Settings");
												WolfAPI.message("Your fly mode has been set to false by " + ChatColor.RESET + send, target, "Settings");
											} else if (!c.getConfig().getBoolean("fly." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("fly." + target.getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(true);
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true", p, "Settings");
												WolfAPI.message("Your fly mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
											} else {
												c.getConfig().set("fly." + target.getUniqueId().toString(), true);
												c.save();
												Bukkit.getPlayer(args[3]).setAllowFlight(true);
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s fly mode has been set to true", p, "Settings");
												WolfAPI.message("Your fly mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
											}
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(send), "Settings");
									}
								} else if (args[1].equalsIgnoreCase("afk")) {
									Player target = Bukkit.getServer().getPlayer(args[3]);
									if (sender.hasPermission("settings.set.other.afk")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "has been set as afk", p, "Settings");
											WolfAPI.message("You have been set as afk by " + ChatColor.RESET + send, target, "Settings");
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "has been set as not afk", p, "Settings");
											WolfAPI.message("You have been set as not afk by " + ChatColor.RESET + send, target, "Settings");
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "has been set as not afk", p, "Settings");
												WolfAPI.message("You have been set as not afk by " + ChatColor.RESET + send, target, "Settings");
											} else if (!c.getConfig().getBoolean("afk." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "has been set as afk", p, "Settings");
												WolfAPI.message("You have been set as afk by " + ChatColor.RESET + send, target, "Settings");
											} else {
												c.getConfig().set("afk." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "has been set as afk", p, "Settings");
												WolfAPI.message("You have been set as afk by " + ChatColor.RESET + send, target, "Settings");
											}
										} else {
											Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(send), "Settings");
									}
								} else if (args[1].equalsIgnoreCase("music")) {
									Player target = Bukkit.getServer().getPlayer(args[3]);
									if (Bukkit.getPluginManager().getPlugin("Music") != null) {
										if (sender.hasPermission("settings.set.other.music")) {
											if (args[2].equalsIgnoreCase("true")) {
												c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true", p, "Settings");
												WolfAPI.message("Your music mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
											} else if (args[2].equalsIgnoreCase("false")) {
												c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to false", p, "Settings");
												WolfAPI.message("Your music mode has been set to false by " + ChatColor.RESET + send, target, "Settings");
											} else if (args[2].equalsIgnoreCase("toggle")) {
												if (c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
													c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
													c.save();
													WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to false", p, "Settings");
													WolfAPI.message("Your music mode has been set to false by " + ChatColor.RESET + send, target, "Settings");
												} else if (!c.getConfig().getBoolean("music." + Bukkit.getPlayer(args[3]).getUniqueId())) {
													c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
													c.save();
													WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true", p, "Settings");
													WolfAPI.message("Your music mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
												} else {
													c.getConfig().set("music." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
													c.save();
													WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s music mode has been set to true", p, "Settings");
													WolfAPI.message("Your music mode has been set to true by " + ChatColor.RESET + send, target, "Settings");
												}
											} else {
												Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
											}
										} else {
											Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(send), "Settings");
										}
									} else {
										Errors.sendError(Errors.CUSTOM, p, "Settings", "Because the music plugin is not installed, the setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
									}
								} else if (args[1].equalsIgnoreCase("scoreboard")) {
									Player target = Bukkit.getServer().getPlayer(args[3]);
									if (sender.hasPermission("settings.set.other.scoreboard")) {
										if (args[2].equalsIgnoreCase("true")) {
											c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
											c.save();
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s scoreboard visibility has been set to true", p, "Settings");
											WolfAPI.message("Your scoreboard visibility has been set to true by " + ChatColor.RESET + send, target, "Settings");
											Scoreboard.scoreboard(target.getName().toString());
										} else if (args[2].equalsIgnoreCase("false")) {
											c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
											c.save();
											WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s scoreboard visibility has been set to false", p, "Settings");
											WolfAPI.message("Your scoreboard visibility has been set to false by " + ChatColor.RESET + send, target, "Settings");
											Scoreboard.scoreboard(target.getName().toString());
										} else if (args[2].equalsIgnoreCase("toggle")) {
											if (c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), false);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s scoreboard visibility has been set to false", p, "Settings");
												WolfAPI.message("Your scoreboard visibility has been set to false by " + ChatColor.RESET + send, target, "Settings");
												Scoreboard.scoreboard(target.getName().toString());
											} else if (!c.getConfig().getBoolean("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId())) {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s scoreboard visibility has been set to true", p, "Settings");
												WolfAPI.message("Your scoreboard visibility has been set to true by " + ChatColor.RESET + send, target, "Settings");
												Scoreboard.scoreboard(target.getName().toString());
											} else {
												c.getConfig().set("scoreboard." + Bukkit.getPlayer(args[3]).getUniqueId().toString(), true);
												c.save();
												WolfAPI.message(ChatColor.RESET + args[3] + ChatColor.GREEN + "'s scoreboard visibility has been set to true", p, "Settings");
												WolfAPI.message("Your scoreboard visibility has been set to true by " + ChatColor.RESET + send, target, "Settings");
												Scoreboard.scoreboard(target.getName().toString());
											}
										} else {
											Errors.sendError(Errors.CUSTOM, p, "Settings", "The value " + args[2] + " is not possible. Possible values are true/false/toggle.");
										}
									} else {
										Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(send), "Settings");
									}
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Settings", "The setting " + args[1] + " does not exist. Possible settings are " + settings + ".");
								}
							} else {
								Errors.sendError(Errors.NO_PERMISSION, Bukkit.getServer().getPlayer(send), "Settings");
							}
						}
					} else {
						Errors.sendError(Errors.CUSTOM, p, "Settings", "Unknown sub command! Use /settings help for commands!");
					}
				}
			}
		}
		return false;
	}
}
