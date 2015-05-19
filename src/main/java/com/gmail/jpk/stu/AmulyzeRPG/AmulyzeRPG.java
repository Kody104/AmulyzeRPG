package com.gmail.jpk.stu.AmulyzeRPG;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.AmulyzeCommands.BasicCommands;
import com.gmail.jpk.stu.AmulyzeListeners.BasicListener;

public final class AmulyzeRPG extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Global.Load(this);
		BasicCommands exec = new BasicCommands(this);
		this.getCommand("amchat").setExecutor(exec);
		this.getCommand("setlvl").setExecutor(exec);
		new BasicListener(this);
		getLogger().info("AmulyzeRPG  v0.1 has been enabled!");
	}
	
	@Override
	public void onDisable() {
		Global.Save(this);
		getLogger().info("AmulyzeRPG has been disabled!");
	}
}
