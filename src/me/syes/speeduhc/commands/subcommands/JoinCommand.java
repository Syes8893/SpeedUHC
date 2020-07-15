package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;

public class JoinCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		if(SpeedUHC.getInstance().getGameManager().getAvailableGame() == null) {
			p.sendMessage("§cCouldn't find any available games");
			return;
		}
		SpeedUHC.getInstance().getGameManager().getAvailableGame().addPlayer(p);
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc join";
	}

	@Override
	public String getPermission() {
		return "speeduhc.default";
	}

}
