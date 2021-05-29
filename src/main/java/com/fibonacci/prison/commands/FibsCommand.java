package com.fibonacci.prison.commands;

import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "fibs", requiresPlayer = true)
public class FibsCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		player.sendMessage(ColorUtils.colorize("&7(&dStats&7) &eYou have a balance of " + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getFibs()) + " Fibs!"));
	}


}
