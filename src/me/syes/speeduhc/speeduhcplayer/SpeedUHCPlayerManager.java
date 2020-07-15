package me.syes.speeduhc.speeduhcplayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.game.Game;
import me.syes.speeduhc.utils.ItemUtils;

public class SpeedUHCPlayerManager {

	private HashMap<UUID, SpeedUHCPlayer> uhcPlayers;
	private HashMap<SpeedUHCPlayer, Game> playerGames;
	
	public SpeedUHCPlayerManager() {
		uhcPlayers = new HashMap<UUID, SpeedUHCPlayer>();
		playerGames = new HashMap<SpeedUHCPlayer, Game>();
	}

	public HashMap<UUID, SpeedUHCPlayer> getUhcPlayers() {
		return uhcPlayers;
	}

	public SpeedUHCPlayer getUhcPlayer(UUID uuid) {
		return uhcPlayers.get(uuid);
	}

	public void addUhcPlayer(UUID uuid, SpeedUHCPlayer uhcPlayer) {
		uhcPlayers.put(uuid, uhcPlayer);
	}

	public void removeUhcPlayer(UUID uuid) {
		uhcPlayers.remove(uuid);
	}

	public Game getUhcPlayerGame(SpeedUHCPlayer uhcPlayer) {
		if(playerGames.get(uhcPlayer) == null)
			return null;
		return playerGames.get(uhcPlayer);
	}

	public void setUhcPlayerGame(SpeedUHCPlayer uhcPlayer, Game game) {
		playerGames.put(uhcPlayer, game);
	}

	public void removeUhcPlayerGame(SpeedUHCPlayer uhcPlayer) {
		playerGames.remove(uhcPlayer);
	}
	
	//Elo Management
	/*public void handleKillElo(Player victim, Player killer) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		SpeedUHCPlayer killerUhc = this.getUhcPlayer(killer.getUniqueId());
		if(killerUhc.getElo() > 0) {
			double eloChange = (victimUhc.getElo()/killerUhc.getElo())*0.1 + 1;
			victimUhc.removeElo((int) eloChange);
			victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
			killerUhc.addElo((int) eloChange);
			killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
			return;
		}
		double eloChange = victimUhc.getElo()*0.1 + 1;
		victimUhc.removeElo((int) eloChange);
		victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
		killerUhc.addElo((int) eloChange);
		killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
	}
	
	public void handleDeathElo(Player victim) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		double eloToRemove = victimUhc.getElo()*0.1 + 1;
		victimUhc.removeElo((int)eloToRemove);
		victim.sendMessage("§c-" + (int)eloToRemove + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
	}*/
	
	public void setLobbyInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(1, ItemUtils.buildItem(new ItemStack(Material.IRON_SWORD), "&b&lJoin a Game &7(Right-Click)", Arrays.asList("§7Right-Click to join a SpeedUHC game")));
		p.getInventory().setItem(3, ItemUtils.buildItem(new ItemStack(Material.EMERALD), "&a&lOpen the Shop &7(Right-Click)", Arrays.asList("§7Right-Click to open the shop")));
		p.getInventory().setItem(5, ItemUtils.buildItem(new ItemStack(Material.PAINTING), "&e&lYour Stats &7(Right-Click)", Arrays.asList("§7Right-Click to view your stats")));
		p.getInventory().setItem(7, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM), "&c???", Arrays.asList("§7Coming soon...")));
	}
	
	public void handleKillElo(Player victim, Player killer) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		SpeedUHCPlayer killerUhc = this.getUhcPlayer(killer.getUniqueId());
		
		double eloChange = 0;
		if(killerUhc.getElo() > 0)
			eloChange = Math.sqrt(victimUhc.getElo()/killerUhc.getElo()) + 1;
		else if(killerUhc.getElo() == 0)
			eloChange = Math.sqrt(victimUhc.getElo()/1) + 1;
		
		victimUhc.removeElo((int) eloChange);
		victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
		killerUhc.addElo((int) eloChange);
		killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
	}
	
	public void handleDeathElo(Player victim) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		Game g = victimUhc.getGame();
		
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			if(pl.getUniqueId() != victim.getUniqueId())
				allPlayerElo += SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(pl.getUniqueId()).getElo();
		double eloChange = 0;
		if(allPlayerElo > 0)
			eloChange = ((victimUhc.getElo() * 0.1)/((allPlayerElo/(g.getAllPlayers().size()-1)))) * 4 + 1;
		else if(allPlayerElo == 0)
			eloChange = (victimUhc.getElo() * 0.1) * 4 + 1;
		
		victimUhc.removeElo((int) eloChange);
		victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
	}
	
	/*public void handleWinElo(Game g) {
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(g.getWinner().getUniqueId());
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			allPlayerElo += SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(pl.getUniqueId()).getElo();
		double eloToAdd = (allPlayerElo/(uhcPlayer.getElo() ^ g.getAllPlayers().size())) * 10 + 1;
		if(uhcPlayer.getElo() == 0)
			eloToAdd = (allPlayerElo/g.getAllPlayers().size()) * 10 + 1;
		uhcPlayer.addElo((int) eloToAdd);
		g.getWinner().sendMessage("§a+" + (int)eloToAdd + " §7ELO §a(\u25b2" + uhcPlayer.getElo() + ")");
	}*/
	
	public void handleWinElo(Game g) {
		//Get Player
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(g.getWinner().getUniqueId());
		
		//Calculate ELO
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			if(pl.getUniqueId() != g.getWinner().getUniqueId())
				allPlayerElo += SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(pl.getUniqueId()).getElo();
		double eloChange = (((allPlayerElo * 0.5)/(g.getAllPlayers().size()-1))/uhcPlayer.getElo()) * 4 + 1;
		if(uhcPlayer.getElo() == 0)
			eloChange = 1;
		
		//Apply ELO + Message Player
		uhcPlayer.addElo((int) eloChange);
		g.getWinner().sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + uhcPlayer.getElo() + ")");
	}
	
}
