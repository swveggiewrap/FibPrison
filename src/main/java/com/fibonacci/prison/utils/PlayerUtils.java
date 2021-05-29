package com.fibonacci.prison.utils;

import com.comphenix.protocol.PacketType;
import com.fibonacci.prison.Prison;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerUtils {

	public static List<Player> vanished = new ArrayList<>();

	public static boolean hasPlayedBefore(Player player) {
		return JedisUtils.getValue("firstjoin:" + player.getUniqueId().toString()) != null;
	}

	public static boolean hasPlayedBefore(String uuid) {
		return JedisUtils.getValue("firstjoin:" + getDashedUUID(uuid)) != null;
	}

	public static String getFirstJoinDate(Player player) {
		long firstJoin = Long.parseLong(JedisUtils.getValue("firstjoin:" + player.getUniqueId().toString()));
		Date date = new Date(firstJoin);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.format(date);
	}

	public static void setFirstJoin(Player player) {
		long firstJoin = System.currentTimeMillis();
		JedisUtils.setKeyValue("firstjoin:" + player.getUniqueId().toString(), String.valueOf(firstJoin));
	}

	public static String getPlayerUUIDIfPlayedBefore(String playerName) {
		String uuid = "";
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
			InputStreamReader reader = new InputStreamReader(url.openStream());
			uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (hasPlayedBefore(uuid)) {
			return getDashedUUID(uuid);
		} else {
			return "";
		}
	}

	public static String getDashedUUID(String uuid) {
		StringBuilder sb = new StringBuilder(uuid);
		sb.insert(8, "-");
		sb = new StringBuilder(sb.toString());
		sb.insert(13, "-");
		sb = new StringBuilder(sb.toString());
		sb.insert(18, "-");
		sb = new StringBuilder(sb.toString());
		sb.insert(23, "-");
		return sb.toString();
	}

	public static void refreshTabList(boolean removePlayer) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setPlayerListHeader(ColorUtils.colorize("&e&lWelcome to &d&lFib&b&lPrison&e&l!\n&6&nConnect with the community! &d&l/discord\n"));
			if (removePlayer) {
				player.setPlayerListFooter(ColorUtils.colorize("\n&f&lOnline Players: " + (Bukkit.getOnlinePlayers().size() - 1)));
			} else {
				player.setPlayerListFooter(ColorUtils.colorize("\n&f&lOnline Players: " + Bukkit.getOnlinePlayers().size()));
				if (PrisonPlayerUtils.getPrisonPlayer(player).isStaff()) {
					player.setPlayerListName(ColorUtils.colorize(PrisonPlayerUtils.getPrisonPlayer(player).getStaffPrefix() + player.getName()));
				} else {
					player.setPlayerListName(ColorUtils.colorize(PrisonPlayerUtils.getPrisonPlayer(player).getRank().getPrefix() + player.getName()));
				}
			}

		}
	}

	public static boolean isStaff(String uuid) {
		boolean staff = false;
		PrisonPlayer player = new PrisonPlayer(uuid, PrisonPlayerUtils.getPrisonPlayerMap(uuid));

		if (player.isStaff()) {
			staff = true;
		}
		return staff;
	}

	public static String getRankupProgressScoreboard(Player player) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		double progress = prisonPlayer.getDibs() / prisonPlayer.getRank().getPrice();
		if (progress >= 1) {
			return ColorUtils.colorize("&eProgress = 100%");
		} else {
			return ColorUtils.colorize("&cProgress = " + (int) Math.floor(progress * 100) + "%");
		}
	}

	public static void unvanish(Player player) {
		Bukkit.broadcastMessage(ColorUtils.colorize("&e&l[+] &d" + player.getName()));
		if (player.getName().equalsIgnoreCase("Fibonacc122")) {
			Bukkit.broadcastMessage(ColorUtils.colorize("&d&lYou are late to Fibonacci122's class!"));
		}

		for (Player online : Bukkit.getOnlinePlayers()) {
			online.showPlayer(Prison.getInstance(), player);
		}

		vanished.remove(player);
		PlayerUtils.refreshTabList(false);

	}

	public static void vanish(Player player) {
		Bukkit.broadcastMessage(ColorUtils.colorize("&c&l[-] &d" + player.getName()));
		if (player.getName().equalsIgnoreCase("Fibonacci122")) {
			Bukkit.broadcastMessage(ColorUtils.colorize("&d&lFibonacci122 went away to code or sleep."));
		}

		for (Player online : Bukkit.getOnlinePlayers()) {
			online.hidePlayer(Prison.getInstance(), player);
		}

		PlayerUtils.refreshTabList(true);
		vanished.add(player);
	}

	public static void vanishNewConnection(Player newPlayer) {
		if (vanished.isEmpty()) {
			return;
		}

		for (Player player : vanished) {
			newPlayer.hidePlayer(Prison.getInstance(), player);
		}

	}


}
