package com.gmail.jpk.stu.AmulyzeRPG;

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
}
