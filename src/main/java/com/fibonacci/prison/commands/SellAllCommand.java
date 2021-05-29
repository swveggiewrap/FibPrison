package com.fibonacci.prison.commands;

import com.fibonacci.prison.utils.SellUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "sellall", requiresPlayer = true)
public class SellAllCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		SellUtils.sellAll(player);
	}

}
