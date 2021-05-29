package com.fibonacci.prison.staff;

import com.fibonacci.prison.utils.ColorUtils;

public enum StaffPrefix {

	OWNER("&7[&d&lOwner&7] &f"),
	HELPER("&7[&aHelper&7] &f"),
	MOD("&7[&6Mod&7] &f");

	String prefix;

	public String getPrefix() {
		return ColorUtils.colorize(prefix);
	}

	StaffPrefix(String prefix) {
		this.prefix = ColorUtils.colorize(prefix);
	}

}
