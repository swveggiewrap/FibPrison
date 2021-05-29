package com.fibonacci.prison.commands;


import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

//@CommandInfo(name = "pay", requiresPlayer = true)
public class PayCommand /*extends FibCommand*/ {

	/*@Override
	public void execute(Player player, String[] args) {

		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

		if (args.length <= 2) {

		} else {
			Player receiving = Bukkit.getPlayer(args[1]);
			if (receiving != null) {

				if (receiving.getName().equalsIgnoreCase(player.getName())) {
					player.sendMessage(ColorUtils.colorize("&cYou cannot pay yourself."));
					return;
				}

				double amt;
				if (isDouble(args[2])) {
					amt = Double.parseDouble(args[2]);
				} else {
					player.sendMessage(ColorUtils.colorize("&cThe amount specified is NOT a number."));
					return;
				}

				if (args[0].equalsIgnoreCase("fibs")) {
					if (prisonPlayer.getFibs() >= amt) {
						PrisonPlayerUtils.getPrisonPlayer(receiving).addFibs(amt);
						player.sendMessage(ColorUtils.colorize("&eYou have sent " + NumberUtils.doubleToSuffixedNumber(amt) + " Fibs to " + receiving.getName()));
						receiving.sendMessage(ColorUtils.colorize("&eYou have received " + NumberUtils.doubleToSuffixedNumber(amt) + " Fibs from " + player.getName()));
						prisonPlayer.removeFibs(amt);
					} else {
						player.sendMessage(ColorUtils.colorize("&cYou do not have enough Fibs to pay this amount."));
					}

				} else if (args[0].equalsIgnoreCase("dibs")) {
					if (prisonPlayer.getDibs() >= amt) {
						PrisonPlayerUtils.getPrisonPlayer(receiving).addDibs(amt);
						player.sendMessage(ColorUtils.colorize("&eYou have sent " + NumberUtils.doubleToSuffixedNumber(amt) + " Dibs to " + receiving.getName()));
						receiving.sendMessage(ColorUtils.colorize("&eYou have received " + NumberUtils.doubleToSuffixedNumber(amt) + " Dibs from " + player.getName()));
						prisonPlayer.removeDibs(amt);
					} else {
						player.sendMessage(ColorUtils.colorize("&cYou do not have enough Dibs to pay this amount."));
					}

				} else {
					player.sendMessage(ColorUtils.colorize("&cYou must specify Fibs or Dibs."));
				}
			} else {
				player.sendMessage(ColorUtils.colorize("&cThis player does not exist."));
			}

		}
	}

	public boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}*/
}
