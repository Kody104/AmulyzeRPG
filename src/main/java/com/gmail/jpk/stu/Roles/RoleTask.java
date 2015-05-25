package com.gmail.jpk.stu.Roles;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum RoleTask {
	
	//BREW_MASTER Tasks
	EARN_WARTS (0.20, 10, new ItemStack(Material.NETHER_WARTS, 1)),
	//FARMER Tasks
	FARM_WHEAT (0.10, 10, new ItemStack(Material.WHEAT, 2 + (int)(Math.random() * 3))),
	FARM_MELON (0.10, 10, new ItemStack(Material.MELON_SEEDS, 2)),
	FARM_CARROT(0.15, 10, new ItemStack(Material.CARROT_ITEM, 2)),
	MAKE_BREAD (0.15, 7),
	//Miner Tasks
	MINE_COAL  (0.10, 10, new ItemStack(Material.COAL, 1)),
	MINE_IRON  (0.05, 10, new ItemStack(Material.IRON_ORE, (int) Math.random() * 2)),
	MINE_GOLD  (0.40, 10, new ItemStack(Material.GOLD_ORE, (int) Math.random() * 2)),
	MINE_DMND  (5.00, 40, new ItemStack(Material.DIAMOND,  (int) Math.random() * 2));
	
	
	private int maxlvl;
	private double exp;
	private ItemStack[] rewards;

	/**
	 * 
	 * @param roleID The ID for this role. BREW_MASTER (0), FARMER (1), MINER (2)
	 * @param maxlvl The maximum level the player can be to get exp from this task
	 * @param rewards What rewards this task gives, if any
	 */
	RoleTask(int maxlvl, ItemStack... rewards) {
		this.maxlvl = maxlvl;
		this.rewards = new ItemStack[rewards.length];
		this.exp = 0;
		
		for (int i = 0; i < rewards.length; i++) {
			this.rewards[i] = rewards[i];
		}
	}
	
	RoleTask(double exp, int maxlvl, ItemStack... rewards) {
		this(maxlvl, rewards);
		
		this.exp = exp;
	}
	
	public int getMaxLevel() {
		return maxlvl;
	}
	
	public ItemStack[] getRewards() {
		return rewards;
	}
	
	public double getExp() {
		return exp;
	}
}

