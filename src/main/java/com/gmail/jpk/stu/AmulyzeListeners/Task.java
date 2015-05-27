package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.GamePlayer;
import com.gmail.jpk.stu.Recipes.RollItem;

public class Task extends BukkitRunnable{
	
	private String abilityName;
	private Player player;
	private GamePlayer gPlayer;
	
	public Task(String abilityName, Player player){
		this.abilityName = abilityName;
		this.player = player;
		gPlayer = Global.getPlayer(player);
	}
	
	@Override
	public void run() {
		switch(abilityName.toLowerCase()) { // switch case depending on ability
		case "enrage":
			for(RollItem i : gPlayer.getCurrentItems().values()) {
				if(i.getIsActive()) { // Turns off enrage
					if(i.getAbility().getName().equalsIgnoreCase(abilityName)) {
						i.setIsActive(false);
						Global.amChat(player, "Your enrage has ended!");
						break;
					}
				}
			}
			break;
		case "beserkrage":
			for(RollItem i : gPlayer.getCurrentItems().values()) {
				if(i.getIsActive()) { // Turns off beserk rage
					if(i.getAbility().getName().equalsIgnoreCase(abilityName)) {
						i.setIsActive(false);
						Global.amChat(player, "Your beserking rage has ended!");
						break;
					}
				}
			}
			break;
		default:
			break;
		}
	}
}
