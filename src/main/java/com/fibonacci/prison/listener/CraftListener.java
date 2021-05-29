package com.fibonacci.prison.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftListener implements Listener {

	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		e.getInventory().setResult(null);
	}

	@EventHandler
	public void onTake(CraftItemEvent e) {
		e.setCancelled(true);
	}

}
