package com.gmail.jpk.stu.AmulyzeCommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

/**
 * 
 * This class represents the base class for commands to be used in game.
 * 
 * @author Kody104
 * @since AmulyzeRPG 0.1
 */
public class BasicCommands implements CommandExecutor {

	private AmulyzeRPG plugin;
	
	/**
	 * The default constructor for this class. Creates an instance of the
	 * commands that may be used.
	 * @param plugin 
	 */
	public BasicCommands(AmulyzeRPG plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("amchat")) {
			if(args.length == 0) { // No arguments for this command
				if(sender instanceof Player) {
					Player p = (Player) sender;
					toggleAmChat(p.getUniqueId()); //Toggles player amulyze chat info
					p.sendMessage("Chat on is now set to " + Global.AllPlayers.get(p.getUniqueId()).getInfoOn());
					return true;
				}
				else { //Needs to be a player to toggle chat
					sender.sendMessage("You need to be a player!");
					return true;
				}
			}
			else {
				sender.sendMessage("Too many arguments!");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("getlvl")) {
			if(args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) { //Checks if player is online
					sender.sendMessage("That player isn't online!");
					return true;
				}
				sender.sendMessage(args[0] + "'s lvl is: " + getLvl(target.getUniqueId()));
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setlvl")) {
			if(args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) { //Checks if player is online
					sender.sendMessage("That player isn't online!");
					return true;
				}
				setLvl(target.getUniqueId(), args[1]); //Sets player's lvl
				sender.sendMessage(args[0] + "'s level changed to " + args[1] + ".");
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
		
		if(iLvl > 100) { //Make sure lvl parameter's are inbounds.
			iLvl = 100;
		}
		else if(iLvl < 0) {
			iLvl = 0;
		}
		
		Global.AllPlayers.get(player).setLvl(iLvl); //Sets player level to lvl
		Player p = Bukkit.getServer().getPlayer(player);
		p.setLevel(iLvl);
		p.setExp(0.0f);
		p.setDisplayName("[Lvl " + iLvl + "] " + p.getName());
		p.sendMessage("An admin has set your level to " + iLvl + ".");
	}
	
	private int getLvl(UUID player) {
		return Global.AllPlayers.get(player).getLvl(); //Returns player's lvl
	}
	
	private void toggleAmChat(UUID player)
	{
		Global.AllPlayers.get(player).setInfoOn(!Global.AllPlayers.get(player).getInfoOn()); //Sets chatOn to opposite of what it is
	}

}
