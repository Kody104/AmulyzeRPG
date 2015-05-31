package com.gmail.jpk.stu.AmulyzeCommands;

import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class QuitRole extends BasicCommand {

	public QuitRole(AmulyzeRPG plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		sender.sendMessage("This command is currently being reworked");
		return true;
	}

}
