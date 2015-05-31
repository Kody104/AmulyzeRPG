package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;

public class SetLevel extends BasicCommand {

	public SetLevel(AmulyzeRPG plugin) {
		super(plugin);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if(args.length == 2) {
			Player target = Bukkit.getServer().getPlayer(args[0]);
			Bukkit.getServer().getOnlinePlayers();
			if(target == null) { //Checks if player is online
				AmulyzeRPG.sendMessage(sender, "That player isn't online!");
				return true;
			}
			setLvl(target, args[1]); //Sets player's lvl
			AmulyzeRPG.sendMessage(sender, args[0] + "'s level changed to " + args[1] + ".");
			return true;
		}
		else { //If argument length doesn't match
			AmulyzeRPG.sendMessage(sender, "This command takes two arguments.");
			return true;
		}		
	}
	
	private void setLvl(Player player, String lvl) {
		int iLvl = Global.getPlayer(player).getLvl(); //Sets to player's lvl as default
		try{
			iLvl = Integer.parseInt(lvl); //Parses string to integer hopefully
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		if(iLvl > 100) { //Make sure lvl parameter's are inbounds.
			iLvl = 100;
		}
		else if(iLvl < 0) {
			iLvl = 0;
		}
		
		Global.getPlayer(player).setLvl(iLvl); //Sets player level to lvl
		Player p = Bukkit.getServer().getPlayer(player.getUniqueId());
		p.setLevel(iLvl);
		p.setExp(0.0f);
		p.setDisplayName("[Lvl " + iLvl + "] " + p.getName());
		AmulyzeRPG.sendMessage(p, "An admin has set your level to " + iLvl + ".");
	}
	
}
