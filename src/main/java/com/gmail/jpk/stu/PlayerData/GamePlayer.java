package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GamePlayer implements Serializable{
	
	public enum ClassType {
		WARRIOR, MAGE, BESERKER, ROGUE, ARCHER;
	}
	
	private static final long serialVersionUID = 1L;
	
	private String Name; //The player's name
	private int Lvl; //The player's current level
	private ClassType classType; //The player's class (type)
	private Options options; //The player's options
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		options = new Options();
		Lvl = 1;
	}
	
	public String getPlayerName() {
		String output = "";
		if(classType == null) {
			output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.WHITE + Name;
		}
		else {
			if(classType == ClassType.WARRIOR) {
				output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.DARK_RED + Name + ChatColor.WHITE;
			}
			else if(classType == ClassType.MAGE) {
				output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.BLUE + Name + ChatColor.WHITE;
			}
			else if(classType == ClassType.BESERKER) {
				output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.LIGHT_PURPLE + Name + ChatColor.WHITE;
			}
			else if(classType == ClassType.ROGUE) {
				output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.GRAY + Name + ChatColor.WHITE;
			}
			else if(classType == ClassType.ARCHER) {
				output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.GREEN + Name + ChatColor.WHITE;
			}
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
	
	public void setInfoOn(boolean InfoOn) {
		this.options.InfoOn = InfoOn;
	}
	
	public boolean getInfoOn() {
		return options.InfoOn;
	}
}
