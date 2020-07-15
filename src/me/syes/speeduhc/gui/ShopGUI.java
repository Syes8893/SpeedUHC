package me.syes.speeduhc.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.perk.Perk;
import me.syes.speeduhc.perk.PerkUtils;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;
import me.syes.speeduhc.utils.ItemUtils;

public class ShopGUI {
	
	public static void openGUI(Player p) {
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		
		//Create GUI
		Inventory inv = Bukkit.createInventory(null, (int)(((SpeedUHC.getInstance().getPerkManager().getPerks().size()+1)/9)+3) * 9 + 9, "§8SpeedUHC Shop");
		
		//Add Items
		int firstItem = 10;
		for(Perk perk : SpeedUHC.getInstance().getPerkManager().getPerks()) {
			inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(perk.getIcon())
					, getName(uhcPlayer, perk)
					, getFullDescription(uhcPlayer, perk)));
			if((firstItem+2) % 9 == 0) {
				firstItem += 3;
				continue;
			}
			firstItem++;
		}
		
		//Open the GUI
		SpeedUHC.getInstance().getGuiManager().setInGUI(p, true);
		p.openInventory(inv);
	}
	
	public static String getName(SpeedUHCPlayer uhcPlayer, Perk p) {
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) == -1)
			return "§a" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p));
		else if(p.getPrice(uhcPlayer.getPerkLevel(p)) <= uhcPlayer.getCoins())
			return "§e" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p)+1);
		else
			return "§c" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p)+1);
	}
	
	public static ArrayList<String> getFullDescription(SpeedUHCPlayer uhcPlayer, Perk p) {
		ArrayList<String> desc = new ArrayList<String>();
		for(String str : p.getDescription(uhcPlayer.getPerkLevel(p)))
			desc.add(str);
		desc.add("");
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) == -1) {
			desc.add("§aMAX LEVEL!");
			return desc;
		}
		desc.add("§7Price: §6" + p.getPrice(uhcPlayer.getPerkLevel(p)));
		desc.add("");
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) <= uhcPlayer.getCoins())
			desc.add("§eClick to unlock!");
		else
			desc.add("§cNot enough coins!");
		return desc;
	}
}
