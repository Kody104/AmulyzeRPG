package com.gmail.jpk.stu.PlayerData;

import java.util.Random;

public class Ability {
	
	private enum AbilityData {
		ENRAGE("Enrage", GamePlayer.ClassType.WARRIOR, AbilityType.BUFF, 0.5, 5.0);
		
		private String Name;
		private GamePlayer.ClassType reqClassType;
		private AbilityType abilityType;
		private double MinMultiplier;
		private double MaxMultiplier;
		
		AbilityData(String Name, GamePlayer.ClassType reqClassType, AbilityType abilityType,
				double MinMultiplier, double MaxMultiplier) {
			this.Name = Name;
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
	private GamePlayer.ClassType reqClassType; // The ability's required classtype
	private AbilityType abilityType; // The ability's type
	private double Multiplier; // The variable
	
	public Ability(AbilityData a) {
		Random r = new Random();
		this.Name = a.Name;
		this.reqClassType = a.reqClassType;
		this.abilityType = a.abilityType;
		/* Randomly generates a multiplier for the ability through the min and max */
		this.Multiplier = a.MinMultiplier + (a.MaxMultiplier - a.MinMultiplier) * r.nextDouble();
	}
	
	public String getName() {
		return Name;
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
