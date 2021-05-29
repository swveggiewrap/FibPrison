package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandInfo(name = "gmc", requiresPlayer = true, permission = "fibprison.admin")
public class GMCCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.setGameMode(GameMode.CREATIVE);
		player.sendMessage(ColorUtils.colorize("&eYour gamemode is set to Creative."));
	}

}
