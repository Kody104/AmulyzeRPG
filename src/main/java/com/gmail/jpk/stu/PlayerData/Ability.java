package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

public class Ability implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private enum AbilityData {
		ENRAGE("Enrage", GamePlayer.ClassType.WARRIOR, AbilityType.BUFF, 0.5f, 5.0f, "Increases your next attack's damage by ");
		
		private String name;
		private List<String> whatIs;
		private GamePlayer.ClassType ReqClassType;
		private AbilityType abilitytype;
		private float minMultiplier;
		private float maxMultiplier;
		
		AbilityData(String name, GamePlayer.ClassType ReqClassType, AbilityType abilitytype,
				float minMultiplier, float maxMultiplier, String... whatIs) {
			this.name = name;
			this.whatIs = new ArrayList<String>();
			for(String s : whatIs) {
				this.whatIs.add(s);
			}
			this.ReqClassType = ReqClassType;
			this.abilitytype = abilitytype;
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
		Random r = new Random(System.currentTimeMillis());
		List<AbilityData> classAbilities = new ArrayList<AbilityData>();
		for(int i = 0; i < AbilityData.values().length; i++) {
			if(AbilityData.values()[i].ReqClassType == playerType) {
				classAbilities.add(AbilityData.values()[i]);
			}
		}
		int i = classAbilities.size();
		AbilityData a = classAbilities.get(r.nextInt(i));
		this.Name = a.name;
		this.WhatIs = new ArrayList<String>();
		this.WhatIs = a.whatIs;
		this.reqClassType = a.ReqClassType;
		this.abilityType = a.abilitytype;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = a.minMultiplier + (a.maxMultiplier - a.minMultiplier) * r.nextFloat();
		this.WhatIs.add(String.format("%.2f", Multiplier));
	}
	
	public String getName() {
		return this.Name;
	}
	
	public List<String> getWhatis() {
		return this.WhatIs;
	}
	
	public GamePlayer.ClassType getReqClassType() {
		return this.reqClassType;
	}
	
	public AbilityType getAbilityType() {
		return this.abilityType;
	}
	
	public double getMultiplier() {
		return this.Multiplier;
	}
}
