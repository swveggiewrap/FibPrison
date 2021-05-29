package com.fibonacci.prison.commands;

import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "trash", requiresPlayer = true)
public class TrashCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.openInventory(Bukkit.createInventory(null, 27, ColorUtils.colorize("&l&nTRASH")));
	}
}
