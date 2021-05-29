package com.fibonacci.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandInfo(name = "spawn", requiresPlayer = true)
public class SpawnCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		Location location = new Location(Bukkit.getWorld("prison1"), 0.5, 92, 0.5, 90F, -9.6F);
		player.teleport(location);
	}

}
