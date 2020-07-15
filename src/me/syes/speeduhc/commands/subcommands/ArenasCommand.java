package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.arena.Arena;

public class ArenasCommand extends SubCommand {

	@Override
	public void runCommand(Player p, String[] args) {
		for(Arena a : SpeedUHC.getInstance().getArenaManager().getArenas()) {
			p.sendMessage("§eArena: §6" + a.getName());
		}
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc arenas";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
