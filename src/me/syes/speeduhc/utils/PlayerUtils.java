package me.syes.speeduhc.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.perk.Perk;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class PlayerUtils {
	
	public static void savePlayerData() {
		for(SpeedUHCPlayer uhcPlayer : SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayers().values()) {
			//new File(Kits.getInstance().getDataFolder() + "/players").mkdir();
			File f = new File(SpeedUHC.getInstance().getDataFolder() + "/players/" + uhcPlayer.getUuid() + ".yml");
			
			if(!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			FileConfiguration fc = new YamlConfiguration();
			try {
				fc.load(f);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}

			fc.set("ELO", uhcPlayer.getElo());
			fc.set("Wins", uhcPlayer.getWins());
			fc.set("Kills", uhcPlayer.getKills());
			fc.set("Deaths", uhcPlayer.getDeaths());
			fc.set("Coins", uhcPlayer.getCoins());
			for(Perk perk : SpeedUHC.getInstance().getPerkManager().getPerks())
				fc.set("Perks." + perk.getName(), uhcPlayer.getPerkLevel(perk));
			
			try {
				fc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadPlayerData() {
		new File(SpeedUHC.getInstance().getDataFolder() + "/players").mkdir();
		for(File f : new File(SpeedUHC.getInstance().getDataFolder() + "/players").listFiles()) {
			FileConfiguration fc = new YamlConfiguration();
			try {
				fc.load(f);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			SpeedUHCPlayer uhcPlayer = new SpeedUHCPlayer(UUID.fromString(f.getName().replace(".yml", "")));
			uhcPlayer.loadStats(fc);
			//KitPlayer kp = new KitPlayer(UUID.fromString(f.getName().replace(".yml", "")), fc.getInt("Kills"), fc.getInt("Deaths"), fc.getBoolean("inArena"));
		}
	}

}
