package com.fibonacci.prison.commands;

import com.fibonacci.prison.enchants.CEUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "ceinfo", requiresPlayer = true)
public class CustomEnchantInfoCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		CEUtils.getCEInfoGUI().open(player);
	}
}
