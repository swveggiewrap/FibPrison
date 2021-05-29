package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.api.ItemButtonGUI;
import com.fibonacci.prison.api.ItemUtils;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class Cavity extends CE {

	public Cavity() {
		super("cavity", "&e&lCavity", 5, 7500);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eCavity works with Explosion to break"), ColorUtils.colorize("&eextra layers as you mine.")), (event) -> { ;
		});
	}

	@Override
	public ItemButton getBuyableButton(PrisonPlayer prisonPlayer, Player player, ItemButtonGUI gui) {
		return ItemButton.create(new ItemBuilder(Material.NETHER_STAR).setGlowing(true).setDisplayName(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel()).setLoreLinesFromArray("", "&eYour Level - " + getPDCLevel(player.getInventory().getItemInMainHand()), "&dLevel " + (getPDCLevel(player.getInventory().getItemInMainHand()) + 1) + " Cost - " + NumberUtils.doubleToSuffixedNumber(getNextLevelPrice(player.getInventory().getItemInMainHand())) + " Fibs"), (event) -> {
			if (!ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.EXPLOSION.getNamespace())) {
				player.sendMessage(ColorUtils.colorize("&7(&d&lEnchants&7) &cYou need to have Explosion Enchant to buy Cavity."));
				return;
			}

			if (getPDCLevel(player.getInventory().getItemInMainHand()) >= this.getMaxLevel()) {
				gui.addButton(event.getSlot(), getMaxedButton());
				return;
			}

			if (!prisonPlayer.canBuyEnchant(this, player)) {
				gui.addButton(event.getSlot(), getUnbuyableButton(player));
				return;
			}

			prisonPlayer.removeFibs(getNextLevelPrice(player.getInventory().getItemInMainHand()));
			player.sendMessage(ColorUtils.colorize("&7(&d&lEnchants&7) &eYou purchased 1x Level of " + this.getName() + " &efor " + NumberUtils.doubleToSuffixedNumber(getNextLevelPrice(player.getInventory().getItemInMainHand())) + " Fibs!"));
			ItemBuilder newPick = new ItemBuilder(player.getInventory().getItemInMainHand()).setGlowing(true).addCE(CustomEnchants.CAVITY, getPDCLevel(player.getInventory().getItemInMainHand()) + 1).setUnbreakable().addItemFlagsToItem(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
			player.getInventory().remove(player.getInventory().getItemInMainHand());
			player.getInventory().setItemInMainHand(newPick);
			player.updateInventory();
			if (getPDCLevel(player.getInventory().getItemInMainHand()) >= this.getMaxLevel()) {
				gui.addButton(event.getSlot(), getMaxedButton());
				return;
			}
			if (!prisonPlayer.canBuyEnchant(this, player)) {
				gui.addButton(event.getSlot(), getUnbuyableButton(player));
				return;
			}
			gui.addButton(event.getSlot(), getBuyableButton(prisonPlayer, player, gui));
		});
	}

}
