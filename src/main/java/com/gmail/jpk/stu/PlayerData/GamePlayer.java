package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Recipes.RollItem;
import com.gmail.jpk.stu.Roles.Role;

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
	
	private static final long serialVersionUID = 1L;
	public static int memoCap = 10; //The maximum number of memos a player can set
	
	private ClassType classType; //The player's class (type)
	private Role role;
	private Options options; //The player's options
	private Map<Integer, RollItem> currentItems;
	private List<String> memos; //reminders this player has set
	private String Name; //The player's name
	private int Lvl; //The player's current level
	private Player player;
	private double MaxHp;
	private double Hp;
	private double Atk;
	private double Mag;
	private double Amr;
	
	public GamePlayer(Player p) {
		classType = null;
		role = null;
		options = new Options();
		currentItems = new HashMap<Integer, RollItem>();
		memos = new ArrayList<String>();
		Name = p.getPlayerListName();
		player = p;
		MaxHp = 20.0d;
		Hp = MaxHp;
		Atk = 0.5d;
		Mag = 0.5d;
		Amr = 0.0d;
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
	
	public void clearMemos() {
		memos.clear();
	}
	
	public boolean popMemos() {
		if (memos.size() == 0)
			return false;
		
		memos.remove(0);
		
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

	public boolean hasRole() {
		return (role == null);
	}
	
	public String getPlayerName() {
		String output = ChatColor.GOLD + "[Lvl " + Lvl + "] " + getClassColor() + Name + ChatColor.WHITE;
		return output;
	}
	
	public ChatColor getClassColor() {
		if (classType == null) {
			return ChatColor.WHITE;
		}
		
		switch (classType) {
			case ARCHER:
				return ChatColor.GREEN;
			
			case BESERKER:
				return ChatColor.DARK_PURPLE;
			
			case MAGE:
				return ChatColor.BLUE;
			
			case ROGUE:
				return ChatColor.DARK_GRAY;
			
			case WARRIOR:
				return ChatColor.DARK_RED;
			
			default:
				return ChatColor.WHITE;
		}
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getLvl() {
		return Lvl;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	public RollItem getRollItem(int index) {
		return currentItems.get(index);
	}
	
	public boolean hasActiveAbility() {
		for(int i = 0; i < currentItems.size(); i++) {
			if(currentItems.get(i) != null) {
				if(currentItems.get(i).getIsActive()){
					return true;
				}
			}
		}
		return false;
	}
	
	public RollItem getActiveAbilityItem(int startFrom) {
		for(int i = 0; i < currentItems.size(); i++) {
			if(currentItems.get(i) != null) {
				if(currentItems.get(i).getIsActive()){
					if(startFrom == 0) {
						return currentItems.get(i);
					}
					else {
						startFrom--;
					}
				}
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
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setLvl(int Lvl) {
		this.Lvl = Lvl;
		switch(classType) {
		case ARCHER:
			MaxHp = (20) + (Lvl * 6.5d);
			Atk = (0.5d) + (Lvl * 0.5d);
			Mag = (0.5d) + (Lvl * 0.0d);
			Amr = (0.0d) + (Lvl * 0.25d);
			break;
		case BESERKER:
			MaxHp = (20) + (Lvl * 7.0d);
			Atk = (0.5d) + (Lvl * 0.35d);
			Mag = (0.5d) + (Lvl * 0.0d);
			Amr = (0.0d) + (Lvl * 0.25d);
			break;
		case MAGE:
			MaxHp = (20) + (Lvl * 5.5d);
			Atk = (0.5d) + (Lvl * 0.15d);
			Mag = (0.5d) + (Lvl * 0.6d);
			Amr = (0.0d) + (Lvl * 0.25d);
			break;
		case ROGUE:
			MaxHp = (20) + (Lvl * 5.0d);
			Atk = (0.5d) + (Lvl * 0.5d);
			Mag = (0.5d) + (Lvl * 0.25d);
			Amr = (0.5d) + (Lvl * 0.25d);
			break;
		case WARRIOR:
			MaxHp = 20 + (Lvl * 8.5d);
			Atk = (0.5d) + (Lvl * 0.25d);
			Mag = (0.5d) + (Lvl * 0.0d);
			Amr = (0.5d) + (Lvl * 0.5d);
			break;
		default:
			break;
		
		}
	}
	
	public void setClassType(ClassType classType) {
		this.classType =  classType;
	}
	
	public void deleteRole() {
		role = null;
	}

	public void setInfoOn(boolean InfoOn) {
		this.options.InfoOn = InfoOn;
	}
	
	public boolean hasRollItem(int index) {
		if(currentItems.get(index) != null) {
			return true;
		}
		return false;
	}
	
	public void deleteRollItem(int index) {
		currentItems.remove(index);
	}
	
	public boolean addRollItem(int index, RollItem i) {
		if(index < 4) {
			currentItems.put(index, i);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Map<Integer, RollItem> getCurrentItems() {
		return currentItems;
	}
	
	public void setMemos(ArrayList<String> memos) {
		this.memos = memos;
	}
	
	public void setMaxHp(double MaxHp) {
		this.MaxHp = MaxHp;
	}
	
	public void setHp(double Hp) {
		this.Hp = Hp;
	}
	
	public void setAmr(double Amr) {
		this.Amr = Amr;
	}
	
	public double getMaxHp() {
		return MaxHp;
	}
	
	public double getHp() {
		return Hp;
	}
	
	public double getAtk() {
		return Atk;
	}
	
	public double getMag() {
		return Mag;
	}
	
	public double getAmr() {
		return Amr;
	}

}
