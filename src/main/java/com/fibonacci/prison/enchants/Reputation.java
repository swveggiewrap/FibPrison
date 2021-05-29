package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Material;

public class Reputation extends CE {

	public Reputation() {
		super("reputation", "&c&lReputation", 25, 100);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eAllows you to exchange experience for"), ColorUtils.colorize("&eextra &dDibs&e.")), (event) -> { ;
		});
	}

}
