package com.fibonacci.prison.items;

import com.fibonacci.prison.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AdminItems {

	public static ItemStack MINE_TOOL = new ItemBuilder(Material.STICK).setGlowing(true).setLoreLinesFromArray("", "&fLeft click to set Location 1", "&fRight click to set Location 2").setDisplayName("&d&lMine Super-Tool");


}
