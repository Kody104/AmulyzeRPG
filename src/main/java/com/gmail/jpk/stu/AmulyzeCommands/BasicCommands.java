package com.gmail.jpk.stu.AmulyzeCommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public class BasicCommands implements CommandExecutor {

	private AmulyzeRPG plugin;
	
	public BasicCommands(AmulyzeRPG plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setlvl")) {
			if(args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) { //Checks if player is online
					sender.sendMessage("That player isn't online!");
					return true;
				}
				setLvl(target.getUniqueId(), args[1]); //Sets player's lvl
				return true;
			}
			else { //If argument length doesn't match
				sender.sendMessage("Must have two arguments!");
				return true;
			}
		}
		return false;
	}
	
	private void setLvl(UUID player, String lvl) {
		int iLvl = Global.AllPlayers.get(player).getLvl(); //Sets to player's lvl as default
		try{
			iLvl = Integer.parseInt(lvl); //Parses string to integer hopefully
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		Global.AllPlayers.get(player).setLvl(iLvl); //Sets player level to lvl
	}

}
