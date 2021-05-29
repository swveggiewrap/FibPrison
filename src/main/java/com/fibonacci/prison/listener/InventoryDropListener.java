package com.fibonacci.prison.listener;

import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.enchants.CustomEnchants;
import com.fibonacci.prison.mines.MineUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryDropListener implements Listener {

	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockDrop(BlockBreakEvent e) {

		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			e.setCancelled(true);
			return;
		}

		if (!MineUtils.isBlockInMine(e.getBlock().getLocation())) {
			if (e.getPlayer().isOp()) {
				return;
			}
			e.setCancelled(true);
			return;
		}

		e.setDropItems(false);
		if (!CEUtils.hasEnchant(CustomEnchants.AUTOSELL, e.getPlayer().getInventory().getItemInMainHand())) {
			for (ItemStack itemStack : e.getBlock().getDrops()) {
				e.getPlayer().getInventory().addItem(itemStack);
			}
		}
	}

}
