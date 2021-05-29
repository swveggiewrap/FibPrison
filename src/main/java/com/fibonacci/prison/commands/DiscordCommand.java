package com.fibonacci.prison.commands;

import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "discord", requiresPlayer = true)
public class DiscordCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &dJoin our Discord here. &nhttps://discord.gg/QTjGBtJuWv"));
	}
}
