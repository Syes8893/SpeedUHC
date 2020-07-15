package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;

public class WarpCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		p.sendMessage(p.getWorld().getName());
		if(SpeedUHC.getInstance().getArenaManager().getRandomArena() != null) {
			p.teleport(SpeedUHC.getInstance().getArenaManager().getRandomArena().getCenter());
			p.sendMessage(p.getWorld().getName());
		}
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc warp <arena>";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
