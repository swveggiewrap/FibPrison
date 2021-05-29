package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.rank.PrisonRank;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "calculatemaxrank", requiresPlayer = true)
public class CMRCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		Bukkit.getScheduler().runTask(Prison.getInstance(), () -> {
			PrisonRank rank = PrisonPlayerUtils.calculateMaxRank(PrisonPlayerUtils.getPrisonPlayer(player));
			if (rank == null) {
				player.sendMessage(ColorUtils.colorize("&7(&d&lCMR&7) You cannot rankup any higher."));
			} else {
				player.sendMessage(ColorUtils.colorize("&7(&d&lCMR&7) You can rankup to " + rank.getPrefix()));
			}
		});

	}

}
