package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;
import me.syes.speeduhc.speeduhcplayer.SpeedUHCPlayer;

public class AddSpawnCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(!uhcPlayer.isEditingArena()) {
			p.sendMessage("§cYou are currently not editing an arena.");
			return;
		}
		uhcPlayer.getEditedArena().addSpawn(p.getLocation());
		p.sendMessage("§eAdded spawn §6" + uhcPlayer.getEditedArena().getSpawns().size() + "§e!");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc addspawn";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
