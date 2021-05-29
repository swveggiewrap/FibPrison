package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "vanish", requiresPlayer = true)
public class VanishCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (!PrisonPlayerUtils.getPrisonPlayer(player).isStaff()) {
			return;
		}

		if (PlayerUtils.vanished.contains(player)) {
			PlayerUtils.unvanish(player);
			player.sendMessage(ColorUtils.colorize("&eYou have been UNVANISHED."));
		} else {
			PlayerUtils.vanish(player);
			player.sendMessage(ColorUtils.colorize("&cYou have been VANISHED."));
		}
	}
}
