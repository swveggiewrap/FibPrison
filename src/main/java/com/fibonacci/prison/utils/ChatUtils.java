package com.fibonacci.prison.utils;

import com.fibonacci.prison.user.PrisonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {

	public static void sendFormattedPrefixChat(PrisonPlayer pPlayer, Player player, String message) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.sendMessage(ColorUtils.colorize(pPlayer.getStaffPrefix() + pPlayer.getRank().getPrefix() + "&7» &f" + player.getName() + ": " + message));
		}
	}


}
