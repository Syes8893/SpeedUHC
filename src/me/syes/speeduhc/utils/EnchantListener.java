package me.syes.speeduhc.utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

import me.syes.speeduhc.SpeedUHC;
import net.minecraft.server.v1_8_R3.ContainerEnchantTable;

public class EnchantListener implements Listener, PrepareItemEnchant {

	
	
	@EventHandler
	public void prepareEnchant(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		Random rand = new Random();
		generateNewCosts(table.costs, rand, Math.min(e.getEnchantmentBonus(), 15));
		table.f = rand.nextInt();// Set the enchantment seed
		SpeedUHC.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SpeedUHC.getInstance(), ()-> {// Remove the display of what enchantment you will get
			clearArray(table.h);
		}, 1);
	}

	@Override
	public void randomizeSeed(PrepareItemEnchantEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oldEnchantCosts(PrepareItemEnchantEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideEnchants(PrepareItemEnchantEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@EventHandler
    public void onEnchant(EnchantItemEvent e) {
        e.getEnchanter().setLevel(e.getEnchanter().getLevel() - e.getExpLevelCost() + (e.whichButton() + 1));
        e.setExpLevelCost(0);
    }
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if(e.getInventory().getType().equals((Object)InventoryType.ENCHANTING)) {
            final EnchantingInventory en = (EnchantingInventory)e.getInventory();
            en.setSecondary(new ItemStack(Material.INK_SACK, 3, (short)4));
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType().equals((Object)InventoryType.ENCHANTING)) {
            event.getInventory().setItem(1, (ItemStack)null);
        }
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals((Object)InventoryType.ENCHANTING) && event.getRawSlot() == 1) {
            event.setCancelled(true);
        }
        if(event.getInventory().getType().equals((Object)InventoryType.ENCHANTING)) {
            final EnchantingInventory en = (EnchantingInventory)event.getInventory();
            en.setSecondary(new ItemStack(Material.INK_SACK, 3, (short)4));
        }
    }
}
