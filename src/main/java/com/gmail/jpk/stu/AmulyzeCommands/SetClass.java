package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class SetClass extends BasicCommand {

	public SetClass(AmulyzeRPG plugin) {
		super(plugin);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 1) {
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				GamePlayer gpl = Global.getPlayer(player);
				
				if (gpl.getClassType() != null) {
					AmulyzeRPG.sendMessage(player, "You already have a class!");
					return true;
				}
				
				if (setClass(gpl, args[0])) {
					String color = ChatColor.BOLD + "" + gpl.getClassColor();
					AmulyzeRPG.sendMessage(player, "You have selected " + color + gpl.getClassType() + ChatColor.RESET + " as your class!");
					AmulyzeRPG.info(player.getName() + " has set their class to " + gpl.getClassType());
					return true;
				} 
				else {
					AmulyzeRPG.sendMessage(player, '\"' + args[0] + "\" is not a class. Perhaps you mistyped?");
					return true;
				}
				
			} 
			else {
				AmulyzeRPG.sendMessage(sender, this.SENDER_NOT_PLAYER);
				return true;
			}
			
		} 
		else {
			
		}
		
		
		return false;
	}
	
	private boolean setClass(GamePlayer gpl, String type) {
		for (GamePlayer.ClassType ct : GamePlayer.ClassType.values()) {
			if (type.equalsIgnoreCase(ct.toString())) {
				gpl.setClassType(ct);
				return true;
			}
		}
		
		return false;
	}
	

}
