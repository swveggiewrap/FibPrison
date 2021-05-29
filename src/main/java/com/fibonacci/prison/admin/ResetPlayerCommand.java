package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.JedisUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;

@CommandInfo(name = "resetplayer", requiresPlayer = true, permission = "fibprison.admin")
public class ResetPlayerCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cSpecify a player"));
		} else {
			String playerName = args[0];
			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);
			if (Bukkit.getPlayer(playerName) != null) {
				Bukkit.getPlayer(playerName).getInventory().clear();
				Bukkit.getPlayer(playerName).kickPlayer("Your progress is being reset.");
			}

			JedisUtils.delete("prisonplayer:" + uuid);
			JedisUtils.delete("firstjoin:" + uuid);

			for (World world : Bukkit.getWorlds()) {
				File file = new File(world.getWorldFolder() + File.separator + "playerdata" + File.separator + uuid + ".dat");
				File other = new File(world.getWorldFolder() + File.separator + "playerdata" + File.separator + uuid + ".dat_old");
				file.delete();
				other.delete();
			}


		}


	}
}
