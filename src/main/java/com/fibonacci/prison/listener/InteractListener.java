package com.fibonacci.prison.listener;

import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
				CEUtils.getCEGUI(e.getPlayer()).open(e.getPlayer());
			}

		}

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (player.isOp()) {
			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
			}
		}

		if (player.getWorld().getName().equalsIgnoreCase("world")) {
			return;
		}

		if (!MineUtils.isBlockInMine(e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPermission("prison.build")) {
			if (player.getWorld().getName().equalsIgnoreCase("world")) {
				return;
			}
			player.sendMessage(ColorUtils.colorize("&cYou are not allowed to build here."));
			e.setCancelled(true);
			return;
		}
	}

}
