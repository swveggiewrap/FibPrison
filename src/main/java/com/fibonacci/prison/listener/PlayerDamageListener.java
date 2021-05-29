package com.fibonacci.prison.listener;

import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {


		if (e.getEntity().getWorld().getName().equalsIgnoreCase("pvp")) {
			return;
		}

		if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
			e.setCancelled(true);
		}

		e.setCancelled(true);

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.getEntity().teleport(WarpUtils.getWarp("spawn").getLocation());
		if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
			Bukkit.broadcastMessage(ColorUtils.colorize("&7(&1&lPVP&7) &e"
			+ e.getEntity().getName() + " was killed by " + e.getEntity().getKiller().getName()));
		}
	}

	@EventHandler
	public void onSpawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(WarpUtils.getWarp("spawn").getLocation());
	}

}
