package com.gmail.jpk.stu.Recipes;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
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
		Server server = plugin.getServer(); //The server this plugin is running on.	
		server.getLogger().info("*****Registering Recipes*****");
		
		/* ISwordA - The test sword! */
		server.getLogger().info("Creating Item: ISwordA");
		CustomItem ISwordA = new CustomItem(Material.DIAMOND_SWORD, 1, "Sword A", "This is Sword A", "This is a test Sword.", "Please disregard");
		//ShapedRecipe ISwoardA_recipe = ISwordA.createShapedRecipe(" D .DDD. D ").setIngredient('D', Material.DIAMOND);
		ShapedRecipe recipe = new ShapedRecipe(ISwordA);
		recipe.shape(" D ", "DDD", " D ");
		recipe.setIngredient('D', Material.DIAMOND);
		server.addRecipe(recipe);
		
	}
}
