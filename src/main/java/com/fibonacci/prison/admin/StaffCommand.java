package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.JedisUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandInfo(name = "staff", permission = "fibprison.admin", requiresPlayer = false)
public class StaffCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length <= 1) {
			sender.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cStaff command must promote or demote a player."));
		} else if (args.length == 2) {

			String playerName = args[1];
			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);
			if (uuid.equals("")) {
				sender.sendMessage(ColorUtils.colorize("&cThis player has never played before."));
				return;
			}

			Player player = Bukkit.getPlayer(playerName);

			if (args[0].equalsIgnoreCase("promote")) {
				int currentIndex = Integer.parseInt(PrisonPlayerUtils.getPrisonPlayerMap(uuid).get("staff"));
				if (player != null && player.isOnline()) {

					if (currentIndex < 2) {
						PrisonPlayerUtils.getPrisonPlayer(player).setStaffIndex(currentIndex + 1);
					} else {
						sender.sendMessage(ColorUtils.colorize("&cThis player cannot be promoted further."));
					}

					player.kickPlayer("Congratulations on your promotion!");


				} else {
					if (currentIndex < 2) {
						Map<String, String> map = PrisonPlayerUtils.getPrisonPlayerMap(uuid);
						map.put("staff", String.valueOf(currentIndex + 1));
						JedisUtils.setHashMap("prisonplayer:" + uuid, map);
					} else {
						sender.sendMessage(ColorUtils.colorize("&cThis player cannot be promoted further."));
					}
				}

			} else if (args[0].equalsIgnoreCase("demote")) {
				int currentIndex = Integer.parseInt(PrisonPlayerUtils.getPrisonPlayerMap(uuid).get("staff"));

				if (player != null && player.isOnline()) {

					if (currentIndex == 0) {
						sender.sendMessage(ColorUtils.colorize("&cThis player cannot be demoted further."));
					} else {
						PrisonPlayerUtils.getPrisonPlayerMap(uuid).put("staff", String.valueOf(currentIndex - 1));
					}

					player.kickPlayer("You have been demoted.");
				} else {
					if (currentIndex == 0) {
						sender.sendMessage(ColorUtils.colorize("&cThis player cannot be demoted further."));
					} else {
						Map<String, String> map = PrisonPlayerUtils.getPrisonPlayerMap(uuid);
						map.put("staff", String.valueOf(currentIndex - 1));
						JedisUtils.setHashMap("prisonplayer:" + uuid, map);
					}
				}

				if (currentIndex == 0) {
					sender.sendMessage(ColorUtils.colorize("&cThis player cannot be demoted further."));
				} else {
					PrisonPlayerUtils.getPrisonPlayerMap(uuid).put("staff", String.valueOf(currentIndex - 1));
				}



			} else {
				sender.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cStaff command must promote or demote a player."));
			}

		} else {
			sender.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cStaff command must promote or demote a player."));
		}
	}
}
