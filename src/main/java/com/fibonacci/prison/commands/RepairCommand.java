package com.fibonacci.prison.commands;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.enchants.CE;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

@CommandInfo(name = "repair", requiresPlayer = true)
public class RepairCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {

		if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_PICKAXE) {
			player.sendMessage(ColorUtils.colorize("&cYou do not have a proper item to repair in your hand!"));
			return;
		}

		if (((Damageable) player.getInventory().getItemInMainHand().getItemMeta()).getDamage() >= player.getInventory().getItemInMainHand().getType().getMaxDurability()) {
			player.sendMessage(ColorUtils.colorize("&cThis item does not need repairing!"));
			return;
		}

		ItemStack stack = new ItemBuilder(player.getInventory().getItemInMainHand());
		ItemMeta meta = stack.getItemMeta();
		((Damageable) meta).setDamage(0);
		stack.setItemMeta(meta);
		stack.setItemMeta(meta);
		player.getInventory().setItemInMainHand(stack);
		player.sendMessage(ColorUtils.colorize("&eYou repaired the item in your hand!"));

	}
}
