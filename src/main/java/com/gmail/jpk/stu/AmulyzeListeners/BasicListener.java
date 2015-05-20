package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
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
			p.setDisplayName("[Lvl 0] " + p.getName());
		}
		else {
			p.setLevel(Global.AllPlayers.get(p.getUniqueId()).getLvl());
			p.setDisplayName("[Lvl " + Global.AllPlayers.get(p.getUniqueId()).getLvl() + "] " + p.getName());//Sets the player's level to his lvl
		}
	}
	
	@EventHandler
	public void onBlockGiveExp(BlockExpEvent e) {
		e.setExpToDrop(0); //There is no exp gain from blocks.
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent e) {
		e.setExpToDrop(0); //There is no exp gain from fishing.
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getRightClicked();
			Player p = e.getPlayer();
			if((p.getItemInHand().getType() == Material.WHEAT) || 
					(p.getItemInHand().getType() == Material.CARROT) ||
					(p.getItemInHand().getType() == Material.SEEDS)) {
					switch(le.getType()) { //Can't breed because breeding drops exp.
						case CHICKEN:
							e.setCancelled(true);
							break;
						case COW:
							e.setCancelled(true);
							break;
						case PIG:
							e.setCancelled(true);
							break;
						case SHEEP:
							e.setCancelled(true);
							break;
						default:
							break;
					}
			}
		}
	}
	
	/**
	 * Todo: Figure out how to keep player level's on death/respawn
	 * 
	 **/
	
	@EventHandler
	public void onPlayerLevel(PlayerLevelChangeEvent e) { //Keeps lvl on same page as player's actual level
		Player p = e.getPlayer();
		Global.AllPlayers.get(p.getUniqueId()).setLvl(p.getLevel());
		p.setDisplayName("[Lvl " + p.getLevel() + "] " + p.getName());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) { //Make sure it was an entity attacking
			EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
			if(ev.getDamager() instanceof Player) { // If the killer is the player
				Player killer = (Player) ev.getDamager();
				if(Global.AllPlayers.get(killer.getUniqueId()).getChatOn()) { //Checks for player's chaton
					killer.sendMessage("You have slain " + e.getEntityType().toString().toLowerCase());
				}
				switch(entity.getType()) { //Gives experience depending on who the dead entity is. These are all default minecraft values.
				case BLAZE:
					e.setDroppedExp(10);
					break;
				case CAVE_SPIDER:
					e.setDroppedExp(5);
					break;
				case CHICKEN:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case COW:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case CREEPER:
					e.setDroppedExp(5);
					break;
				case ENDERMAN:
					e.setDroppedExp(5);
					break;
				case ENDER_DRAGON:
					e.setDroppedExp(12000);
					break;
				case GHAST:
					e.setDroppedExp(5);
					break;
				case GIANT:
					e.setDroppedExp(7);
					break;
				case IRON_GOLEM:
					e.setDroppedExp(0);
					break;
				case MAGMA_CUBE:
					e.setDroppedExp(3);
					break;
				case MUSHROOM_COW:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case OCELOT:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case PLAYER: //These are minecraft formulas for total experience a player has at certain levels
					Player dead = (Player) entity;
					int expToDrop = 0;
					if(dead.getLevel() < 16) {
						expToDrop = (int) (Math.pow(dead.getLevel(), 2)) + (6 * dead.getLevel());
					}
					else if(dead.getLevel() < 31) {
						expToDrop = (int) ((2.5 * Math.pow(dead.getLevel(), 2) - (40.5 * dead.getLevel()) + 360));
					}
					else if(dead.getLevel() > 30) {
						expToDrop = (int) ((4.5 * Math.pow(dead.getLevel(), 2) - (162.5 * dead.getLevel()) + 2220));
					}
					e.setDroppedExp(expToDrop);
					break;
				case PIG:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case PIG_ZOMBIE:
					e.setDroppedExp(5);
					break;
				case SHEEP:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case SILVERFISH:
					e.setDroppedExp(5);
					break;
				case SKELETON:
					e.setDroppedExp(5);
					break;
				case SLIME:
					e.setDroppedExp(3);
					break;
				case SNOWMAN:
					e.setDroppedExp(0);
					break;
				case SPIDER:
					e.setDroppedExp(5);
					break;
				case SQUID:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case WITCH:
					e.setDroppedExp(5);
					break;
				case WITHER:
					e.setDroppedExp(50);
					break;
				case WOLF:
					if(killer.getLevel() < 16) {
						e.setDroppedExp(1);
					}
					else {
						e.setDroppedExp(0);
					}
					break;
				case ZOMBIE:
					e.setDroppedExp(5);
					break;
				default:
					e.setDroppedExp(0);
					break;
				}
				return;
			}
		}
		e.setDroppedExp(0); //If it's not a player who kills the entity, drops no exp.
	}
}
