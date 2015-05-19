package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;

import org.bukkit.entity.Player;

public class GamePlayer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private int Lvl;
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		Lvl = 1;
	}
	
	public String getPlayerName() {
		return Name;
	}
	
	public void setLvl(int Lvl) {
		this.Lvl = Lvl;
	}
	
	public int getLvl() {
		return Lvl;
	}
}
