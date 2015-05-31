package com.gmail.jpk.stu.Roles;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * Tasks are small events that player's in a certain role can complete for experience.
 * Each task gives out a certain number of exp depending on its difficulty and may also
 * give bonus items depending on the task. While the player will always recieve the items,
 * the exp gained is lost after a certain level. The RoleTask enum serves as the definitions for
 * each task.
 * 
 * @author TSHC
 *
 */
public enum RoleTask {
	
	TEST_TASK(4, 5, new ItemStack(Material.DIAMOND, 1)),
	TEST_TASK_2(1000, 5);
	
	private int maxLevel; //The maximum level the player can be to earn EXP 
	private int expGiven; //The experience this task gives
	private ItemStack[] items; //The items this task gives

	RoleTask(int expGiven, int maxLevel, ItemStack... items) {
		this.expGiven = expGiven;
		this.maxLevel = maxLevel;
		this.items = new ItemStack[items.length];
		
		for (int i = 0; i < items.length; i++) {
			this.items[i] = items[i];
		}
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public int getExpGiven() {
		return expGiven;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
}

