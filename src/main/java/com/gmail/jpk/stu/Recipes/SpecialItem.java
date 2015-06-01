package com.gmail.jpk.stu.Recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class SpecialItem {

	private SpecialItem() {} //This class can not be instantiated
	
	private static ItemStack createItem(ItemStack stack, String displayName, String... lore) {
		ItemMeta meta = stack.getItemMeta();
		List<String> loredata = new ArrayList<String>();
		
		for (String s : lore) {
			loredata.add(s);
		}
		
		meta.setDisplayName(displayName);
		meta.setLore(loredata);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static ItemStack farmersBread() {
		return createItem(new ItemStack(Material.BREAD, 1), ChatColor.GOLD + "Farmer's Bread", "Regenerative Bread made by a farmer");
	}	
	
}
