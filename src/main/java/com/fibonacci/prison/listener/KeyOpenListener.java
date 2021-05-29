package com.fibonacci.prison.listener;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class KeyOpenListener implements Listener {

	@EventHandler
	public void onKeyOpen(PlayerInteractEvent e) {

		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.TRIPWIRE_HOOK) {
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.setCancelled(true);
			if (e.getPlayer().isSneaking()) {
				e.getPlayer().sendMessage(ColorUtils.colorize("&eShift-Click to BulkOpen Keys coming soon!"));
			}

			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), "key"), PersistentDataType.STRING)) {
				return;
			}
			String str = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Prison.getInstance(), "key"), PersistentDataType.STRING);

			switch (str) {
				case "rankupkey":
					redeemRankupKey(e.getPlayer(), PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()), item.getAmount());
					break;
				case "minekey":
					redeemMineKey(e.getPlayer(), PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()), item.getAmount());
					break;
				default:
					break;
			}
		}
	}


	public void redeemRankupKey(Player player, PrisonPlayer prisonPlayer, int amount) {
		Random random = new Random();
		int count = 0;
		for (int i = 0; i < amount; i++) {
			int fibs = random.nextInt(1000) + 1;
			prisonPlayer.addFibs(fibs);
			count += fibs;
			if (random.nextInt(10) + 1 == 1) {
				player.getInventory().addItem(KitItems.FIBBER_BOMB);
			}
		}

		player.sendMessage(ColorUtils.colorize("&7(&d&lKeys&7) &eYou received &d" + count + " Fibs&e from your redeemed Key(s)!"));
		player.getInventory().setItemInMainHand(null);
	}

	public void redeemMineKey(Player player, PrisonPlayer prisonPlayer, int amount) {
		Random random = new Random();
		int count = 0;
		for (int i = 0; i < amount; i++) {
			int dibs = random.nextInt(10000) + 1;
			prisonPlayer.addDibs(dibs);
			count += dibs;
			if (random.nextInt(10) + 1 == 1) {
				player.getInventory().addItem(KitItems.DIBBER_BOMB);
			}
		}

		player.sendMessage(ColorUtils.colorize("&7(&d&lKeys&7) &eYou received &d" + count + " Dibs&e from your redeemed Key(s)!"));
		player.getInventory().setItemInMainHand(null);
	}

}
