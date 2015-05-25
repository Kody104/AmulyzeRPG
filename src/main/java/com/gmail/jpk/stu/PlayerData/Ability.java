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
		ENRAGE(GamePlayer.ClassType.WARRIOR, 5000, 0.5f, 5.0f, 0, 0, "ENRAGE", "Increases your attack by "),
		VANISH(GamePlayer.ClassType.ROGUE, 10000, 0.0f, 0.0f, 1000, 5000, "VANISH", "You are able to stealth for this long in seconds: "),
		FIREBALL(GamePlayer.ClassType.MAGE, 2500, 0.5f, 7.5f, 0, 0, "FIREBALL", "Send a fireball at your target that hits for "),
		FLASH(GamePlayer.ClassType.BESERKER, 1500, 0.0f, 0.0f, 0, 0, "FLASH", "Instantly leaps to your target"),
		BACKSTEP(GamePlayer.ClassType.ARCHER, 2500, 0.0f, 0.0f, 0, 0, "BACKSTEP", "Instantly steps back from anything around");
		
		
		private List<String> lore;
		private GamePlayer.ClassType neededClass;
		private long cooldown;
		private float minMultiplier;
		private float maxMultiplier;
		private long minTime;
		private long maxTime;
		
		AbilityData(GamePlayer.ClassType neededClass, long cooldown,
				float minMultiplier, float maxMultiplier, long minTime, long maxTime, String... lore) {
			this.lore = new ArrayList<String>();
			for(String s : lore) {
				this.lore.add(s);
			}
			this.neededClass = neededClass;
			this.cooldown = cooldown;
			this.minMultiplier = minMultiplier;
			this.maxMultiplier = maxMultiplier;
			this.minTime = minTime;
			this.maxTime = maxTime;
		}
	}
	
	private String Name; // The ability's name
	private List<String> WhatIs; // Explains what the ability does
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private long Cooldown;
	private float Multiplier; // The variable of certain spells
	private long Duration; // Duration of certain spells
	
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
		this.Cooldown = a.cooldown;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = (a.minMultiplier + (a.maxMultiplier - a.minMultiplier)) * r.nextFloat();
		//this.Duration = r.nextLong(((a.maxTime - a.minTime) + 1)) + a.minTime;
		this.Duration = (a.minTime + (a.maxTime - a.minTime)) * r.nextLong();
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
	
	public long getCooldown() {
		return Cooldown;
	}
	
	public double getMultiplier() {
		return Multiplier;
	}
	
	public long getDuration() {
		return Duration;
	}
}
