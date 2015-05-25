package com.gmail.jpk.stu.AmulyzeRPG;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.AmulyzeCommands.BasicCommands;
import com.gmail.jpk.stu.AmulyzeListeners.BasicListener;
import com.gmail.jpk.stu.Recipes.Recipes;

/**
 * 
 * The AmulyzeRPG class is non-instantiated class that
 * enables and disables the plugin. 
 * 
 * @since AmulyzeRPG 0.1
 * @author Kody104
 * 
 */

public final class AmulyzeRPG extends JavaPlugin {
	
	public static final String TITLE = "<\u00A75[AmulyzeRPG]\u00A7f>: "; //Unicode because it's easier to put into a string then continually separating them.
	
	/**
	 * This method initiates the plugins; it registers the
	 * event listeners and commands.
	 * 
	 * @since AmulyzeRPG 0.1
	 * @author Kody104
	 */
	@Override
	public void onEnable() {
		info("AmulyzeRPG v0.1 starting up...");
		Global.Load(this);
		Recipes.Init(this);
		BasicCommands exec = new BasicCommands(this);
		info("Registering commands...");
		this.getCommand("amchat").setExecutor(exec);
		this.getCommand("getlvl").setExecutor(exec);
		this.getCommand("global").setExecutor(exec);
		this.getCommand("quitclass").setExecutor(exec);
		this.getCommand("quitrole").setExecutor(exec);
		this.getCommand("setclass").setExecutor(exec);
		this.getCommand("setlvl").setExecutor(exec);
		this.getCommand("setrole").setExecutor(exec);
		this.getCommand("roll").setExecutor(exec);
		this.getCommand("memos").setExecutor(exec);
		new BasicListener(this);
		info("AmulyzeRPG  v0.1 has successfully been enabled!");
	}
	
	/**
	 * This method disables the plugin; it saves all necessary
	 * files and server data.
	 * 
	 * @since AmulyzeRPG 0.1
	 * @author Kody104
	 */
	@Override
	public void onDisable() {
		Global.Save(this);
		getLogger().info("AmulyzeRPG has been disabled!");
	}
	
	public static void info(String text) {
		System.out.println("[AmuylzeRPG]: " + text);
	}
		
	public static void infoFormat(String format, String text) {
		System.out.printf(format + "%n", text);
	}
	
	/**
	 * 
	 * Sends the player a formatted message. This
	 * will allow the plugin's text to remain consistent through-out.
	 * 
	 * @param player the target player who will receive the message
	 * @param text the message to tell the player
	 */
	public static void sendMessage(Player player, String text) {
		player.sendMessage(TITLE + text);
	}
	
	/**
	 * Sends the command sender a formatted message. If the sender
	 * is a player, they will see the colored title, otherwise,
	 * it will be plain. 
	 * 
	 * @param sender the target sender who will receive the message
	 * @param text the message to tell the sender
	 */
	public static void sendMessage(CommandSender sender, String text) {
		String title = (sender instanceof Player) ? TITLE : "[AmulyzeRPG]: ";
		
		sender.sendMessage(title + text);
	}
}
