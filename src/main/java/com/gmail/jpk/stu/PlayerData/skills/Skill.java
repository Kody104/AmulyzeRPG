package com.gmail.jpk.stu.PlayerData.skills;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public abstract class Skill implements Serializable {
	
	private int level;
	private int expNeeded;
	private double expGained;
	
	public enum Skills { 
		ALCHEMY, FARMING, MINING;
	}
	
	public Skill(int level) {
		this.level = level;
		this.expNeeded = calculateExpNeeded();
		this.expGained = 0.0; 
	}
	
	public abstract String toString();
	
	public void earnExperience(Player player, double amount) {
		expGained += amount;
		
		while (expGained >= expNeeded) {
			level++;
			
			expGained -= expNeeded;
			expNeeded = calculateExpNeeded();
			expGained = expGained <= 0 ? 0 : expGained;
			
			switch (level) {
			
			case 40:
				return;
				
				default:
					player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 2.0f);
					AmulyzeRPG.sendMessage(player, ChatColor.GOLD + "Congratulations! You have reached level " + level + " in " + this + "!");
			}
		}
	}
	
	public void performSkillTask(Player player, SkillTask task) {
		double experience = task.getExperience(this.level);
		ItemStack[] rewards = task.getRewards();
		
		if (rollForBonus()) {
			int offs = level / 5 > 0 ? level / 5 > 1 ? 4 : 2 : 0;
			double bonus = 3 * (Math.pow(2, offs + (level / 5))); 
			experience += bonus;
			player.getInventory().addItem(rewards);
			player.getInventory().addItem(rewards);
			
			AmulyzeRPG.sendMessage(player, ChatColor.GOLD + "You have rolled for a " + ChatColor.GREEN + "bonus " + experience + " experience " + ChatColor.GOLD + "and " + ChatColor.GREEN + "double items!");
		} else {
			Global.amChat(player, String.format("You have earned %.2f experience in %s!", experience, this.toString()));
			player.getInventory().addItem(rewards);
		}
		
		this.earnExperience(player, experience);
	}
	
	public void resetSkill() {
		this.level = 1;
		this.expNeeded = calculateExpNeeded();
		this.expGained = 0.0;
	}
	
	public boolean rollForBonus() {
		float chance = (float) (1 + Math.random() * 10);		
		float factor = 0;
		
		if (level <= 5) {
			factor = 9;
		}
		else if (level <= 15) {
			factor = 8;
		}
		else if (level <= 25) {
			factor = 7.5f;
		}
		else if (level <= 35) {
			factor = 7;
		}
		else {
			factor = 6;
		}
		
		return (chance > factor);
	}
	
	public int calculateExpNeeded() {
		return (int) Math.ceil((level * 10.0d) + 4.0d * Math.pow(1.2d, level + 1.0d));
	}
	
	public int getExpNeeded() {
		return expNeeded;
	}
	
	public double getExpGained() {
		return expGained;
	}
	
	public boolean setSkillLevel(int level) {
		if (level > 40 || level <= 0) {
			return false;
		}
		
		this.level = level;
		
		return true;
	}
	
}
