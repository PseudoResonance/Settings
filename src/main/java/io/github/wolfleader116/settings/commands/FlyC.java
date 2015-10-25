package io.github.wolfleader116.settings.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyC implements CommandExecutor {
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				player.chat("/settings set fly toggle");
			} else if (args.length >= 1) {
				player.chat("/settings set fly toggle " + args[0]);
			}
		} else {
			if (args.length == 0) {
				log.info("Please add a player!");
			} else {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "settings set fly toggle " + args[0]);
			}
		}
	return false;
	}
}
