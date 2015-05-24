package com.gmail.jpk.stu.Recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 
 * The custom item class allows creation of custom items for the game.
 * It allows you to create a shaped or shapeless recipe, name the item,
 * create lore, among other things. 
 * 
 * @author TSHC
 * @since AmulyzeRPG 0.1 
 */
public class CustomItem extends ItemStack {
	
	public CustomItem(Material type, int amount, String displayName, String... lore) {
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
}
