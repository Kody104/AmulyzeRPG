package com.gmail.jpk.stu.Recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
public class CustomItem {
		
	private ItemStack itemBase; //The base item that will be used for this Item
	private String displayName; //The name that will be shown for this item
	private List<String> lore; //The lore of this item; information about it
	
	/**
	 * Creates a CustomItem that can be used by the player.
	 * @param itemBase The base item that will be used for this item (i.e. Wood Sword, Iron Sword, etc)
	 * @param displayName The name that will be displayed for this item
	 * @param lore The lore for this item
	 */
	public CustomItem(ItemStack itemBase, String displayName, String... lore) {
		this.itemBase = itemBase;
		this.displayName = displayName;
		this.lore = new ArrayList<String>();
		this.setLore(lore);
		
		ItemMeta meta = itemBase.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(this.lore);
		
		itemBase.setItemMeta(meta);
	}
	
	/**
	 * 
	 * Creates a shaped recipe to be ADDED to the server. This method is used to shorten it as much as possible.
	 * To create a shaped recipe, define the pattern in one string by separated rows with periods. For example,
	 * the following recipe: " D .DDD. D ", is equivalent to " D ", "DDD", " D ". After this object has created,
	 * the setIngredient can be used to define each letter. Example usage:
	 * 
	 * ShapedRecipe ISwoardA_recipe = ISwordA.createShapedRecipe(" D .DDD. D ").setIngredient('D', Material.DIAMOND);
	 * server.add(ISword_recipe);
	 * 
	 * @param pattern The ShapedRecipes pattern. Rows are separated by dots.
	 * @return The ShapedRecipe to add to the server
	 */
	public ShapedRecipe createShapedRecipe(String pattern) {
		ShapedRecipe recipe = new ShapedRecipe(itemBase);
		recipe.shape(pattern.split("\\."));
		
		return recipe;
	}
	
	public ItemStack getItemBase() {
		return itemBase;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public void setItemBase(ItemStack stack) {
		this.itemBase = stack;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setLore(String... lore) {
		for (String s : lore)
			this.lore.add(s);
	}
}
