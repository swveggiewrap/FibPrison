package com.fibonacci.prison.enchants;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.api.ItemButtonGUI;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
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

public class Durability extends CE {

	public Durability() {
		super("durability", "&7Durability", 50, 100);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eDurability allows you to mine more"), ColorUtils.colorize("&eblocks without your pickaxe breaking"), ColorUtils.colorize("&eso fast. Buy ShatterProof to never"), ColorUtils.colorize("&eworry about your pickaxe breaking again!"), "",  ColorUtils.colorize("&cDurability will warn you to repair your pickaxe,"), ColorUtils.colorize("&cbut will NOT repair it automatically.")), (event) -> { ;
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
			ItemBuilder newPick = new ItemBuilder(player.getInventory().getItemInMainHand()).setGlowing(true).addCE(CustomEnchants.DURABILITY, getPDCLevel(player.getInventory().getItemInMainHand()) + 1).addEnchant(Enchantment.DURABILITY, getPDCLevel(player.getInventory().getItemInMainHand()) + 1).addItemFlagsToItem(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
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
		meta.addEnchant(Enchantment.DURABILITY, level, true);
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
