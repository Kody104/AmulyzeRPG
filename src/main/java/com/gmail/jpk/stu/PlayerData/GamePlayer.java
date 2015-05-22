package com.gmail.jpk.stu.PlayerData;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GamePlayer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String Name; //The player's name
	private boolean chatOn; //The player's preference for chat
	private int Lvl; //The player's current level
	private double Dmg; //The amount of damage this player does
	private double Amr; //The amount of armor this player does
	
	public GamePlayer(Player p) {
		Name = p.getPlayerListName();
		chatOn = true;
		Lvl = 1;
		Dmg = 0.5d;
		Amr = 0.0d;
	}
	
	public void lvlUp() {
		Lvl++;
		Dmg += 0.25;
		Amr += 0.25;
	}
	
	public String getPlayerName() {
		return Name;
	}
	
	public void setChatOn(boolean chatOn) {
		this.chatOn = chatOn;
	}
	
	public boolean getChatOn() {
		return chatOn;
	}
	
	public void setLvl(int Lvl) {
		this.Lvl = Lvl;
		Dmg = 0.5d + (Lvl * 0.25);
		Amr = Lvl * 0.25d;
	}
	
	public int getLvl() {
		return Lvl;
	}
	
	public void setDmg(double Dmg) {
		this.Dmg = Dmg;
	}
	
	public double getDmg() {
		return Dmg;
	}
	
	public void setAmr(double Amr) {
		this.Amr = Amr;
	}
	
	public double getAmr() {
		return Amr;
	}
	
	public String toString()
	{
		String string = ChatColor.GOLD + "[Lvl " + Lvl + "] " + ChatColor.WHITE + Name;
		return string;
	}
}
