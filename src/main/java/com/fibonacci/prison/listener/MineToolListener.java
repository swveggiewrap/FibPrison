package com.fibonacci.prison.listener;

import com.fibonacci.prison.admin.MineCommand;
import com.fibonacci.prison.items.AdminItems;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MineToolListener implements Listener {

	@EventHandler
	public void onMineToolInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (!player.isOp()) {
			return;
		}

		if (player.getInventory().getItemInMainHand().getItemMeta() == null) {
			return;
		}

		if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(AdminItems.MINE_TOOL.getItemMeta().getDisplayName())) {
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (e.getHand() == EquipmentSlot.HAND) {
					MineCommand.loc1 = e.getClickedBlock().getLocation();
					e.setCancelled(true);
					player.sendMessage(ColorUtils.colorize("&eFirst location set to " + MineCommand.loc1.getBlockX() + ", " + MineCommand.loc1.getBlockY() + ", " + MineCommand.loc1.getBlockZ() + "."));
				}
			} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getHand() == EquipmentSlot.HAND) {
					MineCommand.loc2 = e.getClickedBlock().getLocation();
					e.setCancelled(true);
					player.sendMessage(ColorUtils.colorize("&eSecond location set to " + MineCommand.loc2.getBlockX() + ", " + MineCommand.loc2.getBlockY() + ", " + MineCommand.loc2.getBlockZ() + "."));
				}
			}
		}
	}

}
