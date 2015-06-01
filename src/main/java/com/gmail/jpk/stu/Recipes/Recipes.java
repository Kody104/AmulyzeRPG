package com.gmail.jpk.stu.Recipes;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

/**
 * The Recipes class always the creation and addition of custom recipes to the plugin.
 * 
 * @author Kody104, TSHC
 * @since AmulyzeRPG 0.1
 */
public class Recipes {
	
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
		
		AmulyzeRPG.info("Creating Farmer's Bread");
		ShapedRecipe farmersBread = new ShapedRecipe(SpecialItem.farmersBread());
		farmersBread.shape("WWW", "SBS", "WWW").setIngredient('W', Material.WHEAT).setIngredient('S', Material.SUGAR).setIngredient('B', Material.WATER_BUCKET);
		
		server.addRecipe(recipe);
		server.addRecipe(bow_recipe);
		server.addRecipe(seeds);
		server.addRecipe(farmersBread);
		
		AmulyzeRPG.info("All Custom Items and Recipes successful created!");
	}
	
}
