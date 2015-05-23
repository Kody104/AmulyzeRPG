package com.gmail.jpk.stu.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ability {
	
	private enum AbilityData {
		ENRAGE("Enrage", "Increases your next attack's damage by ", GamePlayer.ClassType.WARRIOR, AbilityType.BUFF, 0.5, 5.0);
		
		private String Name;
		private String WhatIs;
		private GamePlayer.ClassType reqClassType;
		private AbilityType abilityType;
		private double MinMultiplier;
		private double MaxMultiplier;
		
		AbilityData(String Name, String WhatIs, GamePlayer.ClassType reqClassType, AbilityType abilityType,
				double MinMultiplier, double MaxMultiplier) {
			this.Name = Name;
			this.WhatIs = WhatIs;
			this.reqClassType = reqClassType;
			this.abilityType = abilityType;
			this.MinMultiplier = MinMultiplier;
			this.MaxMultiplier = MaxMultiplier;
		}
	}
	
	private enum AbilityType {
		INSTANT_DAMAGE, INSTANT_HEAL, INSTANT_ARCANE, BUFF, DEBUFF;
	}
	
	private String Name; // The ability's name
	private String WhatIs; // Explains what the ability does
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private AbilityType abilityType; // The ability's type
	private double Multiplier; // The variable
	
	public Ability(GamePlayer.ClassType playerType) {
		Random r = new Random();
		List<AbilityData> classAbilities = new ArrayList<AbilityData>();
		for(int i = 0; i < AbilityData.values().length; i++) {
			if(AbilityData.values()[i].reqClassType == playerType) {
				classAbilities.add(AbilityData.values()[i]);
			}
		}
		int i = (classAbilities.size() - 1) > 0 ? (classAbilities.size() - 1) : 0;
		AbilityData a = classAbilities.get(r.nextInt(i));
		this.Name = a.Name;
		WhatIs = a.WhatIs;
		this.reqClassType = a.reqClassType;
		this.abilityType = a.abilityType;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = a.MinMultiplier + (a.MaxMultiplier - a.MinMultiplier) * r.nextDouble();
		WhatIs += Multiplier;
	}
	
	public String getName() {
		return Name;
	}
	
	public String getWhatis() {
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
