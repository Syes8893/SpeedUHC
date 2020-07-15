package me.syes.speeduhc.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {
	
	public static ItemStack getGracefulItem(Material mat, String name) {
		ItemStack pick = new ItemStack(mat);
		ItemMeta pickmeta = pick.getItemMeta();
		pickmeta.setDisplayName("�a" + name);
		pickmeta.setLore(Arrays.asList("�7", "�7This Tool deals 0 damage to players."));
		pickmeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
		pickmeta.addEnchant(Enchantment.DURABILITY, 10, true);
		pick.setItemMeta(pickmeta);
		return pick;
	}

	public static ItemStack buildItem(ItemStack i, String name, List<String> lore) {
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name.replace("&", "�"));
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack buildEnchantedItem(ItemStack i, Enchantment ench, int level) {
		ItemMeta im = i.getItemMeta();
		im.addEnchant(ench, level, true);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack buildPotion(PotionEffectType effectType, int durationTicks, int potionLevel, short durability) {
		ItemStack p = new ItemStack(Material.POTION);
		PotionMeta pm = (PotionMeta) p.getItemMeta();
		pm.addCustomEffect(new PotionEffect(effectType, durationTicks, potionLevel-1), false);
		p.setItemMeta(pm);
		p.setDurability(durability);
		return p;
	}

}
