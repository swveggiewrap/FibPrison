package com.fibonacci.prison.utils;

import com.fibonacci.prison.misc.BlockValue;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SellUtils {

	public static void sellHand(Player player) {
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		if (BlockValue.fromMaterial(itemStack.getType()) == null) {
			player.sendMessage(ColorUtils.colorize("&cThis item cannot be sold with /sellhand."));
		} else {
			double total = getSellValue(itemStack, PrisonPlayerUtils.getPrisonPlayer(player));
			player.sendMessage(ColorUtils.colorize("&eYou sold " + itemStack.getAmount() + "x " + MessageUtils.getItemName(itemStack) + "&e for " + NumberUtils.doubleToSuffixedNumber(total) + " Dibs."));
			PrisonPlayerUtils.getPrisonPlayer(player).addDibs(total);
			player.getInventory().setItemInMainHand(null);
		}
	}

	public static void sellAll(Player player) {
		double total = 0;
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

		for (ItemStack itemStack : player.getInventory().getContents()) {
			if (itemStack == null || itemStack.getType().isAir()) {
				continue;
			}

			if (BlockValue.fromMaterial(itemStack.getType()) == null) {
				continue;
			}

			total += getSellValue(itemStack, prisonPlayer);
			player.getInventory().remove(itemStack);
		}

		if (total == 0) {
			player.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cYou do not have any SELLABLE blocks/items in your inventory!"));
		} else {
			prisonPlayer.addDibs(total);
			player.sendMessage(ColorUtils.colorize("&eYou sold all of the SELLABLE items in your inventory for " + NumberUtils.doubleToSuffixedNumber(total) + " Dibs."));
		}
	}

	public static double getSellValue(ItemStack stack, PrisonPlayer player) {
		double amount = stack.getAmount();
		double value = BlockValue.fromMaterial(stack.getType()).getBasePrice();
		return amount * value * player.getRank().getSellMultiplier();
	}

	public static void autoSell(PrisonPlayer player, Material material) {
		player.addDibs(BlockValue.fromMaterial(material).getBasePrice() * player.getRank().getSellMultiplier());
	}

	public static void autoSell(PrisonPlayer player, ItemStack stack) {
		player.addDibs(BlockValue.fromMaterial(stack.getType()).getBasePrice() * stack.getAmount() * player.getRank().getSellMultiplier());
	}

	public static void exchangeXP(Player player, int level) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		prisonPlayer.addDibs(prisonPlayer.getRank().getSellMultiplier() * 25 * level);
		player.setLevel(player.getLevel() - 25);
	}



}
