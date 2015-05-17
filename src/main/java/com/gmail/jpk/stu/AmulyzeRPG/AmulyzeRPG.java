package com.gmail.jpk.stu.AmulyzeRPG;

import org.bukkit.plugin.java.JavaPlugin;

public final class AmulyzeRPG extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getLogger().info("AmulyzeRPG Version 0.1 has been Enabled!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("AmulyzeRPG has been disabled!");
	}
}
