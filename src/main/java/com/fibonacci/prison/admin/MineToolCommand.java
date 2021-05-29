package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.items.AdminItems;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "minetool", requiresPlayer = true, permission = "fibprison.admin")
public class MineToolCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (player.getInventory().contains(AdminItems.MINE_TOOL)) {
			player.sendMessage("&cYou already have the Mine Super-Tool in your inventory.");
		} else {
			player.getInventory().addItem(AdminItems.MINE_TOOL);
			player.sendMessage(ColorUtils.colorize("&7(&d&l!&7) &eYou have been given 1x " + AdminItems.MINE_TOOL.getItemMeta().getDisplayName() + "&e."));
		}

	}

}
