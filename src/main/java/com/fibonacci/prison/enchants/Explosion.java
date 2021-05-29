package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;

public class Explosion extends CE {

	public Explosion() {
		super("explosion", "&dExplosion", 10, 2500);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eExplosion randomly explodes blocks around"), ColorUtils.colorize("&ethe block you mine. Increase in levels"), ColorUtils.colorize("&ewill increase explosion proc rate.")), (event) -> { ;
		});
	}



}
