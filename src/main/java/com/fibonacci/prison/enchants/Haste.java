package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;

public class Haste extends CE {

	public Haste() {
		super("haste", "&7Haste", 10, 400);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eHaste performs the same as the Vanilla"), ColorUtils.colorize("&eenchant for Minecraft.")), (event) -> { ;
		});
	}

}
