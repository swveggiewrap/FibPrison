package com.fibonacci.prison.items;

import com.fibonacci.prison.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Keys {

	public static ItemStack RANKUP_KEY = new ItemBuilder(Material.TRIPWIRE_HOOK).setGlowing(true).setDisplayName("&6&lRankup Key").setLoreLinesFromArray("&dRedeem by right clicking the ground.").addPDC("rankupkey");

	public static ItemStack MINE_KEY = new ItemBuilder(Material.TRIPWIRE_HOOK).setGlowing(true).setDisplayName("&7&lMine Key").setLoreLinesFromArray("&dRedeem by right clicking the ground.").addPDC("minekey");

}
