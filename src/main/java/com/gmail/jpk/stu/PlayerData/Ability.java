package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ability implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private enum AbilityData {
		ENRAGE(GamePlayer.ClassType.WARRIOR, AbilityType.BUFF, 0.5f, 5.0f, "Increases your next attack's damage by ");
		
		private List<String> lore;
		private GamePlayer.ClassType neededClass;
		private AbilityType type;
		private float minMultiplier;
		private float maxMultiplier;
		
		AbilityData(GamePlayer.ClassType neededClass, AbilityType type,
				float minMultiplier, float maxMultiplier, String... lore) {
			this.lore = new ArrayList<String>();
			for(String s : lore) {
				this.lore.add(s);
			}
			this.neededClass = neededClass;
			this.type = type;
			this.minMultiplier = minMultiplier;
			this.maxMultiplier = maxMultiplier;
		}
	}
	
	private enum AbilityType {
		INSTANT_DAMAGE, INSTANT_HEAL, INSTANT_ARCANE, BUFF, DEBUFF;
	}
	
	private String Name; // The ability's name
	private List<String> WhatIs; // Explains what the ability does
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private AbilityType abilityType; // The ability's type
	private float Multiplier; // The variable
	
	public Ability(GamePlayer.ClassType playerType) {
		Random r = new Random(System.currentTimeMillis()); // Seed is current time
		List<AbilityData> classAbilities = new ArrayList<AbilityData>(); // All abilities for player classtype
		for(int i = 0; i < AbilityData.values().length; i++) {
			if(AbilityData.values()[i].neededClass == playerType) { // Adds it if player classtype is the same
				classAbilities.add(AbilityData.values()[i]);
			}
		}
		int i = classAbilities.size();
		AbilityData a = classAbilities.get(r.nextInt(i)); // Randomly gets an ability
		this.Name = a.toString(); 
		this.WhatIs = new ArrayList<String>();
		this.reqClassType = a.neededClass;
		this.abilityType = a.type;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = a.minMultiplier + (a.maxMultiplier - a.minMultiplier) * r.nextFloat();
		if(this.WhatIs.isEmpty()) {
			this.WhatIs.add(a.lore.get(0));
			this.WhatIs.add(String.format("%.2f", Multiplier));
		}
		else {
			this.WhatIs.set(0, a.lore.get(0));
			this.WhatIs.set(1, String.format("%.2f", Multiplier));
		}
		}
	
	public String getName() {
		return Name;
	}
	
	public List<String> getWhatis() {
		return WhatIs;
	}
	
	public GamePlayer.ClassType getReqClassType() {
		return reqClassType;
	}
	
	public AbilityType getAbilityType() {
		return abilityType;
	}
	
	public double getMultiplier() {
		return Multiplier;
	}
}
