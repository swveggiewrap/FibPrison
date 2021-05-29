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
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CE extends Enchantment {

	private final String name;
	private final int maxLevel;
	private final int priceIncreasePerLevel;
	private final String namespace;
	public static Map<String, CE> enchantMap = new HashMap<>();
	public static List<CE> enchantList = new ArrayList<>();

	public CE(String namespace, String name, int max, int priceIncreasePerLevel) {
		super(NamespacedKey.minecraft(namespace));
		this.name = ColorUtils.colorize(name);
		this.namespace = namespace;
		this.maxLevel = max;
		this.priceIncreasePerLevel = priceIncreasePerLevel;
		enchantMap.put(namespace, this);
		enchantList.add(this);
	}

	public String getNamespace() {
		return namespace;
	}

	public ItemButton getInfoButton() {
		return null;
	}

	public ItemButton getMaxedButton() {
		return ItemButton.create(new ItemBuilder(Material.IRON_BARS).setDisplayName(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel()).setLoreLinesFromArray("", "&dYour Level - MAXED (" + this.getMaxLevel() + ")"), (event) -> {
			((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0);
		});
	}

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
			ItemBuilder newPick = new ItemBuilder(player.getInventory().getItemInMainHand()).setGlowing(true).addCE(this, getPDCLevel(player.getInventory().getItemInMainHand()) + 1);
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

	public ItemButton getButton(PrisonPlayer prisonPlayer, Player player, ItemButtonGUI gui) {

		if (getPDCLevel(player.getInventory().getItemInMainHand()) >= this.getMaxLevel()) {
			return getMaxedButton();
		}

		if (!prisonPlayer.canBuyEnchant(this, player)) {
			return getUnbuyableButton(player);
		}

		return getBuyableButton(prisonPlayer, player, gui);
	}

	public ItemButton getUnbuyableButton(Player player) {
		return ItemButton.create(new ItemBuilder(Material.BARRIER).setDisplayName(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel()).setLoreLinesFromArray("", ColorUtils.colorize("&cYou do not have enough Fibs"), ColorUtils.colorize("&cto buy this Enchant."), "", "&cNext Level Cost = " + NumberUtils.doubleToSuffixedNumber(getNextLevelPrice(player.getInventory().getItemInMainHand()))), (event) -> {
			((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0);
		});
	}


	public double getNextLevelPrice(ItemStack itemStack) {
		return priceIncreasePerLevel * (getPDCLevel(itemStack) + 1);
	}

	public double getPreviousPrice(ItemStack itemStack) {
		return priceIncreasePerLevel * (getPDCLevel(itemStack) - 1);
	}

	public double getPrice() {
		return priceIncreasePerLevel;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	public int getPDCLevel(ItemStack itemStack) {
		if (itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), "enchant_" + this.getNamespace()), PersistentDataType.INTEGER)) {
			return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Prison.getInstance(), "enchant_" + this.getNamespace()), PersistentDataType.INTEGER);
		} else {
			return 0;
		}
	}

	public ItemStack addToItem(ItemStack stack, int level) {
		stack.addEnchantment(this, level);
		ItemMeta meta = stack.getItemMeta();
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
