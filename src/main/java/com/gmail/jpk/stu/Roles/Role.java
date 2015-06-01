package com.gmail.jpk.stu.Roles;

import java.io.Serializable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.jpk.stu.AmulyzeRPG.Global;

public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum RoleType {
		FARMER;
	}
	
	private RoleType roleType; //What role this player is
	private int level;         //What level they are
	private int expGained;     //How much exp. they have gained
	private int expNeeded;     //How much exp. they need to level up
	
	public Role(RoleType type) {
		this.roleType = type;
		this.level = 1;
		this.expGained = 0;
		this.expNeeded = calcExpNeeded();
	}
	
	private int calcExpNeeded() {
		double raw = Math.ceil((level * 10) + 4 * (Math.pow(1.2, level + 1)));
		return (int) raw;
	}
	
	public void performRoleTask(Player player, RoleTask task) {
		int exp = task.getExpGiven();
		ItemStack[] stack = task.getItems();
		player.getInventory().addItem(stack);
		expGained += exp;
		Global.amChat(player, "Experience gained: " + expGained);
		updateLevel(player);
	}
	
	public void updateLevel(Player player) {
		while (expGained >= expNeeded) {
			level++;
			
			expGained -= expNeeded;
			expNeeded = calcExpNeeded();
			expGained = expGained <= 0 ? 0 : expGained;
			
			switch (level) {
				default:
					player.sendMessage("Congratulations! You have reached " + roleType + " level " + level);
			}
		}
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
	
	public int getLevel() {
		return level;
	}
	
	public void setRoleType(RoleType type) {
		this.roleType = type;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.expGained = 0;
		this.expNeeded = level + calcExpNeeded();
	}
}