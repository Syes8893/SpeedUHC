package me.syes.speeduhc.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.game.Game.GameMode;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class GameManager {
	
	private HashSet<Game> games;
	
	public GameManager() {
		games = new HashSet<Game>();
	}
	
	public Game getAvailableGame() {
		for(Game g : games) {
			if(g.getGameMode() == GameMode.WAITING || g.getGameMode() == GameMode.STARTING) {
				return g;
			}
		}
		if(SpeedUHC.getInstance().getArenaManager().getRandomArena() != null){
			return new Game();
		}
		return null;
	}
	
	public ArrayList<Game> getRunningGames() {
		ArrayList<Game> runningGames = new ArrayList<Game>();
		for(Game g : games) {
			if(g.getGameMode() != GameMode.RESETING) 
				runningGames.add(g);
		}
		return runningGames;
	}
	
	public void addGame(Game g) {
		games.add(g);
	}
	
	public void removeGame(Game g) {
		games.add(g);
	}
    
    public HashMap<Integer, SpeedUHCPlayer> getTopKillers(Game g) {
    	Comparator<SpeedUHCPlayer> killSorter = new Comparator<SpeedUHCPlayer>() {
			@Override
			public int compare(SpeedUHCPlayer a, SpeedUHCPlayer b) {
				if(a.getGameKills() > b.getGameKills()) return -1;
				else if(a.getGameKills() < b.getGameKills()) return 1;
				return 0;
			}
    	};
    	HashMap<Integer, SpeedUHCPlayer> map = new HashMap<Integer, SpeedUHCPlayer>();
    	ArrayList<SpeedUHCPlayer> kitPlayers = new ArrayList<SpeedUHCPlayer>();
    	for(Player kp : g.getAllPlayers()) {
    		kitPlayers.add(SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(kp.getUniqueId()));
    	}
    	kitPlayers.sort(killSorter);
    	for(SpeedUHCPlayer kp : kitPlayers) {
    		map.put(kitPlayers.indexOf(kp)+1, kp);
    	}
		return map;
    }

}
