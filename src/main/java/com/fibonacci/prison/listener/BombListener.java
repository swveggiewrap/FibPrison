package com.fibonacci.prison.listener;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.MiscUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public class BombListener implements Listener {

	public static final List<String> delayDrop = new ArrayList<>();
	public static final List<String> fibDelayDrop = new ArrayList<>();

	@EventHandler
	public void onDibberBomb(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(KitItems.DIBBER_BOMB.getItemMeta().getDisplayName())) {
			if (delayDrop.contains(player.getName())) {
				player.sendMessage(ColorUtils.colorize("&cYou must wait to drop another DibberBomb until your current one explodes."));
				e.setCancelled(true);
				return;
			}
			int amount = e.getItemDrop().getItemStack().getAmount();
			e.getItemDrop().setPickupDelay(100);
			if (amount != 1) {
				player.sendMessage(ColorUtils.colorize("&cYou can only drop 1 DibberBomb at a time."));
				e.setCancelled(true);
			}

			if (MineUtils.isPlayerInMine(player.getLocation()) == null) {
				player.sendMessage(ColorUtils.colorize("&cYou must place a DibberBomb in a mine for it to work."));
				e.setCancelled(true);
				return;
			}

			delayDrop.add(player.getName());
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 3), "", 10, 10, 10);
			}, 0);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 2), "", 10, 10, 10);
			}, 20L);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 1), "", 10, 10, 10);
			}, 40L);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				MiscUtils.dibberBomb(player, e.getItemDrop().getLocation());
				player.playSound(e.getItemDrop().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
				e.getItemDrop().remove();
				delayDrop.remove(player.getName());
				}, 60L);

		} else if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(KitItems.FIBBER_BOMB.getItemMeta().getDisplayName())) {
			int amount = e.getItemDrop().getItemStack().getAmount();
			e.getItemDrop().setPickupDelay(100);
			if (delayDrop.contains(player.getName())) {
				player.sendMessage(ColorUtils.colorize("&cYou must wait to drop another FibberBomb until your current one explodes."));
				e.setCancelled(true);
				return;
			}
			if (amount != 1) {
				player.sendMessage(ColorUtils.colorize("&cYou can only drop 1 FibberBomb at a time."));
				e.setCancelled(true);
			}

			if (MineUtils.isPlayerInMine(player.getLocation()) == null) {
				player.sendMessage(ColorUtils.colorize("&cYou must place a FibberBomb in a mine for it to work."));
				e.setCancelled(true);
				return;
			}
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 3), "", 10, 10, 10);
			}, 0);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 2), "", 10, 10, 10);
			}, 20L);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				player.sendTitle(ColorUtils.colorize("&d&l" + 1), "", 10, 10, 10);
			}, 40L);
			Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
				MiscUtils.fibberBomb(player, e.getItemDrop().getLocation());
				player.playSound(e.getItemDrop().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
				e.getItemDrop().remove();
				fibDelayDrop.remove(player.getName());
			}, 60L);

		} else {
			player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &cYou cannot drop items. To get rid of something, use /trash"));
			e.setCancelled(true);
		}

	}



}
