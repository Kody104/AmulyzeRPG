package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class GlobalCommand extends BasicCommand {

	public GlobalCommand(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) { //Console may not use this command...use /say instead
			if (args.length == 0) { //if the user doesn't say anything
				AmulyzeRPG.sendMessage(sender, "You must say at least one word!");
				return true;
			} 
			else {
				Player player = (Player) sender;
				sendGlobalMessage(player, args);
				return true;
			}
		} 
		else {
			AmulyzeRPG.sendMessage(sender, SENDER_NOT_PLAYER);
			return true;
		}
	}
	
	private void sendGlobalMessage(Player player, String[] args) {
		String message = "";
		
		for (int i = 0; i < args.length; i++) {
			message += (args[i] + " ");
		}
		
		
		for (Player target : player.getWorld().getPlayers()) {
			target.sendMessage(player.getDisplayName() + ": " + message);
		}
		
		AmulyzeRPG.info(player.getName() + ": " + message);
		
	}

}
