package com.fibonacci.prison.listener;

import com.fibonacci.prison.items.Keys;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class MiningListener implements Listener {

	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockMine(BlockBreakEvent e) {

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

		if (PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()).isInAM()) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ColorUtils.colorize("&cYou are AutoMining. You cannot Mine while AutoMining."));
		}

		int chance = new Random().nextInt(500) + 1;
		if (chance <= 1) {
			e.getPlayer().getInventory().addItem(Keys.MINE_KEY);
			e.getPlayer().sendMessage(ColorUtils.colorize("&7(&d&lKeys&7) &eYou received 1x " + Keys.MINE_KEY.getItemMeta().getDisplayName()));
		}

		PrisonPlayer player = PrisonPlayerUtils.getPrisonPlayer(e.getPlayer());
		player.incrementBlocks();
	}

}
