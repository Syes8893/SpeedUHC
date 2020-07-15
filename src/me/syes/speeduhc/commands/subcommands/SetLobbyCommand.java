package me.syes.speeduhc.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.speeduhc.SpeedUHC;

public class SetLobbyCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		SpeedUHC.getInstance().getConfig().set("Lobby.Spawn", p.getLocation().getWorld().getName());
		SpeedUHC.getInstance().getConfig().set("Lobby.X", p.getLocation().getBlockX());
		SpeedUHC.getInstance().getConfig().set("Lobby.Y", p.getLocation().getBlockY());
		SpeedUHC.getInstance().getConfig().set("Lobby.Z", p.getLocation().getBlockZ());
		SpeedUHC.getInstance().saveConfig();
		p.sendMessage("§eSuccesfully set the new lobby location!");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc setlobby";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
