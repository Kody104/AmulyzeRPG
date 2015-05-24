package com.gmail.jpk.stu.AmulyzeCommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

/**
 * 
 * This class represents the base class for commands to be used in game.
 * 
 * @author Kody104
 * @since AmulyzeRPG 0.1
 */

@SuppressWarnings("deprecation")
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
					sender.sendMessage("You need to be a player to use this command.");
					return true;
				}
			}
			else {
				sender.sendMessage("This command takes zero arguments.");
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
			else {
				sender.sendMessage("This command takes one argument");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("quitclass")) {
			if(args.length == 0) { // No arguments for this command
				Player player = (Player) sender;
				Global.AllPlayers.get(player.getUniqueId()).setClassType(null);
				sender.sendMessage("You have quit your class.");
				return true;
			}
			else {
				sender.sendMessage("This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("quitrole")) {
			if(args.length == 0) { // No arguments for this command
				Player player = (Player) sender;
				Global.AllPlayers.get(player.getUniqueId()).setRole(null);
				sender.sendMessage("You have quit your role");
				return true;
			}
			else {
				sender.sendMessage("This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setclass")) {
			if(args.length == 1) { // Only 1 argument
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(Global.AllPlayers.get(p.getUniqueId()).getClassType() != null) { 
						p.sendMessage("You already have a class!");
						return true;
					}
					else { // If the player doesn't have a classtype, let's the pick
						if(setClass(p.getUniqueId(), args[0])) { // If this method returns true, we have set the classtype
							p.sendMessage("You have selected " + args[0] + " as your class!");
							return true;
						}
						else { // If the method returns false, we weren't able to set the classtype
							p.sendMessage("That's not a class that's playable! Current classes: Archer, Beserker, Mage, Rogue, or Warrior");
							return true;
						}
					}
				}
				else { // Needs to be a player to select a classtype
					sender.sendMessage("You need to be a player to use this command.");
					return true;
				}
			}
			else { // Argument length doesn't match
				sender.sendMessage("This command takes one argument.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setlvl")) {
			if(args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				Bukkit.getServer().getOnlinePlayers();
				if(target == null) { //Checks if player is online
					sender.sendMessage("That player isn't online!");
					return true;
				}
				setLvl(target.getUniqueId(), args[1]); //Sets player's lvl
				sender.sendMessage(args[0] + "'s level changed to " + args[1] + ".");
				return true;
			}
			else { //If argument length doesn't match
				sender.sendMessage("This command takes two arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setrole")) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (Global.AllPlayers.get(player.getUniqueId()).getPlayerRole() != null) {
						sender.sendMessage("You already have a role!");
					}
					else {
						if (setRole(player.getUniqueId(), args[0])) {
							player.sendMessage("You have selected " + args[0] + " as your role!");
							return true;
						} 
						else {
							player.sendMessage("Unknown or mistyped role! Current roles: Brew_Master, Farmer, Miner");
							return true;
						}
					}
				} 
				else {
					sender.sendMessage("Only players may use this command.");
				}
			}
			else {
				sender.sendMessage("This command only takes one argument.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("global")) {
			if (sender instanceof Player) { //Console may not use this command...use /say instead
				if (args.length == 0) { //if the user doesn't say anything
					sender.sendMessage("You must say at least one word!");
					return true;
				} 
				else {
					Player player = (Player) sender;
					sendGlobalMessage(player, args);
					return true;
				}
			} 
			else {
				sender.sendMessage("You must a player to use this command.");
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
	
	private boolean setClass(UUID player, String type) {
		for(GamePlayer.ClassType ct : GamePlayer.ClassType.values()) { // For all classtypes that exist
			if(type.equalsIgnoreCase(ct.toString())) { // If user inputted type equals one of the classtypes
				Global.AllPlayers.get(player).setClassType(ct); // Set player's classtype to itself
				return true;
			}
		}
		return false; // User input didn't match classtypes
	}
	
	private boolean setRole(UUID player, String type) {
		for (GamePlayer.PlayerRole role : GamePlayer.PlayerRole.values()) {
			if (type.equalsIgnoreCase(role.toString())) {
				Global.AllPlayers.get(player).setRole(role);
				return true;
			}
		}
		
		return false;
	}
	
	private void sendGlobalMessage(Player player, String[] args) {
		String message = "";
		
		for (int i = 0; i < args.length; i++) {
			message += (args[i] + " ");
		}
		
		
		for (Player target : Bukkit.getOnlinePlayers()) {
			target.sendMessage(player.getDisplayName() + message);
		}
		
		AmulyzeRPG.info(player.getName() + ": " + message);
		
	}
	
	private void toggleAmChat(UUID player) {
		Global.AllPlayers.get(player).setInfoOn(!Global.AllPlayers.get(player).getInfoOn()); //Sets chatOn to opposite of what it is
	}

}
