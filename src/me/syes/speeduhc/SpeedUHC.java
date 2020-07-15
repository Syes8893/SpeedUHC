package me.syes.speeduhc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.syes.speeduhc.arena.ArenaHandler;
import me.syes.speeduhc.arena.ArenaManager;
import me.syes.speeduhc.arena.ArenaUtils;
import me.syes.speeduhc.commands.CommandHandler;
import me.syes.speeduhc.elo.EloManager;
import me.syes.speeduhc.game.Game;
import me.syes.speeduhc.game.GameHandler;
import me.syes.speeduhc.game.GameManager;
import me.syes.speeduhc.gui.GUIManager;
import me.syes.speeduhc.gui.InventoryHandler;
import me.syes.speeduhc.perk.PerkManager;
import me.syes.speeduhc.scoreboard.ScoreboardManager;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayerHandler;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayerManager;
import me.syes.speeduhc.utils.EnchantListener;
import me.syes.speeduhc.utils.PlayerUtils;

public class SpeedUHC extends JavaPlugin {

	public static SpeedUHC instance;
	
	private ArenaManager arenaManager;
	private SpeedUHCPlayerManager speedUHCPlayerManager;
	private GameManager gameManager;
	private ScoreboardManager scoreboardManager;
	private EloManager eloManager;
	private GUIManager guiManager;
	private PerkManager perkManager;
	
	public static Location lobbySpawn;
	
	public SpeedUHC() {
		instance = this;
	}
	
	public void onEnable() {
		//Load UHC Core:
		arenaManager = new ArenaManager();
		speedUHCPlayerManager = new SpeedUHCPlayerManager();
		gameManager = new GameManager();
		scoreboardManager = new ScoreboardManager();
		eloManager = new EloManager();
		guiManager = new GUIManager();
		perkManager = new PerkManager();
		
		//Register Commands:
		getCommand("speeduhc").setExecutor(new CommandHandler());
		
		
		//Register Handlers:
		getServer().getPluginManager().registerEvents(new ArenaHandler(), this);
		getServer().getPluginManager().registerEvents(new GameHandler(), this);
		getServer().getPluginManager().registerEvents(new SpeedUHCPlayerHandler(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
		getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);
		
		//Load Players:
		PlayerUtils.loadPlayerData();
		for(Player p : getServer().getOnlinePlayers())
			if(!speedUHCPlayerManager.getUhcPlayers().containsKey(p.getUniqueId()))
				new SpeedUHCPlayer(p.getUniqueId());
		
		//Load Arena:
		ArenaUtils.loadArenas();
		
		//Start Scoreboard:
		scoreboardManager.runTaskTimer(this, 20, 20);
		
		//Load LobbySpawn:
		if(Bukkit.getWorld(this.getConfig().getString("Lobby.Spawn")) == null){
			lobbySpawn = new Location(Bukkit.getWorlds().get(0), 0 , 100 , 0);
		}else
			lobbySpawn = new Location(Bukkit.getWorld(this.getConfig().getString("Lobby.Spawn"))
				, this.getConfig().getInt("Lobby.X"), this.getConfig().getInt("Lobby.Y"), this.getConfig().getInt("Lobby.Z"));
	}
	
	public void onDisable() {
		//Reset Running Games:
		for(Game g : gameManager.getRunningGames())
			g.resetGame();
		
		//Save Arenas:
		ArenaUtils.saveArenas();
		PlayerUtils.savePlayerData();
	}
	
	public ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	public SpeedUHCPlayerManager getSpeedUHCPlayerManager() {
		return speedUHCPlayerManager;
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}

	public EloManager getEloManager() {
		return eloManager;
	}

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public PerkManager getPerkManager() {
		return perkManager;
	}

	public static SpeedUHC getInstance() {
		return instance;
	}

}
