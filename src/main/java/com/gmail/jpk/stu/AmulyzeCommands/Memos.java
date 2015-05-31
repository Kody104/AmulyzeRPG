package com.gmail.jpk.stu.AmulyzeCommands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class Memos extends BasicCommand {

	public Memos(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			AmulyzeRPG.sendMessage(sender, "You must be a player to use this command.");
			return true;
		}
		else {
			Player player = (Player) sender;
			GamePlayer gp = Global.getPlayer(player);
			
			if (args.length == 0) {
				if (gp.getMemos().size() == 0) {
					AmulyzeRPG.sendMessage(player, "You currently have no memos.");
					return true;
				}
				
				player.sendMessage("----------Current Memos----------"); //looks better without tag
				for (int i = 0; i < gp.getMemos().size(); i++) {
					player.sendMessage((i + 1) + ". " + gp.getMemos().get(i));
				}
				
				return true;
			} 
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("clear")) {
					AmulyzeRPG.sendMessage(player, "Deleting ALL memos.");
					gp.clearMemos();
					return true;
				} 
				else if (args[0].equalsIgnoreCase("pop")) {
					if (gp.popMemos()) {
						AmulyzeRPG.sendMessage(player, "Deleted first memo");
						return true;
					}
					else {
						AmulyzeRPG.sendMessage(player, "You do not have any memos to pop!");
						return true;
					}
				}
				else {
					return false;
				}
			}
			else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
				int index = 0;
				
				try {
					index = Integer.parseInt(args[1]);
				} 
				catch (NumberFormatException e) {
					AmulyzeRPG.sendMessage(player, ("\"" + args[1] + "\" isn't quite a number..."));
					return true;
				}
				
				if (gp.removeMemo(index - 1)) {
					AmulyzeRPG.sendMessage(player, "Deleting reminder number " + index);
					return true;
				} 
				else {
					AmulyzeRPG.sendMessage(player, "A memo with this number does not exist.");
					return true;
				}
			} else if (args.length > 2) {
				if (args[0].equalsIgnoreCase("add")) {
					String memo = "";
					
					for (int i = 1; i < args.length; i++) {
						memo += (args[i] + " ");
					}
					
					if (gp.getMemos() == null) {
						gp.setMemos(new ArrayList<String>());
					}
					
					if (gp.addMemo(memo)) {
						AmulyzeRPG.sendMessage(player, "Added memo: " + memo);
						return true;
					}
					else {
						AmulyzeRPG.sendMessage(player, "Memo cap has been reached! Try deleting a few before adding more.");
						return true;
					}
				}
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	
}
