package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.warps.Warp;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "deletewarp", requiresPlayer = true, permission = "fibprison.admin")
public class DeleteWarpCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cYou need to specify a warp name to delete idiot."));
		} else if (args.length == 1) {
			Warp warp = WarpUtils.getWarp(args[0]);
			if (warp == null) {
				player.sendMessage(ColorUtils.colorize("&cThis warp does not exist noob."));
			} else {
				player.sendMessage(ColorUtils.colorize("&eSuccessfully deleted Warp " + warp.getName()));
				WarpUtils.deleteWarp(warp.getName());
			}
		}
	}

}
