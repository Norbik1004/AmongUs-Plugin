package me.JayMar921.amongus;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class gameStartTimer {
	 private int taskID = 3;
	 private final Main plugin;
	 private BossBar bar;
	 double progress = 1.0;
	 double time = 1.0/(30 * 10);
	 boolean allow = false;
	 
	 public gameStartTimer(Main plugin) {
		 this.plugin = plugin;
	 }
	 
	 public void addPlayer(Player player) {
		 bar.addPlayer(player);
	 }
	 
	 public BossBar getBar() {
		 return bar;
	 }
	 
	 public void createBar() {
		 bar = Bukkit.createBossBar(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Game will start soon! "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"/among_us join", BarColor.YELLOW, BarStyle.SOLID);
		 cast();
	 }
	 
	 public void barOn() {
		 bar.setVisible(true);
		 progress = 1.0;
		 allow = true;
		 
	 }
	 
	 public void barOff() {
		 bar.setVisible(false);
	 }
	 
	 public void cast() {
		 taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			
			
			@Override
			public void run() {
				if(allow) {
				bar.setProgress(progress);
				if(progress > 0.2) {
					bar.setColor(BarColor.YELLOW);
				}else if(progress <= 0.2) {
					bar.setColor(BarColor.RED);
				}
				progress = progress - time;
				if(progress <=0.0001) {
					progress = 0.1;
					bar.setVisible(false);
					if(plugin.allowTimer) {
						plugin.gameStart();
						plugin.allowTimer = false;
						allow = false;
					}
				}
				}
			}
			 
		 }, 0, 10);
	 }
	 
	 public int getTaskID() {
		 return taskID;
	 }
	 
	 public void setTaskID(int taskID) {
		 this.taskID = taskID;
	 }
}
