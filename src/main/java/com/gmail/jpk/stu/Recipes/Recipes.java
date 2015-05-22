package com.gmail.jpk.stu.Recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class Recipes {
	
	public static ShapedRecipe SwordA; //Test sword recipe. It works.
	
	public static void Init(AmulyzeRPG plugin) {
		ItemStack ISwordA = new ItemStack(Material.DIAMOND_SWORD); // The sword will be diamond
		ItemMeta ISwordAMeta = ISwordA.getItemMeta(); // Brings ItemMeta out for editing
		ISwordAMeta.setDisplayName("Sword A"); // Sets the sword name
		List<String> ISwordAMetaLore = new ArrayList<String>(); // ItemMetaLore is a List<String>
		ISwordAMetaLore.add("This is Sword A."); // Adding some test strings
		ISwordAMetaLore.add("This is a test sword.");
		ISwordAMetaLore.add("Please disregard.");
		ISwordAMeta.setLore(ISwordAMetaLore); // Sets the lore of the ItemMeta
		ISwordA.setItemMeta(ISwordAMeta); // Sets the ItemMeta of the ItemStack
		SwordA = new ShapedRecipe(ISwordA); // Sets the outcome of the custom recipe to be what we created.
		SwordA.shape(" D ", "DDD", " D "); // The shape of the sword on the workbench.
		SwordA.setIngredient('D', Material.DIAMOND); // Spaces are empty spots. Chars are defined here.
		plugin.getServer().addRecipe(SwordA); // Finally add it to ther server plugin. Registers it.
	}
}
