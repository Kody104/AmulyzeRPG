package com.gmail.jpk.stu.AmulyzeRPG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class Global {
	
	public static HashMap<UUID, GamePlayer> AllPlayers = new HashMap<UUID, GamePlayer>();
	
	public static void Save(JavaPlugin plugin) {
		AmulyzeRPG.info("Attempting to save player data...");
		
		try {
			File dir = new File("plugins/AmulyzeRPG"); //Get the file, if present
			File file;
			FileOutputStream fos;
			ObjectOutputStream out;
			
			AmulyzeRPG.info("Creating the directory.");
			dir.mkdirs(); //Create the directory
			
			AmulyzeRPG.info("Opening fos and out");
			file = new File(dir, "players.aur"); //Create the data file
			fos = new FileOutputStream(file, false); //Create a new one, don't append
			out = new ObjectOutputStream(fos);
			
			AmulyzeRPG.info("Saving the hashmap. AllPlayers: " + AllPlayers);
			out.writeObject(AllPlayers); //Save the hash map

			AmulyzeRPG.info("Closing fos and out");
			fos.close();
			out.close();
			
			AmulyzeRPG.info("Player Data Saved!");
		} catch(IOException io) {
			AmulyzeRPG.info("Any error has occured while trying to save player.aur");
			AmulyzeRPG.info("Reason: " + io.getCause());
			io.printStackTrace();
		}
	}
	
	public static void Load(JavaPlugin plugin)
	{
		AmulyzeRPG.info("Attempting to load Player Data...");

		File file = new File("plugins/AmulyzeRPG/players.aur");
		AllPlayers = new HashMap<UUID, GamePlayer>();
		FileInputStream fis;
		ObjectInputStream ois;
		
		if (!file.exists()) { 	//Verify that player.aur exists. Can not load hashmap without it. 
			AmulyzeRPG.info("\"players.ser\" is missing or corrupted.");
			AmulyzeRPG.info("Program will not attempt to load data");
			return;
		} else {
			AmulyzeRPG.info("\"players.ser\" found! Loading the hashmap data...");
		}
		
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			
			AllPlayers = (HashMap<UUID, GamePlayer>) ois.readObject();
			AmulyzeRPG.info("AllPlayers data was successful loaded!");
			
			fis.close();
			ois.close();
		} catch(IOException io) {
			AmulyzeRPG.info("Any error has occured while trying to load player.aur");
			AmulyzeRPG.info("Reason: " + io.getCause());
			io.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			AmulyzeRPG.info("Any error has occured while trying to load player.aur");
			AmulyzeRPG.info("Reason: " + cnfe.getCause());
			cnfe.printStackTrace();
		}
	}
	
	public static GamePlayer getPlayer(Player player) {
		return AllPlayers.get(player.getUniqueId());
	}
	
	public static boolean containsPlayer(Player player) {
		return AllPlayers.containsKey(player.getUniqueId());
	}
	
	/**
	 * Sends chat to the player that is toggable by the /amchat command.
	 * If the player has AMCHAT set to TRUE, the message will be send to them,
	 * otherwise, it will not.
	 * 
	 * @param text the message to send to the player
	 */
	public static void amChat(Player player, String text) {
		if (AllPlayers.get(player.getUniqueId()).getInfoOn()) {
			AmulyzeRPG.sendMessage(player, text);
		} 
	}
}
