package com.fibonacci.prison.discord;

import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class StaffChatListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			return;
		}

		if (!event.getChannel().getId().equals("821823680828801044")) {
			return;
		}

		for (PrisonPlayer pPlayer : PrisonPlayerUtils.playerMap.values()) {
			if (!pPlayer.isStaff()) {
				continue;
			}
			Bukkit.getPlayer(pPlayer.getPlayerName()).sendMessage(ColorUtils.colorize("&7(&c&lStaff&7-&6&lChat&7-&d&lDiscord&7) " + event.getAuthor().getName() + "&7 » &f" + event.getMessage().getContentRaw()));
		}
	}

}
