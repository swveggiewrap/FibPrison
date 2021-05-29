package com.fibonacci.prison.commands;

import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@CommandInfo(name = "customenchants", requiresPlayer = true)
public class CECommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
			CEUtils.getCEGUI(player).open(player);
		} else {
			player.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cYou must have a pickaxe in your hand to apply CustomEnchants!"));
		}
	}


}
