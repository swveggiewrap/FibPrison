package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "getmine", requiresPlayer = true, permission = "fibprison.admin")
public class GetMineCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (MineUtils.isPlayerInMine(player.getLocation()) == null) {
			player.sendMessage(ColorUtils.colorize("&cYou are not in a Mine noob."));
		} else {
			player.sendMessage(ColorUtils.colorize("&eYou are in Mine " + MineUtils.isPlayerInMine(player.getLocation()).getMineName()));
		}
	}


}
