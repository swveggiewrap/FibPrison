package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandInfo(name = "gms", requiresPlayer = true, permission = "fibprison.admin")
public class GMSCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		player.setGameMode(GameMode.SURVIVAL);
		player.sendMessage(ColorUtils.colorize("&eYour gamemode is set to Survival."));
	}


}
