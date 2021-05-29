package com.fibonacci.prison.commands;

import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "rank", requiresPlayer = true)
public class RankCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		player.sendMessage(ColorUtils.colorize("&7(&dStats&7) &eYou are currently Rank " + prisonPlayer.getRank().getPrefix()));
	}


}
