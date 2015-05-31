package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public class GetLevel extends BasicCommand {

	public GetLevel(AmulyzeRPG plugin) {
		super(plugin);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if(args.length == 1) {
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target == null) { //Checks if player is online
				AmulyzeRPG.sendMessage(sender, "That player isn't online!");
				return true;
			}
			AmulyzeRPG.sendMessage(sender, args[0] + "'s lvl is: " + Global.getPlayer(target).getLvl());
			return true;
		}
		else {
			AmulyzeRPG.sendMessage(sender, "This command takes one argument");
			return true;
		}
	}
	
}
