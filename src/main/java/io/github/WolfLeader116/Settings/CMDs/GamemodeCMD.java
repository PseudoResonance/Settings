package io.github.WolfLeader116.Settings.CMDs;

import io.github.WolfLeader116.Settings.Config;
import io.github.WolfLeader116.Settings.Settings;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD implements CommandExecutor {
	private static final Logger log = Logger.getLogger("Minecraft");

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Config c = new Config("playerdata", Settings.plugin);
		if (cmd.getName().equalsIgnoreCase("gamemode") || cmd.getName().equalsIgnoreCase("gm")) { //Check if player used a non specific gamemode command
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					log.info("Please enter a gamemode and player.");
				} else if (args.length >= 1) {
					if (args.length == 1) {
						log.info("Please enter a player.");
					} else if (args.length >= 2) { //Check for player and gamemode entered
						if (Bukkit.getServer().getPlayer(args[1]) == null) { //Check if player is offline
							log.info("The specified player is not online!");
						} else {
							Player player = Bukkit.getServer().getPlayer(args[1]);
							if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) { //Check if gamemode is survival
								player.setGameMode(GameMode.SURVIVAL);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival by the console");
								log.info(args[1] + "'s game mode has been set to survival");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) { //Check if gamemode is creative
								player.setGameMode(GameMode.CREATIVE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative by the console");
								log.info(args[1] + "'s game mode has been set to creative");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) { //Check if gamemode is adventure
								player.setGameMode(GameMode.ADVENTURE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure by the console");
								log.info(args[1] + "'s game mode has been set to adventure");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("r")) { //Check if gamemode is spectator
								player.setGameMode(GameMode.SPECTATOR);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator by the console");
								log.info(args[1] + "'s game mode has been set to spectator");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else {
								log.info("Possible gamemodes are survival/creative/adventure/spectator, 0/1/2/3 or s/c/a/r.");
							}
						}
					}
				}
			} else if ((sender instanceof Player)) {
				if (sender.hasPermission("settings.gamemode")) {
					if (args.length == 0) {
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Please enter a gamemode.");
					} else if (args.length >= 1) {
						if (args.length == 1) {
							Player player1 = (Player) sender;
							if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) { //Check if gamemode is survival
								player1.setGameMode(GameMode.SURVIVAL);
								player1.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival");
								if (c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(true);
									if (!(player1.isOnGround())) { //Turn on flight if player is off ground
										player1.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) { //Check if gamemode is creative
								player1.setGameMode(GameMode.CREATIVE);
								player1.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative");
								if (c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(true);
									if (!(player1.isOnGround())) { //Turn on flight if player is off ground
										player1.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) { //Check if gamemode is adventure
								player1.setGameMode(GameMode.ADVENTURE);
								player1.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure");
								if (c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(true);
									if (!(player1.isOnGround())) { //Turn on flight if player is off ground
										player1.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(false);
								}
							} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("r")) { //Check if gamemode is spectator
								player1.setGameMode(GameMode.SPECTATOR);
								player1.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator");
								if (c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(true);
									if (!(player1.isOnGround())) { //Turn on flight if player is off ground
										player1.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player1.getUniqueId())) {
									player1.setAllowFlight(false);
								}
							} else if (args.length >= 2) {
								if (Bukkit.getServer().getPlayer(args[1]) == null) {
									sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "The specified player is not online!");
								} else {
									Player player2 = Bukkit.getServer().getPlayer(args[1]);
									Player send = (Player) sender;
									if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) { //Check if gamemode is survival
										player2.setGameMode(GameMode.SURVIVAL);
										player2.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival by " + ChatColor.RESET + send.toString());
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[1] + ChatColor.GREEN + "'s game mode has been set to survival");
										if (c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(true);
											if (!(player2.isOnGround())) { //Turn on flight if player is off ground
												player2.setFlying(true);
											}
										} else if (!c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(false);
										}
									} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) { //Check if gamemode is creative
										player2.setGameMode(GameMode.CREATIVE);
										player2.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative by " + ChatColor.RESET + send.toString());
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[1] + ChatColor.GREEN + "'s game mode has been set to creative");
										if (c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(true);
											if (!(player2.isOnGround())) { //Turn on flight if player is off ground
												player2.setFlying(true);
											}
										} else if (!c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(false);
										}
									} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) { //Check if gamemode is adventure
										player2.setGameMode(GameMode.ADVENTURE);
										player2.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure by " + ChatColor.RESET + send.toString());
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[1] + ChatColor.GREEN + "'s game mode has been set to adventure");
										if (c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(true);
											if (!(player2.isOnGround())) { //Turn on flight if player is off ground
												player2.setFlying(true);
											}
										} else if (!c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(false);
										}
									} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("r")) { //Check if gamemode is spectator
										player2.setGameMode(GameMode.SPECTATOR);
										player2.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator by " + ChatColor.RESET + send.toString());
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[1] + ChatColor.GREEN + "'s game mode has been set to spectator");
										if (c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(true);
											if (!(player2.isOnGround())) { //Turn on flight if player is off ground
												player2.setFlying(true);
											}
										} else if (!c.getConfig().getBoolean("fly." + player2.getUniqueId())) {
											player2.setAllowFlight(false);
										}
									} else {
										sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Possible gamemodes are survival/creative/adventure/spectator, 0/1/2/3 or s/c/a/r.");
									}
								}
							}
							if (args.length == 1) {
								Player player = (Player) sender;
								if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) { //Check if gamemode is survival
									player.setGameMode(GameMode.SURVIVAL);
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival");
									if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(true);
										if (!(player.isOnGround())) { //Turn on flight if player is off ground
											player.setFlying(true);
										}
									} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(false);
									}
								} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) { //Check if gamemode is creative
									player.setGameMode(GameMode.CREATIVE);
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative");
									if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(true);
										if (!(player.isOnGround())) { //Turn on flight if player is off ground
											player.setFlying(true);
										}
									} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(false);
									}
								} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) { //Check if gamemode is adventure
									player.setGameMode(GameMode.ADVENTURE);
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure");
									if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(true);
										if (!(player.isOnGround())) { //Turn on flight if player is off ground
											player.setFlying(true);
										}
									} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(false);
									}
								} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("r")) { //Check if gamemode is spectator
									player.setGameMode(GameMode.SPECTATOR);
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator");
									if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(true);
										if (!(player.isOnGround())) { //Turn on flight if player is off ground
											player.setFlying(true);
										}
									} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
										player.setAllowFlight(false);
									}
								} else {
									player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Possible gamemodes are survival/creative/adventure/spectator, 0/1/2/3 or s/c/a/r.");
								}
							}
						}
					} else {
						sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "You do not have permission to do this!"); //Message if permission was denied
					}
				}
			} else if (cmd.getName().equalsIgnoreCase("survival") || cmd.getName().equalsIgnoreCase("creative") || cmd.getName().equalsIgnoreCase("adventure") || cmd.getName().equalsIgnoreCase("spectator")) { //Check for a gamemode specific command.
				if (!(sender instanceof Player)) {
					if (args.length == 0) {
						log.info("Please enter a player.");
					} else if (args.length >= 1) {
						Player player = Bukkit.getServer().getPlayer(args[0]);
						if (Bukkit.getServer().getPlayer(args[1]) == null) {
							log.info("The specified player is not online!");
						} else if (cmd.getName().equalsIgnoreCase("survival")) {
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival by the console");
							log.info(args[0] + "'s game mode has been set to survival");
							if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(true);
								if (!(player.isOnGround())) { //Turn on flight if player is off ground
									player.setFlying(true);
								}
							} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(false);
							}
						} else if (cmd.getName().equalsIgnoreCase("creative")) {
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative by the console");
							log.info(args[0] + "'s game mode has been set to creative");
							if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(true);
								if (!(player.isOnGround())) { //Turn on flight if player is off ground
									player.setFlying(true);
								}
							} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(false);
							}
						} else if (cmd.getName().equalsIgnoreCase("adventure")) {
							player.setGameMode(GameMode.ADVENTURE);
							player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure by the console");
							log.info(args[0] + "'s game mode has been set to adventure");
							if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(true);
								if (!(player.isOnGround())) { //Turn on flight if player is off ground
									player.setFlying(true);
								}
							} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(false);
							}
						} else if (cmd.getName().equalsIgnoreCase("spectator")) {
							player.setGameMode(GameMode.SPECTATOR);
							player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator by the console");
							log.info(args[0] + "'s game mode has been set to spectator");
							if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(true);
								if (!(player.isOnGround())) { //Turn on flight if player is off ground
									player.setFlying(true);
								}
							} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
								player.setAllowFlight(false);
							}
						}
					}
				} else if (sender instanceof Player) {
					if (args.length == 0) {
						if (sender.hasPermission("settings.gamemode")) {
							Player player = (Player) sender;
							if (cmd.getName().equalsIgnoreCase("survival")) {
								player.setGameMode(GameMode.SURVIVAL);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("creative")) {
								player.setGameMode(GameMode.CREATIVE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("adventure")) {
								player.setGameMode(GameMode.ADVENTURE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("spectator")) {
								player.setGameMode(GameMode.SPECTATOR);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							}
						}
					} else if (args.length >= 1) {
						if (sender.hasPermission("settings.gamemode.other")) {
							Player player = Bukkit.getServer().getPlayer(args[0]);
							Player send = (Player) sender;
							if (cmd.getName().equalsIgnoreCase("survival")) {
								player.setGameMode(GameMode.SURVIVAL);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to survival by " + ChatColor.RESET + send.toString());
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[0] + ChatColor.GREEN + "'s game mode has been set to survival");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("creative")) {
								player.setGameMode(GameMode.CREATIVE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to creative by " + ChatColor.RESET + send.toString());
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[0] + ChatColor.GREEN + "'s game mode has been set to creative");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("adventure")) {
								player.setGameMode(GameMode.ADVENTURE);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to adventure by " + ChatColor.RESET + send.toString());
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[0] + ChatColor.GREEN + "'s game mode has been set to adventure");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							} else if (cmd.getName().equalsIgnoreCase("spectator")) {
								player.setGameMode(GameMode.SPECTATOR);
								player.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.GREEN + "Your game mode has been set to spectator by " + ChatColor.RESET + send.toString());
								sender.sendMessage(ChatColor.BLUE + "Hub> " + ChatColor.RESET + args[0] + ChatColor.GREEN + "'s game mode has been set to spectator");
								if (c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(true);
									if (!(player.isOnGround())) { //Turn on flight if player is off ground
										player.setFlying(true);
									}
								} else if (!c.getConfig().getBoolean("fly." + player.getUniqueId())) {
									player.setAllowFlight(false);
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
