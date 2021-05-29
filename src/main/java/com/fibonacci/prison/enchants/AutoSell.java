package com.fibonacci.prison.enchants;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.api.ItemButton;
import com.fibonacci.prison.utils.ColorUtils;

import org.bukkit.Material;

public class AutoSell extends CE {

	public AutoSell() {
		super("autosell", "&f&lAutoSell", 1, 5000);
	}

	@Override
	public ItemButton getInfoButton() {
		return ItemButton.create(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ColorUtils.colorize(this.getName() + " &d&lMaxLevel = " + this.getMaxLevel())).setLoreLinesFromArray("", ColorUtils.colorize("&eAutoSell is self-explanatory. It sells"), ColorUtils.colorize("&eevery block you mine. No need to sell"), ColorUtils.colorize("&emanually anymore!")), (event) -> { ;
		});
	}

}
