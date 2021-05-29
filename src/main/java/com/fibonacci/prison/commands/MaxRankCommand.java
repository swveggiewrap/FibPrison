package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "maxrank", requiresPlayer = true)
public class MaxRankCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(Prison.getInstance(), () -> {
			PrisonPlayerUtils.maxRank(PrisonPlayerUtils.getPrisonPlayer(player));
		});
	}

}
