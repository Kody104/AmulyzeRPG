package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;
import com.gmail.jpk.stu.Roles.Role;
import com.gmail.jpk.stu.Roles.Role.RoleType;

public class SetRole extends BasicCommand {

	public SetRole(AmulyzeRPG plugin) {
		super(plugin);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				GamePlayer gpl = Global.getPlayer(player);
				
				if (gpl.hasRoleType()) {
					AmulyzeRPG.sendMessage(player, "You already are a " + gpl.getRoleType());
					return true;
				}
				
				if (setRole(gpl, args[0])) {
					AmulyzeRPG.sendMessage(player, "Successful set your role to " + gpl.getRoleType());
				}
				else {
					AmulyzeRPG.sendMessage(player, "The role " + args[0] + " is an unknown or mistyped role!");
					return true;
				}
				
			} 
			else {
				AmulyzeRPG.sendMessage(sender, SENDER_NOT_PLAYER);
				return true;
			}
			
			return true;
		} else {
			AmulyzeRPG.sendMessage(sender, "This command only takes one argument.");
			return true;
		}		
	}
	
	private boolean setRole(GamePlayer gpl, String type) {
		for (Role.RoleType rt : RoleType.values()) {
			if (type.equalsIgnoreCase(rt.toString())) {
				gpl.setRoleType(rt);
				return true;
			}
		}
		
		return false;
	}
	
}
