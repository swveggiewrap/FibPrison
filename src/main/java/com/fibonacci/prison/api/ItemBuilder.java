package com.fibonacci.prison.api;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.enchants.CE;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder extends ItemStack {

	public ItemBuilder(Material material) {
		super(material, 1);
	}

	public ItemBuilder(Material material, int amount) {
		super(material, amount);
	}

	public ItemBuilder(ItemStack itemStack) {
		super(itemStack);
	}

	public ItemBuilder setDisplayName(String name) {
		return new ItemBuilder(ItemUtils.setName(this, name));
	}

	public ItemBuilder setCount(int amount) {
		return new ItemBuilder(ItemUtils.setCount(this, amount));
	}

	public ItemBuilder addLore(String line) {
		return new ItemBuilder(ItemUtils.addLineToLore(this, line));
	}

	public ItemBuilder setLoreLines(List<String> lore) {
		return new ItemBuilder(ItemUtils.setLoreFromList(this, lore));
	}

	public ItemBuilder setLoreLinesFromArray(String... lore) {
		List<String> list = new ArrayList<>();
		for (String str : lore) {
			str = ColorUtils.colorize(str);
			list.add(str);
		}
		return new ItemBuilder(ItemUtils.setLoreFromList(this, list));
	}

	public ItemBuilder setUnbreakable() {
		return new ItemBuilder(ItemUtils.setUnbreakable(this));
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		return new ItemBuilder(ItemUtils.addEnchant(this, enchantment, level)).addItemFlagsToItem(ItemFlag.HIDE_ENCHANTS);
	}

	public ItemBuilder addCE(CE enchantment, int level) {
		return new ItemBuilder(ItemUtils.addCustomEnchant(this, enchantment, level));
	}

	public ItemBuilder addItemFlagsToItem(ItemFlag... flags) {
		return new ItemBuilder(ItemUtils.addItemFlags(this, flags));
	}

	public ItemBuilder setGlowing(boolean glow) {
		return new ItemBuilder(ItemUtils.setGlowing(this, glow));
	}

	public ItemBuilder addPDC(String key) {
		return new ItemBuilder(ItemUtils.setKeysPDC(this, key));
	}

	public ItemBuilder addAllEnchants() {
		ItemStack stack = new ItemStack(this);
		CE.enchantList.forEach((enchant) -> {
			enchant.addToItem(stack, enchant.getMaxLevel());
		});
		return new ItemBuilder(stack).setGlowing(true);
	}

	public ItemBuilder addPDCString(String keyName, String key) {
		return new ItemBuilder(ItemUtils.addPDCString(this, keyName, key));
	}





}
