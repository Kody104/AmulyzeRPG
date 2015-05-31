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
		HOOK(GamePlayer.ClassType.WARRIOR, 6000L, 25, 0.0f, 0.0f, 0.0f, 0.5f, 25, 125, "HOOK", "Drags your target to you and slows them for this long in seconds"),
		ENRAGE(GamePlayer.ClassType.WARRIOR, 5000L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 10, 50, "ENRAGE", "Increases your armor by 50%"),
		BLIND(GamePlayer.ClassType.ROGUE, 5000L, 25, 0.0f, 0.25f, 0.0f, 0.0f, 25, 125, "BLIND", "Blinds target for this long is seconds "),
		AMBUSH(GamePlayer.ClassType.ROGUE, 6500L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "AMBUSH", "Stealths you and multiplies your first attack in stealth by 50%"),
		LIGHTNING(GamePlayer.ClassType.MAGE, 5500L, 25, 0.0f, 0.0f, 1.0f, 0.0f, 0, 0, "LIGHTNING", "Strikes lightning at your target"),
		FIREBALL(GamePlayer.ClassType.MAGE, 3000L, 25, 0.0f, 0.0f, 0.25f, 0.0f, 0, 0, "FIREBALL", "Send a fireball at your target"),
		TELEPORT(GamePlayer.ClassType.MAGE, 7500L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "TELEPORT", "Teleports you to your desired location"),
		LIFESTEAL(GamePlayer.ClassType.BESERKER, 0L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "LIFESTEAL", "You heal yourself for 15% of your damage on hit"),
		BESERKRAGE(GamePlayer.ClassType.BESERKER, 5000L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 10, 50, "BESERK RAGE", "Increases your damage by 30% and decreases your amr by the same"),
		LEAP(GamePlayer.ClassType.BESERKER, 1500L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "LEAP", "Instantly leaps ahead"),
		BACKSTEP(GamePlayer.ClassType.ARCHER, 2500L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "BACKSTEP", "Instantly steps back from target"),
		POISONSHOT(GamePlayer.ClassType.ARCHER, 0L, 25, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "POISONSHOT", "Shoots poison arrows with every shot"),
		FLAMESHOT(GamePlayer.ClassType.ARCHER, 0L, 25, 0.0f, 0.0f, 0.0f ,0.0f,  0, 0, "FLAMESHOT", "Shoots flame arrows with every shot");
		// TRISHOT(GamePlayer.ClassType.ARCHER, 0L, 25, 0.0f, 0.0f, 0, 0, "TRISHOT", "Shoots three arrows with every shot");
		
		
		private List<String> lore;
		private GamePlayer.ClassType neededClass;
		private long cooldown;
		private int rollChance;
		private float HpScale;
		private float AtkScale;
		private float MagScale;
		private float AmrScale;
		private int minTime;
		private int maxTime;
		
		AbilityData(GamePlayer.ClassType neededClass, long cooldown, int rollChance,
				float HpScale, float AtkScale, float MagScale, float AmrScale, int minTime, int maxTime, String... lore) {
			this.lore = new ArrayList<String>();
			for(String s : lore) {
				this.lore.add(s);
			}
			this.neededClass = neededClass;
			this.cooldown = cooldown;
			this.rollChance = rollChance;
			this.HpScale = HpScale;
			this.AtkScale = AtkScale;
			this.MagScale = MagScale;
			this.AmrScale = AmrScale;
			this.minTime = minTime;
			this.maxTime = maxTime;
		}
	}
	
	private String Name; // The ability's name
	private List<String> WhatIs; // Explains what the ability does
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private long Cooldown;
	private float HpScale;// The scale of the player's hp for damage
	private float AtkScale;
	private float MagScale;
	private float AmrScale;
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
		this.HpScale = a.HpScale;
		this.AtkScale = a.AtkScale;
		this.MagScale = a.MagScale;
		this.AmrScale = a.AmrScale;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Duration = r.nextInt(((a.maxTime - a.minTime) + 1)) + a.minTime;
	//	this.Duration = (a.minTime + (a.maxTime - a.minTime)) * r.nextLong();
		if(this.WhatIs.isEmpty()) {
			this.WhatIs.add(a.lore.get(0));
			this.WhatIs.add(a.lore.get(1));
			if(Duration != 0){
				if(a.toString().equalsIgnoreCase("blind")) {
					this.WhatIs.add(String.format("%d", (Duration / 20)));
				}
				else if(a.toString().equalsIgnoreCase("hook")) {
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
	
	public boolean setAbility(String abilityName) {
		Random r = new Random(System.currentTimeMillis());
		AbilityData a = null;
		for(int i = 0; i < AbilityData.values().length; i++) {
			if(AbilityData.values()[i].toString().equalsIgnoreCase(abilityName)) {
				a = AbilityData.values()[i];
			}
		}
		if(a == null) {
			return false;
		}
		this.Name = a.toString(); 
		this.reqClassType = a.neededClass;
		this.Cooldown = a.cooldown;
		this.HpScale = a.HpScale;
		this.AtkScale = a.AtkScale;
		this.MagScale = a.MagScale;
		this.AmrScale = a.AmrScale;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Duration = r.nextInt(((a.maxTime - a.minTime) + 1)) + a.minTime;
	//	this.Duration = (a.minTime + (a.maxTime - a.minTime)) * r.nextLong();
		this.WhatIs.clear();
		this.WhatIs.add(a.lore.get(0));
		this.WhatIs.add(a.lore.get(1));
		if(Duration != 0){
			if(a.toString().equalsIgnoreCase("blind")) {
				this.WhatIs.add(String.format("%d", (Duration / 20)));
			}
			else if(a.toString().equalsIgnoreCase("hook")) {
				this.WhatIs.add(String.format("%d", (Duration / 20)));
			}
			else {
				this.WhatIs.add(String.format("%d", (Duration / 10)));
			}
			AmulyzeRPG.info(String.format("%d", Duration));
		}
		return true;
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
	
	public float getHpScale() {
		return HpScale;
	}
	
	public float getAtkScale() {
		return AtkScale;
	}
	
	public float getMagScale() {
		return MagScale;
	}
	
	public float getAmrScale() {
		return AmrScale;
	}
	
	public int getDuration() {
		return Duration;
	}
}
