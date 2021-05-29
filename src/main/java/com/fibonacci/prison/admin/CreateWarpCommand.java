package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "createwarp", requiresPlayer = true, permission = "fibprison.admin")
public class CreateWarpCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cMust specify a warp name idiot"));
		} else if (args.length == 1) {
			WarpUtils.createWarp(args[0], player.getLocation());
			player.sendMessage(ColorUtils.colorize("&eSuccessfully created Warp " + args[0]));
		}
	}


}
