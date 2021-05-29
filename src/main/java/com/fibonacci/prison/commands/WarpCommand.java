package com.fibonacci.prison.commands;

import com.fibonacci.prison.rank.Rank;
import com.fibonacci.prison.rank.RankRegistry;
import com.fibonacci.prison.rank.RankUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.warps.Warp;
import com.fibonacci.prison.warps.WarpUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "warp", requiresPlayer = true)
public class WarpCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cPlease specify a warp name. /warp <name>"));
		} else if (args.length == 1) {
			String lowercase = args[0].toLowerCase();
			if (lowercase.equalsIgnoreCase("mine")) {
				player.teleport(WarpUtils.getWarp(PrisonPlayerUtils.getPrisonPlayer(player).getRank().getRank().getLetter()).getLocation());
				return;
			}

			Warp warp = WarpUtils.getWarp(lowercase);

			if (warp != null) {

				if (lowercase.equalsIgnoreCase("lobby") || lowercase.equalsIgnoreCase("spawn") || lowercase.equalsIgnoreCase("hub")) {
					player.teleport(warp.getLocation());
					return;
				}

				if (lowercase.equalsIgnoreCase("pvp")) {
					player.teleport(warp.getLocation());
					return;
				}

				PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
				Rank rank = prisonPlayer.getRank().getRank();
				int index = Rank.valueOf(lowercase.toUpperCase()).getIndex();

				if (rank.getIndex() >= index) {
					player.teleport(warp.getLocation());
				} else {
					player.sendMessage(ColorUtils.colorize("&cYou need to be Rank " + Rank.valueOf(lowercase.toUpperCase()).getPrefix() + "&c to teleport here!"));
				}


			} else {
				player.sendMessage(ColorUtils.colorize("&cThis warp does not exist."));
			}
		}
	}

}
