package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;

/**
 * 
 * The basic listener class object listens for events in the game.
 * Depending on the event, it will execute a certain task that varies
 * from greeting the player, granting exp, and managing the database
 * of known players.
 * 
 * @author Kody104, TSHC
 * @since AmulyzeRPG 0.1
 */
public final class BasicListener implements Listener {
	private AmulyzeRPG plugin;
	
	/**
	 * The default constructor for this class. Creates and registers this object to the plugin.
	 * 
	 * @author TSHC, Kody104
	 * @param plugin The plugin that runs this
	 */
	public BasicListener(AmulyzeRPG plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin); //Registers to plugin
	}
	
	/**
	 * This method handles when the player has logged in into the server and joins
	 * the server. It will display a message with their level and class.
	 * 
	 * @author TSHC, Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer(); //The player that logged in
		GamePlayer player = Global.AllPlayers.get(p.getUniqueId());
		if (player != null) { //Should this be here? Everyone should be a gameplayer at this point.
			e.setJoinMessage(player.getPlayerName() + " has joined the adventure!");
		}
	}
	
	/**
	 * This method handles when the player is logging off of the server. 
	 * It will display a message with their level and class.
	 * 
	 * @author TSHC, Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer(); //The player that logged in
		GamePlayer player = Global.AllPlayers.get(p.getUniqueId());
		e.setQuitMessage(player.getPlayerName() + " has taken a break from the adventure.");
	}
	
	
	/**
	 * 
	 * This method handles when the player attempts to login into the server.
	 * If the player is not in the database they will be added.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player p = (Player) e.getPlayer();
		if(!(Global.AllPlayers.containsKey(p.getUniqueId()))) {
			Global.AllPlayers.put(p.getUniqueId(), new GamePlayer(p)); //Creates new player
			p.setDisplayName(Global.AllPlayers.get(p.getUniqueId()).getPlayerName());
		}
		else {
			p.setLevel(Global.AllPlayers.get(p.getUniqueId()).getLvl());
			p.setDisplayName(Global.AllPlayers.get(p.getUniqueId()).getPlayerName());//Sets the player's level to his lvl
		}
	}
	
	/**
	 * This method handles when a player breaks a block and prevents them from earning exp.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onBlockGiveExp(BlockExpEvent e) {
		e.setExpToDrop(0); //There is no exp gain from blocks.
	}
	
	/**
	 * This method handles when a player catches a fish and prevents them from earning exp.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onPlayerFish(PlayerFishEvent e) {
		e.setExpToDrop(0); //There is no exp gain from fishing.
	}
	
	/**
	 * 
	 * This method handles when an entity takes damage. Depending on the source and the level
	 * of the player, the damage system is highly controlled by this method.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onEntityTakeDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) { //Is the victim alive?
			LivingEntity le = (LivingEntity) e.getEntity();
			switch(le.getType()) {
			case BAT:
				break;
			case BLAZE:
				break;
			case CAVE_SPIDER:
				break;
			case CHICKEN:
				break;
			case COW:
				break;
			case CREEPER:
				break;
			case ENDERMAN:
				break;
			case ENDER_DRAGON:
				break;
			case GHAST:
				break;
			case GIANT:
				break;
			case HORSE:
				break;
			case IRON_GOLEM:
				break;
			case MAGMA_CUBE:
				break;
			case MUSHROOM_COW:
				break;
			case OCELOT:
				break;
			case PIG:
				break;
			case PIG_ZOMBIE:
				break;
			case PLAYER: //If the victim is a player
				if(e.getDamager() instanceof Player) { //If attacker is player
					Player attacker = (Player) e.getDamager();
					Player victim = (Player) le;
					double aDmg = Global.AllPlayers.get(attacker.getUniqueId()).getDmg(); //Get attacker dmg
					double vAmr = Global.AllPlayers.get(victim.getUniqueId()).getAmr(); //Get victim amr
					double Dmg = e.getDamage();
					if(aDmg > (vAmr + 0.5d)) { //If attacker's dmg is more than victim's armor + 0.5
						Dmg = aDmg - vAmr;
					}
					else if(aDmg < vAmr) { //If victim has more amr than attacker's dmg
						Dmg = 0.25d;
					}
					else { // If attacker dmg is equal to or less than victim amr + 0.5
						Dmg = 0.5d;
					}
					e.setDamage(Dmg);
					if(Global.AllPlayers.get(attacker.getUniqueId()).getInfoOn()) { //If attacker has chat on
						attacker.sendMessage("You hit " + victim.getName() + " for " + Dmg + " damage!");
					}
					if(Global.AllPlayers.get(victim.getUniqueId()).getInfoOn()) { //If victim has chat on
						victim.sendMessage(attacker.getName() + " hit you for " + Dmg + " damage!"); 
					}
					break;
				}
				break;
			case SHEEP:
				break;
			case SILVERFISH:
				break;
			case SKELETON:
				break;
			case SLIME:
				break;
			case SNOWMAN:
				break;
			case SPIDER:
				break;
			case SQUID:
				break;
			case WITCH:
				break;
			case WITHER:
				break;
			case WOLF:
				break;
			case ZOMBIE:
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 
	 * This method handles when players interact with various objects in the game. It
	 * prevents certain items and entities from giving exp.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e
	 */
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
		if(e.getNewLevel() <= 100) { //Max level is 100
			GamePlayer player = Global.AllPlayers.get(p.getUniqueId());
			player.setLvl(p.getLevel());
			p.setDisplayName(player.getPlayerName());
		}
		else {
			p.setLevel(100); //Reset player level to 100 and exp to 0
			p.setExp(0);
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) { //Make sure it was an entity attacking
			EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
			if(ev.getDamager() instanceof Player) { // If the killer is the player
				Player killer = (Player) ev.getDamager();
				if(Global.AllPlayers.get(killer.getUniqueId()).getInfoOn()) { //Checks for player's chaton
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
						expToDrop = (int) ((Math.pow(dead.getLevel(), 2)) + (6 * dead.getLevel()) / 2); //If their level is below 16, divide exp drop by 2
					}
					else if(dead.getLevel() < 31) {
						expToDrop = (int) (((2.5 * Math.pow(dead.getLevel(), 2) - (40.5 * dead.getLevel()) + 360)) / 3); //If their level is between 16 and 31, divide exp drop by 3
					}
					else if(dead.getLevel() > 30) {
						expToDrop = (int) (((4.5 * Math.pow(dead.getLevel(), 2) - (162.5 * dead.getLevel()) + 2220)) / 4); //If their level is above 30, divide exp drop by 4
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
