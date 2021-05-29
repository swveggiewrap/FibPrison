package com.fibonacci.prison.commands;

import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "rankupcost", requiresPlayer = true)
public class RankupCostCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {

		player.sendMessage(ColorUtils.colorize("&7(&d&lRankup-Cost&7) &eIt will cost &d" + NumberUtils.longToSuffixedNumber(PrisonPlayerUtils.getPrisonPlayer(player).getRank().getPrice()) + " Dibs to rankup to " + PrisonPlayerUtils.getNextRank(PrisonPlayerUtils.getPrisonPlayer(player)).getPrefix()));

	}
}
