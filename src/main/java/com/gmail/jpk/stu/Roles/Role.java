package com.gmail.jpk.stu.Roles;

import java.io.Serializable;

import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum RoleType {
		ALCHEMIST, FARMER, MINER, ENGINEER;
	}
	
	private RoleType roleType; //What role this player is
	private int level;         //What level they are
	private int expGained;     //How much exp. they have gained
	private int expNeeded;     //How much exp. they need to level up
	
	public Role(RoleType type) {
		this.roleType = type;
		this.level = 0;
		this.expGained = 0;
		this.expNeeded = calcExpNeeded();
	}
	
	private int calcExpNeeded() {
		double raw = Math.ceil((level * 10) + 4 * (Math.pow(1.2, level + 1)));
		return (int) raw;
	}
	
	public void grantExperience(Player player, int amount) {
		expGained += amount;
		
		while (expGained >= expNeeded) {
			level++;
			expNeeded = calcExpNeeded();
			
			switch (level) {
				default:
					AmulyzeRPG.sendMessage(player, "Congratulations! You have reached level " + level + " in the " + roleType + " role!");
				break;
			}
		}		
	}
	
	public void performRoleTask(Player player, RoleTask task) {
		
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public int getExpGained() {
		return expGained;
	}
	
	public int getExpNeeded() {
		return expNeeded;
	}
	
	public void setRoleType(RoleType type) {
		this.roleType = type;
	}
}