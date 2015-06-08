package com.gmail.jpk.stu.PlayerData.skills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.jpk.stu.Recipes.Recipes;

public class Alchemy extends Skill {
	
	public Alchemy() {
		super(1);
	}
	
	public String toString() {
		return ChatColor.GOLD + "Alchemy" + ChatColor.WHITE;
	}
	
	public static ItemStack enhancedGlowstone() {
		return Recipes.createItem(new ItemStack(Material.GLOWSTONE, 1), ChatColor.GOLD + "Enhanced Glowstone", "Glowstone...but now enhanced!");
	}
	
	public static ItemStack minorAlcBook() {
		return Recipes.createItem(new ItemStack(Material.BOOK_AND_QUILL, 1), ChatColor.GOLD + "Minor Alchemical Book", "A magical book that should be used with caution.");
	}
	
	public static ItemStack majorAlcBook() {
		return Recipes.createItem(new ItemStack(Material.BOOK_AND_QUILL, 1), ChatColor.GOLD + "Major Alchemical Book", "A powerful magical book...use with caution");
	}
	
	public static ItemStack legendaryAlcBook() {
		return Recipes.createItem(new ItemStack(Material.BOOK_AND_QUILL, 1), ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.RED + "Legendary Alchemical Book" + ChatColor.MAGIC + "X", "A forbidden book that has ultimate power over the magical field...");
	}
}
