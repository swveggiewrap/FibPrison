package com.fibonacci.prison.api;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.enchants.CE;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class ItemUtils {

	private static ItemStack rename(ItemStack itemStack, String name) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(ColorUtils.colorize(name));
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack setName(ItemStack stack, String name) {
		return rename(stack, name);
	}

	private static ItemStack setAmount(ItemStack itemStack, int amount) {
		if (amount > 64) {
			throw new IllegalArgumentException("Amount must be less than or equal to 64.");
		}
		itemStack = itemStack.clone();
		itemStack.setAmount(amount);
		return itemStack;
	}

	public static ItemStack setCount(ItemStack itemStack, int amount) {
		return setAmount(itemStack, amount);
	}

	private static ItemStack addLoreLine(ItemStack itemStack, String line) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore;
		if (meta.getLore() == null) {
			lore = new ArrayList<>();
		} else {
			lore = meta.getLore();
		}
		lore.add(ColorUtils.colorize(line));
		meta.setLore(lore);
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack addLineToLore(ItemStack itemStack, String line) {
		return addLoreLine(itemStack, line);
	}

	private static ItemStack setFullLore(ItemStack itemstack, List<String> lore) {
		ItemMeta meta = itemstack.getItemMeta();
		List<String> loreLines = new ArrayList<>();
		for (String str : lore) {
			loreLines.add(ColorUtils.colorize(str));
		}
		meta.setLore(lore);
		ItemStack clone = itemstack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack setLoreFromList(ItemStack itemStack, List<String> lore) {
		return setFullLore(itemStack, lore);
	}

	private static ItemStack unbreakable(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(true);
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack setUnbreakable(ItemStack itemStack) {
		return unbreakable(itemStack);
	}

	private static ItemStack addEnchantToItem(ItemStack itemStack, Enchantment enchantment, int level) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addEnchant(enchantment, level, true);
		if (level == 0) {
			meta.removeEnchant(enchantment);
		}
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack addEnchant(ItemStack itemStack, Enchantment enchantment, int level) {
		return addEnchantToItem(itemStack, enchantment, level);
	}

	public static ItemStack addCustomEnchant(ItemStack itemStack, CE enchantment, int level) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), "enchant_" + enchantment.getNamespace()), PersistentDataType.INTEGER, level);


		List<String> lore = new ArrayList<>();
		if (meta.hasLore()) {
			lore = meta.getLore();
			if (lore.contains(ColorUtils.colorize(enchantment.getName() + " " + (level - 1)))) {
				lore.remove(ColorUtils.colorize(enchantment.getName() + " " + (level - 1)));
			}
		}
		lore.add(ColorUtils.colorize(enchantment.getName() + " " + level));
		meta.setLore(lore);
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	private static ItemStack addItemFlagsToItem(ItemStack itemStack, ItemFlag... flags) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flags);
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack addItemFlags(ItemStack itemStack, ItemFlag... flags) {
		return addItemFlagsToItem(itemStack, flags);
	}

	public static int count(Inventory inventory, ItemStack itemStack, BiPredicate<ItemStack, ItemStack> comparison) {
		int count = 0;
		for (ItemStack i : inventory) {
			if (comparison.test(itemStack, i)) {
				count += i.getAmount();
			}
		}
		return count;
	}

	private static boolean remove(Inventory inv, ItemStack item, int amount, BiPredicate<ItemStack, ItemStack> comparison) {
		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < contents.length && amount > 0; i++) {
			if (!comparison.test(item, contents[i])) {
				continue;
			}
			if (amount >= contents[i].getAmount()) {
				amount -= contents[i].getAmount();
				contents[i] = null;
				if (amount == 0) {
					inv.setContents(contents);
					return true;
				}
				continue;
			}
			contents[i].setAmount(contents[i].getAmount() - amount);
			inv.setContents(contents);
			return true;
		}
		inv.setContents(contents);
		return false;
	}

	public static boolean remove(Inventory inv, ItemStack item, int amount) {
		return remove(inv, item, amount, ItemStack::isSimilar);
	}

	private static int countAndRemove(Inventory inv, ItemStack item, int max, BiPredicate<ItemStack, ItemStack> comparison) {
		int count = count(inv, item, comparison);
		count = Math.min(max, count);
		remove(inv, item, count, comparison);
		return count;
	}

	public static int countAndRemove(Inventory inventory, ItemStack itemStack, int max) {
		return countAndRemove(inventory, itemStack, max, ItemStack::isSimilar);
	}

	public static ItemStack setGlowing(ItemStack itemStack, boolean glow) {
		ItemMeta meta = itemStack.getItemMeta();
		if (glow) {
			meta.addEnchant(Enchantment.DURABILITY, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack setPDCKeyEnchant(ItemStack itemStack, String name, int value) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), "enchant_" + name), PersistentDataType.INTEGER, value);
		ItemStack clone = itemStack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static boolean hasPDCEnchant(ItemStack itemStack, String enchantName) {
		ItemMeta meta = itemStack.getItemMeta();
		if (meta == null) {
			return false;
		}
		return meta.getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), "enchant_" + enchantName), PersistentDataType.INTEGER);
	}

	public static int getCELevel(ItemStack itemStack, String enchantName) {
		ItemMeta meta = itemStack.getItemMeta();
		if (meta.getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), "enchant_" + enchantName), PersistentDataType.INTEGER)) {
			return meta.getPersistentDataContainer().get(new NamespacedKey(Prison.getInstance(), "enchant_" + enchantName), PersistentDataType.INTEGER);
		} else {
			return 0;
		}
	}

	public static ItemStack setKeysPDC(ItemStack stack, String namespace) {
		ItemMeta meta = stack.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), "key"), PersistentDataType.STRING, namespace);
		ItemStack clone = stack.clone();
		clone.setItemMeta(meta);
		return clone;
	}

	public static ItemStack addPDCString(ItemStack stack, String keyName, String key) {
		ItemMeta meta = stack.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), keyName), PersistentDataType.STRING, key);
		ItemStack clone = stack.clone();
		clone.setItemMeta(meta);
		return clone;
	}




}
