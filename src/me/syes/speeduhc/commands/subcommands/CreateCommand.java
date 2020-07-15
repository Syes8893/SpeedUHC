package me.syes.speeduhc.commands.subcommands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.arena.Arena;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class CreateCommand extends SubCommand {

	@Override
	public void runCommand(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /suhc create <name>");
			return;
		}
		/*if(ArenaUtils.getCornerLoc1(p) == null || ArenaUtils.getCornerLoc2(p) == null) {
			p.sendMessage("§cPlease set both corners before creating an arena");
			return;
		}*/
		
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		for(Arena a : SpeedUHC.getInstance().getArenaManager().getArenas()) {
			if(a.getName().equalsIgnoreCase(args[1])) {
				uhcPlayer.setEditedArena(a);
				p.teleport(new Location(a.getArenaWorld(), 0, 100, 0));
				p.sendMessage("§eYou are now editing the arena §6\"" + args[1] + "\"§e.");
				return;
			}
		}
		
		WorldCreator wc = new WorldCreator(SpeedUHC.getInstance().getDataFolder() + "/worlds/" + args[1]);

		wc.type(WorldType.FLAT);
		wc.generatorSettings("2;0;1;");

		wc.createWorld();
		
		World w = Bukkit.getWorld(SpeedUHC.getInstance().getDataFolder() + "/worlds/" + args[1]);
		
		w.setAutoSave(false);
        w.setTicksPerAnimalSpawns(1);
        w.setTicksPerMonsterSpawns(1);
        w.setGameRuleValue("doMobSpawning", "false");
        w.setGameRuleValue("mobGriefing", "false");
        w.setGameRuleValue("doFireTick", "false");
        w.setGameRuleValue("showDeathMessages", "false");
		
		p.teleport(new Location(w, 0, 100, 0));
		uhcPlayer.setEditedArena(new Arena(p.getWorld(), args[1]));
		p.sendMessage("§eSuccessfully created the world for arena §6\"" + args[1] + "\"§e.");
		p.sendMessage("§eUse §6/speeduhc save §eto save the arena.");
		
		//new Arena(ArenaUtils.getCornerLoc1(p), ArenaUtils.getCornerLoc2(p), args[1]);
		//p.sendMessage("§eSuccessfully created the arena §6\"" + args[1] + "\"");
		
		//TODO - SkywarsReloaded arena creation system
		//Create world and let player setup arena in said world
		//Save world
		//Dupe world every time a new map needs to be used
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc create <arena>";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
