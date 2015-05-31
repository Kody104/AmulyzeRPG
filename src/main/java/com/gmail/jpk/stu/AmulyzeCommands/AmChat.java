package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;


public class AmChat extends BasicCommand {

	public AmChat(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				toggleAmChat(player);
				
				return true;
			}
			else {
				AmulyzeRPG.sendMessage(sender, "You must be a player to use this command");
				return true;
			}
			
		} 
		else {
			AmulyzeRPG.sendMessage(sender, "This command does not take any arguments.");
			return true;
		}		
	}
	
	private void toggleAmChat(Player player) {
		GamePlayer gpl = Global.getPlayer(player);
		boolean infoOn = !gpl.getInfoOn();
		ChatColor color = infoOn ? ChatColor.GREEN : ChatColor.RED;
		
		gpl.setInfoOn(infoOn);
		
		AmulyzeRPG.sendMessage(player, "You have toggle Amulyze Chat Text to: " + color + gpl.getInfoOn());
	}

	
}
