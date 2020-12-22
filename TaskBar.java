package me.Norbik1004.amongus;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TaskBar {
	 private int taskID = 2;
	 private final Main plugin;
	 private BossBar bar;
	 double progress = 0;
	 double time = 0;
	 
	 public TaskBar(Main plugin) {
		 this.plugin = plugin;
	 }
	 
	 public void addPlayer(Player player) {
		 bar.addPlayer(player);
	 }
	 
	 public BossBar getBar() {
		 return bar;
	 }
	 
	 public void createBar() {
		 bar = Bukkit.createBossBar(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "TASKS DONE", BarColor.GREEN, BarStyle.SOLID);
		cast();
	 }
	 
	 public void barOn() {
		 bar.setVisible(true);
		 progress = 0.0000001;
		 bar.setProgress(progress);
	 }
	 
	 public void updateBar() {
		 
		 time = (plugin.taskDone()/1.0)/plugin.totalTask();
		 progress = time;
	 }
	 
	 public void barOff() {
		 bar.setVisible(false);
		 progress = 1.0;
	 }
	 
	 public void cast() {
		 taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			
			
			@Override
			public void run() {
				
				plugin.checkIfThere();
				
				
				bar.setProgress(progress);
				
				if(progress == 1.0) {
					bar.setVisible(false);
					plugin.crewmateWin();
					progress = 0.001;
					
				}
			}
			 
		 }, 0, 20);
	 }
	 
	 public int getTaskID() {
		 return taskID;
	 }
	 
	 public void setTaskID(int taskID) {
		 this.taskID = taskID;
	 }
}
