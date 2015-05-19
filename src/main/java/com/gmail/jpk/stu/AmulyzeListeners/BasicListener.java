package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.Listener;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

public final class BasicListener implements Listener {
	private AmulyzeRPG plugin;
	
	public BasicListener(AmulyzeRPG plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin); //Registers to plugin
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player p = (Player) e.getPlayer();
		if(!(Global.AllPlayers.containsKey(p.getUniqueId()))) {
			Global.AllPlayers.put(p.getUniqueId(), new GamePlayer(p)); //Creates new player
			p.setDisplayName("[Lvl 1] " + p.getName());
			p.setPlayerListName("[Lvl 1] " + p.getName());
		}
		else {
			int iLvl = Global.AllPlayers.get(p.getUniqueId()).getLvl(); //Sets to player's lvl as default
			String lvl = "[Lvl " + iLvl + "] ";
			p.setDisplayName(lvl + p.getName());
			p.setPlayerListName(lvl + p.getName());
		}
	}
	
	@EventHandler
	public void onPlayerExpGain(PlayerExpChangeEvent e) {
		e.setAmount(0); //There is no exp gain for the server yet
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		if(!(entity instanceof Player)) { //Make sure the dead thing isn't a player
			if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) { //Make sure it was an entity attacking
				EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
				if(ev.getDamager() instanceof Player) { // If the killer is the player
					Player killer = (Player) ev.getDamager();
					if(Global.AllPlayers.get(killer.getUniqueId()).getChatOn()) { //Checks for player's chaton
						killer.sendMessage("You have slain " + e.getEntityType());
					}
				}
			}
		}
	}
}
