package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

/**
 * 
 * This class represents the base class for commands to be used in game.
 * 
 * @author Kody104
 * @since AmulyzeRPG 0.1
 */

public abstract class BasicCommand implements CommandExecutor {

	private AmulyzeRPG plugin;
	protected final String SENDER_NOT_PLAYER = "You must be a player to use this command.";
	
	public BasicCommand(AmulyzeRPG plugin) {
		this.plugin = plugin;
	}
	
	public abstract boolean performCommand(CommandSender sender, String[] args);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (performCommand(sender, args)) {
			return true;
		} 
		
		return false;
	}
	
	public AmulyzeRPG getPlugin() {
		return plugin;
	}
}
