package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class QuitRole extends BasicCommand {

	public QuitRole(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				GamePlayer gpl = Global.getPlayer(player);
				
				if (!gpl.hasRoleType()) {
					AmulyzeRPG.sendMessage(player, "You do not currently have a role.");
					return true;
				}
				
				gpl.setRoleType(null);
				AmulyzeRPG.sendMessage(player, "You have quit your role.");
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
