package com.fibonacci.prison.enchants;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.api.ItemButtonGUI;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Efficiency extends CE {

	public Efficiency() {
		super("efficiency", "&7Efficiency", 50, 100);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eEfficiency mines blocks faster."), ColorUtils.colorize("&eEvery 10 levels, you will start"), ColorUtils.colorize("&eto mine blocks faster!")), (event) -> { ;
		});
	}

	@Override
	public ItemButton getBuyableButton(PrisonPlayer prisonPlayer, Player player, ItemButtonGUI gui) {
		return ItemButton.create(new ItemBuilder(Material.NETHER_STAR).setGlowing(true).setDisplayName(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel()).setLoreLinesFromArray("", "&eYour Level - " + getPDCLevel(player.getInventory().getItemInMainHand()), "&dLevel " + (getPDCLevel(player.getInventory().getItemInMainHand()) + 1) + " Cost - " + NumberUtils.doubleToSuffixedNumber(getNextLevelPrice(player.getInventory().getItemInMainHand())) + " Fibs"), (event) -> {
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
			ItemBuilder newPick = new ItemBuilder(player.getInventory().getItemInMainHand()).setGlowing(true).addCE(CustomEnchants.EFFICIENCY, getPDCLevel(player.getInventory().getItemInMainHand()) + 1).addEnchant(Enchantment.DIG_SPEED, getPDCLevel(player.getInventory().getItemInMainHand()) + 1).addItemFlagsToItem(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
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

	@Override
	public ItemStack addToItem(ItemStack stack, int level) {
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, level, true);
		meta.getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), "enchant_" + this.getNamespace()), PersistentDataType.INTEGER, level);

		List<String> lore = new ArrayList<>();
		if (meta.hasLore()) {
			lore = meta.getLore();
			if (lore.contains(ColorUtils.colorize(this.getName() + " " + (level - 1)))) {
				lore.remove(ColorUtils.colorize(this.getName() + " " + (level - 1)));
			}
		}
		lore.add(ColorUtils.colorize(this.getName() + " " + level));
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}


}
