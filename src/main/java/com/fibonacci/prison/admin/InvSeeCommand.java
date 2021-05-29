package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "invsee", requiresPlayer = true, permission = "fibprison.admin")
public class InvSeeCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {

		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cYou must specify a player."));
		} else if (args.length > 1) {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /invsee <Player>"));
		} else {
			Player invPlayer = Bukkit.getPlayer(args[0]);
			if (invPlayer == null) {
				player.sendMessage(ColorUtils.colorize("&cThat player does not exist."));
			} else if (!invPlayer.isOnline()) {
				player.sendMessage(ColorUtils.colorize("&cThat player is not online."));
			} else {
				player.openInventory(invPlayer.getInventory());
			}
		}

	}
}
