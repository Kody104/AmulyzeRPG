package com.gmail.jpk.stu.AmulyzeListeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;
import com.gmail.jpk.stu.Recipes.RollItem;
import com.gmail.jpk.stu.PlayerData.GamePlayer.RoleType;
import com.gmail.jpk.stu.Roles.Role;
import com.gmail.jpk.stu.Roles.RoleTask;

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
	
	private Random random; //Used for random events
	
	/**
	 * The default constructor for this class. Creates and registers this object to the plugin.
	 * 
	 * @author TSHC, Kody104
	 * @param plugin The plugin that runs this
	 */
	public BasicListener(AmulyzeRPG plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin); //Registers to plugin
		this.random = new Random();
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		GamePlayer player = Global.getPlayer(e.getPlayer());
		
		if (player.getRoleType() == null)
			return;
		
		Material type = e.getBlock().getType();
		Role role = player.getRole();
		RoleType roleType = player.getRoleType();
		
		if (roleType == RoleType.FARMER) {
			switch (type)
			{
				case CROPS:
					role.earnReward(RoleTask.FARM_WHEAT);
				break;
				
				case MELON_BLOCK:
					role.earnReward(RoleTask.FARM_MELON);
				break;
				
				case CARROT:
					role.earnReward(RoleTask.FARM_CARROT);
				break;
			
				default:
					break;
			}
		} 
		else if (roleType == RoleType.MINER) {			
			switch (type) {
				case COAL_ORE:
					role.earnReward(RoleTask.MINE_COAL);
				break;
				
				case IRON_ORE:
					role.earnReward(RoleTask.MINE_IRON);
				break;
				
				case GOLD_ORE:
					role.earnReward(RoleTask.MINE_GOLD);
				break;
				
				case DIAMOND_ORE:
					role.earnReward(RoleTask.MINE_DMND);
				break;
				
				default:
					break;
			}
		}
		else if (roleType == RoleType.BREW_MASTER) {
			switch (type) {
				case NETHER_WARTS:
					role.earnReward(RoleTask.EARN_WARTS);
				break;
				
				default:
					break;
			}
		}
	}
	
	/**
	 * 
	 * Called when a player enters the bed. If the player lies down, the plugin
	 * will do a count of how many other players are laying in bed. If a certain percentage
	 * of the players on the server are sleeping then time will advance to just before dawn.
	 * This percentage is calculated depending on the number of players online.
	 * 
	 * @param e The invoked event
	 */
	@EventHandler
	public void onPlayerLieDown(PlayerBedEnterEvent e) {
		Player[] players = Bukkit.getOnlinePlayers(); //Online Players
		Player rester = e.getPlayer(); //The player that has laid down
		World world = rester.getWorld(); //**********THIS IS A TEMPORARY FIX; WE NEED TO ACCOUNT FOR PLAYERS IN THE NETHER
		Server server = rester.getServer(); //Get the server the player is on
		int total = players.length; //Number of online players
		int part = 1; //The amount of players in bed. 1 because the player that has laid down isn't counted.
		
		for (Player player : players) {
			if (player.isSleeping()) {
				++part;
			}
		}
		
		double percentThreshhold = (total < 6 ? 0.50 : 0.75); //This is tenative...will be updated as needed
		
		if ( ((double)(part / total) > percentThreshhold) ) {
			server.broadcastMessage(ChatColor.GOLD + "Enough players are sleeping! Advancing time...");
			world.setTime(22800); //A few ticks before dawn
		}
		else {
			server.broadcastMessage(ChatColor.GOLD + "Not enough players sleeping to advance time yet! " + (double)(part / total));
		}
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
		Player player = e.getPlayer(); //The player that logged in
		String joinMessage = "";
		
		if (!player.hasPlayedBefore()) {
			joinMessage = player.getName() + " has begun their adventure!";
			
			//TODO: What should we send the player on their first join?
			//    : Basic commands? Class info? Link to a wiki? (TSHC)
			AmulyzeRPG.sendMessage(player, "Welcome to Amulyze!");
		} 
		else {
			joinMessage = player.getDisplayName() + " has returned to their adventure!";
			
			AmulyzeRPG.sendMessage(player, "Welcome back!");
			player.performCommand("memos");
		}
		
		e.setJoinMessage(joinMessage);
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
		GamePlayer player = Global.getPlayer(p);
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
		if(!(Global.containsPlayer(p))) {
			Global.AllPlayers.put(p.getUniqueId(), new GamePlayer(p)); //Creates new player
			p.setDisplayName(Global.AllPlayers.get(p.getUniqueId()).getPlayerName());
		}
		else {
			p.setLevel(Global.AllPlayers.get(p.getUniqueId()).getLvl());
			p.setDisplayName(Global.AllPlayers.get(p.getUniqueId()).getPlayerName());//Sets the player's level to his lvl
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {// Realistic talking. Player's have to be within 50 blocks to hear chat.
		Player sender = e.getPlayer(); //The player who fired this event
		GamePlayer player = Global.getPlayer(sender); //Gets the GamePlayer representation
		List<Player> InRange = new ArrayList<Player>(); //Collection of all players in range of chat
		String message = e.getMessage(); //The message that this player said
		int range = 50; //The range at which the message can be heard.
		
		if (message.equals(message.toUpperCase())) //If the player is using caps, the range will be further.
			range = 100;

		AmulyzeRPG.sendMessage(sender, "<" + player.getPlayerName() + "> " + message); //Sends chatter what they sent		
		
		for(Entity entity : sender.getNearbyEntities(range, range, range)){ // For every entity near player with 50 blocks
			if(entity instanceof Player) { // If entity is a player
				Player recieve = (Player) entity;
				AmulyzeRPG.sendMessage(recieve, "<" + player.getPlayerName() + "> " + e.getMessage()); // Send player the chat
				InRange.add(recieve); // Add the player to a collection
			}
		}
		
		if(InRange.isEmpty()) { // If no one was in range of the chat.
			Global.amChat(sender, "No one is close enough to here you message! Trying shouting or using /global <message>");
		}
		else if(InRange.size() < 5) { // If less than 5 people were in range of the chat.
			String names = InRange.get(0).getName();
			for(int i = 1; i < InRange.size(); i++) {
				names += "," + InRange.get(i).getName();
			}
			
			Global.amChat(sender, names + " has heard your message.");
		}
		else { // If 5 or more people were in range of the chat.
			Global.amChat(sender, InRange.size() + " people have heard your message.");
		}
		
		AmulyzeRPG.info(sender.getName() + ": " + message);
		
		e.setCancelled(true); // Cancels sending it to everyone else
	}
	
	/**
	 * This method handles when a player breaks a block. If the player is a miner, they will earn exp
	 * from mining an ore. Otherwise, no experience is given.
	 * 
	 * @author Kody104
	 * @since AmulyzeRPG 0.1
	 * @param e The invoked event
	 */
	@EventHandler
	public void onBlockGiveExp(BlockExpEvent e) {
		e.setExpToDrop(0);
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
			/*
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
			*/
			case PLAYER: //If the victim is a player
				if(e.getDamager() instanceof Player) { //If attacker is player
					Player attacker = (Player) e.getDamager();
					GamePlayer aPlayer = Global.getPlayer(attacker);
					Player victim = (Player) le;
					GamePlayer vPlayer = Global.getPlayer(victim);
					double Dmg = aPlayer.getAtk();
					AmulyzeRPG.info("Dmg: " + Dmg);
					for(int i = 0; i < aPlayer.getCurrentItems().size(); i++) { // For all roll items
						if(aPlayer.hasRollItem(i)) { // Safety check for null pointer
							RollItem ri = aPlayer.getRollItem(i); // Get roll item
							if(ri.getIsActive()) { // If that roll item is active
								if(ri.getAbility().getReqClassType() == aPlayer.getClassType()) {
									if(ri.getAbility().getName().equalsIgnoreCase("beserkrage")) {
										Dmg += (aPlayer.getAtk() * 0.30d);
									}
									if(ri.getAbility().getName().equalsIgnoreCase("ambush")) {
										Dmg *= 1.5d;
										AbilityMethod.PlayerDestealth(attacker, ri);
									}
								}
							}
							else if(ri.getAbility().getName().equalsIgnoreCase("lifesteal")) {
								double heal = Dmg * 0.15d;
								if(attacker.getHealth() + heal <= 20) {
									attacker.setHealth(attacker.getHealth() + heal);
									Global.amChat(attacker, String.format("Your attack is healing you for %.2f!", heal));
								}
							}
						}
					}
					if(vPlayer.hasActiveAbility()) { // If victim has an active ability
						for(int i = 0; i < vPlayer.getCurrentItems().size(); i++) { // For all roll items
							if(vPlayer.hasRollItem(i)) { // Safety check for null pointer
								RollItem ri = vPlayer.getRollItem(i); // Get roll item
								if(ri.getIsActive()) { // If that roll item is active
									if(ri.getAbility().getReqClassType() == vPlayer.getClassType()) {
										if(ri.getAbility().getName().equalsIgnoreCase("beserkrage")) {
											Dmg -= (vPlayer.getAmr() * 0.70d);
										}
										if(ri.getAbility().getName().equalsIgnoreCase("enrage")) {
											Dmg -= (vPlayer.getAmr() * 1.50d);
										}
									}
								}
							}
						}
					}
					e.setDamage(Dmg);
					
					Global.amChat(attacker, String.format("You hit %s for \u00A74%.2f\u00A7f damage!", victim.getName(), Dmg));
					Global.amChat(victim, String.format("%s has hit you for \u00A74%.2f\u00A7f damage!", attacker.getName(), Dmg));
	
					break;
				}
				else {
					if(e.getDamager() instanceof Fireball) {
						Player victim = (Player) le;
						double Dmg = e.getDamage();
						e.setDamage(Dmg);
						
						Global.amChat(victim, String.format("A fireball has hit you for \u00A74%.2f\u00A7f damage", Dmg));
						break;
					}
					else if(e.getDamager() instanceof LightningStrike) {
						Player victim = (Player) le;
						double Dmg = e.getDamage();
						e.setDamage(Dmg);
						Global.amChat(victim, String.format("Lightning has hit you for \u00A74%.2f\u00A7f damage!", Dmg));
						break;
					}
					else if(e.getDamager() instanceof Arrow) {
						Player victim = (Player) le;
						double Dmg = e.getDamage();
						e.setDamage(Dmg);
						Arrow arrow = (Arrow) e.getDamager();
						
						if(arrow.getShooter() instanceof Player) {
							Player attacker = (Player) arrow.getShooter();
							Global.amChat(attacker, String.format("Your arrow has hit %s for \u00A74%.2f\u00A7f damage!", victim.getName(), Dmg));
						}
						
						if(ArrowTask.getElementalArrows().containsKey(arrow)) { // If the arrow is an elemental arrow
							victim.addPotionEffect(new PotionEffect(ArrowTask.getElementalArrows().get(arrow), 75, 0)); // Add the element to the player
							Global.amChat(victim, String.format("A %s arrow has hit you for \u00A74%.2f\u00A7f damage!", ArrowTask.getElementalArrows().get(arrow).getName().toLowerCase(), Dmg));
							ArrowTask.getElementalArrows().remove(arrow); // Remove arrow from list of arrows
							break;
						}
						else {
							Global.amChat(victim, String.format("An arrow has hit you for \u00A74%.2f\u00A7f damage!", Dmg));
							break;
						}
					}
				}
				break;
			/*
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
			*/
			default: // If victim is anything else
				if(e.getDamager() instanceof Player) {
					Player attacker = (Player) e.getDamager();
					GamePlayer player = Global.getPlayer(attacker);
					double Dmg = player.getAtk();
					AmulyzeRPG.info("Dmg: " + Dmg);
					if(player.hasActiveAbility()) { // If player has an active ability
						for(int i = 0; i < player.getCurrentItems().size(); i++) { // For all roll items
							if(player.hasRollItem(i)) { // Safety check for null pointer
								RollItem ri = player.getRollItem(i); // Get roll item
								if(ri.getIsActive()) { // If that roll item is active
									if(ri.getAbility().getReqClassType() == player.getClassType()) {
										if(ri.getAbility().getName().equalsIgnoreCase("beserkrage")) { // If it's beserk rage
											Dmg += (player.getAtk() * 0.30d);
										}
										if(ri.getAbility().getName().equalsIgnoreCase("ambush")) {
											Dmg *= 1.5d;
											AbilityMethod.PlayerDestealth(attacker, ri);
										}
									}
								}
							}
						}
					}
					e.setDamage(Dmg);
					
					Global.amChat(attacker, String.format("You hit %s for \u00A74%.2f\u00A7f damage!", le.getType(), Dmg));
					break;
				}
				else if(e.getDamager() instanceof Arrow) {
					double Dmg = e.getDamage();
					e.setDamage(Dmg);
					Arrow arrow = (Arrow) e.getDamager();
					
					if(arrow.getShooter() instanceof Player) {
						Player attacker = (Player) arrow.getShooter();
						Global.amChat(attacker, String.format("Your arrow has hit %s for \u00A74%.2f\u00A7f damage!", le.getType(), Dmg));
					}
					
					if(ArrowTask.getElementalArrows().containsKey((Arrow)e.getDamager())) { // If the arrow is an elemental arrow
						le.addPotionEffect(new PotionEffect(ArrowTask.getElementalArrows().get(arrow), 75, 0)); // Add the arrow's element to the player
						ArrowTask.getElementalArrows().remove(arrow); // Remove the arrow from the list of arrows
						break;
					}
				}
				break;
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) { // Beserkers don't take fall damage
		if(e.getEntity() instanceof Player) {
			if(e.getCause() == DamageCause.FALL) {
				Player victim = (Player) e.getEntity();
				GamePlayer player = Global.getPlayer(victim);
				
				if(player.getClassType() == GamePlayer.ClassType.BESERKER) {
					AmulyzeRPG.info("Player's classtype is beserker");
					e.setCancelled(true);
				}
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
			GamePlayer player = Global.getPlayer(p);
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
			if(player.getClassType() != null) { // If player has classtype
				if(p.getItemInHand().hasItemMeta()) { // Safety check for itemmeta
					if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Ability")) { // If item is ours
						Inventory in = p.getInventory();
						int index = -1;
						for(int i = 0; i < 4; i++) { // Run through the first 4 items of the quickbar
							if(in.getContents()[i] != null) { // Safety check for null pointers
								if(in.getContents()[i].equals(p.getItemInHand())) { // If they have our item in one of those slots
									index = i;
								}
							}
						}
						if(index > -1 && index < 4) { // If index was changed
							if(player.getRollItem(index) != null) { // Safety check to make sure it's there
								RollItem item = player.getRollItem(index);
								if(item.getAbility().getReqClassType() == player.getClassType()){ // Make sure they are the right classtype
									if(item.getAbility().getName().equalsIgnoreCase("backstep")) {
										if(System.currentTimeMillis() >= item.getNextTime()) { // Cooldown equation
											Vector vec = p.getLocation().getDirection(); // Get's player's direction vector
											vec.multiply(-1); // Inverts the vector
											p.setVelocity(vec); // Makes the player vault away
											Global.amChat(p, "You have backstepped away from " + e.getRightClicked().getType());
											item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
										}
										else {
											Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
										}
									}
									else if(item.getAbility().getName().equalsIgnoreCase("blind")) {
										if(System.currentTimeMillis() >= item.getNextTime()) {
											AbilityMethod.BlindPlayer(le, p, item);
										}
										else {
											Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCloseInventory(InventoryCloseEvent e) { // Checks if player drops ability to reset HashMap
		Player p = (Player) e.getPlayer();
		GamePlayer player = Global.getPlayer(p);
		Inventory inven = p.getInventory();
		for (int i = 0; i < 4; i++) { // For the first four slots on the quickbar
			if (inven.getContents()[i] != null) {
				if (inven.getContents()[i].hasItemMeta()) { // Safety check for null pointer
					if (!(inven.getContents()[i].getItemMeta().getDisplayName().equalsIgnoreCase("Ability"))) {
						if (player.hasRollItem(i)) {
							player.getRollItem(i).setIsActive(false);
							player.deleteRollItem(i); // If the player had a roll item there, it deletes it from the hashmap
						}
					}
					// TODO: Figure out how to drag roll items back into one of the first four slots
				}
			} 
			else {
				if (player.hasRollItem(i)) {
					player.getRollItem(i).setIsActive(false);
					player.deleteRollItem(i);
				}
			}
		}
		for (int i = 4; i < inven.getSize(); i++) { // Check rest of inventory for roll items that shouldn't be there
			if (inven.getContents()[i] != null) {
				if (inven.getContents()[i].hasItemMeta()) { // Safety check for null pointer
					if ((inven.getContents()[i].getItemMeta().getDisplayName().equalsIgnoreCase("Ability"))) {
						inven.clear(i);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent e) {
		if(e.getEntity() instanceof Player) { // If shooter is player
			Player p = (Player) e.getEntity();
			GamePlayer player = Global.getPlayer(p);
			if(player.getClassType() != null) { // If player has a classtype
				Inventory in = p.getInventory();
				for(int i = 0; i < 4; i++) { // For the first for slots of their inventory
					if(in.getContents()[i] != null) { // If the inventory slot isn't empty
						if(in.getContents()[i].hasItemMeta()) { // If the item has itemmeta
							if(in.getContents()[i].getItemMeta().getDisplayName().equalsIgnoreCase("Ability")) { // If the item is ours
								if(player.getRollItem(i) != null) { // Safety check to make sure it's there
									RollItem item = player.getRollItem(i);
									if(item.getAbility().getReqClassType() == player.getClassType()) { // If the ability is the same as the player's classtype
										if(item.getAbility().getName().equalsIgnoreCase("poisonshot")) { // Ability: POISONSHOT
											Arrow arrow = (Arrow) e.getProjectile();
											ArrowTask.getElementalArrows().put(arrow, PotionEffectType.POISON); // Add to elemental arrows list
											if(!ArrowTask.getIsRunning()) { // If cleanup is already running, don't reinitiliaze it
												new ArrowTask().runTaskLater(plugin, 250); // Else run it ~10 secs later
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("flameshot")) {
											Arrow arrow = (Arrow) e.getProjectile();
											arrow.setFireTicks(15);
										}
										// TODO: Figure out how to fire more than one arrow
										/* else if(item.getAbility().getName().equalsIgnoreCase("trishot")) {
											Arrow one = p.getWorld().spawn(p.getEyeLocation().add(p.getLocation().getDirection()), Arrow.class);
											one.getLocation().setX(one.getLocation().getX() - 1.5d);
											one.getLocation().setZ(one.getLocation().getZ() - 1.5d);
											one.setVelocity(p.getLocation().getDirection().multiply(3.0d));
											one.setShooter(p);
											Arrow two = p.getWorld().spawn(p.getEyeLocation().add(p.getLocation().getDirection()), Arrow.class);
											two.getLocation().setX(two.getLocation().getX() + 1.5d);
											two.getLocation().setZ(two.getLocation().getZ() + 1.5d);
											two.setVelocity(p.getLocation().getDirection().multiply(3.0d));
											two.setShooter(p);
											Arrow three = p.getWorld().spawn(p.getEyeLocation().add(p.getLocation().getDirection()), Arrow.class);
											three.getLocation().setX(three.getLocation().getX() + 2.5d);
											three.getLocation().setZ(three.getLocation().getZ() + 2.5d);
											three.setVelocity(p.getLocation().getDirection().multiply(3.0d));
											three.setShooter(p);
										}
										*/
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) { 
		Player p = e.getPlayer();
		GamePlayer player = Global.AllPlayers.get(p.getUniqueId());
		if(player.getClassType() != null) {
			if(p.getItemInHand().hasItemMeta()){
				if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Ability")) {
					if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						Inventory in = p.getInventory();
						int index = -1;
						for(int i = 0; i < 4; i++) {
							if(in.getContents()[i] != null) {
								if(in.getContents()[i].equals(p.getItemInHand())) {
									index = i;
								}
							}
						}
						if(index > -1 && index < 4) {
							if(player.getRollItem(index) != null) { // Safety check to make sure it's there
								RollItem item = player.getRollItem(index);
								if(item.getAbility().getReqClassType() == player.getClassType()){
									if(!item.getIsActive()) {
										if(item.getAbility().getName().equalsIgnoreCase("enrage")) { //Ability: ENRAGE
											if(System.currentTimeMillis() >= item.getNextTime()) { // Cooldown equation
												AbilityMethod.EnragePlayer(plugin, p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
												/* Check out task.java (it basically just toggles item off) */
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("hook")) { // Ability: HOOK
											if(System.currentTimeMillis() >= item.getNextTime()) {
												AbilityMethod.HookPlayer(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("ambush")) { // Ability: AMBUSH
											if(System.currentTimeMillis() >= item.getNextTime()) {
												AbilityMethod.PlayerStealth(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("fireball")) { // Ability: FIREBALL
											if(System.currentTimeMillis() >= item.getNextTime()) {
												AbilityMethod.PlayerCastFireball(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("lightning")) { // Ability: LIGHTNING
											if(System.currentTimeMillis() >= item.getNextTime()) {
												AbilityMethod.PlayerCastLightning(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("teleport")) { // Ability: TELEPORT
											if(System.currentTimeMillis() >= item.getNextTime()) {
												AbilityMethod.PlayerCastTeleport(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("leap")) { //Ability: LEAP
											if(System.currentTimeMillis() >= item.getNextTime()) { // Cooldown equation
												AbilityMethod.PlayerLeap(p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
										else if(item.getAbility().getName().equalsIgnoreCase("beserkrage")) { // Ability: BESERKRAGE
											if(System.currentTimeMillis() >= item.getNextTime()) { // Cooldown equation
												AbilityMethod.BeserkRagePlayer(plugin, p, item);
											}
											else {
												Global.amChat(p, String.format("You need to wait %d seconds", ((item.getNextTime() - System.currentTimeMillis()) / 1000)));
											}
										}
									}
								}
							}
						}
						}
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
			GamePlayer player = Global.getPlayer(p);
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
				Global.amChat(killer, "You have slain " + e.getEntityType().toString().toLowerCase());
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
