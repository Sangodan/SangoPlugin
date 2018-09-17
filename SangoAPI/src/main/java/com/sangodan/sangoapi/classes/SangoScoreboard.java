package com.sangodan.sangoapi.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("unused")
public class SangoScoreboard {

	private ScoreboardManager manager;
	private Scoreboard board;
	private int maxPlayers;
	private int minPlayers;
	private String mapName;
	private int gameStart;
	private Objective o;
	private Objective buffer;
	private Objective t;
	
	private World world;
	
	private Team team;
	private Team players;
	private String playersEntry = ChatColor.WHITE + "" + ChatColor.BLACK + "";
	private Team gameStartTime;
	private String gameStartEntry = ChatColor.BLACK + "" + ChatColor.WHITE + "";
	private Team waitingForPlayers;
	private String waitingEntry = ChatColor.AQUA + "" + ChatColor.BLUE + "";
	
	private Score map;

	
	public SangoScoreboard(String title, String mapName, int maxPlayers, int minPlayers, int gameStart, World world) {
		this.manager = Bukkit.getScoreboardManager();
		this.board = manager.getNewScoreboard();
		this.o = board.registerNewObjective("sango", "dummy");
		this.buffer = board.registerNewObjective("buffer", "dummy");
		o.setDisplayName(title);
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.mapName = mapName;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.gameStart = gameStart;
		this.world = world;
		this.team = board.registerNewTeam("team");
		this.players = board.registerNewTeam("players");
		this.players.addEntry(playersEntry);
		this.gameStartTime = board.registerNewTeam("gamestart");
		this.gameStartTime.addEntry(gameStartEntry);
		this.waitingForPlayers = board.registerNewTeam("waiting");
		this.waitingForPlayers.addEntry(waitingEntry);
	}
	
	public Scoreboard getScoreboard() {
		return this.board;
		
	}
	public void setScore(String name, int score) {
		buffer.getScore(name).setScore(score);
		
		swapBuffer();
		buffer.getScore(name).setScore(score);
	}
	
	public void swapBuffer(){
		buffer.setDisplayName(o.getDisplayName());
		buffer.setDisplaySlot(o.getDisplaySlot());
       
        
        t = o;
        o = buffer;
        buffer = t;
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public void setMaxPlayers(int num) {
		this.maxPlayers = num;
	}
	
	public void setGameStart(int num) {
		this.gameStart = num;
	}
	
	private void distributeText(String text, Team team) {
		if(text.length() > 16) {
			String text1 = text.substring(0, 16);
			int we = text1.lastIndexOf(ChatColor.COLOR_CHAR);
			String text2 = text.substring(16, text.length());
			if(we != -1) {
				String text3 = new String(text2);
				text2 = text1.subSequence(we, we + 2) + text3; 
			}
			team.setPrefix(text1);
			team.setSuffix(text2);
		} else {
			team.setPrefix(text);
		}
	}
	
	public void setDisplaySlot(DisplaySlot slot) {
		o.setDisplaySlot(slot);
	}
	

	public void update() {
		int worldPlayers = world.getPlayers().size();
		distributeText(ChatColor.GOLD + "Needed: " + ChatColor.LIGHT_PURPLE + (minPlayers - worldPlayers), waitingForPlayers);
		distributeText(ChatColor.GOLD + "Game Start: " + gameStart, gameStartTime);	
		distributeText(ChatColor.AQUA + "Players: " + ChatColor.GREEN + "" + worldPlayers + ChatColor.GOLD + "/" + ChatColor.DARK_GREEN + maxPlayers, players);
		
		setScore(ChatColor.WHITE +"" + ChatColor.BOLD + "Map: " + ChatColor.DARK_GREEN + mapName, 15);
		setScore(ChatColor.RESET.toString(), 14);
		setScore(ChatColor.RESET.toString() + ChatColor.RESET.toString(), 13);
		setScore(playersEntry, 12);
		setScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString(), 11);

		if(worldPlayers < this.minPlayers) {
			board.resetScores(gameStartEntry);
			setScore(waitingEntry, 10);
		} else {
			board.resetScores(waitingEntry);
			setScore(gameStartEntry, 10);
		}
	}
	
    public void addPlayer(Player p) {
        team.addPlayer(p);
        p.setScoreboard(board);
    }
    
    public void removePlayer(Player p) {
    	team.removePlayer(p);
    	p.setScoreboard(manager.getNewScoreboard());
    }
}
