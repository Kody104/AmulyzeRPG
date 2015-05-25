package com.gmail.jpk.stu.Recipes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.jpk.stu.PlayerData.Ability;

/**
 * 
 * The custom item class allows creation of custom items for the game.
 * It allows you to create a shaped or shapeless recipe, name the item,
 * create lore, among other things. 
 * 
 * @author TSHC
 * @since AmulyzeRPG 0.1 
 */
public class RollItem extends ItemStack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ability ability;
	private boolean isActive; // Used for toggeable abilities
	private long nextTime;
	
	public RollItem(Material type, int amount, String displayName, String... lore) {
		super(type, amount);
		
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(displayName);
		List<String> Lore = new ArrayList<String>();
		for(String s : lore) {
			Lore.add(s);
		}
		meta.setLore(Lore);
		
		this.setItemMeta(meta);
	}
	
	public RollItem(ItemStack clone, Ability add) {
		super(clone.getData().getItemType(), 1);
		this.ability = add;
		this.isActive = false;
		this.nextTime = 0;
	}
	
	
	public void setNextTime(long nextTime) {
		this.nextTime = nextTime;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public long getNextTime() {
		return nextTime;
	}
}
