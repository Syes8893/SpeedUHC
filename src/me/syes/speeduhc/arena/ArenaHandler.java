package me.syes.speeduhc.arena;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class ArenaHandler implements Listener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission("speeduhc.admin"))
			return;
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.BLAZE_ROD)
			return;
		if(!e.getItem().hasItemMeta())
			return;
		if(!e.getItem().getItemMeta().getDisplayName().contains("Arena Wand"))
			return;
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			ArenaUtils.setCornerLoc1(p, e.getClickedBlock().getLocation());
			p.sendMessage("�eCorner �61 �eset!");
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ArenaUtils.setCornerLoc2(p, e.getClickedBlock().getLocation());
			p.sendMessage("�eCorner �62 �eset!");
		}
		e.setCancelled(true);
	}

}
