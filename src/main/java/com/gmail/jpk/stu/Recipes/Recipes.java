package com.gmail.jpk.stu.Recipes;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

/**
 * The Recipes class always the creation and addition of custom recipes to the plugin.
 * 
 * @author Kody104, TSHC
 * @since AmulyzeRPG 0.1
 */
public class Recipes {
	
	/**
	 * Initializes ALL recipes for the plugin
	 * @param plugin YOU KNOW WHAT DIS IS COME ON
	 */
	public static void Init(AmulyzeRPG plugin) {
		AmulyzeRPG.info("*****Registering Custom Items and Recipes*****");
		
		Server server = plugin.getServer();
		
		/* ISwordA - The test sword! */
		AmulyzeRPG.info("Creating Item: ISwordA");
		CustomItem ISwordA = new CustomItem(Material.DIAMOND_SWORD, 1, "Sword A", "This is Sword A", "This is a test Sword.", "Use /roll while holding this");
		ShapedRecipe recipe = new ShapedRecipe(ISwordA);
		recipe.shape(" D ", "DDD", " D ");
		recipe.setIngredient('D', Material.DIAMOND);
		server.addRecipe(recipe);
		
		AmulyzeRPG.info("All Custom Items and Recipes successful created!");
	}
}
