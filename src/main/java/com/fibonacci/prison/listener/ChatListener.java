package com.fibonacci.prison.listener;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.misc.MutedPlayer;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ChatUtils;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

		if (MutedPlayer.isStillMuted(player)) {
			player.sendMessage(ColorUtils.colorize("&7(&6Server&7) &cYou are muted."));
			e.setCancelled(true);
			return;
		}

		if (e.getMessage().startsWith("$") && prisonPlayer.isStaff()) {
			e.setCancelled(true);
			for (PrisonPlayer pPlayer : PrisonPlayerUtils.playerMap.values()) {
				if (!pPlayer.isStaff()) {
					continue;
				}
				Bukkit.getPlayer(pPlayer.getPlayerName()).sendMessage(ColorUtils.colorize("&7(&c&lStaff&7-&6&lChat&7) " + player.getName() + "&7 » &f" + e.getMessage().replace("$", "")));
			}

			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("821823680828801044");
			channel.sendMessage(ChatColor.stripColor("**(ServerStaffChat)** " + player.getName() + " » " + e.getMessage().replace("$", ""))).queue();

			return;
		}

		ChatUtils.sendFormattedPrefixChat(prisonPlayer, player, e.getMessage());
		e.setCancelled(true);
		MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("835662029557727254");
		if (prisonPlayer.getStaffPrefix().equals("")) {
			channel.sendMessage(ChatColor.stripColor(prisonPlayer.getRank().getPrefix()) + player.getName() + " » " + e.getMessage()).queue();
		} else {
			channel.sendMessage(ChatColor.stripColor("**" + prisonPlayer.getStaffPrefix() + "**" + prisonPlayer.getRank().getPrefix()) + player.getName() + " » " + e.getMessage()).queue();
		}
	}


}
