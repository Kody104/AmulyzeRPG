package com.gmail.jpk.stu.AmulyzeListeners;

import org.bukkit.Bukkit;
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
		switch(abilityName.toLowerCase()) {
		case "vanish":
			for(Player p : Bukkit.getWorld(player.getWorld().getName()).getPlayers()) {
				p.showPlayer(player);
			}
			player.sendMessage("You vanish has worn off!");
			break;
		case "enrage":
			for(RollItem i : gPlayer.getCurrentItems().values()) {
				if(i.getIsActive()) {
					if(i.getAbility().getName().equalsIgnoreCase(abilityName)) {
						i.setIsActive(false);
						player.sendMessage("Your enrage has ended!");
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
