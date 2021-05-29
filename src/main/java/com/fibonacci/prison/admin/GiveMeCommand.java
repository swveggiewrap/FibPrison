package com.fibonacci.prison.admin;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandInfo(name = "giveme", requiresPlayer = true)
public class GiveMeCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		if (!prisonPlayer.isStaff()) {
			return;
		}

		if (args.length == 0) {
			return;
		}

		if (args[0].equalsIgnoreCase("bombs")) {
			ItemBuilder builder = new ItemBuilder(KitItems.FIBBER_BOMB);
			ItemBuilder dib = new ItemBuilder(KitItems.DIBBER_BOMB);
			builder.setAmount(64);
			dib.setAmount(64);
			player.getInventory().addItem(builder);
			player.getInventory().addItem(dib);

		} else if (args[0].equalsIgnoreCase("am")) {
			prisonPlayer.addAMSeconds(300);
		}

	}
}
