package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class QuitClass extends BasicCommand {

	public QuitClass(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				GamePlayer gpl = Global.getPlayer(player);
				
				if (gpl.getClassType() == null) {
					AmulyzeRPG.sendMessage(player, "You currently do not have a class.");
					return true;
				}
				
				AmulyzeRPG.sendMessage(player, "You have quit your class.");
				gpl.setClassType(null);
				
				Inventory inven = player.getInventory();
				
				for(int i = 0; i < 4; i++) {
					if(inven.getContents()[i] != null) {
						if(inven.getContents()[i].hasItemMeta()) {
							if(inven.getContents()[i].getItemMeta().getDisplayName().equalsIgnoreCase("Ability")) {
								inven.clear(i);
							}
						}
					}
					if(gpl.hasRollItem(i)) {
						gpl.getRollItem(i).setIsActive(false);
						gpl.deleteRollItem(i);
					}
				}
				
				for(Player players : Bukkit.getWorld(player.getWorld().getName()).getPlayers()) {
					players.showPlayer(player);
				}
				
				return true;
			} 
			else {
				AmulyzeRPG.sendMessage(sender, SENDER_NOT_PLAYER);
				return true;
			}
			
		}
		else {
			AmulyzeRPG.sendMessage(sender, "This command does not take any arguments.");
			return true;
		}		
	}

}
