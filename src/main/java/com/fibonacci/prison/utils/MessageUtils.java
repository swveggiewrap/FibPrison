package com.fibonacci.prison.utils;

import com.fibonacci.prison.rank.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

	public static String getPrefix(int fiblevel, int prestige, Rank rank) {
		StringBuilder builder = new StringBuilder("");

		if (fiblevel > 0) {
			builder.append("&d&l[L" + fiblevel + "] &f");
		}

		if (prestige > 0) {
			builder.append("&e&l[P" + prestige + "] &f");
		}

		builder.append(rank.getPrefix() + " ");
		return ColorUtils.colorize(builder.toString());
	}

	public static String getItemName(ItemStack itemStack) {
		return WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));
	}

	public static String PRISON_HEADER_FOOTER = ColorUtils.colorize("&d---------- &7(&d&lFib&b&lPrison&7) &d----------");

	public static void sendPrisonFormattedMsg(Player player, String... lines) {
		List<String> strings = new ArrayList<>();
		strings.add(PRISON_HEADER_FOOTER);
		for (String string : lines) {
			strings.add(ColorUtils.colorize(string));
		}
		strings.add(PRISON_HEADER_FOOTER);

		for (String string : strings) {
			sendCenteredMessage(player, ColorUtils.colorize(string));
		}
	}

	public static void sendPrisonFormattedMsg(Player player, List<String> lines) {
		List<String> strings = new ArrayList<>();
		strings.add(PRISON_HEADER_FOOTER);
		for (String string : lines) {
			strings.add(ColorUtils.colorize(string));
		}
		strings.add(PRISON_HEADER_FOOTER);

		for (String string : strings) {
			sendCenteredMessage(player, ColorUtils.colorize(string));
		}
	}

	public static void sendCenteredSpigotMessage(Player player, String message, String hover, String command) {
		if (message == null || message.equals("")) {
			player.spigot().sendMessage(new ComponentBuilder().append("").create());
		}
		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if(previousCode) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		BaseComponent[] component = new ComponentBuilder().appendLegacy(ChatColor.translateAlternateColorCodes('&', sb.toString() + message)).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text(hover))).create();
		player.spigot().sendMessage(component);

	}

	private final static int CENTER_PX = 154;

	public static void sendCenteredMessage(Player player, String message) {
		if (message == null || message.equals("")) {
			player.sendMessage("");
		}
		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if(previousCode) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		player.sendMessage(sb.toString() + message);
	}



}
