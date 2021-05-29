package com.fibonacci.prison.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandInfo(name = "food", requiresPlayer = true)
public class FoodCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

	}
}


