package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * The GamePlayer class is a representation of the player
 * after they have joined the server. It serves to hold information
 * about them on an individual level including their preferences, class,
 * and role.
 * @author Kody104, TSHC
 *
 */
public class GamePlayer implements Serializable{
	
	public enum ClassType {
		ARCHER, BESERKER, MAGE, ROGUE, WARRIOR;
	}
	
	public enum PlayerRole {
		BREW_MASTER, FARMER, MINER;
	}
	
	private static final long serialVersionUID = 1L;
	
	private String Name; //The player's name
	private int Lvl; //The player's current level
	private ClassType classType; //The player's class (type)
	private PlayerRole role; //The Player's role
	private List<Ability> abilities;
	private Options options; //The player's options
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		Lvl = 1;
		classType = null;
		role = null;
		abilities = new ArrayList<Ability>();
		options = new Options();
	}
	
	public String getPlayerName() {
		String output = ChatColor.GOLD + "[Lvl " + Lvl + "] ";
		
		if (classType == null) {
			output += ChatColor.WHITE + Name;
			return output;
		}
		
		switch (classType) {
			case ARCHER:
				output += ChatColor.GREEN + Name + ChatColor.WHITE;
			break;
			
			case BESERKER:
				output += ChatColor.LIGHT_PURPLE + Name + ChatColor.WHITE;
			break;
			
			case MAGE:
				output += ChatColor.BLUE + Name + ChatColor.WHITE;
			break;
			
			case ROGUE:
				output += ChatColor.GRAY + Name + ChatColor.WHITE;
			break;
			
			case WARRIOR:
				output += ChatColor.DARK_RED + Name + ChatColor.WHITE;
			break;
			
			default:
				output += ChatColor.WHITE + Name;
			break;
		}
		
		return output;
	}
	
	public int getLvl() {
		return Lvl;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	public PlayerRole getPlayerRole() {
		return role;
	}
	
	public Ability getAbility(int index) {
		return abilities.get(index);
	}
	
	public Ability getAbility(String name) {
		for(Ability a : abilities) {
			if(a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}
	
	public boolean getInfoOn() {
		return options.InfoOn;
	}
	
	public void setLvl(int Lvl) {
		this.Lvl = Lvl;
	}
	
	public void setClassType(ClassType classType) {
		this.classType =  classType;
	}
	
	public void setRole(PlayerRole role) {
		this.role = role;
	}

	public void setInfoOn(boolean InfoOn) {
		this.options.InfoOn = InfoOn;
	}
	
	public void setAbility(int index, Ability a) {
		abilities.set(index, a);
	}
	
	public void setAbility(Ability a) {
		if(abilities.size() < 4) {
			abilities.add(a);
		}
		else {
			abilities.set(3, a);
		}
	}

}
