package com.fibonacci.prison.items;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.enchants.CustomEnchants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class KitItems {

	public static ItemStack ENCHANT_SHARD = new ItemBuilder(Material.PRISMARINE_SHARD).setDisplayName("&6&lEnchant Shard").setLoreLinesFromArray("", "&7Perhaps you could craft something with this...").setGlowing(true).addPDCString("enchantshard", "enchantshard");
	public static ItemStack ENCHANT_VOUCHER = new ItemBuilder(Material.PAPER).setDisplayName("&6&lEnchant Voucher").setLoreLinesFromArray("", "&7Drag this onto a pickaxe to", "&7add a random enchant of +1 Level.").setGlowing(true).addPDCString("enchantvoucher", "enchantvoucher");

	public static ItemStack STARTER_PICKAXE = new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayName("&b&lStarter Pickaxe").addCE(CustomEnchants.EFFICIENCY, 5).addCE(CustomEnchants.DURABILITY, 5).addItemFlagsToItem(ItemFlag.HIDE_ATTRIBUTES);
	public static ItemStack STEAK = new ItemBuilder(Material.COOKED_BEEF, 64);
	public static ItemStack PVP_HELMET = new ItemBuilder(Material.DIAMOND_HELMET);
	public static ItemStack PVP_CHESTPLATE = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
	public static ItemStack PVP_LEGGINGS = new ItemBuilder(Material.DIAMOND_LEGGINGS);
	public static ItemStack PVP_BOOTS = new ItemBuilder(Material.DIAMOND_BOOTS);
	public static ItemStack PVP_SWORD = new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DURABILITY, 1).setGlowing(true).addItemFlagsToItem(ItemFlag.HIDE_ATTRIBUTES);

	public static ItemStack DIBBER_BOMB = new ItemBuilder(Material.FIRE_CHARGE).setGlowing(true).setDisplayName("&6&lDibber Bomb").setLoreLinesFromArray("", "&eExplodes a 3x3x3 block area.");
	public static ItemStack FIBBER_BOMB = new ItemBuilder(Material.FIRE_CHARGE).setGlowing(true).setDisplayName("&d&lFibber Bomb").setLoreLinesFromArray("", "&eExplodes a 6x6x6 block area.");

}
