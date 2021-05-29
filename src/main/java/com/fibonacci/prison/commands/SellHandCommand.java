package com.fibonacci.prison.commands;

import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.SellUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "sellhand", requiresPlayer = true)
public class SellHandCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {

		if (args.length == 0) {
			SellUtils.sellHand(player);
		}
	}


}
