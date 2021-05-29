package com.fibonacci.prison.discord;

import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ServerChatListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			return;
		}

		if (!event.getChannel().getId().equals("835662029557727254")) {
			return;
		}

		String message = event.getMessage().getContentDisplay();
		Bukkit.broadcastMessage(ColorUtils.colorize("&7(&dDiscord&bChat&7) &f" + event.getAuthor().getName() + " &7»&f " + message));

	}
}
