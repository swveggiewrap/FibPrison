package com.fibonacci.prison.enchants;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.api.ItemButtonGUI;
import com.fibonacci.prison.api.ItemUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

public class CEUtils {

	public static boolean hasEnchant(CE customEnchant, ItemStack stack) {
		if (stack.getItemMeta() == null) {
			return false;
		}

		return stack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), "enchant_" + customEnchant.getNamespace()), PersistentDataType.INTEGER);
	}

	public static void removeAllEnchants(Player player) {
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.setFlying(false);
		player.setAllowFlight(false);
		Prison.getInstance().hungerPlayers.remove(player);
		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
	}

	public static void checkEnchants(Player player, int slot) {
		ItemStack itemStack = player.getInventory().getItem(slot);
		if (itemStack == null) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			player.setFlying(false);
			player.setAllowFlight(false);
			Prison.getInstance().hungerPlayers.remove(player);
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
			return;
		}
		if (itemStack.getType() != Material.DIAMOND_PICKAXE) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			player.setFlying(false);
			player.setAllowFlight(false);
			Prison.getInstance().hungerPlayers.remove(player);
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
			return;
		}

		if (CEUtils.hasEnchant(CustomEnchants.NIGHT_VISION, itemStack)) {
			player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, 1));
		} else {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}

		if (CEUtils.hasEnchant(CustomEnchants.FLY, itemStack)) {
			player.setAllowFlight(true);
		} else {
			player.setAllowFlight(false);
		}

		if (CEUtils.hasEnchant(CustomEnchants.SATISFIED, itemStack)) {
			Prison.getInstance().hungerPlayers.add(player);
		} else {
			Prison.getInstance().hungerPlayers.remove(player);
		}

		if (CEUtils.hasEnchant(CustomEnchants.HASTE, itemStack)) {
			player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(Integer.MAX_VALUE, ItemUtils.getCELevel(itemStack, CustomEnchants.HASTE.getNamespace())));
		} else {
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}

	}

	public static ItemButtonGUI getCEInfoGUI() {
		ItemButtonGUI buttonGUI = new ItemButtonGUI(Bukkit.createInventory(null, 36, ColorUtils.colorize("&lCustomEnchant Info")));
		int[] slotsAvailable = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
		int count = 0;
		for (CE ce : CE.enchantList) {
			int slot = slotsAvailable[count];
			buttonGUI.addButton(slot, ce.getInfoButton());
			count++;
		}
		buttonGUI.fillEmptySpace();
		return buttonGUI;
	}

	public static ItemButtonGUI getCEGUI(Player player) {
		ItemButtonGUI buttonGUI = new ItemButtonGUI(Bukkit.createInventory(null, 36, ColorUtils.colorize("&lCustomEnchant Menu")));
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		int[] slotsAvailable = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
		int count = 0;
		for (CE ce : CE.enchantList) {
			int slot = slotsAvailable[count];
			buttonGUI.addButton(slot, ce.getButton(prisonPlayer, player, buttonGUI));
			/*if (ce.getPDCLevel(player.getInventory().getItemInMainHand()) >= ce.getMaxLevel()) {
				buttonGUI.addButton(slot, ce.getMaxedButton());
			} else {
				if (prisonPlayer.canBuyEnchant(ce, player)) {
					buttonGUI.addButton(slot, ce.getBuyableButton(prisonPlayer, player, buttonGUI));
				} else {
					buttonGUI.addButton(slot, ce.getUnbuyableButton(player));
				}
			}*/
			count++;
		}
		buttonGUI.fillEmptySpace();
		return buttonGUI;
	}


}
