package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;
import com.gmail.jpk.stu.AmulyzeRPG.Global;
import com.gmail.jpk.stu.PlayerData.Ability;
import com.gmail.jpk.stu.PlayerData.GamePlayer;
import com.gmail.jpk.stu.Recipes.RollItem;

public class Roll extends BasicCommand {

	public Roll(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				GamePlayer gpl = Global.getPlayer(player);
				if(gpl.getClassType() != null) {
					if(rollItem(player)) {
						AmulyzeRPG.sendMessage(player, "This item has had it's stats rolled!");
						return true;
					}
					else {
						AmulyzeRPG.sendMessage(player, "This item can't be rolled.");
						return true;
					}
				}
				else {
					AmulyzeRPG.sendMessage(player, "You need to use /setclass and choose your class.");
					return true;
				}
			}
			else {
				AmulyzeRPG.sendMessage(sender, SENDER_NOT_PLAYER);
				return true;
			}
		}
		else {
			AmulyzeRPG.sendMessage(sender, "This command takes zero arguments.");
			return true;
		}		
	}
	
	private boolean rollItem(Player p) {
		if(p.getItemInHand().hasItemMeta()) { // Safety check for null
			ItemStack i = p.getItemInHand(); // Item in hand
			GamePlayer player = Global.AllPlayers.get(p.getUniqueId());
			if(player.getClassType() != null){
				if(i.getItemMeta().getDisplayName().equalsIgnoreCase("Roll Item")) { // If the item is ours
					if(i.getType() == Material.BOW && player.getClassType() != GamePlayer.ClassType.ARCHER) {
						AmulyzeRPG.sendMessage(p, "You can't roll this unless you are an archer!");
						return false;
					}
					Ability gen = new Ability(player.getClassType()); // Create an ability for it
					RollItem rolled = new RollItem(i, gen);
					if(player.addRollItem(p.getInventory().getHeldItemSlot(), rolled)) {
						ItemMeta meta = i.getItemMeta();
						meta.setLore(null);
						i.setItemMeta(meta); // Set item lore
						meta.setLore(rolled.getAbility().getWhatis());
						meta.setDisplayName("ABILITY");
						i.setItemMeta(meta); // Sets item lore
						for(String s : gen.getWhatis()) {
							p.sendMessage(s);
						}
					}
					else {
						p.sendMessage("You need to drop one of your current roll items.");
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
}
