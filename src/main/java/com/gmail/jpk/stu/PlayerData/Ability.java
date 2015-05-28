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
		HOOK(GamePlayer.ClassType.WARRIOR, 6000L, 25, 0.0f, 0.0f, 0, 0, "HOOK", "Drags your target to you"),
		ENRAGE(GamePlayer.ClassType.WARRIOR, 5000L, 25, 0.5f, 5.0f, 10, 50, "ENRAGE", "Increases your armor by "),
		BLIND(GamePlayer.ClassType.ROGUE, 5000L, 25, 0.0f, 0.0f, 25, 125, "BLIND", "Blinds target for this long is second "),
		AMBUSH(GamePlayer.ClassType.ROGUE, 6500L, 25, 0.0f, 0.0f, 0, 0, "AMBUSH", "Stealths you and multiplies your first attack in stealth by 1.5"),
		LIGHTNING(GamePlayer.ClassType.MAGE, 5500L, 25, 0.0f, 0.0f, 0, 0, "LIGHTNING", "Strikes lightning at your target"),
		FIREBALL(GamePlayer.ClassType.MAGE, 3000L, 25, 0.0f, 0.0f, 0, 0, "FIREBALL", "Send a fireball at your target"),
		TELEPORT(GamePlayer.ClassType.MAGE, 6000L, 25, 0.0f, 0.0f, 0, 0, "TELEPORT", "Teleports you to your desired location"),
		BESERKRAGE(GamePlayer.ClassType.BESERKER, 5000L, 25, 1.0f, 6.0f, 10, 50, "BESERK RAGE", "Increases your damage, but decreases your armor by "),
		LEAP(GamePlayer.ClassType.BESERKER, 1500L, 25, 0.0f, 0.0f, 0, 0, "LEAP", "Instantly leaps ahead"),
		BACKSTEP(GamePlayer.ClassType.ARCHER, 2500L, 25, 0.0f, 0.0f, 0, 0, "BACKSTEP", "Instantly steps back from target"),
		POISONSHOT(GamePlayer.ClassType.ARCHER, 0L, 25, 0.0f, 0.0f, 0, 0, "POISONSHOT", "Shoots poison arrows with every shot");
		// TRISHOT(GamePlayer.ClassType.ARCHER, 0L, 25, 0.0f, 0.0f, 0, 0, "TRISHOT", "Shoots three arrows with every shot");
		
		
		private List<String> lore;
		private GamePlayer.ClassType neededClass;
		private long cooldown;
		private int rollChance;
		private float minMultiplier;
		private float maxMultiplier;
		private int minTime;
		private int maxTime;
		
		AbilityData(GamePlayer.ClassType neededClass, long cooldown, int rollChance,
				float minMultiplier, float maxMultiplier, int minTime, int maxTime, String... lore) {
			this.lore = new ArrayList<String>();
			for(String s : lore) {
				this.lore.add(s);
			}
			this.neededClass = neededClass;
			this.cooldown = cooldown;
			this.rollChance = rollChance;
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
	private int Duration; // Duration of certain spells
	
	public Ability(GamePlayer.ClassType playerType) {
		Random r = new Random(System.currentTimeMillis()); // Seed is current time
		List<AbilityData> classAbilities = new ArrayList<AbilityData>(); // All abilities for player classtype
		for(int i = 0; i < AbilityData.values().length; i++) {
			if(AbilityData.values()[i].neededClass == playerType) { // Adds it if player classtype is the same
				classAbilities.add(AbilityData.values()[i]);
			}
		}
		AbilityData a = null;
		for(int i = 0; i < classAbilities.size(); i++) { // Randomly gets an ability
			int roll = r.nextInt((100 - 1) + 1) + 1;
			AmulyzeRPG.info("rollChance: " + roll);
			if(roll <= classAbilities.get(i).rollChance) {
				AmulyzeRPG.info(classAbilities.get(i).toString());
				a = classAbilities.get(i);
				break;
			}
			else if(i == (classAbilities.size() - 1)) {
				roll = r.nextInt(i+1);
				AmulyzeRPG.info(classAbilities.get(roll).toString());
				a = classAbilities.get(roll);
				break;
			}
		}
		this.Name = a.toString(); 
		this.WhatIs = new ArrayList<String>();
		this.reqClassType = a.neededClass;
		this.Cooldown = a.cooldown;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = (a.minMultiplier + (a.maxMultiplier - a.minMultiplier)) * r.nextFloat();
		this.Duration = r.nextInt(((a.maxTime - a.minTime) + 1)) + a.minTime;
	//	this.Duration = (a.minTime + (a.maxTime - a.minTime)) * r.nextLong();
		if(this.WhatIs.isEmpty()) {
			this.WhatIs.add(a.lore.get(0));
			this.WhatIs.add(a.lore.get(1));
			if(Multiplier != 0.0f) {
				this.WhatIs.add(String.format("%.2f", Multiplier));
			}
			if(Duration != 0){
				if(a.toString().equalsIgnoreCase("blind")) {
					this.WhatIs.add(String.format("%d", (Duration / 20)));
				}
				else {
					this.WhatIs.add(String.format("%d", (Duration / 10)));
				}
				AmulyzeRPG.info(String.format("%d", Duration));
			}
			/*else {
				this.WhatIs.add(String.format(""));
			}
			*/
		}
		/*else {
			this.WhatIs.set(0, a.lore.get(0));
			this.WhatIs.set(1, a.lore.get(1));
			if(Multiplier != 0.0f) {
				this.WhatIs.set(2, String.format("%.2f", Multiplier));
			}
			else if(Duration != 0){
				this.WhatIs.set(2, String.format("%d", (Duration / 10)));
			}
			else {
				this.WhatIs.add(String.format(""));
			}
		}
		*/
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
	
	public int getDuration() {
		return Duration;
	}
}
