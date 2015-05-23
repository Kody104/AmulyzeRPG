package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
	private Options options; //The player's options
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		options = new Options();
		Lvl = 1;
	}
	
	public String getPlayerName() {
		String output = ChatColor.GOLD + "[Lvl " + Lvl + "] ";
		
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
	
	public void setLvl(int Lvl) {
		this.Lvl = Lvl;
	}
	
	public int getLvl() {
		return Lvl;
	}
	
	public void setClassType(ClassType classType) {
		this.classType =  classType;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	public PlayerRole getPlayerRole() {
		return role;
	}
	
	public void setInfoOn(boolean InfoOn) {
		this.options.InfoOn = InfoOn;
	}
	
	public boolean getInfoOn() {
		return options.InfoOn;
	}
}
