package io.github.WolfLeader116.Settings.CMDs;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCMD implements CommandExecutor {
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				player.chat("/settings set scoreboard toggle");
			} else if (args.length >= 1) {
				player.chat("/settings set scoreboard toggle " + args[0]);
			}
		} else {
			log.info("You can't run this command!");
		}
	return false;
	}
}
