package me.syes.speeduhc.commands.subcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.arena.Arena;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class EditCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /suhc edit <name>");
			return;
		}
		
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		for(Arena a : SpeedUHC.getInstance().getArenaManager().getArenas()) {
			if(a.getName().equalsIgnoreCase(args[1])) {
				uhcPlayer.setEditedArena(a);
				p.teleport(new Location(a.getArenaWorld(), 0, 100, 0));
				p.sendMessage("§eYou are now editing the arena §6\"" + args[1] + "\"§e.");
				return;
			}
		}
		p.sendMessage("§cThe specified arena does not exist, use /suhc arenas for a list of all arenas.");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc edit <arena>";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}
	
}
