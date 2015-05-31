package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;
import com.gmail.jpk.stu.Recipes.RollItem;

public class AbilityMethod {
	
	public static double PlayerSetMinecraftHealth(double Dmg, GamePlayer player) {
		player.setHp(player.getHp() - Dmg);
		if(player.getHp() <= 0) 
			return 0.0d;
		return (player.getHp() * 20.0d) / player.getMaxHp();
	}
	
	public static void HookPlayer(Player p, RollItem item) {
		GamePlayer player = Global.getPlayer(p);
		Block targetBlock = p.getTargetBlock(null, 30); // The block the player is targeting
		for(Entity ent : targetBlock.getChunk().getEntities()) { // For the entities inside of the chunk
			/* If the entity is alive, and the entity is within 2 blocks of the target block */
			if(ent instanceof LivingEntity && targetBlock.getLocation().distance(ent.getLocation()) <= 2) {
				if(ent instanceof Player) {
					Player ep = (Player) ent;
					if(ep.getUniqueId().equals(p.getUniqueId())) {
						break;
					}
					
				}
				double Dmg = 0.0d;
				Dmg = (item.getAbility().getAmrScale() * player.getAmr());
				LivingEntity le = (LivingEntity) ent;
				le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, item.getAbility().getDuration(), 1));
				le.setHealth(le.getHealth() - Dmg);
				Vector vec = p.getLocation().getDirection().multiply(-2.0d); // Invert the player's direction
				le.setVelocity(vec); // Give the inversion to the entity
				Global.amChat(p, "You have hooked  " + le.getType());
				item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
			}
		}
	}
	
	public static void EnragePlayer(Plugin plugin, Player p, RollItem item) {
		item.setIsActive(true);
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		Global.amChat(p, "You are enraged!");
		/* This used Minecraft ticks (100 milliseconds per tick) */
		new AbilityTask(item.getAbility().getName(), p).runTaskLater(plugin, item.getAbility().getDuration());
	}
	
	public static void BlindPlayer(LivingEntity le, Player p, RollItem item) {
		le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, item.getAbility().getDuration(), 1));  // Adds blind to player
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		Global.amChat(p, "You have blinded " + le.getType());
	}
	
	public static void PlayerStealth(Player p, RollItem item) {
		for(Player others : Bukkit.getWorld(p.getWorld().getName()).getPlayers()) { // Hide player from every other player
			others.hidePlayer(p);
		}
		item.setIsActive(true);
		Global.amChat(p, "You have stealthed!");
	}
	
	public static void PlayerDestealth(Player p, RollItem item) {
		item.setIsActive(false);
		for(Player players : p.getWorld().getPlayers()) {
			players.showPlayer(p);
		}
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		Global.amChat(p, "Your stealth has ended!");
	}
	
	public static void PlayerCastLightning(Player p, RollItem item) {
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		p.getWorld().strikeLightning(p.getTargetBlock(null, 30).getLocation()); //The player's lightning strikes anywhere within 30 blocks
		Global.amChat(p, "You have cast lightning!");
	}
	
	public static void PlayerCastFireball(Player p, RollItem item) {
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		/* Spawns fireball right in front of caster */
		p.getWorld().spawn(p.getEyeLocation().add(p.getLocation().getDirection()), Fireball.class);
		Global.amChat(p, "You have cast fireball!");
	}
	
	public static void PlayerCastTeleport(Player p, RollItem item) {
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
		Location loc = p.getTargetBlock(null, 20).getLocation(); // Sets the teleport block
		loc.setDirection(p.getLocation().getDirection()); // Sets player's direction to look the same
		p.teleport(loc); // Teleports the player to their target within 30 blocks
		Global.amChat(p, "You have cast teleport!");
	}
	
	public static void BeserkRagePlayer(Plugin plugin, Player p, RollItem item) {
		item.setIsActive(true);
		new AbilityTask(item.getAbility().getName(), p).runTaskLater(plugin, item.getAbility().getDuration());
		Global.amChat(p, "You are beserking rage!");
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
	}
	
	public static void PlayerLeap(Player p, RollItem item) {
		Vector vec = p.getLocation().getDirection().multiply(1.5d); // Sets vector to player's direction * 2
		p.setVelocity(vec); // Makes player vault in that direction
		Global.amChat(p, "You have leapt!");
		item.setNextTime(System.currentTimeMillis() + item.getAbility().getCooldown());
	}
}
