package me.Norbik1004.amongus;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Bar {
	 private int taskID = -1;
	 private final Main plugin;
	 private BossBar bar;
	 double progress = 1.0;
	 double time = 1.0/(35 * 20);
	 
	 public Bar(Main plugin) {
		 this.plugin = plugin;
	 }
	 
	 public void addPlayer(Player player) {
		 bar.addPlayer(player);
	 }
	 
	 public BossBar getBar() {
		 return bar;
	 }
	 
	 public void createBar() {
		 bar = Bukkit.createBossBar(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "OXYGEN WARNING", BarColor.RED, BarStyle.SOLID);
		 cast();
	 }
	 
	 public void barOn() {
		 bar.setVisible(true);
		 progress = 1.0;
		 
	 }
	 
	 public void barOff() {
		 bar.setVisible(false);
	 }
	 
	 public void cast() {
		 taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			
			
			@Override
			public void run() {
				
				bar.setProgress(progress);
				
				progress = progress - time;
				if(progress <=0) {
					progress = 0.1;
					bar.setVisible(false);
					if(plugin.isOxSet) {
						plugin.endTimer();
					}
				}
			}
			 
		 }, 0, 0);
	 }
	 
	 public int getTaskID() {
		 return taskID;
	 }
	 
	 public void setTaskID(int taskID) {
		 this.taskID = taskID;
	 }
}
