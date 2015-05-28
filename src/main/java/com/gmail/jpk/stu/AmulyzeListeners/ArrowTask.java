package com.gmail.jpk.stu.AmulyzeListeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Arrow;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.AmulyzeRPG.AmulyzeRPG;

public class ArrowTask extends BukkitRunnable {
	
	private static Map<Arrow, PotionEffectType> ElementalArrows = new HashMap<Arrow, PotionEffectType>();
	private static boolean isRunning = false;
	
	public ArrowTask() {
		isRunning = true;
	}
	
	public static Map<Arrow, PotionEffectType> getElementalArrows() {
		return ElementalArrows;
	}
	
	public static boolean getIsRunning() {
		return isRunning;
	}
	
	@Override
	public void run() {
		ElementalArrows.clear();
		AmulyzeRPG.info("Cleared, Arrow Cleanup: " + ElementalArrows.size());
		isRunning = false;
	}

}
