package com.gmail.jpk.stu.PlayerData.skills;

import org.bukkit.inventory.ItemStack;

public enum SkillTask {

	BAKE_BREAD(0.25, 5),
	BAKE_CAKE(0.125, 10),
	BAKE_COOKIES(0.125, 10),
	BAKE_PIE(0.125, 10),
	FARM_CARROT(0.5, 10),
	FARM_COCOA(0.75, 15),
	FARM_MELON(0.5, 10),
	FARM_MUSHROOM(1.0, 10),
	FARM_POTATO(0.5, 10),
	FARM_PUMPKIN(0.5, 10),
	FARM_SUGARCANE(8.0, 5),
	FARM_WHEAT(2.0, 10),
	FARM_WARTS(0.5, 10),
	HARVEST_MILK(1, 15),
	MINE_COAL(4.0, 10),
	MINE_IRON(2.0, 10),
	MINE_GOLD(0.25, 20),
	MINE_REDSTONE(2.0, 10),
	MINE_LAPIS(0.5, 10),
	MINE_DIAMOND(0.1, 40),
	TILL_LAND(1, 5)
	;
	
	private double valuePerOne;
	private int depreciationRate;
	private ItemStack[] rewards;
	
	SkillTask(double valuePerOne, int depreciationRate, ItemStack... rewards) {
		this.valuePerOne = valuePerOne;
		this.depreciationRate = depreciationRate;
		this.rewards = rewards;
	}
	
	public double getExperience(int level) {
		return (1.0 / (valuePerOne + 2 * (level / depreciationRate)));
	}
	
	public ItemStack[] getRewards() {
		return rewards;
	}
}
