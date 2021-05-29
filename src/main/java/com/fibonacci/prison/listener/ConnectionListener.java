package com.fibonacci.prison.listener;

import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.scoreboard.PlayerScoreboard;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		if (BannedPlayer.isStillBanned(e.getPlayer())) {
			BannedPlayer banned = BannedPlayer.getBannedList().get(e.getPlayer().getUniqueId().toString());
			String message;
			if (banned.getDuration() == 0) {
				message = "Permanently banned.\nReason: " + banned.getReason() + "\nEnd Date : " + banned.getEndDate();
			} else {
				message = "Temporarily banned.\nReason: " + banned.getReason() + "\nEnd Date : " + banned.getEndDate();
			}
			e.getPlayer().kickPlayer(message);
			return;
		}

		PrisonPlayerUtils.createPrisonPlayer(e.getPlayer());
		PlayerScoreboard.getNewPlayerScoreboard(e.getPlayer());
		e.setJoinMessage(ColorUtils.colorize("&e&l[+] &d" + e.getPlayer().getName()));
		e.getPlayer().teleport(WarpUtils.getWarp("lobby").getLocation());
		if (e.getPlayer().getName().equals("Fibonacci122")) {
			Bukkit.broadcastMessage(ColorUtils.colorize("&d&lYou are late to Fibonacci122's class!"));
		}
		PrisonPlayer player = PrisonPlayerUtils.getPrisonPlayer(e.getPlayer());
		if (player.isStaff()) {
			e.getPlayer().setPlayerListName(PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()).getStaffPrefix() + e.getPlayer().getName());
		} else {
			e.getPlayer().setPlayerListName(PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()).getRank().getPrefix() + e.getPlayer().getName());
		}

		PlayerUtils.refreshTabList(false);
		CEUtils.checkEnchants(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());
		PlayerUtils.vanishNewConnection(e.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		if (BannedPlayer.isStillBanned(e.getPlayer())) {
			PlayerScoreboard.removeFromScoreboards(e.getPlayer());
			return;
		}
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(e.getPlayer());

		PrisonPlayerUtils.savePrisonPlayer(e.getPlayer());
		PlayerScoreboard.removeFromScoreboards(e.getPlayer());

		if (prisonPlayer.isInAM()) {
			prisonPlayer.setAutomining(false);
		}

		CEUtils.checkEnchants(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());

		if (PlayerUtils.vanished.contains(e.getPlayer())) {
			PlayerUtils.vanished.remove(e.getPlayer());
			PlayerUtils.refreshTabList(false);
			return;
		}

		PlayerUtils.refreshTabList(true);

		e.setQuitMessage(ColorUtils.colorize("&c&l[-] &d" + e.getPlayer().getName()));
		if (e.getPlayer().getName().equals("Fibonacci122")) {
			Bukkit.broadcastMessage(ColorUtils.colorize("&d&lFibonacci122 went away to code or sleep."));
		}
	}

}
