package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.MiscUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandInfo(name = "automine", requiresPlayer = true)
public class AutoMineCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		if (prisonPlayer.getAutominerSecondsLeft() <= 0) {
			player.sendMessage(ColorUtils.colorize("&cYou do not have any AutoMiner time left!"));
			return;
		}

		if (prisonPlayer.isInAM()) {
			player.sendMessage(ColorUtils.colorize("&cAutomining Stopped."));
			prisonPlayer.setAutomining(false);
			return;
		}

		prisonPlayer.setAutomining(true);

		new BukkitRunnable() {
			@Override
			public void run() {
				MiscUtils.autoMine(player, prisonPlayer);
				if (!prisonPlayer.isInAM()) {
					this.cancel();
				}
			}
		}.runTaskTimer(Prison.getInstance(), 0, 20);

	}
}
