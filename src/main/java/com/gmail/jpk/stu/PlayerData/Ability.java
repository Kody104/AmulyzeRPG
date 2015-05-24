package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class Ability implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private enum AbilityData {
		ENRAGE(GamePlayer.ClassType.WARRIOR, AbilityType.BUFF, 0.5f, 5.0f, 0, 0, "ENRAGE", "Increases your next attack's damage by "),
		VANISH(GamePlayer.ClassType.ROGUE, AbilityType.PASSIVE, 0.0f, 0.0f, 1000, 5000, "VANISH", "You are able to stealth for this long in seconds: "),
		FIREBALL(GamePlayer.ClassType.MAGE, AbilityType.SPELL, 0.5f, 7.5f, 0, 0, "FIREBALL", "Send a fireball at your target that hits for "),
		FLASH(GamePlayer.ClassType.BESERKER, AbilityType.INSTANT, 0.0f, 0.0f, 0, 0, "FLASH", "Instantly leaps to your target"),
		BACKSTE(GamePlayer.ClassType.ARCHER, AbilityType.INSTANT, 0.0f, 0.0f, 0, 0, "BACKSTEP", "Instantly steps back from anything around");
		
		
		private List<String> lore;
		private GamePlayer.ClassType neededClass;
		private AbilityType type;
		private float minMultiplier;
		private float maxMultiplier;
		private int minTime;
		private int maxTime;
		
		AbilityData(GamePlayer.ClassType neededClass, AbilityType type,
				float minMultiplier, float maxMultiplier, int minTime, int maxTime, String... lore) {
			this.lore = new ArrayList<String>();
			for(String s : lore) {
				this.lore.add(s);
			}
			this.neededClass = neededClass;
			this.type = type;
			this.minMultiplier = minMultiplier;
			this.maxMultiplier = maxMultiplier;
			this.minTime = minTime;
			this.maxTime = maxTime;
		}
	}
	
	private enum AbilityType {
		INSTANT, SPELL, BUFF, DEBUFF, PASSIVE;
	}
	
	private String Name; // The ability's name
	private List<String> WhatIs; // Explains what the ability does
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private AbilityType abilityType; // The ability's type
	private float Multiplier; // The variable of certain spells
	private int Duration; // Duration of certain spells
	
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
		this.Multiplier = (a.minMultiplier + (a.maxMultiplier - a.minMultiplier)) * r.nextFloat();
		this.Duration = r.nextInt(((a.maxTime - a.minTime) + 1)) + a.minTime;
		if(this.WhatIs.isEmpty()) {
			this.WhatIs.add(a.lore.get(0));
			this.WhatIs.add(a.lore.get(1));
			if(Multiplier != 0.0f) {
				this.WhatIs.add(String.format("%.2f", Multiplier));
			}
			else {
				this.WhatIs.add(String.format("%d", (Duration / 1000)));
				AmulyzeRPG.info(String.format("%d", Duration));
			}
		}
		else {
			this.WhatIs.set(0, a.lore.get(0));
			this.WhatIs.set(1, a.lore.get(1));
			if(Multiplier != 0.0f) {
				this.WhatIs.set(2, String.format("%.2f", Multiplier));
			}
			else {
				this.WhatIs.set(2, String.format("%d", (Duration / 1000)));
			}
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
	
	public int getDuration() {
		return Duration;
	}
}
