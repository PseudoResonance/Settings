package io.github.WolfLeader116.Settings.CMDs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCMD implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			player.chat("/settings set afk toggle");
		} else if (args.length >= 1) {
			player.chat("/settings set afk toggle " + args[0]);
		}
	return false;
	}
}
