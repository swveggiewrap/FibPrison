package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;

public class NightVision extends CE {

	public NightVision() {
		super("nightvision", "&f&lNightVision", 1, 500);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eBrightens the area around you permanently.")), (event) -> { ;
		});
	}

}
