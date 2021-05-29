package com.fibonacci.prison.commands;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.rank.RankUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.MessageUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "reset", requiresPlayer = true)
public class ResetCommand extends FibCommand {

	public List<Player> resetConfirmationList = new ArrayList<>();

	@Override
	public void execute(Player player, String[] args) {
		if (resetConfirmationList.contains(player)) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("cancel")) {
					resetConfirmationList.remove(player);
					player.sendMessage(ColorUtils.colorize("&a&lYou have &c&lCANCELLED &a&lyour reset request."));
				} else if (args[0].equalsIgnoreCase("confirm")) {
					resetConfirmationList.remove(player);
					player.sendMessage(ColorUtils.colorize("&aYou have &e&lCONFIRMED &ayour reset request. &a&nResetting..."));
					PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
					prisonPlayer.setRank(RankUtils.getFirstRank());
					prisonPlayer.setFibs(1000);
					prisonPlayer.setBlocksMined(0);
					prisonPlayer.setDibs(0);
					player.getInventory().clear();
					MessageUtils.sendPrisonFormattedMsg(player, "&eYou have SUCCESSFULLY reset your progress.", "&dDibs, and BlocksMined have been set to 0.", "&dFibs have been set to 1000.", "&6Your rank is now " + prisonPlayer.getRank().getPrefix());
					player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &eYou have been given 1x Diamond Pickaxe."));
					player.teleport(WarpUtils.getWarp("lobby").getLocation());
					player.getInventory().addItem(KitItems.STARTER_PICKAXE, KitItems.STEAK);
					player.teleport(WarpUtils.getWarp("spawn").getLocation());
				} else {
					player.sendMessage(ColorUtils.colorize("&aYou must either &e&lCONFIRM or &c&lCANCEL &athe reset command. /reset <confirm|cancel>"));
				}
			}

		} else {
			MessageUtils.sendPrisonFormattedMsg(player, "&c&lAre you SURE you want to reset?", "", "&c&lYou will lose ALL progress and start from scratch.", "", "&d&lType &e/reset confirm &d&lif you want to reset");
			resetConfirmationList.add(player);
		}
	}
}
