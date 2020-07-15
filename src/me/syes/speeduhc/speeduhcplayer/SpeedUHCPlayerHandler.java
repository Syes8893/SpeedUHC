package me.syes.speeduhc.speeduhcplayer;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.gui.ShopGUI;

public class SpeedUHCPlayerHandler implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		SpeedUHCPlayer uhcPlayer;
		if(SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId()) == null)
			uhcPlayer = new SpeedUHCPlayer(e.getPlayer().getUniqueId());
		uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(SpeedUHC.lobbySpawn);
		p.setPlayerListName(SpeedUHC.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
				+ "[" + uhcPlayer.getElo() + "] " + p.getName());
		SpeedUHC.getInstance().getSpeedUHCPlayerManager().setLobbyInventory(p);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		//if(!uhcPlayer.isInGame())
			//e.setRespawnLocation(SpeedUHC.lobbySpawn);
	}
	
	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		e.setFormat(SpeedUHC.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
				+ e.getPlayer().getName() + ": §f" + e.getMessage());
	}
	
	@EventHandler
	public void playerLobbyInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame())
			return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		e.setCancelled(true);
		if(e.getItem().getType() == Material.EMERALD)
			ShopGUI.openGUI(p);
		else if(e.getItem().getType() == Material.IRON_SWORD) {
			if(SpeedUHC.getInstance().getGameManager().getAvailableGame() == null) {
				p.sendMessage("§cCouldn't find any available games");
				return;
			}
			SpeedUHC.getInstance().getGameManager().getAvailableGame().addPlayer(p);
		}
			
	}

}
