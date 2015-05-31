package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class GetClass extends BasicCommand {

	public GetClass(AmulyzeRPG plugin) {
		super(plugin);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 1) {
			Player player = Bukkit.getPlayer(args[0]);
			GamePlayer gpl = null;
			
			if (player == null) {
				AmulyzeRPG.sendMessage(sender, args[0] + " is not online...or doesn't exist!");
				return true;
			}
			
			gpl = Global.getPlayer(player);
			
			if (gpl.getClassType() == null) {
				AmulyzeRPG.sendMessage(sender, "This player does not currently have a class.");
				return true;
			}
			
			AmulyzeRPG.sendMessage(sender, player.getDisplayName() + " is currently a " + gpl.getClassColor() + gpl.getClassType());
			return true;
		}
		else {
			AmulyzeRPG.sendMessage(sender, "This command takes 1 argument.");
			return true;
		}		
	}
	
	

	
}
