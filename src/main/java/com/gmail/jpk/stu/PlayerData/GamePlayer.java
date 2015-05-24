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
	public static int memoCap = 10; //The maximum number of memos a player can set
	
	private ClassType classType; //The player's class (type)
	private PlayerRole role; //The Player's role
	private Options options; //The player's options
	private List<Ability> abilities;
	private List<String> memos; //reminders this player has set
	private String Name; //The player's name
	private int Lvl; //The player's current level
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		Lvl = 0;
		classType = null;
		role = null;
		options = new Options();
		abilities = new ArrayList<Ability>();
		memos = new ArrayList<String>();
		Name = p.getPlayerListName();
		Lvl = 1;
	}
	
	public boolean addMemo(String memo) {
		if (memos.size() >= memoCap) {
			return false;
		} 
		else {
			memos.add(memo);
		}
		
		return true;
	}
	
	public boolean removeMemo(int index) {
		if (memos.size() == 0 || (index < 0 && index > memos.size() - 1)) {
			return false;
		} 
		else {
			memos.remove(index);
		}
		
		return true;
	}
	
	public void clearMemos() {
		memos.clear();
	}
	
	public boolean popMemos() {
		if (memos.size() == 0)
			return false;
		
		memos.remove(0);
		
		return true;
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
				output += ChatColor.DARK_GRAY + Name + ChatColor.WHITE;
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
		for(int x = abilities.size()-1; x >= 0; x--) {
			if(abilities.get(x).getName().equalsIgnoreCase(name)){
				return abilities.get(x);
			}
		}
		return null;
	}
	
	public boolean getInfoOn() {
		return options.InfoOn;
	}
	
	public List<String> getMemos() {
		return memos;
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
	
	public void setMemos(ArrayList<String> memos) {
		this.memos = memos;
	}

}
