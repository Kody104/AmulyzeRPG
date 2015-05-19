package com.gmail.jpk.stu.AmulyzeRPG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.PlayerData.GamePlayer;

public class Global {
	
	public static HashMap<UUID, GamePlayer> AllPlayers = new HashMap<UUID, GamePlayer>();
	
	public static void Save(JavaPlugin plugin) {
		try {
			plugin.getLogger().info("Saving Player Data...");
			File dir = new File("plugins/AmulyzeRPG/"); //Makes file directory
			dir.mkdirs();
			File file = new File(dir, "players.aur"); //Creates file
			FileOutputStream fos = new FileOutputStream(file, false); 
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(AllPlayers); //Saves file and directory
			out.close();
			fos.close();
			plugin.getLogger().info("Player Data Saved!");
		} catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	public static void Load(JavaPlugin plugin)
	{
		try {
			plugin.getLogger().info("Loading Player Data...");
			File file = new File("plugins/AmulyzeRPG/players.aur"); //Attempts to load from file
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fin);
			AllPlayers = (HashMap<UUID, GamePlayer>) in.readObject();
			in.close();
			fin.close();
			plugin.getLogger().info("Player Data Loaded!");
		} catch(IOException io) {
			io.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
}
