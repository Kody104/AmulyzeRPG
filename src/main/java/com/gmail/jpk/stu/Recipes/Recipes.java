package com.gmail.jpk.stu.Recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.PlayerData.skills.Alchemy;

/**
 * The Recipes class always the creation and addition of custom recipes to the plugin.
 * 
 * @author Kody104, TSHC
 * @since AmulyzeRPG 0.1
 */
public final class Recipes {
	
	private Recipes() {} //This class can not be instantiated
	
	public static ShapelessRecipe seeds = new ShapelessRecipe(new ItemStack(Material.SEEDS, 1)).addIngredient(Material.WHEAT);
	
	/**
	 * Initializes ALL recipes for the plugin
	 * @param plugin YOU KNOW WHAT DIS IS COME ON
	 */
	public static void Init(AmulyzeRPG plugin) {
		AmulyzeRPG.info("*****Registering Custom Items and Recipes*****");
		
		Server server = plugin.getServer();
		
		/* ISwordA - The test sword! */
		AmulyzeRPG.info("Creating Item: Diamond_Roll_Sword");
		RollItem Diamond_Roll_Sword = new RollItem(Material.DIAMOND_SWORD, 1, "Roll Item", "This is a Roll Item", "Use /roll while holding this to roll abilities");
		ShapedRecipe recipe = new ShapedRecipe(Diamond_Roll_Sword);
		recipe.shape(" D ", "DDD", " D ");
		recipe.setIngredient('D', Material.DIAMOND);
		
		AmulyzeRPG.info("Creating Item: Roll_Bow");
		RollItem Roll_Bow = new RollItem(Material.BOW, 1, "Roll Item", "This is a Roll Item", "Use /roll while holding this to roll abilities");
		ShapedRecipe bow_recipe = new ShapedRecipe(Roll_Bow);
		bow_recipe.shape("DD ", "D D", "DD ");
		bow_recipe.setIngredient('D', Material.DIAMOND);
		
//		ShapedRecipe eGlowstone = new ShapedRecipe(Alchemy.enhancedGlowstone());
//		eGlowstone.shape("GGG", "GGG", "GGG").setIngredient('G', Material.GLOWSTONE);
		
//		ShapedRecipe minAlcBook = new ShapedRecipe(Alchemy.minorAlcBook());
		
		server.addRecipe(recipe);
		server.addRecipe(bow_recipe);
		server.addRecipe(seeds);
//		server.addRecipe(eGlowstone);
		
		AmulyzeRPG.info("All Custom Items and Recipes successful created!");
	}
	
	public static ItemStack createItem(ItemStack stack, String displayName, String... lore) {
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
}
