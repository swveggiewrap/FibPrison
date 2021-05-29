package com.fibonacci.prison.listener;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.Collection;

public class ItemSwitchListener implements Listener {

	@EventHandler
	public void onItemSwitch(PlayerItemHeldEvent e) {
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			CEUtils.removeAllEnchants(e.getPlayer());
			return;
		}
		CEUtils.checkEnchants(e.getPlayer(), e.getNewSlot());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			CEUtils.removeAllEnchants((Player) e.getPlayer());
			return;
		}
		CEUtils.checkEnchants((Player) e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());
	}




}
