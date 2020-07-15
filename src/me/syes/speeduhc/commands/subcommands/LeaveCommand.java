package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class LeaveCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame())
			uhcPlayer.getGame().removePlayer(p);
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc leave";
	}

	@Override
	public String getPermission() {
		return "speeduhc.default";
	}

}
