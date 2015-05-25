package com.gmail.jpk.stu.Roles;

import org.bukkit.entity.Player;


public class Farmer extends Role {
	
	private int customRecipesCreated;
	
	public Farmer(Player player) {
		super(player);
		
		customRecipesCreated = 0;
	}
	
	public int getCustomRecipesCreated() {
		return customRecipesCreated;
	}
	
	public String toString() {
		return "Farmer";
	}
}
