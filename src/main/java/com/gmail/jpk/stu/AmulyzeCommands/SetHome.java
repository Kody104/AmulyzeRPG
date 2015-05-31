package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class SetHome extends BasicCommand {
	
	public SetHome(AmulyzeRPG plugin) {
		super(plugin);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			if(args.length == 0) {
				Player p = (Player) sender;
				p.setBedSpawnLocation(p.getLocation(), true); // Sets player's respawn point
				AmulyzeRPG.sendMessage(p, "Home has been set!");
				return true;
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
				return true;
			}
		}
		else {
			AmulyzeRPG.sendMessage(sender, "This command can only be used by players.");
			return true;
		}		
	}
}
