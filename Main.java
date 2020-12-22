package me.Norbik1004.amongus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.Norbik1004.amongus.dataManager.dataManager;
import net.md_5.bungee.api.ChatColor;


@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener{
	

    //First Player in the List is the hidden one
	public gameStartTimer gst;
	public voteTimer votetimer;
	public Bar bar;
	public TaskBar taskbar;
	public dataManager data;
	//Declaration of variables/objects
	List<String> joinedPlayers = new ArrayList<String>();
	List<String> impostors = new ArrayList<String>();
	List<String> crewmates = new ArrayList<String>();
	List<String> deadPeople = new ArrayList<String>();
	List<String> coloredPlayers = new ArrayList<String>();
	List<String> downloadPlayers = new ArrayList<String>();
	List<String> gflPlayers = new ArrayList<String>();
	List<String> pflPlayers = new ArrayList<String>();
	List<String> uploadPlayers = new ArrayList<String>();
	Map<String, String> specificColor = new HashMap<String, String>();
	Map<String, Long> killCooldown = new HashMap<String, Long>();
	Map<String, ItemStack[]> joinedPlayersItem = new HashMap<String, ItemStack[]>();
	//Map<String, Long> meetingCD = new HashMap<String, Long>();
	int setImpostors = 0;
	int noImpostor = 0;
	int noCrewmate = 0;
	boolean isShuffled = false;
	boolean gameStarted = false;
	boolean allowTimer = false;
	int colorNo = 1;
	Random random = new Random();
	private BukkitTask countdown;
	BukkitTask load1;
	BukkitTask load2;
	BukkitTask load3;
	BukkitTask load4;
	//Maps to be loaded
	Location lobby;
	Location meeting1;
	Location meeting2;
	Location electrical1;
	Location electrical2;
	Location admin1;
	Location admin2;
	Location comm1;
	Location comm2;
	Location engine1;
	Location engine2;
	Location cafe1;
	Location cafe2;
	Location weapons1;
	Location weapons2;
	Location ox1;
	Location ox2;
	Location reactor1;
	Location reactor2;
	Location v1;
	Location v2;
	Location v3;
	Location v4;
	Location v5;
	Location v6;
	Location d1 = null;
	Location d2 = null;
	Location d3 = null;
	Location d4 = null;
	Location d5 = null;
	Location d6 = null;
	Location d7 = null;
	Location d8 = null;
	//votes
	int v_aqua = 0;
	int v_black = 0;
	int v_blue = 0;
	int v_green = 0;
	int v_red = 0;
	int v_white = 0;
	int v_yellow = 0;
	int v_purple = 0;
	int v_orange = 0;
	int v_gray = 0;
	int v_skip = 0;
	//Tasks
	public Inventory fxl;
	public Inventory sab;
	public Inventory o2;
	public Inventory vote;
	public Inventory c1;
	public Inventory c2;
	public Inventory scanId;
	public Inventory fixWire;
	public Inventory dl;
	public Inventory up;
	public Inventory gfl;
	public Inventory pfl;
	boolean sab_elect = false;
	boolean sab_ox = false;
	boolean isOxSet = false;
	int fixed = 0;
	String oxCode = "";
	String codeEntered = "";
	ItemStack voteCard = new ItemStack(Material.PAPER);
	boolean report = false;
	int voteAdded = 0;
	int totalTasks = 0;
	int taskDone = 0;
	int c1Stat = 0;
	int c2Stat = 0;
	int giveTaskId = 0;
	int fWire = 0;
	int download = 0;
	int upload = 0;
	int getFL = 0;
	int putFL = 0;
	
	

	
	
	@Override
	public void onEnable() {
		this.data = new dataManager(this);
		loadMap();
		scanIdWindow();
		cafe1Window();
		cafe2Window();
		FixLight();
		sabotageWindow();
		o2DepeleteWindow();
		fixWiredWindow();
		this.getServer().getPluginManager().registerEvents(this, this);
		bar = new Bar(this);
		bar.createBar();
		taskbar = new TaskBar(this);
		taskbar.createBar();
		votetimer = new voteTimer(this);
		votetimer.createBar();
		gst = new gameStartTimer(this);
		gst.createBar();
	}
	
	@Override
	public void onDisable() {
		
	}
	public int totalTask() {
		return totalTasks;
	}
	public int taskDone() {
		return taskDone;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("among_us")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Console cannot use this command :/");
				return true;
			}
			try {
			////////////////////////////////////////////////////////////////////////////////////////////////////
			//Player join command
				Player player = (Player) sender;
				if(args[0].equalsIgnoreCase("help")) {
					
					help(player);
					return true;
				}else if(args[0].equalsIgnoreCase("join")) {
				if(gameStarted) {
					sender.sendMessage(ChatColor.RED + "The game has already started.. please wait");
				}
					if(joinedPlayers.size() < 10) {
						if(!joinedPlayers.contains(player.getName())) {
							player.getInventory().clear();
						JoinEvent(player.getName());
						String toAnnounce = player.getName();
						
						for(Player announce: Bukkit.getOnlinePlayers()) {
								announce.sendMessage(ChatColor.GOLD + toAnnounce+" has joined [Among-us] game");
						}
						
						ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
						player.getEquipment().setHelmet(helmet);
						player.sendMessage(ChatColor.GOLD + "You joined the game... Please wait for the game to start");
						}else {
							player.sendMessage(ChatColor.RED + "You already joined the game, please wait for the host");
						}
					}else {
						player.sendMessage(ChatColor.RED + "Sorry.. the game is already full [try again when game ends]");
					}
					if(joinedPlayers.size() > 3) {
						if(setImpostors == 0) {
							setImpostors = 1;
						}
						if(!allowTimer)
							gst.barOn();
						allowTimer = true;
						for(Player all : Bukkit.getOnlinePlayers()) {
							gst.addPlayer(all);
						}
						
					}
				}
			//SETS THE NUMBER OF IMPOSTORS RANGE OF 1 - 2
				else if(args[0].equalsIgnoreCase("impostors")) {
					if(args[1].equalsIgnoreCase("1")) {
						if(sender.isOp()) {
							setImpostors = 1;
							sender.sendMessage(ChatColor.GOLD + "[AMONG US]: Impostors = 1");
						}else {
							sender.sendMessage(ChatColor.RED + "[AMONG US]: You must be op to use this command");
						}
					}else if(args[1].equalsIgnoreCase("2")) {
						if(sender.isOp()) {
							setImpostors = 2;
							sender.sendMessage(ChatColor.GOLD + "[AMONG US]: Impostors = 2");
						}else {
							sender.sendMessage(ChatColor.RED + "[AMONG US]: You must be op to use this command");
						}
					}else {
						sender.sendMessage(ChatColor.RED + "[AMONG US]: Impostors must be set to 1 or 2");
					}
					return true;
				}
				
			//Game start
				else if(args[0].equalsIgnoreCase("gamestart")) {
					if(sender.isOp()) {
						if(joinedPlayers.size() > 3) { //if there are at least 4 players joined the game
							if(setImpostors != 0) { //checks if thre is impostor set
								if(gameStarted) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: Game already started");
									return true;
								}
								
							
								
								if(meeting1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [meeting_1 location] was not loaded yet, please load the map");
									return true;
								}else if(electrical1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [electrical_1 location] was not loaded yet, please load the map");
									return true;
								}else if(admin1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [admin_1 location] was not loaded yet, please load the map");
									return true;
								}else if(comm1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [comm_1 location] was not loaded yet, please load the map");
									return true;
								}else if(engine1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [engine_1 location] was not loaded yet, please load the map");
									return true;
								}else if(cafe1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [cafe_1 location] was not loaded yet, please load the map");
									return true;
								}else if(weapons1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [weapon_1 location] was not loaded yet, please load the map");
									return true;
								}else if(ox1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [oxygen_1 location] was not loaded yet, please load the map");
									return true;
								}else if(reactor1 == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [reactor_1 location] was not loaded yet, please load the map");
									return true;
								}else if(lobby == null) {
									sender.sendMessage(ChatColor.RED + "[AMONG US]: The [Lobby location] was not loaded yet, please load the map");
									return true;
								}
								
								gameStart();
							//sender.sendMessage(ChatColor.GOLD + "[AMONG US]: Game will start");
							return true;
							}else {
								sender.sendMessage(ChatColor.RED + "[AMONG US]: There are no impostors set in the game");
							}
						}else {
							sender.sendMessage(ChatColor.RED + "[AMONG US]: There must be at least 4 players joined the game");
						}
					}else {
						sender.sendMessage(ChatColor.RED + "[AMONG US]: You must be op to use this command");
					}
				}
				
				//End the game command / reset
				else if (args[0].equalsIgnoreCase("gamestop")) {
					if(gameStarted) {
						gameStopEvent();
						sender.sendMessage(ChatColor.AQUA + "[AMONG US]: Game was stopped");
					}else {
						sender.sendMessage(ChatColor.RED + "[AMONG US]: Game has not started yet");
					}
				}
				//Saving maps
				else if(args[0].equalsIgnoreCase("save")) {
					if(args[1].equalsIgnoreCase("meeting1")) {
						mapAdd("meeting1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Meeting_1 Location is set");
					}else if(args[1].equalsIgnoreCase("meeting2")) {
						mapAdd("meeting2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Meeting_2 Location is set");
					}else if(args[1].equalsIgnoreCase("electrical1")) {
						mapAdd("electrical1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Electrical_1 Location is set");
					}else if(args[1].equalsIgnoreCase("electrical2")) {
						mapAdd("electrical2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Electrical_2 Location is set");
					}else if(args[1].equalsIgnoreCase("admin1")) {
						mapAdd("admin1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Admin_1 Location is set");
					}else if(args[1].equalsIgnoreCase("admin2")) {
						mapAdd("admin2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Admin_2 Location is set");
					}else if(args[1].equalsIgnoreCase("comm1")) {
						mapAdd("comm1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Communication_1 Location is set");
					}else if(args[1].equalsIgnoreCase("comm2")) {
						mapAdd("comm2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Communication_2 Location is set");
					}else if(args[1].equalsIgnoreCase("cafe1")) {
						mapAdd("cafe1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Cafeteria_1 Location is set");
					}else if(args[1].equalsIgnoreCase("cafe2")) {
						mapAdd("cafe2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Cafeteria_2 Location is set");
					}else if(args[1].equalsIgnoreCase("engine1")) {
						mapAdd("engine1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Engine_1 Location is set");
					}else if(args[1].equalsIgnoreCase("engine2")) {
						mapAdd("engine2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Engine_2 Location is set");
					}else if(args[1].equalsIgnoreCase("weapons1")) {
						mapAdd("weapon1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Weapon_1 Location is set");
					}else if(args[1].equalsIgnoreCase("weapons2")) {
						mapAdd("weapon2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Weapon_2 Location is set");
					}else if(args[1].equalsIgnoreCase("oxygen1")) {
						mapAdd("ox1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Oxygen_1 Location is set");
					}else if(args[1].equalsIgnoreCase("oxygen2")) {
						mapAdd("ox2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Oxygen_2 Location is set");
					}else if(args[1].equalsIgnoreCase("reactor1")) {
						mapAdd("re1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Reactor_1 Location is set");
					}else if(args[1].equalsIgnoreCase("reactor2")) {
						mapAdd("re2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Reactor_2 Location is set");
					}else if(args[1].equalsIgnoreCase("v1")) {
						mapAdd("v1", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_1 Location is set");
					}else if(args[1].equalsIgnoreCase("v2")) {
						mapAdd("v2", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_2 Location is set");
					}else if(args[1].equalsIgnoreCase("v3")) {
						mapAdd("v3", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_3 Location is set");
					}else if(args[1].equalsIgnoreCase("v4")) {
						mapAdd("v4", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_4 Location is set");	
					}else if(args[1].equalsIgnoreCase("v5")) {
						mapAdd("v5", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_5 Location is set");
					}else if(args[1].equalsIgnoreCase("v6")) {
						mapAdd("v6", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Vent_6 Location is set");
					}else if(args[1].equalsIgnoreCase("lobby")) {
						mapAdd("lobby", player.getLocation());
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: Lobby Location is set");
					}else {
						player.sendMessage(ChatColor.GOLD + "[AMONG US]: [Invalid Map Command]");
					}
					
				}else if(args[0].equalsIgnoreCase("loadmap")) {
					loadMap();
					player.sendMessage(ChatColor.GOLD + loadMap());;
				}
			
			
			
			
			
			
			
			
			
			
			
			
			
			}catch(Exception e) {
				
			}
		}
		
		return true;
	}
	public void gameStart() {

		shuffleEvent(); //shuffles the players to random crewmates and impostors
		for(Player online : Bukkit.getOnlinePlayers()) {
			playerGameTitle(online);
			playerGiveItem(online);
		}
		for(Player joined : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(joined.getName())) {
				joined.teleport(meeting1);
				joined.setCustomNameVisible(false);
				joined.setGameMode(GameMode.ADVENTURE);
				taskbar.addPlayer(joined);
			}
			gst.barOff();
		}
		
		for(Player online : Bukkit.getOnlinePlayers()) {
			playerGameTitle(online);
		}
		allowTimer = false;
		gameStarted = true;
		taskbar.barOn();
		addJoined();
	}
	
	public void addJoined() {
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(online.getName())) {
				joinedPlayersItem.put(online.getName(), online.getInventory().getContents());
				createBoard(online);
			}
		}
	}
	



	public void checkIfThere() {
		if(!gameStarted) return;
		try {
		List<String> names = new ArrayList<String>();
		for(Player players : Bukkit.getOnlinePlayers()) {
			names.add(players.getName());
		}
		for(String inGame : joinedPlayers) {
			if(names.contains(inGame)) {
				
			}else {
				ItemStack[] contents = joinedPlayersItem.get(inGame);
				for(ItemStack a : contents) {
					if(a.isSimilar(scanID())) {
						taskDone++;
					}
					if(a.isSimilar(dumpGarbage())) {
						taskDone++;
					}if(a.isSimilar(dumpGarbage2())) {
						taskDone++;
					}if(a.isSimilar(fixWires())) {
						taskDone++;
					}if(a.isSimilar(downloadWeapons()) || a.isSimilar(uploadWeapons())) {
						taskDone++;
					}if(a.isSimilar(uploadComms()) || a.isSimilar(downloadComms())) {
						taskDone++;
					}if(a.isSimilar(downloadOx()) || a.isSimilar(uploadOx())) {
						taskDone++;
					}if(a.isSimilar(getFuel()) || a.isSimilar(putFuel())) {
						taskDone++;
					}
				}
				if(crewmates.contains(inGame)) {
					noCrewmate--;
					crewmates.remove(inGame);
				}else if(impostors.contains(inGame)) {
					noImpostor--;
					impostors.remove(inGame);
				}
				if(noImpostor <= 0) {
					crewmateWin();
				}
				if(noCrewmate <= noImpostor) {
					impostorWin();
				}
				
				joinedPlayersItem.remove(inGame);
				joinedPlayers.remove(inGame);
			}
		}
		
		
		
		names.clear();
	
		
		}catch(Exception e) {
			
		}	
	}
	

	
	public void help(Player player) {
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		player.sendMessage(ChatColor.GOLD +"|                 [AMONG US PLUGIN BY Norbik1004]                 | ");
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		player.sendMessage(ChatColor.GOLD +"|  [HERE ARE THE COMMANDS]  ");
		player.sendMessage(ChatColor.GOLD +"|  [EVERYONE]         ");
		player.sendMessage(ChatColor.GOLD +"| /among_us join           - join a game     ");
		player.sendMessage(ChatColor.GOLD +"|  [OP players/ Admins]                                      ");
		player.sendMessage(ChatColor.GOLD +"| /among_us impostors <n>  - set numbers of impostors (1 - 2)");
		player.sendMessage(ChatColor.GOLD +"| /among_us gamestart      - start the game                  ");
		player.sendMessage(ChatColor.GOLD +"| /among_us gamestop       - stop the game                   ");
		player.sendMessage(ChatColor.GOLD +"| /among_us loadmap        - loads the map in config file    ");
		player.sendMessage(ChatColor.GOLD +"| /among_us save <map>     - save a specific area            ");
		player.sendMessage(ChatColor.GOLD +"| LIST OF <map>                                              ");
		player.sendMessage(ChatColor.GOLD +"|  meeting1 / meeting2     - save location for meeting place ");
		player.sendMessage(ChatColor.GOLD +"|  electrical1 / electrical2 - save location for electrical  ");
		player.sendMessage(ChatColor.GOLD +"|  admin1 / admin2         - save location for admin         ");
		player.sendMessage(ChatColor.GOLD +"|  comm1 / comm2           - save location for communications");
		player.sendMessage(ChatColor.GOLD +"|  cafe1 / cafe2           - save location for cafeteria     ");
		player.sendMessage(ChatColor.GOLD +"|  engine1 / engine2       - save location for engine room   ");
		player.sendMessage(ChatColor.GOLD +"|  weapons1 / weapons2     - save location for weapon room   ");
		player.sendMessage(ChatColor.GOLD +"|  oxygen1 / oxygen2       - save location for oxygen room   ");
		player.sendMessage(ChatColor.GOLD +"|  reactor1/ reactor2      - save location for reactor room  ");
		player.sendMessage(ChatColor.GOLD +"|  v1, v2, v3,... v6       - save location for vent1 to vent6");
		player.sendMessage(ChatColor.GOLD +"|  lobby                   - save location for lobby         ");
		player.sendMessage(ChatColor.GOLD +"|                                                           ");
		player.sendMessage(ChatColor.GOLD +"| [PLEASE NOTE THAT] The game can start even though there are");
		player.sendMessage(ChatColor.GOLD +"| no vents, but the rest of the locations must be set for the");
		player.sendMessage(ChatColor.GOLD +"| tasks of the crews and impostor/s                          ");
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		player.sendMessage(ChatColor.GOLD +"| To reset the maps saved, just delete the config file located");
		player.sendMessage(ChatColor.GOLD +"| at the plugins folder");
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		/*
		player.sendMessage(ChatColor.GOLD +"| [CONFUSED?]                                                ");
		player.sendMessage(ChatColor.GOLD +"| If you don't know how to use the plugin, feel free to watch");
		player.sendMessage(ChatColor.GOLD +"| my tutorials at Youtube, check it on my channel:  ");
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		player.sendMessage(ChatColor.GOLD +"| [ANY WEIRD STUFF BUGGIN'?]                                 ");
		player.sendMessage(ChatColor.GOLD +"| This plugin is still on process, feel free to report any   ");
		player.sendMessage(ChatColor.GOLD +"| issues at my discord, I'll try to fix it when");
		player.sendMessage(ChatColor.GOLD +"| I am available");
		player.sendMessage(ChatColor.GOLD +"=====================================================");
		*/
	}
	//Loading the map
	public String loadMap() {
		String status = "[AMONG US]: [MAP LOAD] -- ";
		if(!this.data.getConfig().contains("Among us")) {
			data.getConfig().set("Among us", "By Norbik1004, this file is the data folder where the locations are loaded and saved (deleting this file will reset the map locations)");
			data.saveConfig();
		}
		if(this.data.getConfig().contains("meeting1")) {
			meeting1 = this.data.getConfig().getLocation("meeting1");
			status += "[meeting_1 loaded] ";
		}else {
			status += "[meeting_1 not loaded] ";
			meeting1 = null;
		}
		if(this.data.getConfig().contains("meeting2")) {
			meeting2 = this.data.getConfig().getLocation("meeting2");
			status += "[meeting_2 loaded] ";
		}else {
			status += "[meeting_2 not loaded] ";
			meeting2 = null;
		}
		if(this.data.getConfig().contains("electrical1")) {
			electrical1 = this.data.getConfig().getLocation("electrical1");
			status += "[electrical_1 loaded] ";
		}else {
			status += "[electrical_1 not loaded] ";
			electrical1 = null;
		}
		if(this.data.getConfig().contains("electrical2")) {
			electrical2 = this.data.getConfig().getLocation("electrical1");
			status += "[electrical_2 loaded] ";
		}else {
			status += "[electrical_2 not loaded] ";
			electrical2 = null;
		}
		if(this.data.getConfig().contains("admin1")) {
			admin1 = this.data.getConfig().getLocation("admin1");
			status += "[admin_1 loaded] ";
		}else {
			status += "[admin_1 not loaded] ";
			admin1 = null;
		}
		if(this.data.getConfig().contains("admin2")) {
			admin2 = this.data.getConfig().getLocation("admin2");
			status += "[admin_2 loaded] ";
		}else {
			status += "[admin_2 not loaded] ";
			admin2 = null;
		}
		if(this.data.getConfig().contains("comm1")) {
			comm1 = this.data.getConfig().getLocation("comm1");
			status += "[comm_1 loaded] ";
		}else {
			status += "[comm_1 not loaded] ";
			comm1 = null;
		}
		if(this.data.getConfig().contains("comm2")) {
			comm2 = this.data.getConfig().getLocation("comm2");
			status += "[comm_2 loaded] ";
		}else {
			status += "[comm_2 not loaded] ";
			comm2 = null;
		}
		if(this.data.getConfig().contains("cafe1")) {
			cafe1 = this.data.getConfig().getLocation("cafe1");
			status += "[cafe_1 loaded] ";
		}else {
			status += "[cafe_1 not loaded] ";
			cafe1 = null;
		}
		if(this.data.getConfig().contains("cafe2")) {
			cafe2 = this.data.getConfig().getLocation("cafe2");
			status += "[cafe_2 loaded] ";
		}else {
			status += "[cafe_2 not loaded] ";
			cafe2 = null;
		}
		if(this.data.getConfig().contains("engine1")) {
			engine1 = this.data.getConfig().getLocation("engine1");
			status += "[engine_1 loaded] ";
		}else {
			status += "[engine_1 not loaded] ";
			engine1 = null;
		}
		if(this.data.getConfig().contains("engine2")) {
			engine2 = this.data.getConfig().getLocation("engine2");
			status += "[engine_2 loaded] ";
		}else {
			status += "[engine_2 not loaded] ";
			engine2 = null;
		}
		if(this.data.getConfig().contains("weapon1")) {
			weapons1 = this.data.getConfig().getLocation("weapon1");
			status += "[weapon_1 loaded] ";
		}else {
			status += "[weapon_1 not loaded] ";
			weapons1 = null;
		}
		if(this.data.getConfig().contains("weapon2")) {
			weapons2 = this.data.getConfig().getLocation("weapon2");
			status += "[weapon_2 loaded] ";
		}else {
			status += "[weapon_2 not loaded] ";
			weapons2 = null;
		}
		if(this.data.getConfig().contains("ox1")) {
			ox1 = this.data.getConfig().getLocation("ox1");
			status += "[oxygen_1 loaded] ";
		}else {
			status += "[oxygen_1 not loaded] ";
			ox1 = null;
		}
		if(this.data.getConfig().contains("ox2")) {
			ox2 = this.data.getConfig().getLocation("ox2");
			status += "[oxygen_2 loaded] ";
		}else {
			status += "[oxygen_2 not loaded] ";
			ox2 = null;
		}
		if(this.data.getConfig().contains("re1")) {
			reactor1 = this.data.getConfig().getLocation("re1");
			status += "[reactor_1 loaded] ";
		}else {
			status += "[reactor_1 not loaded] ";
			reactor1 = null;
		}
		if(this.data.getConfig().contains("re2")) {
			reactor2 = this.data.getConfig().getLocation("re2");
			status += "[reactor_2 loaded] ";
		}else {
			status += "[reactor_2 not loaded] ";
			reactor2 = null;
		}
		if(this.data.getConfig().contains("v1")) {
			v1 = this.data.getConfig().getLocation("v1");
			status += "[vent_1 loaded] ";
		}else {
			status += "[vent_1 not loaded] ";
			v1 = null;
		}
		if(this.data.getConfig().contains("v2")) {
			v2 = this.data.getConfig().getLocation("v2");
			status += "[vent_2 loaded] ";
		}else {
			status += "[vent_2 not loaded] ";
			v2 = null;
		}
		if(this.data.getConfig().contains("v3")) {
			v3 = this.data.getConfig().getLocation("v3");
			status += "[vent_3 loaded] ";
		}else {
			status += "[vent_3 not loaded] ";
			v3 = null;
		}
		if(this.data.getConfig().contains("v4")) {
			v4 = this.data.getConfig().getLocation("v4");
			status += "[vent_4 loaded] ";
		}else {
			status += "[vent_4 not loaded] ";
			v4 = null;
		}
		if(this.data.getConfig().contains("v5")) {
			v5 = this.data.getConfig().getLocation("v5");
			status += "[vent_5 loaded] ";
		}else {
			status += "[vent_5 not loaded] ";
			v5 = null;
		}
		if(this.data.getConfig().contains("v6")) {
			v6 = this.data.getConfig().getLocation("v6");
			status += "[vent_6 loaded] ";
		}else {
			status += "[vent_6 not loaded] ";
			v6 = null;
		}
		if(this.data.getConfig().contains("lobby")) {
			lobby = this.data.getConfig().getLocation("lobby");
			status += "[lobby loaded] ";
		}else {
			status += "[lobby not loaded] ";
			lobby = null;
		}
		
		return status;
	}
	//Saving map event
	public void mapAdd(String area, Location loc) {
		
		if(area.contains("meeting1")) {
			meeting1 = loc;
			data.getConfig().set("meeting1", (loc));
			data.saveConfig();
		}
		if(area.contains("meeting2")) {
			meeting2 = loc;
			data.getConfig().set("meeting2", (loc));
			data.saveConfig();
		}
		if(area.contains("electrical1")) {
			electrical1 = loc;
			data.getConfig().set("electrical1", (loc));
			data.saveConfig();
		}
		if(area.contains("electrical2")) {
			electrical2 = loc;
			data.getConfig().set("electrical2", (loc));
			data.saveConfig();
		}
		if(area.contains("admin1")) {
			admin1 = loc;
			data.getConfig().set("admin1", (loc));
			data.saveConfig();
		}
		if(area.contains("admin2")) {
			admin2 = loc;
			data.getConfig().set("admin2", (loc));
			data.saveConfig();
		}
		if(area.contains("comm1")) {
			comm1 = loc;
			data.getConfig().set("comm1", (loc));
			data.saveConfig();
		}
		if(area.contains("comm2")) {
			comm2 = loc;
			data.getConfig().set("comm2", (loc));
			data.saveConfig();
		}
		if(area.contains("cafe1")) {
			cafe1 = loc;
			data.getConfig().set("cafe1", (loc));
			data.saveConfig();
		}
		if(area.contains("cafe2")) {
			cafe2 = loc;
			data.getConfig().set("cafe2", (loc));
			data.saveConfig();
		}
		if(area.contains("engine1")) {
			engine1 = loc;
			data.getConfig().set("engine1", (loc));
			data.saveConfig();
		}
		if(area.contains("engine2")) {
			engine2 = loc;
			data.getConfig().set("engine2", (loc));
			data.saveConfig();
		}
		if(area.contains("weapon1")) {
			weapons1 = loc;
			data.getConfig().set("weapon1", (loc));
			data.saveConfig();
		}
		if(area.contains("weapon2")) {
			weapons2 = loc;
			data.getConfig().set("weapon2", (loc));
			data.saveConfig();
		}
		if(area.contains("ox1")) {
			ox1 = loc;
			data.getConfig().set("ox1", (loc));
			data.saveConfig();
		}
		if(area.contains("ox2")) {
			ox2 = loc;
			data.getConfig().set("ox2", (loc));
			data.saveConfig();
		}
		if(area.contains("re1")) {
			reactor1 = loc;
			data.getConfig().set("re1", (loc));
			data.saveConfig();
		}
		if(area.contains("re2")) {
			reactor2 = loc;
			data.getConfig().set("re2", (loc));
			data.saveConfig();
		}
		if(area.contains("v1")) {
			v1 = loc;
			data.getConfig().set("v1", (loc));
			data.saveConfig();
		}
		if(area.contains("v2")) {
			v2 = loc;
			data.getConfig().set("v2", (loc));
			data.saveConfig();
		}
		if(area.contains("v3")) {
			v3 = loc;
			data.getConfig().set("v3", (loc));
			data.saveConfig();
		}
		if(area.contains("v4")) {
			v4 = loc;
			data.getConfig().set("v4", (loc));
			data.saveConfig();
		}
		if(area.contains("v5")) {
			v5 = loc;
			data.getConfig().set("v5", (loc));
			data.saveConfig();
		}
		if(area.contains("v6")) {
			v6 = loc;
			data.getConfig().set("v6", (loc));
			data.saveConfig();
		}
		if(area.contains("lobby")) {
			lobby = loc;
			data.getConfig().set("lobby", (loc));
			data.saveConfig();
		}
	}
	//SCOREBOARD
	public void createBoard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("HubScoreboard-1", "dummy", ChatColor.RED +"[ AMONG US ]");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score1 = obj.getScore(ChatColor.LIGHT_PURPLE + "Hi "+player.getName());
		score1.setScore(5);
		Score score2 = obj.getScore(ChatColor.GREEN + "Online Players: "+ChatColor.AQUA+Bukkit.getOnlinePlayers().size());
		score2.setScore(4);
		Score score5 = obj.getScore(ChatColor.GREEN + "Total Impostors: "+ChatColor.RED+noImpostor);
		score5.setScore(3);
		Score score3 = obj.getScore(ChatColor.GREEN + "Total Tasks: "+ChatColor.GREEN+ totalTasks);
		score3.setScore(2);
		if(crewmates.contains(player.getName())) {
			Score score4 = obj.getScore(ChatColor.GREEN + "ROLE: "+ChatColor.DARK_GREEN+ "CREW");
			score4.setScore(1);
		}else if(impostors.contains(player.getName())) {
			Score score4 = obj.getScore(ChatColor.GREEN + "ROLE: "+ChatColor.DARK_RED+ "IMPOSTOR");
			score4.setScore(1);
		}else {
			Score score4 = obj.getScore(ChatColor.GREEN + "ROLE: "+ChatColor.WHITE+ "NONE");
			score4.setScore(1);
		}
		player.setScoreboard(board);
		
	}
	
	/////////////////////
	//player Join event, it registers player to the arraylist
	public void JoinEvent(String name) { 
		if(joinedPlayers.size() < 10) {
			joinedPlayers.add(name);
		}
	}
	//Displays the title then do the blind and slow effect
	public void playerGameTitle(Player player) {
		if(impostors.contains(player.getName()))
			player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"Impostor",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
			killCooldown.put(player.getName(), System.currentTimeMillis() + (20 * 1000));
		if(crewmates.contains(player.getName()))
			player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"Crewmate",ChatColor.LIGHT_PURPLE+ "there is "+setImpostors+" Impostor among us", 30, 100, 30);
		if(joinedPlayers.contains(player.getName())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 0, false, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 0, false, false, false));
		}
		
	}
	
	
	//Gives player the Item
	public void playerGiveItem(Player player) {
		if(impostors.contains(player.getName())) {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_RED + "Kill");
			lore.add(ChatColor.AQUA + "punch the crew with this paper to kill (20s)");
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
			lore.clear();
			/*
			meta.setDisplayName(ChatColor.DARK_GREEN+ "Vent");
			lore.add(ChatColor.AQUA + "right click use when near vents to hide");
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
			*/
			lore.clear();
			meta.setDisplayName(ChatColor.DARK_GREEN+ "Sabotage");
			lore.add(ChatColor.AQUA + "right click to open sabotage window");
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
		}
		if(joinedPlayers.contains(player.getName())) {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GREEN+ "Report!");
			lore.add(ChatColor.AQUA + "Right click to use");
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
		//	giveTaskItem(player);
			
			
		}
		if(crewmates.contains(player.getName())) {
			giveTaskItem(player);
		}
	}
	ItemStack itemEmergency() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Emergency");
		lore.add(ChatColor.AQUA + "Call a Meeting!!  (25s Cooldown when used)");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack dumpGarbage() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Dump Garbage");
		lore.add(ChatColor.AQUA + "Go to cafeteria");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	
	ItemStack scanID() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Scan ID");
		lore.add(ChatColor.AQUA + "Scan this card at admin");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack dumpGarbage2() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Dump Garbages");
		lore.add(ChatColor.AQUA + "Go to cafeteria");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack fixWires() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Fix Wires");
		lore.add(ChatColor.AQUA + "Go to electrical room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack downloadWeapons() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Download Data at Weapons");
		lore.add(ChatColor.AQUA + "Go to weapon room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack uploadWeapons() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Upload Data at Admin");
		lore.add(ChatColor.AQUA + "Go to admin room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack downloadComms() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Download Data at Communication");
		lore.add(ChatColor.AQUA + "Go to communication room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack uploadComms() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Upload Data at Cafeteria");
		lore.add(ChatColor.AQUA + "Go to weapon room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack downloadReact() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Download Data at Reactor");
		lore.add(ChatColor.AQUA + "Go to reactor room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack uploadReact() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Upload Data at Electrical");
		lore.add(ChatColor.AQUA + "Go to electrical room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack downloadOx() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Download Data at Oxygen Room");
		lore.add(ChatColor.AQUA + "Go to oxygen room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack uploadOx() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Upload Data at Weapons");
		lore.add(ChatColor.AQUA + "Go to weapon room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack getFuel() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Get fuel at Reactor");
		lore.add(ChatColor.AQUA + "Go to reactor room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack putFuel() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GREEN+ "Task - Refuel the engine");
		lore.add(ChatColor.AQUA + "Go to engine room");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	ItemStack FUEL() {
		ItemStack item = new ItemStack(Material.COAL);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GOLD+ "FUEL");
		lore.add(ChatColor.AQUA + "[Don't eat it]");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	
	//GIVES TASKS TO THE CREW
	public void giveTaskItem(Player player) {
		giveTaskId++;
		if(giveTaskId == 1) {
			player.getInventory().addItem(dumpGarbage());
			player.getInventory().addItem(downloadWeapons());
			player.getInventory().addItem(scanID());
			player.getInventory().addItem(fixWires());
			player.getInventory().addItem(getFuel());
			totalTasks+=5;
		}else if(giveTaskId == 2) {
			player.getInventory().addItem(dumpGarbage2());
			player.getInventory().addItem(scanID());
			player.getInventory().addItem(fixWires());
			player.getInventory().addItem(downloadComms());
			
			totalTasks+=4;
		}else if(giveTaskId == 3) {
			player.getInventory().addItem(downloadReact());
			player.getInventory().addItem(scanID());
			player.getInventory().addItem(downloadOx());
			player.getInventory().addItem(dumpGarbage2());
			totalTasks+=4;
		}
		if(giveTaskId >= 3) {
			giveTaskId = 0;
		}
	}
	
	////////////////////
	//Shuffle event
	public void shuffleEvent() {
		if(!isShuffled) {
		try {
		while(noImpostor < setImpostors) {
			
			for(String name: joinedPlayers) {
				if(noImpostor < setImpostors) {
					name = joinedPlayers.get(random.nextInt(joinedPlayers.size()));
					if(!impostors.contains(name)) {
						impostors.add(name);
						noImpostor++;
					}
				}
			}
			
			noCrewmate = joinedPlayers.size() - noImpostor;
		}
		
		for(String name: joinedPlayers) {
			if(!impostors.contains(name)) {
				crewmates.add(name);
			}
		}
		isShuffled = true;
		}catch(Exception e) {
			
		}
		}
	}
	//Clear heads location
	public void clearDeathEvent() {
		if(d1 != null) {
			d1.getBlock().setType(Material.AIR);
			d1 = null;
		}
		if(d2 != null) {
			d2.getBlock().setType(Material.AIR);
			d2 = null;
		}
		if(d3 != null) {
			d3.getBlock().setType(Material.AIR);
			d3 = null;
		}
		if(d4 != null) {
			d4.getBlock().setType(Material.AIR);
			d4 = null;
		}
		if(d5 != null) {
			d5.getBlock().setType(Material.AIR);
			d5 = null;
		}
		if(d6 != null) {
			d6.getBlock().setType(Material.AIR);
			d6 = null;
		}
		if(d7 != null) {
			d7.getBlock().setType(Material.AIR);
			d7 = null;
		}
		if(d8 != null) {
			d8.getBlock().setType(Material.AIR);
			d8 = null;
		}
	}
	@EventHandler()
	public void cannotMove(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		if(player.hasPotionEffect(PotionEffectType.SLOW)) {
			if(joinedPlayers.contains(player.getName())) {
				event.setCancelled(true);
			}
		}
	}
	
	public void addVotingEffects() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(player.getName())) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0, false, false, false));
			}
		}
	}
	
	public void removeVotingEffects() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(player.getName())) {
				player.removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	
	public void broadcastAll() {
		for(Player everyone: Bukkit.getOnlinePlayers()) {
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=============================");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "|              [AMONG US]        ");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=============================");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "| GAME HAS ENDED            ");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "| To join, type the command ");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "| /among_us join            ");
			everyone.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=============================");
		}
	}
	public void reportTick() {
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(impostors.contains(online.getName())) {
				online.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"DEAD BODY REPORTED!!",ChatColor.LIGHT_PURPLE+ "Someone found a body", 30, 100, 30);
				online.setGameMode(GameMode.SPECTATOR);
				online.teleport(lobby);
			}else if(crewmates.contains(online.getName())){
				online.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"DEAD BODY REPORTED!!",ChatColor.LIGHT_PURPLE+ "Someone found a body", 30, 100, 30);
				online.setGameMode(GameMode.ADVENTURE);
				online.teleport(lobby);
			}
			
		}
	}
	public void crewmateWin() {
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(impostors.contains(online.getName())) {
				online.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"Crewmate win",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
				online.setGameMode(GameMode.SPECTATOR);
				online.teleport(lobby);
			}else if(crewmates.contains(online.getName())){
				online.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"Crewmate win",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
				online.setGameMode(GameMode.ADVENTURE);
				online.teleport(lobby);
			}else {
				online.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD +"AMONG US",ChatColor.LIGHT_PURPLE+ "Game has ended", 30, 100, 30);
				
			}
			
		}
		gameStopEvent();
		sab_ox = false;
		isOxSet = false;
	}
	
	public void impostorWin() {
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(impostors.contains(online.getName())) {
				online.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"Impostor win",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
				online.setGameMode(GameMode.SPECTATOR);
				online.teleport(lobby);
			}else if(crewmates.contains(online.getName())){
				online.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"Impostor win",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
				online.setGameMode(GameMode.ADVENTURE);
				online.teleport(lobby);
			}else {
				online.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD +"AMONG US",ChatColor.LIGHT_PURPLE+ "Game has ended", 30, 100, 30);
				
			}
			
		}
		gameStopEvent();
		sab_ox = false;
		isOxSet = false;
	}
	//Stopping the game event
	public void gameStopEvent() {
		clearItem();
		joinedPlayers.clear();
		impostors.clear();
		crewmates.clear();
		deadPeople.clear();
		coloredPlayers.clear();
		downloadPlayers.clear();
		gflPlayers.clear();
		pflPlayers.clear();
		uploadPlayers.clear();
		specificColor.clear();
		killCooldown.clear();
		//setImpostors = 0;
		voteAdded = 0;
		noImpostor = 0;
		noCrewmate = 0;
		colorNo = 1;
		oxCode = "";
		gameStarted = false;
		isShuffled = false;
		clearDeathEvent();
		sab_ox = false;
		sab_elect = false;
		giveTaskId = 0;
		totalTasks = 0;
		taskDone = 0;
		bar.barOff();
		taskbar.barOff();
		broadcastAll();
		allowTimer = false;
	
		for(Player online : Bukkit.getOnlinePlayers()) {
			createBoard(online);
			if(online.hasPotionEffect(PotionEffectType.BLINDNESS))
				online.removePotionEffect(PotionEffectType.BLINDNESS);
		}
			
		
	}
	
	
	///////////////////////
	//get helmet colors
	@EventHandler()
	public void getColors(PlayerMoveEvent event) {
		try {
			Player player = (Player) event.getPlayer();
			Location loc = player.getLocation();
			
				if(!joinedPlayers.contains(player.getName())) {
					
					if(meeting1.distance(loc) < 20 || meeting2.distance(loc) < 20)
						player.teleport(lobby);
				}
			
			if(!isShuffled) return;
			if(coloredPlayers.contains(player.getName()))return;
			if(gameStarted) {
				if(joinedPlayers.contains(player.getName())) {
					
					
					if(!player.getInventory().getHelmet().hasItemMeta()) {
						ItemStack helmet = player.getInventory().getHelmet();
						
						if(colorNo==1)
							specificColor.put("CYAN", player.getName());
						else if(colorNo==2)
							specificColor.put("BLACK", player.getName());
						else if(colorNo==3)
							specificColor.put("BLUE", player.getName());
						else if(colorNo==4)
							specificColor.put("GREEN", player.getName());
						else if(colorNo==5)
							specificColor.put("RED", player.getName());
						else if(colorNo==6)
							specificColor.put("WHITE", player.getName());
						else if(colorNo==7)
							specificColor.put("YELLOW", player.getName());
						else if(colorNo==8)
							specificColor.put("PURPLE", player.getName());
						else if(colorNo==9)
							specificColor.put("ORANGE", player.getName());
						else if(colorNo==10)
							specificColor.put("GRAY", player.getName());
						
						voteWindow();
						helmet = changeColor(helmet, colors());
						coloredPlayers.add(player.getName());
					}
				}
			}
			
		}catch(Exception e) {
			
		}
	}
	//////////////////////////
	//set helmet armors
	public ItemStack changeColor(ItemStack a, Color color) {
		
		try {
			if(a.getType() == Material.LEATHER_HELMET) {
				LeatherArmorMeta meta = (LeatherArmorMeta) a.getItemMeta();
				meta.setColor(color);
				a.setItemMeta(meta);
			}
		}catch(Exception e) {
			
		}
		
		return null;
	}
	//Set player colors
	public Color colors() {
		
		if(colorNo == 1) {
			colorNo++;
			return Color.AQUA;
		}else if(colorNo == 2) {
			colorNo++;
			return Color.BLACK;
		}else if(colorNo == 3) {
			colorNo++;
			return Color.BLUE;
		}else if(colorNo == 4) {
			colorNo++;
			return Color.GREEN;
		}else if(colorNo == 5) {
			colorNo++;
			return Color.RED;
		}else if(colorNo == 6) {
			colorNo++;
			return Color.WHITE;
		}else if(colorNo == 7) {
			colorNo++;
			return Color.YELLOW;
		}else if(colorNo == 8) {
			colorNo++;
			return Color.PURPLE;
		}else if(colorNo == 9) {
			colorNo++;
			return Color.ORANGE;
		}else if(colorNo == 10) {
			colorNo++;
			return Color.GRAY;
		}
		
		return null;
	}
	
	
 
    void endTimer() {
    	impostorWin();
	}
	
	//clear Item uppon win/lose
	public void clearItem() {
		if(gameStarted) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(joinedPlayers.contains(player.getName())) {
					player.getInventory().clear();
				}
			}
		}
	}
	
	@EventHandler()
	public void impostorKillEvent(EntityDamageByEntityEvent event) {
		if(!gameStarted) return;
			Player attacker = null;
			Player defender = null;
			try {
			boolean kill = false;
			if( event.getDamager() instanceof Player ) {	
				attacker = (Player)event.getDamager();
				if(impostors.contains(attacker.getName())) {
				if(attacker.getInventory().getItemInMainHand().hasItemMeta() && (attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Kill"))) {
					if(attacker.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						if(attacker.hasPotionEffect(PotionEffectType.SLOW)) {
							attacker.sendMessage(ChatColor.DARK_RED + "Cannot exceute kill for now...");
							return;
						}
						if(killCooldown.containsKey(attacker.getName())) {
							// player is inside cooldown hasmap
							if(killCooldown.get(attacker.getName()) > System.currentTimeMillis()) {
								long timeleft = (killCooldown.get(attacker.getName()) - System.currentTimeMillis()) / 1000;
								attacker.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Kill is on cooldown... wait for "+timeleft+ "s");
								event.setCancelled(true);
								return;
							}
						}
						killCooldown.put(attacker.getName(), System.currentTimeMillis() + (20 * 1000));
						
						
						
						kill = true;
						event.setCancelled(true);
					}else {
						kill = false;
						event.setCancelled(true);
					}
				}
				}
			}
			if(event.getEntity() instanceof Player) {
			    defender = (Player)event.getEntity();
			}
			
			if(kill) {
				if(crewmates.contains(defender.getName())) {
				noCrewmate--;
				Location loc = defender.getLocation();
				upponDeath(loc);
				deadPeople.add(defender.getName());
				crewmates.remove(defender.getName());
				defender.teleport(meeting1);
				defender.setGameMode(GameMode.SPECTATOR);
				defender.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"You died",ChatColor.LIGHT_PURPLE+ "Among us", 30, 100, 30);
				defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 1, false, false, false));
				
				if(defender.getInventory().contains(dumpGarbage())) {
					taskDone++;
				}
				if(defender.getInventory().contains(scanID())) {
					taskDone++;
				}
				if(defender.getInventory().contains(dumpGarbage2())) {
					taskDone++;
				}
				if(defender.getInventory().contains(fixWires())) {
					taskDone++;
				}
				if(defender.getInventory().contains(downloadWeapons()) || defender.getInventory().contains(uploadWeapons())) {
					taskDone++;
				}
				
				if(defender.getInventory().contains(downloadComms()) || defender.getInventory().contains(uploadComms())) {
					taskDone++;
				}
				if(defender.getInventory().contains(downloadReact()) || defender.getInventory().contains(uploadReact())) {
					taskDone++;
				}
				if(defender.getInventory().contains(downloadOx()) || defender.getInventory().contains(uploadOx())) {
					taskDone++;
				}
				if(defender.getInventory().contains(getFuel()) || defender.getInventory().contains(putFuel())) {
					taskDone++;
				}
				event.setCancelled(true);
				if(noCrewmate <= noImpostor) {
					impostorWin();
				}
				}
			}
		 
			}catch(Exception e) {
				
			}
			event.setCancelled(true);
		}
	
	
	@EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
		try{
			if(deadPeople.contains(event.getPlayer().getName())) {
				if(event.getMessage().equalsIgnoreCase("among_us_stop")) {
					gameStopEvent();
				}else {
				event.getPlayer().sendMessage(ChatColor.RED + "Dead players cannot chat, please wait until the game ends");
				event.setCancelled(true);
				}
			}
        
            
		}catch(Exception e) {
			
		}
    }
	
	//Uppon death
	public void upponDeath(Location loc) {
		if(d1 == null) {
			d1 = loc;
			d1.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d2 == null) {
			d2 = loc;
			d2.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d3 == null) {
			d3 = loc;
			d3.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d4 == null) {
			d4 = loc;
			d4.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d5 == null) {
			d5 = loc;
			d5.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d6 == null) {
			d6 = loc;
			d6.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d7 == null) {
			d7 = loc;
			d7.getBlock().setType(Material.PLAYER_HEAD);
		}else if(d8 == null) {
			d8 = loc;
			d8.getBlock().setType(Material.PLAYER_HEAD);
		}
	}
	//sabotage event
	@EventHandler()
	public void rightClickSabotage(PlayerInteractEvent event) {
		try {
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		
				Player player = (Player)event.getPlayer();
				if(player.hasPotionEffect(PotionEffectType.SLOW)) {
					player.sendMessage(ChatColor.DARK_RED + "Cannot exceute Sabotage for now...");
					return;
				}
				if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Sabotage")) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						player.openInventory(sab);
					}
				}
			}
		}catch(Exception e) {
			
		}
		}
	
	//SABOTAGE WINDOW CLICK
	@EventHandler
	public void ClickSabotageInv(InventoryClickEvent event) {
		if(!(event.getInventory().equals(sab)))
			return;
		if(event.getCurrentItem() == null) return;
		if(event.getCurrentItem().getItemMeta() == null) return;
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SABOTAGE ELECTRICAL")) {
			if(!sab_elect && !sab_ox) {
				player.sendMessage(ChatColor.DARK_RED + "You sabotaged the electrical room");
				electricalRoomDown();
				player.closeInventory();
			}else {
				player.sendMessage(ChatColor.DARK_RED + "You cannot sabotage the electrical room");
			}
		}else if(event.getSlot() == 1 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SABOTAGE OXYGEN")) {
			if(!sab_ox && !sab_elect) {
				player.sendMessage(ChatColor.DARK_RED + "You sabotaged the oxygen room");
				o2DepeleteWindow();
				oxygenRoomDown();
				player.closeInventory();
			}else {
				player.sendMessage(ChatColor.DARK_RED + "You cannot sabotage the oxygen room");
			}
		}
		
	}
	//SABOTAGE WINDOW
	public void sabotageWindow() {
		sab = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "SABOTAGE"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" Among us");
		
		
		ItemStack item = new ItemStack(Material.REDSTONE_LAMP);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SABOTAGE ELECTRICAL");
		item.setItemMeta(meta);
		sab.setItem(0, item);
		
		item.setType(Material.GLASS);
		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SABOTAGE OXYGEN");
		item.setItemMeta(meta);
		sab.setItem(1, item);
		
		item.setType(Material.OAK_SIGN);
		ItemMeta meta7 = item.getItemMeta();
		meta7.setDisplayName(ChatColor.DARK_AQUA +  "" + ChatColor.BOLD +  "DONE");
		item.setItemMeta(meta7);
		sab.setItem(8, item);
		
	}
	
	public void electricalRoomDown() {
		sab_elect = true;
		FixLight();
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(players.getName())) {
				if(!deadPeople.contains(players.getName())) {
					//gives player the task card
					ItemStack item = new ItemStack(Material.PAPER);
					ItemMeta meta = item.getItemMeta();
					List<String> lore = new ArrayList<String>();
					
					meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.BLUE + "Task - Fix lights");
					lore.add(ChatColor.YELLOW + "- Go to electrical room" );
					lore.add(ChatColor.LIGHT_PURPLE + "  (Right click to use) " );
					if(meta.hasLore())
						for(String l : meta.getLore())
							lore.add(l);
					meta.setLore(lore);
					item.setItemMeta(meta);
					players.getInventory().addItem(item);
					players.sendMessage(ChatColor.GOLD + "fix the light");
					
					
					players.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"[WARNING]",ChatColor.LIGHT_PURPLE+ "Electrical room was sabotaged", 30, 100, 30);
					if(crewmates.contains(players.getName()))
						players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000000, 100, false, false, false));
				}
			}
		}
	}
	//triggers when oxygen is sabotaged
	public void oxygenRoomDown() {
		sab_ox = true;
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.BLUE + "Task - Fix Oxygen");
		lore.add(ChatColor.YELLOW + "- Go to oxygen room" );
		
		if(Math.random() <= 0.20) {
			oxCode = "454565";
			lore.add(ChatColor.YELLOW + "- Enter the code: 454565" );
		}else if(Math.random() <= 0.20) {
			oxCode = "291535";
			lore.add(ChatColor.YELLOW + "- Enter the code: 291535" );
		}else if(Math.random() <= 0.20) {
			oxCode = "121298";
			lore.add(ChatColor.YELLOW + "- Enter the code: 121298" );
		}else if(Math.random() <= 0.20) {
			oxCode = "325476";
			lore.add(ChatColor.YELLOW + "- Enter the code: 325476" );
		}else if(Math.random() <= 0.20) {
			oxCode = "326475";
			lore.add(ChatColor.YELLOW + "- Enter the code: 326475" );
		}else if(Math.random() <= 0.20) {
			oxCode = "983163";
			lore.add(ChatColor.YELLOW + "- Enter the code: 983163" );
		}else {
			oxCode = "293847";
			lore.add(ChatColor.YELLOW + "- Enter the code: 293847" );
		}
		
		lore.add(ChatColor.LIGHT_PURPLE + "  (Right click to use) " );
		if(meta.hasLore())
			for(String l : meta.getLore())
				lore.add(l);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		if(!isOxSet) {
			if(sab_ox) {
					
						isOxSet = true;
						
						
						for(Player joined: Bukkit.getOnlinePlayers()) {
							if(joinedPlayers.contains(joined.getName())) {
								bar.addPlayer(joined);
								bar.barOn();
							}
						}
			}
		}
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(joinedPlayers.contains(players.getName())) {
				if(!deadPeople.contains(players.getName())) {
					//gives player the task card
					
					players.getInventory().addItem(item);
					players.sendMessage(ChatColor.GOLD + "fix the oxygen tank");
					
					
					players.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"[WARNING]",ChatColor.LIGHT_PURPLE+ "Oxygen tank was depleted, you have 35s", 30, 100, 30);
					//if(crewmates.contains(players.getName()))
						//players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000000, 100, false, false, false));
				}
			}
		}
	}
	//FIX LIGHT INVENTORY
	public void FixLight() {
		fxl = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Turn on all lights"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" Among us");
		
		
		ItemStack item = new ItemStack(Material.REDSTONE_LAMP);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SWITCH");
		item.setItemMeta(meta);
		fxl.setItem(0, item);
		fxl.setItem(1, item);
		fxl.setItem(2, item);
		fxl.setItem(3, item);
		fxl.setItem(4, item);
		fxl.setItem(5, item);
		fxl.setItem(6, item);
		fxl.setItem(7, item);
		item.setType(Material.OAK_SIGN);
		ItemMeta meta7 = item.getItemMeta();
		meta7.setDisplayName(ChatColor.DARK_AQUA +  "" + ChatColor.BOLD +  "DONE");
		item.setItemMeta(meta7);
		fxl.setItem(8, item);
		
	}
	@EventHandler()
	public void rightClickTask(PlayerInteractEvent event) {
		try {
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		
				Player player = (Player)event.getPlayer();
				Location loc = player.getLocation();
				if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Fix lights")) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						if(electrical1.distance(loc) < 2 || electrical2.distance(loc) < 2) {
						player.openInventory(fxl);
						}else {
							player.sendMessage(ChatColor.RED+"You must go to the electrical room");
						}
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Fix Oxygen")) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						if(ox1.distance(loc) < 2 || ox2.distance(loc) < 2) {
						player.openInventory(o2);
						}else {
							player.sendMessage(ChatColor.RED+"You must go to the oxygen room");
						}
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Report!")) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
							
						
						if(report) {player.sendMessage(ChatColor.RED+"Report is in use");return;}
						if(d1.distance(loc) < 3 || d2.distance(loc) < 3 || d3.distance(loc) < 3 || d4.distance(loc) < 3 || d5.distance(loc) < 3 || d6.distance(loc) < 3 || d7.distance(loc) < 3 || d8.distance(loc) < 3 ) {
						reportTick();
						voteWindow();
						report = true;
						votetimer.barOn();
						addVotingEffects();
						for(Player online : Bukkit.getOnlinePlayers()) {
							if(joinedPlayers.contains(online.getName()) && (!deadPeople.contains(online.getName()))) {
								online.teleport(meeting1);
								ItemMeta meta = voteCard.getItemMeta();
								meta.setDisplayName(ChatColor.GOLD + "Vote");
								voteCard.setItemMeta(meta);
								online.getInventory().addItem(voteCard);
								
							}
						}
					}
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Vote")) {
					
						if(meeting1.distance(loc) < 5 || meeting2.distance(loc) < 5) {
						player.openInventory(vote);
						}else {
							player.sendMessage(ChatColor.RED+"You must be in meeting place");
						}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Dump Garbage")) {
					
					if(cafe1.distance(loc) < 2 || cafe2.distance(loc) < 2) {
					player.openInventory(c1);
				}
			}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Dump Garbages")) {
				
				if(cafe1.distance(loc) < 2 || cafe2.distance(loc) < 2) {
				player.openInventory(c2);
			}
			}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Scan ID")) {
			
					if(admin1.distance(loc) < 2 || admin2.distance(loc) < 2) {
						player.openInventory(scanId);
					}
				}
			else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Fix Wires")) {
			
					if(electrical1.distance(loc) < 2 || electrical2.distance(loc) < 2) {
							player.openInventory(fixWire);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Download Data at Weapons")) {
				
					if(weapons1.distance(loc) < 2 || weapons2.distance(loc) < 2) {
						downloadWindow();
						player.openInventory(dl);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Upload Data at Admin")) {
				
					if(admin1.distance(loc) < 2 || admin2.distance(loc) < 2) {
						uploadWindow();
						player.openInventory(up);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Download Data at Communication")) {
				
					if(comm1.distance(loc) < 2 || comm2.distance(loc) < 2) {
						downloadWindow();
						player.openInventory(dl);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Upload Data at Weapons")) {
				
					if(weapons1.distance(loc) < 2 || weapons2.distance(loc) < 2) {
						uploadWindow();
						player.openInventory(up);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Download Data at Reactor")) {
				
					if(reactor1.distance(loc) < 2 || reactor2.distance(loc) < 2) {
						downloadWindow();
						player.openInventory(dl);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Upload Data at Electrical")) {
				
					if(electrical1.distance(loc) < 2 || electrical2.distance(loc) < 2) {
						uploadWindow();
						player.openInventory(up);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Download Data at Oxygen Room")) {
				
					if(ox1.distance(loc) < 2 || ox2.distance(loc) < 2) {
						downloadWindow();
						player.openInventory(dl);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Upload Data at Cafeteria")) {
				
					if(cafe1.distance(loc) < 2 || cafe2.distance(loc) < 2) {
						uploadWindow();
						player.openInventory(up);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Get fuel at Reactor")) {
				
					if(reactor1.distance(loc) < 2 || reactor2.distance(loc) < 2) {
						getFuelWindow();
						player.openInventory(gfl);
					}
				}else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Task - Refuel the engine")) {
				
					if(engine1.distance(loc) < 2 || engine2.distance(loc) < 2) {
						putFuelWindow();
						player.openInventory(pfl);
					}
				}
				
			}
		}catch(Exception e) {
			
		}
		}
	@EventHandler
	public void clickFixLight(InventoryClickEvent event) {
		if(!(event.getInventory().equals(fxl)))
			return;
		if(event.getCurrentItem() == null) return;
		if(event.getCurrentItem().getItemMeta() == null) return;
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
		event.setCancelled(true);
		
		ItemStack itemon = new ItemStack(Material.GLOWSTONE);
		ItemMeta meta = itemon.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SWITCHED");
		itemon.setItemMeta(meta);
		Player player = (Player) event.getWhoClicked();
		
		try {
		if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(0, itemon);
			fixed++;
		}else if(event.getSlot() == 1 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(1, itemon);
			fixed++;
		}else if(event.getSlot() == 2 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(2, itemon);
			fixed++;
		}else if(event.getSlot() == 3 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(3, itemon);
			fixed++;
		}else if(event.getSlot() == 4 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(4, itemon);
			fixed++;
		}else if(event.getSlot() == 5 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(5, itemon);
			fixed++;
		}else if(event.getSlot() == 6 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(6, itemon);
			fixed++;
		}else if(event.getSlot() == 7 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SWITCH")) {
			fxl.setItem(7, itemon);
			fixed++;
		}else if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("DONE")) {
			if(fixed >= 8) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName())){
						if(!deadPeople.contains(all.getName())) {
							all.removePotionEffect(PotionEffectType.BLINDNESS);
						all.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[FIXED]",ChatColor.LIGHT_PURPLE+ "Electrical room was fixed", 30, 100, 30);
						all.getInventory().remove(hold);
						}
					}
				}
				sab_elect = false;
				player.closeInventory();
			}else {
				player.sendMessage(ChatColor.DARK_RED + "Electrical lights was not fixed");
				player.closeInventory();
			}
		}
		
		}catch(Exception e) {
			
		}
		
	}
	//VOTE WINDOW
	public void voteWindow() {
		vote = Bukkit.createInventory(null, 18, ChatColor.BOLD + "" +ChatColor.BLUE + "VOTE!!!");
		ItemStack item = new ItemStack(Material.CYAN_WOOL);
		ItemMeta meta = item.getItemMeta();
		
	
			for(Player alive : Bukkit.getOnlinePlayers()) {
				if(specificColor.containsKey("CYAN")) {
					if(specificColor.get("CYAN").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.CYAN_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "CYAN");
							item.setItemMeta(meta);
							vote.setItem(0, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "CYAN DEAD");
							item.setItemMeta(meta);
							vote.setItem(0, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(0, item);
				}
				
				if(specificColor.containsKey("BLACK")) {
					if(specificColor.get("BLACK").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.BLACK_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BLACK");
							item.setItemMeta(meta);
							vote.setItem(1, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BLACK DEAD");
							item.setItemMeta(meta);
							vote.setItem(1, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(1, item);
				}
				
				if(specificColor.containsKey("BLUE")) {
					if(specificColor.get("BLUE").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.BLUE_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BLUE");
							item.setItemMeta(meta);
							vote.setItem(2, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BLUE DEAD");
							item.setItemMeta(meta);
							vote.setItem(2, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(2, item);
				}
				
				if(specificColor.containsKey("GREEN")) {
					if(specificColor.get("GREEN").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.GREEN_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GREEN");
							item.setItemMeta(meta);
							vote.setItem(3, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GREEN DEAD");
							item.setItemMeta(meta);
							vote.setItem(3, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(3, item);
				}
				
				if(specificColor.containsKey("RED")) {
					if(specificColor.get("RED").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.RED_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "RED");
							item.setItemMeta(meta);
							vote.setItem(4, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "RED DEAD");
							item.setItemMeta(meta);
							vote.setItem(4, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(4, item);
				}
				
				if(specificColor.containsKey("WHITE")) {
					if(specificColor.get("WHITE").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.WHITE_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "WHITE");
							item.setItemMeta(meta);
							vote.setItem(5, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "WHITE DEAD");
							item.setItemMeta(meta);
							vote.setItem(5, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(5, item);
				}
				
				if(specificColor.containsKey("YELLOW")) {
					if(specificColor.get("YELLOW").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.YELLOW_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "YELLOW");
							item.setItemMeta(meta);
							vote.setItem(6, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "YELLOW DEAD");
							item.setItemMeta(meta);
							vote.setItem(6, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(6, item);
				}
				
				if(specificColor.containsKey("PURPLE")) {
					if(specificColor.get("PURPLE").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.PURPLE_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "PURPLE");
							item.setItemMeta(meta);
							vote.setItem(7, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "PURPLE DEAD");
							item.setItemMeta(meta);
							vote.setItem(7, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(7, item);
				}
				
				if(specificColor.containsKey("ORANGE")) {
					if(specificColor.get("ORANGE").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.ORANGE_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "ORANGE");
							item.setItemMeta(meta);
							vote.setItem(8, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "ORANGE DEAD");
							item.setItemMeta(meta);
							vote.setItem(8, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(8, item);
				}
				
				if(specificColor.containsKey("GRAY")) {
					if(specificColor.get("GRAY").contains(alive.getName())) {
						if(!deadPeople.contains(alive.getName())) {
							item.setType(Material.GRAY_WOOL);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GRAY");
							item.setItemMeta(meta);
							vote.setItem(9, item);
						}else {
							item.setType(Material.SUNFLOWER);
							meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GRAY DEAD");
							item.setItemMeta(meta);
							vote.setItem(9, item);
						}
					}
				}else {
					item.setType(Material.RED_STAINED_GLASS_PANE);
					meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
					item.setItemMeta(meta);
					vote.setItem(9, item);
				}
			}
			item.setType(Material.RED_STAINED_GLASS_PANE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			vote.setItem(10, item);
			vote.setItem(11, item);
			vote.setItem(12, item);
			vote.setItem(13, item);
			vote.setItem(14, item);
			vote.setItem(15, item);
			vote.setItem(16, item);
			item.setType(Material.FIREWORK_ROCKET);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SKIP");
			item.setItemMeta(meta);
			vote.setItem(17, item);
		//}catch(Exception e) {}
		
	}
	
	//FIX OXYGEN INVENTORY
		public void o2DepeleteWindow() {
			o2 = Bukkit.createInventory(null, 18 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Type the code"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" Oxygen Depletion");
			
			
			ItemStack item = new ItemStack(Material.REDSTONE_LAMP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "1");
			item.setItemMeta(meta);
			o2.setItem(0, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "2");
			item.setItemMeta(meta);
			o2.setItem(1, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "3");
			item.setItemMeta(meta);
			o2.setItem(2, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "4");
			item.setItemMeta(meta);
			o2.setItem(3, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "5");
			item.setItemMeta(meta);
			o2.setItem(4, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "6");
			item.setItemMeta(meta);
			o2.setItem(5, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "7");
			item.setItemMeta(meta);
			o2.setItem(6, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "8");
			item.setItemMeta(meta);
			o2.setItem(7, item);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "9");
			item.setItemMeta(meta);
			o2.setItem(8, item);
			item.setType(Material.RED_STAINED_GLASS_PANE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BAD");
			item.setItemMeta(meta);
			o2.setItem(9, item);
			o2.setItem(10, item);
			o2.setItem(11, item);
			o2.setItem(12, item);
			o2.setItem(13, item);
			o2.setItem(14, item);
			o2.setItem(15, item);
			o2.setItem(16, item);
			o2.setItem(17, item);
			item.setType(Material.OAK_SIGN);
			ItemMeta meta7 = item.getItemMeta();
			meta7.setDisplayName(ChatColor.DARK_AQUA +  "" + ChatColor.BOLD +  "DONE");
			item.setItemMeta(meta7);
			o2.setItem(17, item);
			
		}
		@EventHandler
		public void clickFixOxygen(InventoryClickEvent event) {
			if(!(event.getInventory().equals(o2)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			
			
			
			ItemStack itemon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
			ItemMeta meta = itemon.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GOOD");
			itemon.setItemMeta(meta);
			Player player = (Player) event.getWhoClicked();
			try {
				if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("1")) {
					codeEntered+="1";
				}else if(event.getSlot() == 1 && event.getCurrentItem().getItemMeta().getDisplayName().contains("2")) {
					codeEntered+="2";
				}else if(event.getSlot() == 2 && event.getCurrentItem().getItemMeta().getDisplayName().contains("3")) {
					codeEntered+="3";
				}else if(event.getSlot() == 3 && event.getCurrentItem().getItemMeta().getDisplayName().contains("4")) {
					codeEntered+="4";
				}else if(event.getSlot() == 4 && event.getCurrentItem().getItemMeta().getDisplayName().contains("5")) {
					codeEntered+="5";
				}else if(event.getSlot() == 5 && event.getCurrentItem().getItemMeta().getDisplayName().contains("6")) {
					codeEntered+="6";
				}else if(event.getSlot() == 6 && event.getCurrentItem().getItemMeta().getDisplayName().contains("7")) {
					codeEntered+="7";
				}else if(event.getSlot() == 7 && event.getCurrentItem().getItemMeta().getDisplayName().contains("8")) {
					codeEntered+="8";
				}else if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("9")) {
					codeEntered+="9";
				}else if(event.getSlot() == 17 && event.getCurrentItem().getItemMeta().getDisplayName().contains("DONE")) {
					if(oxCode.contains(codeEntered)) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(joinedPlayers.contains(all.getName())){
								if(!deadPeople.contains(all.getName())) {
								all.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[FIXED]",ChatColor.LIGHT_PURPLE+ "Oxygen Tank was fixed", 30, 100, 30);
								all.getInventory().remove(hold);
								}
							}
						}
						codeEntered = "";
						isOxSet = false;
						sab_ox = false;
						player.closeInventory();//
						bar.barOff();
						if (countdown != null && countdown instanceof BukkitTask) { //Check if countdown exists
				            countdown.cancel(); //Cancel the current countdown if there's still one/
				        }
					}else {
						player.sendMessage(ChatColor.DARK_RED + "The code "+codeEntered+" is incorrect");
						player.closeInventory();
						codeEntered = "";
					}
				}
			}catch(Exception e) {
				
			}
		}
		
		
		@EventHandler
		public void clickVote(InventoryClickEvent event) {
			if(!(event.getInventory().equals(vote)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("CYAN")) {
				v_aqua++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 1 && event.getCurrentItem().getItemMeta().getDisplayName().contains("BLACK")) {
				v_black++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 2 && event.getCurrentItem().getItemMeta().getDisplayName().contains("BLUE")) {
				v_blue++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 3 && event.getCurrentItem().getItemMeta().getDisplayName().contains("GREEN")) {
				v_green++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 4 && event.getCurrentItem().getItemMeta().getDisplayName().contains("RED")) {
				v_red++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 5 && event.getCurrentItem().getItemMeta().getDisplayName().contains("WHITE")) {
				v_white++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 6 && event.getCurrentItem().getItemMeta().getDisplayName().contains("YELLOW")) {
				v_yellow++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 7  && event.getCurrentItem().getItemMeta().getDisplayName().contains("PURPLE")) {
				v_purple++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("ORANGE")) {
				v_orange++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 9 && event.getCurrentItem().getItemMeta().getDisplayName().contains("GRAY")) {
				v_gray++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}else if(event.getSlot() == 17 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SKIP")) {
				v_skip++;
				voteAdded++;
				player.getInventory().remove(hold);
				player.closeInventory();
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(all.getName()))
						all.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getName()+" has voted");
				}
			}
			
		}
		
		public void voteCheck() {
			int voterCount = noImpostor+noCrewmate;
			String removedPerson = "";
			boolean isImpostor = false;
			if(voteAdded == voterCount || voteAdded <= voterCount) {
			int max = (v_aqua>v_black)?v_aqua:v_black;
			max = ((max>v_blue)?max:v_blue)>v_green?max:v_green;
			max = ((max>v_red)?max:v_red)>v_white?max:v_white;
			max = ((max>v_purple)?max:v_purple)>v_orange?max:v_orange;
			max = ((max>v_gray)?max:v_gray)>v_skip?max:v_skip;
			
			
			if(max == v_aqua) {
				if(specificColor.containsKey("AQUA")) {
					String votedGuy = specificColor.get("AQUA");
					if(impostors.contains(votedGuy)) {
						noImpostor--;
						impostors.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					else if(crewmates.contains(votedGuy)) {
						noCrewmate--;
						crewmates.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					removedPerson = votedGuy;
				}
			}else if(max == v_black) {
				if(specificColor.containsKey("BLACK")) {
					String votedGuy = specificColor.get("BLACK");
					if(impostors.contains(votedGuy)) {
						noImpostor--;
						impostors.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					else if(crewmates.contains(votedGuy)) {
						noCrewmate--;
						crewmates.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					removedPerson = votedGuy;
				}
			}else if(max == v_blue) {
				if(specificColor.containsKey("BLUE")) {
					String votedGuy = specificColor.get("BLUE");
					if(impostors.contains(votedGuy)) {
						noImpostor--;
						impostors.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					else if(crewmates.contains(votedGuy)) {
						noCrewmate--;
						crewmates.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					removedPerson = votedGuy;
				}
			}else if(max == v_green) {
				if(specificColor.containsKey("GREEN")) {
					String votedGuy = specificColor.get("GREEN");
					if(impostors.contains(votedGuy)) {
						noImpostor--;
						impostors.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					else if(crewmates.contains(votedGuy)) {
						noCrewmate--;
						crewmates.remove(votedGuy);
						deadPeople.add(votedGuy);
					}
					removedPerson = votedGuy;
				}
			}else if(max == v_skip) {
				for(Player announce : Bukkit.getOnlinePlayers()) {
					if(joinedPlayers.contains(announce.getName())) {
					//	String guy = announce.getName();
						announce.sendTitle(ChatColor.WHITE + "" + ChatColor.BOLD +"[Skipped]",ChatColor.LIGHT_PURPLE+ "no one was voted", 30, 100, 30);
					}
				}
				clearDeathEvent();
				report = false;
				voteAdded = 0;
				removeVotingEffects();
				return;
			}
			
			
			
			
			if(!removedPerson.contains("")) {
			for(Player announce : Bukkit.getOnlinePlayers()) {
				if(joinedPlayers.contains(announce.getName())) {
				//	String guy = announce.getName();
					if(removedPerson.contains(announce.getName())) {
						announce.sendTitle(ChatColor.RED + "" + ChatColor.BOLD +"[You are voted]",ChatColor.LIGHT_PURPLE+ "people selected you", 30, 100, 30);
					}else {
						if(!isImpostor) {
							announce.sendTitle(ChatColor.WHITE + "" + ChatColor.BOLD +removedPerson+" was not an impostor",ChatColor.LIGHT_PURPLE+ "more voted him", 30, 100, 30);
						}else {
							announce.sendTitle(ChatColor.WHITE + "" + ChatColor.BOLD +removedPerson+" was an impostor",ChatColor.LIGHT_PURPLE+ "more voted him", 30, 100, 30);
							
						}
					}
				}
			}
			clearDeathEvent();
			}
			removeVotingEffects();
			report = false;
			voteAdded = 0;
			
		}
			if(noCrewmate <= noImpostor) {
				impostorWin();
				removeVotingEffects();
				report = false;
				
			}else if(noImpostor == 0) {
				crewmateWin();
				removeVotingEffects();
			report = false;
			}
			report = false;
			votetimer.barOff();
		}
		
		
		public void cafe1Window() {
			c1 = Bukkit.createInventory(null, 54 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Throw the trash"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" Cafeteria");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			c1.setItem(0, item);
			c1.setItem(1, item);
			c1.setItem(2, item);
			c1.setItem(3, item);
			c1.setItem(4, item);
			c1.setItem(5, item);
			c1.setItem(6, item);
			c1.setItem(7, item);
			c1.setItem(8, item);
			c1.setItem(9, item);
			c1.setItem(17, item);
			c1.setItem(18, item);
			c1.setItem(26, item);
			c1.setItem(27, item);
			c1.setItem(35, item);
			c1.setItem(36, item);
			c1.setItem(44, item);
			c1.setItem(45, item);
			c1.setItem(46, item);
			c1.setItem(47, item);
			c1.setItem(48, item);
			c1.setItem(49, item);
			c1.setItem(50, item);
			c1.setItem(51, item);
			c1.setItem(52, item);
			c1.setItem(53, item);
			
			item.setType(Material.OAK_SIGN);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "DUMP");
			item.setItemMeta(meta);
			c1.setItem(43, item);
			
			item.setType(Material.DEAD_BUSH);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "TRASH");
			item.setItemMeta(meta);
			c1.setItem(10, item);
			c1.setItem(14, item);
			c1.setItem(24, item);
			c1.setItem(29, item);
			c1.setItem(30, item);
			c1.setItem(38, item);
			c1.setItem(41, item);
			c1.setItem(20, item);
			
			
			
		}
		public void cafe2Window() {
			c2 = Bukkit.createInventory(null, 54 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Throw the trash"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" Cafeteria");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			c2.setItem(0, item);
			c2.setItem(1, item);
			c2.setItem(2, item);
			c2.setItem(3, item);
			c2.setItem(4, item);
			c2.setItem(5, item);
			c2.setItem(6, item);
			c2.setItem(7, item);
			c2.setItem(8, item);
			c2.setItem(9, item);
			c2.setItem(17, item);
			c2.setItem(18, item);
			c2.setItem(26, item);
			c2.setItem(27, item);
			c2.setItem(35, item);
			c2.setItem(36, item);
			c2.setItem(44, item);
			c2.setItem(45, item);
			c2.setItem(46, item);
			c2.setItem(47, item);
			c2.setItem(48, item);
			c2.setItem(49, item);
			c2.setItem(50, item);
			c2.setItem(51, item);
			c2.setItem(52, item);
			c2.setItem(53, item);
			
			item.setType(Material.OAK_SIGN);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "DUMP");
			item.setItemMeta(meta);
			c2.setItem(43, item);
			
			item.setType(Material.DEAD_BUSH);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "TRASH");
			item.setItemMeta(meta);
			c2.setItem(11, item);
			c2.setItem(15, item);
			c2.setItem(22, item);
			c2.setItem(28, item);
			c2.setItem(30, item);
			c2.setItem(37, item);
			c2.setItem(39, item);
			c2.setItem(19, item);
		}
		
		public void scanIdWindow() {
			scanId = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Put the card to scan"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" ADMIN");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			scanId.setItem(0, item);
			scanId.setItem(1, item);
			scanId.setItem(2, item);
			scanId.setItem(3, item);
			scanId.setItem(5, item);
			scanId.setItem(6, item);
			scanId.setItem(7, item);
			item.setType(Material.OAK_SIGN);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "SCAN");
			item.setItemMeta(meta);
			scanId.setItem(8, item);
		}
		
		
		@EventHandler
		public void idScanEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(scanId)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GREEN+ "Task - Scan ID");
			lore.add(ChatColor.AQUA + "Scan this card at admin");
			meta.setLore(lore);
			item.setItemMeta(meta);
		
			
			
			Player player = (Player) event.getWhoClicked();
			if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 0 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 1 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 2 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 3 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 5 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 6 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}else if(event.getSlot() == 7 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
				event.setCancelled(true);
			}
			if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("SCAN")) {
				if(scanId.contains(item)) {
					event.setCancelled(true);
					scanIdWindow();
					player.closeInventory();
				
					taskDone++;
					taskbar.updateBar();
					player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Scan ID", 30, 100, 30);
				}else {
					player.sendMessage(ChatColor.DARK_RED + "Scan the card");
					player.closeInventory();
					event.setCancelled(true);
				}
			}
		}
		
		
		@EventHandler
		public void cafe1Event(InventoryClickEvent event) {
			if(!(event.getInventory().equals(c1)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			ItemStack air = new ItemStack(Material.AIR);
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			if(event.getSlot() == 43 && event.getCurrentItem().getItemMeta().getDisplayName().contains("DUMP")) {
				c1Stat++;
				if(c1Stat==1) {
					c1.setItem(10, air);
				}
				if(c1Stat==2) {
					c1.setItem(14, air);
				}
				if(c1Stat==3) {
					c1.setItem(24, air);
				}
				if(c1Stat==4) {
					c1.setItem(29, air);
				}
				if(c1Stat==5) {
					c1.setItem(30, air);
				}
				if(c1Stat==6) {
					c1.setItem(38, air);
				}
				if(c1Stat==7) {
					c1.setItem(41, air);
				}
				if(c1Stat==8) {
					c1.setItem(20, air);
				}
				if(c1Stat>=8) {
					c1Stat=0;
					player.closeInventory();
					player.getInventory().remove(hold);
					taskDone++;
					taskbar.updateBar();
					player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Dump trash", 30, 100, 30);
					cafe1Window();
				}
			}
		}
		@EventHandler
		public void cafe2Event(InventoryClickEvent event) {
			if(!(event.getInventory().equals(c2)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			ItemStack air = new ItemStack(Material.AIR);
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			if(event.getSlot() == 43 && event.getCurrentItem().getItemMeta().getDisplayName().contains("DUMP")) {
				c2Stat++;
				if(c2Stat==1) {
					c2.setItem(19, air);
				}
				if(c2Stat==2) {
					c2.setItem(39, air);
				}
				if(c2Stat==3) {
					c2.setItem(37, air);
				}
				if(c2Stat==4) {
					c2.setItem(30, air);
				}
				if(c2Stat==5) {
					c2.setItem(28, air);
				}
				if(c2Stat==6) {
					c2.setItem(22, air);
				}
				if(c2Stat==7) {
					c2.setItem(15, air);
				}
				if(c2Stat==8) {
					c2.setItem(11, air);
				}
				if(c1Stat>=8) {
					c2Stat=0;
					player.closeInventory();
					player.getInventory().remove(hold);
					taskDone++;
					taskbar.updateBar();
					player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Dump trash", 30, 100, 30);
					cafe2Window();
				}
			}
		}
		
		public void fixWiredWindow() {
			fixWire = Bukkit.createInventory(null, 54 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Fix the wiring"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" ELECTRICAL");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			fixWire.setItem(0, item);
			fixWire.setItem(1, item);
			fixWire.setItem(2, item);
			fixWire.setItem(3, item);
			fixWire.setItem(4, item);
			fixWire.setItem(5, item);
			fixWire.setItem(6, item);
			fixWire.setItem(7, item);
			fixWire.setItem(8, item);
			fixWire.setItem(9, item);
			fixWire.setItem(17, item);
			fixWire.setItem(18, item);
			fixWire.setItem(26, item);
			fixWire.setItem(27, item);
			fixWire.setItem(35, item);
			fixWire.setItem(36, item);
			fixWire.setItem(44, item);
			fixWire.setItem(45, item);
			fixWire.setItem(46, item);
			fixWire.setItem(47, item);
			fixWire.setItem(48, item);
			fixWire.setItem(49, item);
			fixWire.setItem(50, item);
			fixWire.setItem(51, item);
			fixWire.setItem(52, item);
			fixWire.setItem(53, item);
			item.setType(Material.GRAY_STAINED_GLASS_PANE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			fixWire.setItem(12, item);
			fixWire.setItem(13, item);
			fixWire.setItem(14, item);
			fixWire.setItem(21, item);
			fixWire.setItem(22, item);
			fixWire.setItem(23, item);
			fixWire.setItem(30, item);
			fixWire.setItem(31, item);
			fixWire.setItem(32, item);
			fixWire.setItem(39, item);
			fixWire.setItem(40, item);
			fixWire.setItem(41, item);
			item.setType(Material.RED_WOOL);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "RED CABLE");
			item.setItemMeta(meta);
			fixWire.setItem(11, item);
			fixWire.setItem(15, item);
			item.setType(Material.YELLOW_WOOL);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "YELLOW CABLE");
			item.setItemMeta(meta);
			fixWire.setItem(20, item);
			fixWire.setItem(24, item);
			item.setType(Material.PINK_WOOL);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "PINK CABLE");
			item.setItemMeta(meta);
			fixWire.setItem(29, item);
			fixWire.setItem(33, item);
			item.setType(Material.BLUE_WOOL);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "BLUE CABLE");
			item.setItemMeta(meta);
			fixWire.setItem(38, item);
			fixWire.setItem(42, item);
			fWire = 0;
		}
	
		ItemStack redWire() {
			ItemStack redWire = new ItemStack(Material.RED_WOOL);
			ItemMeta meta = redWire.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "FIXED");
			redWire.setItemMeta(meta);
			
			return redWire;
		}
		ItemStack yellowWire() {
			ItemStack yellowWire = new ItemStack(Material.YELLOW_WOOL);
			ItemMeta meta = yellowWire.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "FIXED");
			yellowWire.setItemMeta(meta);
			
			return yellowWire;
		}
		ItemStack pinkWire() {
			ItemStack pinkWire = new ItemStack(Material.PINK_WOOL);
			ItemMeta meta = pinkWire.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "FIXED");
			pinkWire.setItemMeta(meta);
			
			return pinkWire;
		}
		ItemStack blueWire() {
			ItemStack blueWire = new ItemStack(Material.BLUE_WOOL);
			ItemMeta meta = blueWire.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "FIXED");
			blueWire.setItemMeta(meta);
			
			return blueWire;
		}
		@EventHandler
		public void fixWireEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(fixWire)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			
			
			try {
			
				if(event.getSlot() == 12 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(12, redWire());
				}else if(event.getSlot() == 13 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(13, redWire());
				}else if(event.getSlot() == 14 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(14, redWire());
				}else if(event.getSlot() == 21 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(21, yellowWire());
				}else if(event.getSlot() == 22 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(22, yellowWire());
				}else if(event.getSlot() == 23 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(23, yellowWire());
				}else if(event.getSlot() == 30 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(30, pinkWire());
				}else if(event.getSlot() == 31 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(31, pinkWire());
				}else if(event.getSlot() == 32 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(32, pinkWire());
				}else if(event.getSlot() == 39 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(39, blueWire());
				}else if(event.getSlot() == 40 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(40, blueWire());
				}else if(event.getSlot() == 41 && event.getCurrentItem().getItemMeta().getDisplayName().contains("...")) {
					fWire++;
					fixWire.setItem(41, blueWire());
				}
			
			}catch(Exception e) {}
			if(fWire>= 12) {
				fixWiredWindow();
				player.closeInventory();
				taskDone++;
				taskbar.updateBar();
				player.getInventory().remove(hold);
				player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Fix wiring", 30, 100, 30);
			}
		}
		
		public void downloadWindow() {
			dl = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "DOWNLOAD DATA"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" [...]");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			dl.setItem(0, item);
			dl.setItem(1, item);
			dl.setItem(2, item);
			dl.setItem(3, item);
			dl.setItem(4, item);
			dl.setItem(5, item);
			dl.setItem(6, item);
			dl.setItem(7, item);
			item.setType(Material.REDSTONE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "DOWNLOAD");
			item.setItemMeta(meta);
			dl.setItem(8, item);
			
		}
		public void uploadWindow() {
			up = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "UPLOAD DATA"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" [...]");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			up.setItem(0, item);
			up.setItem(1, item);
			up.setItem(2, item);
			up.setItem(3, item);
			up.setItem(4, item);
			up.setItem(5, item);
			up.setItem(6, item);
			up.setItem(7, item);
			item.setType(Material.REDSTONE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "UPLOAD");
			item.setItemMeta(meta);
			up.setItem(8, item);
			
		}
		ItemStack greenLime() {
			ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "[OK]");
			item.setItemMeta(meta);
			return item;
		}
		ItemStack loadingIndicator() {
			ItemStack item = new ItemStack(Material.REDSTONE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "PLEASE WAIT");
			item.setItemMeta(meta);
			return item;
		}
		public void loaderDL(Player player, ItemStack item) {
			load1 = Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
				
	            @Override
	            public void run() {
	            	download++;
	            	if(download == 1) {
	            		dl.setItem(0, greenLime());
	            	}else if(download == 2) {
	            		dl.setItem(1, greenLime());
	            	}else if(download == 3) {
	            		dl.setItem(2, greenLime());
	            	}else if(download == 4) {
	            		dl.setItem(3, greenLime());
	            	}else if(download == 5) {
	            		dl.setItem(4, greenLime());
	            	}else if(download == 6) {
	            		dl.setItem(5, greenLime());
	            	}else if(download == 7) {
	            		dl.setItem(6, greenLime());
	            	}else if(download == 8) {
	            		dl.setItem(7, greenLime());
	            	}
	            	
	                if(download >= 9) {
	                	if(downloadPlayers.contains(player.getName())) {
	                		downloadPlayers.remove(player.getName());
	                		player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Download data", 30, 100, 30);
							player.getInventory().remove(item);
							player.closeInventory();
							downloadWindow();
							
							if(item.isSimilar(downloadWeapons())) {
								player.getInventory().addItem(uploadWeapons());
							}else if(item.isSimilar(downloadComms())) {
								player.getInventory().addItem(uploadComms());
							}else if(item.isSimilar(downloadReact())) {
								player.getInventory().addItem(uploadReact());
							}else if(item.isSimilar(downloadOx())) {
								player.getInventory().addItem(uploadOx());
							}
	                	}
	                	download = 0 ;
	                	load1.cancel();
	                }             
	            }
	        }, 0, 20 );
		}
		
		public void loaderUP(Player player, ItemStack item) {
			load2 = Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
				
	            @Override
	            public void run() {
	            	upload++;
	            	if(upload == 1) {
	            		up.setItem(0, greenLime());
	            	}else if(upload == 2) {
	            		up.setItem(1, greenLime());
	            	}else if(upload == 3) {
	            		up.setItem(2, greenLime());
	            	}else if(upload == 4) {
	            		up.setItem(3, greenLime());
	            	}else if(upload == 5) {
	            		up.setItem(4, greenLime());
	            	}else if(upload == 6) {
	            		up.setItem(5, greenLime());
	            	}else if(upload == 7) {
	            		up.setItem(6, greenLime());
	            	}else if(upload == 8) {
	            		up.setItem(7, greenLime());
	            	}
	            	
	                if(upload >= 9) {
	                	if(uploadPlayers.contains(player.getName())) {
	                		uploadPlayers.remove(player.getName());
	                		player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Upload data", 30, 100, 30);
							player.getInventory().remove(item);
							player.closeInventory();
							taskDone++;
							taskbar.updateBar();
							uploadWindow();
	                	}
	                	upload = 0 ;
	                	load2.cancel();
	                }             
	            }
	        }, 0, 20 );
		}
		@EventHandler
		public void dloadEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(dl)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			
			
			try {
				if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("DOWNLOAD")) {
					downloadPlayers.add(player.getName());
					loaderDL(player, hold);
					dl.setItem(8, loadingIndicator());
				}
				
			
			}catch(Exception e) {}
			
		}
		@EventHandler
		public void uloadEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(up)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			
			
			try {
				if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("UPLOAD")) {
					uploadPlayers.add(player.getName());
					loaderUP(player, hold);
					up.setItem(8, loadingIndicator());
				}
				
			
			}catch(Exception e) {}
			
		}
		public void getFuelWindow() {
			gfl = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "DOWNLOAD DATA"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" [...]");
			
			
			ItemStack item = new ItemStack(Material.COAL);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "FUEL");
			item.setItemMeta(meta);
			gfl.setItem(0, item);
			gfl.setItem(1, item);
			gfl.setItem(2, item);
			gfl.setItem(3, item);
			gfl.setItem(4, item);
			gfl.setItem(5, item);
			gfl.setItem(6, item);
			gfl.setItem(7, item);
			item.setType(Material.REDSTONE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "GET FUEL");
			item.setItemMeta(meta);
			gfl.setItem(8, item);
			
		}
		@EventHandler
		public void gflEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(gfl)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			
			
			try {
				if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("GET FUEL")) {
					gflPlayers.add(player.getName());
					loaderGFL(player, hold);
					gfl.setItem(8, loadingIndicator());
				}
				
			
			}catch(Exception e) {}
			
		}
		public void loaderGFL(Player player, ItemStack item) {
			load3 = Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
				
	            @Override
	            public void run() {
	            	getFL++;
	            	if(getFL == 1) {
	            		gfl.setItem(7, greenLime());
	            	}else if(getFL == 2) {
	            		gfl.setItem(6, greenLime());
	            	}else if(getFL == 3) {
	            		gfl.setItem(5, greenLime());
	            	}else if(getFL == 4) {
	            		gfl.setItem(4, greenLime());
	            	}else if(getFL == 5) {
	            		gfl.setItem(3, greenLime());
	            	}else if(getFL == 6) {
	            		gfl.setItem(2, greenLime());
	            	}else if(getFL== 7) {
	            		gfl.setItem(1, greenLime());
	            	}else if(getFL == 8) {
	            		gfl.setItem(0, greenLime());
	            	}
	            	
	                if(getFL >= 9) {
	                	if(gflPlayers.contains(player.getName())) {
	                		gflPlayers.remove(player.getName());
	                		player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Get Fuel", 30, 100, 30);
							player.getInventory().remove(item);
							player.closeInventory();
							getFuelWindow();
							
								player.getInventory().addItem(FUEL());
								player.getInventory().addItem(putFuel());
							
	                	}
	                	getFL = 0 ;
	                	load3.cancel();
	                }             
	            }
	        }, 0, 20 );
		}
		public void putFuelWindow() {
			pfl = Bukkit.createInventory(null, 9 , ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "DOWNLOAD DATA"+ChatColor.LIGHT_PURPLE+ChatColor.BOLD +" [...]");
			
			
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "...");
			item.setItemMeta(meta);
			pfl.setItem(0, item);
			pfl.setItem(1, item);
			pfl.setItem(2, item);
			pfl.setItem(3, item);
			pfl.setItem(4, item);
			pfl.setItem(5, item);
			pfl.setItem(6, item);
			pfl.setItem(7, item);
			item.setType(Material.REDSTONE);
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD +  "PUT FUEL");
			item.setItemMeta(meta);
			pfl.setItem(8, item);
			
		}
		@EventHandler
		public void pflEvent(InventoryClickEvent event) {
			if(!(event.getInventory().equals(pfl)))
				return;
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().getItemMeta() == null) return;
			if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
			
			
			ItemStack hold = new ItemStack(event.getWhoClicked().getInventory().getItemInMainHand()) ;
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			
			
			
			try {
				if(event.getSlot() == 8 && event.getCurrentItem().getItemMeta().getDisplayName().contains("PUT FUEL")) {
					pflPlayers.add(player.getName());
					loaderPFL(player, hold);
					player.getInventory().remove(FUEL());
					pfl.setItem(8, loadingIndicator());
				}
				
			
			}catch(Exception e) {}
			
		}
		public void loaderPFL(Player player, ItemStack item) {
			load4 = Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
				
	            @Override
	            public void run() {
	            	putFL++;
	            	if(putFL== 1) {
	            		pfl.setItem(0, FUEL());
	            	}else if(putFL == 2) {
	            		pfl.setItem(1,  FUEL());
	            	}else if(putFL == 3) {
	            		pfl.setItem(2,  FUEL());
	            	}else if(putFL == 4) {
	            		pfl.setItem(3,  FUEL());
	            	}else if(putFL == 5) {
	            		pfl.setItem(4,  FUEL());
	            	}else if(putFL == 6) {
	            		pfl.setItem(5,  FUEL());
	            	}else if(putFL== 7) {
	            		pfl.setItem(6,  FUEL());
	            	}else if(putFL == 8) {
	            		pfl.setItem(7,  FUEL());
	            	}
	            	
	                if(putFL >= 9) {
	                	if(pflPlayers.contains(player.getName())) {
	                		pflPlayers.remove(player.getName());
	                		player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"[TASK COMPLETE]",ChatColor.LIGHT_PURPLE+ "Put Fuel", 30, 100, 30);
							player.getInventory().remove(item);
							player.closeInventory();
							putFuelWindow();
							taskDone++;
							taskbar.updateBar();
							
	                	}
	                	putFL = 0 ;
	                	load4.cancel();
	                }             
	            }
	        }, 0, 20 );
		}
		
		

	  
	
}
