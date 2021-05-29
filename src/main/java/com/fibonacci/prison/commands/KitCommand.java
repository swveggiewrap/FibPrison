package com.fibonacci.prison.commands;

import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "kit", requiresPlayer = true)
public class KitCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&7(&6&lKits&7) &eValid Kits include the following: starter, pvp"));
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("starter")) {
				player.getInventory().addItem(KitItems.STARTER_PICKAXE, KitItems.STEAK);
				player.sendMessage(ColorUtils.colorize("&7(&6&lKits&7) &eYou have been given a Starter Kit."));
			} else if (args[0].equalsIgnoreCase("pvp")) {
				player.getInventory().addItem(KitItems.PVP_HELMET, KitItems.PVP_CHESTPLATE, KitItems.PVP_LEGGINGS, KitItems.PVP_BOOTS, KitItems.PVP_SWORD, KitItems.STEAK);
				player.sendMessage(ColorUtils.colorize("&7(&6&lKits&7) &eYou have been given a PVP Kit."));
			} else {
				player.sendMessage(ColorUtils.colorize("&cKit " + args[0] + " does not exist."));
			}

		} else {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /kit <name>"));

		}
	}
}
