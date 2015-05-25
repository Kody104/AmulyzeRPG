package com.gmail.jpk.stu.AmulyzeCommands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.Ability;
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
					toggleAmChat(p); //Toggles player amulyze chat info
					AmulyzeRPG.sendMessage(p, "Chat is now set to " + Global.getPlayer(p).getInfoOn());
					return true;
				}
				else { //Needs to be a player to toggle chat
					AmulyzeRPG.sendMessage(sender, "You need to be a player to use this command.");
					return true;
				}
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("roll")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					GamePlayer player = Global.getPlayer(p);
					if(player.getClassType() != null) {
						if(rollItem(p)) {
							AmulyzeRPG.sendMessage(p, "This item has had it's stats rolled!");
							return true;
						}
						else {
							AmulyzeRPG.sendMessage(p, "This item can't be rolled.");
							return false;
						}
					}
					else {
						AmulyzeRPG.sendMessage(p, "You need to use /setclass and choose your class.");
						return true;
					}
				}
				else {
					AmulyzeRPG.sendMessage(sender, "You need to be a player to use this command.");
					return true;
				}
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("getlvl")) {
			if(args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) { //Checks if player is online
					AmulyzeRPG.sendMessage(sender, "That player isn't online!");
					return true;
				}
				AmulyzeRPG.sendMessage(sender, args[0] + "'s lvl is: " + getLvl(target));
				return true;
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes one argument");
				return true;
			}
		}
		else if (cmd.getName().equalsIgnoreCase("memos")) {
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
		}
		else if(cmd.getName().equalsIgnoreCase("quitclass")) {
			if(args.length == 0) { // No arguments for this command
				Player player = (Player) sender;
				GamePlayer gplayer = Global.getPlayer(player);
				
				if (gplayer.getClassType() == null) {
					AmulyzeRPG.sendMessage(sender, "You currently do not have a class!");
					return true;
				}
				
				AmulyzeRPG.sendMessage(sender, "You have quit your class: " + gplayer.getClassColor() + gplayer.getClassType());
				gplayer.setClassType(null);
				return true;
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("quitrole")) {
			if(args.length == 0) { // No arguments for this command
				Player player = (Player) sender;
				GamePlayer gplayer = Global.getPlayer(player);
				
				if (gplayer.getRoleType() == null) {
					AmulyzeRPG.sendMessage(sender, "You currently do not have a role!");
					return true;
				}
				
				AmulyzeRPG.sendMessage(sender, "You have quit your role: " + gplayer.getRoleType());
				
				gplayer.deleteRole();
				return true;
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setclass")) {
			if(args.length == 1) { // Only 1 argument
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(Global.getPlayer(p).getClassType() != null) { 
						AmulyzeRPG.sendMessage(p, "You already have a class!");
						return true;
					}
					else { // If the player doesn't have a classtype, let's the pick
						if(setClass(p, args[0])) { // If this method returns true, we have set the classtype
							AmulyzeRPG.sendMessage(p, "You have selected " + (Global.getPlayer(p).getClassColor()) + args[0] + ChatColor.WHITE + " as your class!");
							return true;
						}
						else { // If the method returns false, we weren't able to set the classtype
							AmulyzeRPG.sendMessage(p, "That's not a class that's playable! Current classes: Archer, Beserker, Mage, Rogue, or Warrior");
							return true;
						}
					}
				}
				else { // Needs to be a player to select a classtype
					AmulyzeRPG.sendMessage(sender, "You need to be a player to use this command.");
					return true;
				}
			}
			else { // Argument length doesn't match
				AmulyzeRPG.sendMessage(sender, "This command takes one argument.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setlvl")) {
			if(args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				Bukkit.getServer().getOnlinePlayers();
				if(target == null) { //Checks if player is online
					AmulyzeRPG.sendMessage(sender, "That player isn't online!");
					return true;
				}
				setLvl(target, args[1]); //Sets player's lvl
				AmulyzeRPG.sendMessage(sender, args[0] + "'s level changed to " + args[1] + ".");
				return true;
			}
			else { //If argument length doesn't match
				AmulyzeRPG.sendMessage(sender, "This command takes two arguments.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setrole")) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (Global.getPlayer(player).getRoleType() != null) {
						AmulyzeRPG.sendMessage(sender, "You already have a role!");
						return true;
					}
					else {
						if (setRole(player, args[0])) {
							AmulyzeRPG.sendMessage(player, "You have selected " + args[0] + " as your role!");
							return true;
						} 
						else {
							AmulyzeRPG.sendMessage(player, "Unknown or mistyped role! Current roles: Brew_Master, Farmer, Miner");
							return true;
						}
					}
				} 
				else {
					AmulyzeRPG.sendMessage(sender, "Only players may use this command.");
					return true;
				}
			}
			else {
				AmulyzeRPG.sendMessage(sender, "This command only takes one argument.");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("global")) {
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
				AmulyzeRPG.sendMessage(sender, "You must a player to use this command.");
			}
		}
		return false;
	}
	
	private void setLvl(Player player, String lvl) {
		int iLvl = Global.getPlayer(player).getLvl(); //Sets to player's lvl as default
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
		
		Global.getPlayer(player).setLvl(iLvl); //Sets player level to lvl
		Player p = Bukkit.getServer().getPlayer(player.getUniqueId());
		p.setLevel(iLvl);
		p.setExp(0.0f);
		p.setDisplayName("[Lvl " + iLvl + "] " + p.getName());
		AmulyzeRPG.sendMessage(p, "An admin has set your level to " + iLvl + ".");
	}
	
	private int getLvl(Player player) {
		return Global.getPlayer(player).getLvl(); //Returns player's lvl
	}
	
	private boolean setClass(Player player, String type) {
		for(GamePlayer.ClassType ct : GamePlayer.ClassType.values()) { // For all classtypes that exist
			if(type.equalsIgnoreCase(ct.toString())) { // If user inputted type equals one of the classtypes
				Global.getPlayer(player).setClassType(ct); // Set player's classtype to itself
				return true;
			}
		}
		return false; // User input didn't match classtypes
	}
	
	private boolean setRole(Player player, String type) {
		for (GamePlayer.RoleType role : GamePlayer.RoleType.values()) {
			if (type.equalsIgnoreCase(role.toString())) {
				Global.getPlayer(player).setRoleType(role);
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
	
	private boolean rollItem(Player p) {
		ItemStack i = p.getItemInHand(); // Item in hand
		if(i.getItemMeta().getDisplayName().equalsIgnoreCase("sword a")) { // If the item is ours
			GamePlayer player = Global.getPlayer(p);
			Ability gen = new Ability(player.getClassType()); // Create an ability for it
			player.setAbility(gen);
			AmulyzeRPG.info("" + player.getAbility(gen.getName()).getWhatis());
			ItemMeta meta = i.getItemMeta();
			meta.setLore(null);
			i.setItemMeta(meta); // Set item lore
			meta.setLore(player.getAbility(gen.getName()).getWhatis());
			i.setItemMeta(meta); // Sets item lore
			for(String s : gen.getWhatis()) {
				p.sendMessage(s);
			}
			return true;
		}
		return false;
	}
	
	private void toggleAmChat(Player player) {
		Global.getPlayer(player).setInfoOn(!Global.getPlayer(player).getInfoOn()); //Sets chatOn to opposite of what it is
	}

}
