package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "add", requiresPlayer = true, permission = "fibprison.admin")
public class AddCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer;
		if (args.length == 2) {
			prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
			if (args[0].equalsIgnoreCase("fibs")) {
				prisonPlayer.addFibs(Double.parseDouble(args[1]));
			} else if (args[0].equalsIgnoreCase("dibs")) {
				prisonPlayer.addDibs(Double.parseDouble(args[1]));
			} else if (args[0].equalsIgnoreCase("am")) {
				prisonPlayer.addAMSeconds(Long.parseLong(args[1]));
			} else {
				player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /add fibs|dibs player amount or /add fibs|dibs amount"));
			}
		} else if (args.length == 3) {
			prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(Bukkit.getPlayer(args[1]));
			if (args[0].equalsIgnoreCase("fibs")) {
				prisonPlayer.addFibs(Double.parseDouble(args[2]));
			} else if (args[0].equalsIgnoreCase("dibs")) {
				prisonPlayer.addDibs(Double.parseDouble(args[2]));
			} else if (args[0].equalsIgnoreCase("am")) {
				prisonPlayer.addAMSeconds(Long.parseLong(args[2]));
			} else {
				player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /add fibs|dibs player amount or /add fibs|dibs amount"));
			}
		} else {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /add fibs|dibs player amount or /add fibs|dibs amount"));
		}
	}

}
