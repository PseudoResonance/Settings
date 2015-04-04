package io.github.WolfLeader116.Settings.Tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SettingsTabCompleter
implements TabCompleter
{
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase(""))
		{
			List<String> list = new ArrayList<String>();
			if (args.length == 0)
			{
				list.add("help");
				list.add("set");
				Collections.sort(list);
				return list;
			}
			if (args.length >= 1)
			{
				if ((args.length == 1) && (!args[0].equalsIgnoreCase("set")) && (!args[0].equalsIgnoreCase("help")))
				{
					list.add("help");
					list.add("set");
					for (String s : list) {
						if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
							list.remove(s);
						}
					}
					Collections.sort(list);
					return list;
				}
				if (args[0].equalsIgnoreCase("set"))
				{
					if (args.length == 1)
					{
						list.add("fly");
						Collections.sort(list);
						return list;
					}
					if ((args.length == 2) && (!args[1].equalsIgnoreCase("fly")))
					{
						list.add("fly");
						for (String s : list) {
							if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
								list.remove(s);
							}
						}
						Collections.sort(list);
						return list;
					}
					if (args[1].equalsIgnoreCase("fly"))
					{
						if (args.length == 2)
						{
							list.add("true");
							list.add("false");
							list.add("toggle");
							Collections.sort(list);
							return list;
						}
						if ((args.length == 3) && (!args[2].equalsIgnoreCase("true")) && (!args[2].equalsIgnoreCase("false")) && (!args[2].equalsIgnoreCase("toggle")))
						{
							list.add("true");
							list.add("false");
							list.add("toggle");
							for (String s : list) {
								if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
									list.remove(s);
								}
							}
							Collections.sort(list);
							return list;
						}
					}
				}
			}
		}
		return null;
	}
}
