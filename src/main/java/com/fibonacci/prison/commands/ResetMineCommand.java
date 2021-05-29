package com.fibonacci.prison.commands;

import com.fibonacci.prison.mines.Mine;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandInfo(name = "resetmine", requiresPlayer = true)
public class ResetMineCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (MineUtils.isPlayerInMine(player.getLocation()) != null) {
			Mine mine = MineUtils.isPlayerInMine(player.getLocation());
			for (Player inMine : Bukkit.getOnlinePlayers()) {
				if (MineUtils.isPlayerInMine(inMine.getLocation()) == null) {
					continue;
				}
				if (mine.getCuboid().contains(inMine.getLocation())) {
					inMine.teleport(new Location(inMine.getWorld(), inMine.getLocation().getX(), mine.getCuboid().getUpperY() + 1, inMine.getLocation().getZ(), inMine.getLocation().getYaw(), inMine.getLocation().getPitch()));
				}
			}
			MineUtils.resetMine(mine.getMineName());
		} else {
			player.sendMessage(ColorUtils.colorize("&cYou are not in a mine to reset!"));
		}
	}


}
