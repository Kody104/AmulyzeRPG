package com.gmail.jpk.stu.Roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public abstract class Role {
	
	private Player player; //The player that has this role
	private int level; //The level this player is. (Stores direct exp. Their level is the truncated value)
	private int expNeeded; //The amount of exp for the nextLevel. 
	private int expGained; //The amount of exp gained so far
	
	public Role(Player player) {
		this.player = player;
		this.level = 1;
		this.expNeeded = getExpNeeded();
		this.expGained = 0;
	}
	
	public void earnExp(int amount) {
		expGained += amount; //add the specified amount
		
		while (expGained >= expNeeded) { //level up while expGained is higher than expNeeded
			level++; 
			expNeeded = getExpNeeded(); //Recalculate
			
			switch (level) {
				default:
					AmulyzeRPG.sendMessage(player, "Congralutions! You have reached level "+ ChatColor.GREEN + level + ChatColor.WHITE + " in the role: " + ChatColor.GREEN + Global.getPlayer(player).getRole());
					break;
			}
		}
	}
	
	public void earnReward(RoleTask task) {
		ItemStack[] items = task.getItems();
		
//		if (items.length != 0)
//			player.getInventory().addItem(task.getItems());
		
		if (level > task.getMaxLevel()) {
			AmulyzeRPG.sendMessage(player, "You are too high of a level to earn experience for this task! No worries, you still earn the bonus items.");
			return;
		}
		
		AmulyzeRPG.sendMessage(player, "You have earned " + task.getExpGiven());
		
		earnExp(task.getExpGiven());
	}
	
	public int getExpNeeded() {
		return (int) (level * 10 + Math.ceil(3.5 * Math.pow(1.2, level)));
	}
	
	public int getLevel() {
		return level;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
}
