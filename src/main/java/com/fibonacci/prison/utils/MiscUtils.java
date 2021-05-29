package com.fibonacci.prison.utils;

import com.fibonacci.prison.mines.Mine;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.misc.BlockValue;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiscUtils {

	public static List<String> automining = new ArrayList<>();

	public static void dibberBomb(Player player, Location bombLoc) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		double sellAmount = 0;
		double multi = prisonPlayer.getRank().getSellMultiplier();
		for (int x = bombLoc.getBlockX() - 1; x <= bombLoc.getBlockX() + 1; x++) {
			for (int y = bombLoc.getBlockY() - 1; y >= bombLoc.getBlockY() - 3; y--) {
				for (int z = bombLoc.getBlockZ() - 1; z <= bombLoc.getBlockZ() + 1; z++) {
					Location l = new Location(bombLoc.getWorld(), x, y, z);
					if (l.getBlock().getType().isAir()) {
						continue;
					}
					if (!MineUtils.isBlockInMine(l)) {
						continue;
					}

					sellAmount += BlockValue.fromMaterial(l.getBlock().getType()).getBasePrice() * multi;
					l.getBlock().setType(Material.AIR);
					l.getWorld().spawnParticle(Particle.FLAME, l, 5);
				}
			}
		}
		prisonPlayer.addDibs(sellAmount * 10);
	}

	public static void fibberBomb(Player player, Location bombLoc) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		double sellAmount = 0;
		double multi = prisonPlayer.getRank().getSellMultiplier();
		for (int x = bombLoc.getBlockX() - 3; x <= bombLoc.getBlockX() + 3; x++) {
			for (int y = bombLoc.getBlockY() - 1; y >= bombLoc.getBlockY() - 6; y--) {
				for (int z = bombLoc.getBlockZ() - 3; z <= bombLoc.getBlockZ() + 3; z++) {
					Location l = new Location(bombLoc.getWorld(), x, y, z);
					if (l.getBlock().getType().isAir()) {
						continue;
					}
					if (!MineUtils.isBlockInMine(l)) {
						continue;
					}

					sellAmount += BlockValue.fromMaterial(l.getBlock().getType()).getBasePrice() * multi;
					l.getBlock().setType(Material.AIR);
					l.getWorld().spawnParticle(Particle.FLAME, l, 5);
				}
			}
		}
		prisonPlayer.addFibs(sellAmount);
	}

	public static void autoMine(Player player, PrisonPlayer prisonPlayer) {
		if (prisonPlayer.getAutominerSecondsLeft() == 0) {
			prisonPlayer.setAutomining(false);
			return;
		}
		Mine mine = MineUtils.getMine(prisonPlayer.getRank().getRank().getLetter());
		Random random = new Random();
		int length = mine.getChances().size();
		for (int i = 0; i < 5; i++) {
			prisonPlayer.addDibs(BlockValue.fromMaterial(mine.getMaterials().get(random.nextInt(length))).getBasePrice() * prisonPlayer.getRank().getSellMultiplier());
		}
		player.sendTitle(ColorUtils.colorize("&c&lAutoMining..."), NumberUtils.amTimeToFormattedString(prisonPlayer.getAutominerSecondsLeft()), 10, 0, 10);
		prisonPlayer.takeAwayAMSecond();
	}


}
