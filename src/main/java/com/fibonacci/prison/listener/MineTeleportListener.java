package com.fibonacci.prison.listener;

import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.mines.Cuboid;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MineTeleportListener implements Listener {

	public static final Cuboid cuboid = new Cuboid(new Location(Bukkit.getWorld("prison1"), -17.5, 99, 18.5), new Location(Bukkit.getWorld("prison1"), -15.5, 92, 16.5));

	@EventHandler
	public void onSwimMineTeleport(PlayerMoveEvent event) {
		if (!event.getPlayer().getWorld().getName().equalsIgnoreCase("prison1")) {
			return;
		}

	    if (!cuboid.contains(event.getPlayer().getLocation())) {
			return;
		}
		event.getPlayer().teleport(WarpUtils.getWarp(PrisonPlayerUtils.getPrisonPlayer(event.getPlayer()).getRank().getRank().getLetter()).getLocation());

	}

	@EventHandler
	public void onWorldTeleport(PlayerChangedWorldEvent e) {
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
		} else {
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
		}

		CEUtils.checkEnchants(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());

	}

}
