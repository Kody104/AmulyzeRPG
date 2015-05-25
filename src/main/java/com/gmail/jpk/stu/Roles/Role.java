package com.gmail.jpk.stu.Roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public abstract class Role {
	
	private Player player; //The player that has this role
	private int level; //The level this player is. (Stores direct exp. Their level is the truncated value)
	private double expNeeded; //The amount of exp for the nextLevel. Formula y = 25(1 - 0.95)^x
	private double expGained; //The amount of exp gained so far
	
	public Role(Player player) {
		this.player = player;
		this.level = 1;
		this.expNeeded = 1.25D;
		this.expGained = 0.0;
	}
	
	public void addExp(double amount) {
		expGained += amount;
		
		if (expGained >= expNeeded) {
			level++;
			expNeeded = expNeeded + Math.pow(25 * (1 - 0.95), level);
			System.out.println("New exp needed: " + expNeeded);
						
			switch ((int) level) {
			//More cases to add later
				case 40:
					AmulyzeRPG.sendMessage(player, ChatColor.GOLD + "Congratulations! You have reached max level in " + Global.getPlayer(player).getRoleType());
				break;
				
				default:
					AmulyzeRPG.sendMessage(player, ChatColor.GOLD + "Congratulations! You have reached level " + level + " in " + Global.getPlayer(player).getRoleType());
				break;
			}
		}
		
		if (expGained > expNeeded)
			addExp(0);
	}
	
	public void earnReward(RoleTask task) {
		if (!(getLevel() > task.getMaxLevel())) {
			double exp = task.getExp();
			ItemStack[] rewards = task.getRewards();
			
			Global.amChat(player, "You have earned \u00A7abonus rewards and " + exp + " exp \u00A7ffor being a " + Global.getPlayer(player).getRole());
		
			if (rewards.length > 0)
				player.getInventory().addItem(rewards);
			
			addExp(exp);
		} 
		else {
			AmulyzeRPG.sendMessage(player, "You are too high of a level to recieve exp from this task!");
		}
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
