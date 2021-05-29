package com.fibonacci.prison.commands;

import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "firstjoin", requiresPlayer = true)
public class FirstJoinCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.sendMessage(ColorUtils.colorize("&7(&dFib&bPrison&7) &aYou first joined the server on " + PlayerUtils.getFirstJoinDate(player)) + ".");
	}

}
