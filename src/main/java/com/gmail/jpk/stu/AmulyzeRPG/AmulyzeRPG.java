package com.gmail.jpk.stu.AmulyzeRPG;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.AmulyzeCommands.BasicCommands;
import com.gmail.jpk.stu.AmulyzeListeners.BasicListener;

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
		Global.Load(this);
		BasicCommands exec = new BasicCommands(this);
		this.getCommand("amchat").setExecutor(exec);
		this.getCommand("setlvl").setExecutor(exec);
		new BasicListener(this);
		getLogger().info("AmulyzeRPG  v0.1 has been enabled!");
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
}
