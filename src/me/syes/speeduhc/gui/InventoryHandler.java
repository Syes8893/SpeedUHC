package me.syes.speeduhc.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.perk.Perk;
import me.syes.speeduhc.perk.PerkUtils;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;
import me.syes.speeduhc.utils.ItemUtils;

public class InventoryHandler implements Listener {
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		SpeedUHC.getInstance().getGuiManager().setInGUI((Player) 
			e.getPlayer(), false);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame())
			if(!p.hasPermission("speeduhc.admin"))
				e.setCancelled(true);
		if(SpeedUHC.getInstance().getGuiManager().isInGUI(p))
			e.setCancelled(true);
		//if(e.getInventory().getName() != "§7Kit Selector")
			//return;
		if(e.getRawSlot() >= e.getInventory().getSize() || e.getRawSlot() <= -1)
			return;
		if(e.getInventory().getItem(e.getSlot()) == null)
			return;
		if(e.getInventory().getItem(e.getSlot()).getType() == Material.SKULL_ITEM) {
			if(!uhcPlayer.isInGame())
				return;
			if(e.isLeftClick())
				uhcPlayer.getGame().setVote(p, true);
			else if(e.isRightClick())
				uhcPlayer.getGame().setVote(p, false);
			p.getOpenInventory().setItem(13, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), "§eEnable Player Heads?"
					, Arrays.asList("§7Left-Click to vote §aTrue", "§7Right-Click to vote §cFalse"
					, "§7", "§7Status:", "§a" + uhcPlayer.getGame().getTrueVotes()
					+ " §7/ §c" + uhcPlayer.getGame().getFalseVotes() + " §8("
							+ uhcPlayer.getGame().getVotingPercentage() + "%)")));
			//p.closeInventory();
		}
		if(e.getInventory().getName() == "§8SpeedUHC Shop") {
			if(uhcPlayer.isInGame())
				return;
			if(SpeedUHC.getInstance().getPerkManager().getPerk(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null)
				return;
			Perk perk = SpeedUHC.getInstance().getPerkManager().getPerk(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
			if(perk.getPrice(uhcPlayer.getPerkLevel(perk)) == -1) {
				p.sendMessage("§cYou already have this perk at max level!!");
				return;
			}
			if(uhcPlayer.getCoins() < perk.getPrice(uhcPlayer.getPerkLevel(perk))) {
				p.sendMessage("§cYou don't have enough coins to purchase this upgrade!");
				return;
			}
			p.sendMessage("§aSuccessfully purchased " + perk.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(perk)+1) + "!");
			uhcPlayer.removeCoins(perk.getPrice(uhcPlayer.getPerkLevel(perk)));
			uhcPlayer.setPerkLevel(perk, uhcPlayer.getPerkLevel(perk)+1);
			p.closeInventory();
		}
			
	}

}
