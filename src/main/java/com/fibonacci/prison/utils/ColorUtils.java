package com.fibonacci.prison.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

}
