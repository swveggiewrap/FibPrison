package com.fibonacci.prison.scoreboard;

import com.comphenix.protocol.ProtocolLibrary;
import com.fibonacci.prison.Prison;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import com.fibonacci.prison.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class PlayerScoreboard {

	public static Map<String, Scoreboard> scoreboards = new HashMap<>();

	public static void registerScoreboard(Player player, Scoreboard scoreboard) {
		scoreboards.put(player.getName(), scoreboard);
	}

	public static void removeFromScoreboards(Player player) {
		scoreboards.remove(player.getName());
		removeScoreboard(player);
	}

	public static void updateScoreboard(Player player) {
		Scoreboard scoreboard = scoreboards.get(player.getName());
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

		Team rank = scoreboard.getTeam("rank");
		Team fibs = scoreboard.getTeam("fibs");
		Team dibs = scoreboard.getTeam("dibs");
		Team blocksMined = scoreboard.getTeam("blocksmined");
		Team progress = scoreboard.getTeam("progress");
		Team autominer = scoreboard.getTeam("autominer");

		rank.setPrefix(ColorUtils.colorize("&7Rank: " + prisonPlayer.getRank().getPrefix()));
		fibs.setPrefix(ColorUtils.colorize("&7Fibs: &a" + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getFibs())));
		dibs.setPrefix(ColorUtils.colorize("&7Dibs: &e" + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getDibs())));
		blocksMined.setPrefix(ColorUtils.colorize("&7Blocks Mined: &b" + NumberUtils.longToSuffixedNumber(prisonPlayer.getBlocksMined())));
		progress.setPrefix(PlayerUtils.getRankupProgressScoreboard(player));
		autominer.setPrefix(ColorUtils.colorize("&7AM: " + NumberUtils.amTimeToFormattedString(prisonPlayer.getAutominerSecondsLeft())));
	}

	public static void getNewPlayerScoreboard(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		Objective obj = scoreboard.registerNewObjective("sb", "dummy", ColorUtils.colorize("&7>&8> &d&lFib&b&lPrison &8<&7<"));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore(ColorUtils.colorize("&aWelcome, " + player.getName() + ".")).setScore(13);
		obj.getScore("").setScore(12);

		Team rank = scoreboard.registerNewTeam("rank");
		rank.addEntry(" ");
		rank.setPrefix(ColorUtils.colorize("&7Rank: " + prisonPlayer.getRank().getPrefix()));
		obj.getScore(" ").setScore(11);

		obj.getScore("  ").setScore(10);

		Team fibs = scoreboard.registerNewTeam("fibs");
		fibs.addEntry("   ");
		fibs.setPrefix(ColorUtils.colorize("&7Fibs: &a" + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getFibs())));
		obj.getScore("   ").setScore(9);

		obj.getScore("             ").setScore(8);

		Team dibs = scoreboard.registerNewTeam("dibs");
		dibs.addEntry("      ");
		dibs.setPrefix(ColorUtils.colorize("&7Dibs: &e" + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getDibs())));
		obj.getScore("      ").setScore(7);


		obj.getScore("        ").setScore(6);

		Team blocksMined = scoreboard.registerNewTeam("blocksmined");
		blocksMined.addEntry("         ");
		blocksMined.setPrefix(ColorUtils.colorize("&7Blocks Mined: &b" + NumberUtils.longToSuffixedNumber(prisonPlayer.getBlocksMined())));
		obj.getScore("         ").setScore(5);

		obj.getScore("          ").setScore(4);

		Team autominer = scoreboard.registerNewTeam("autominer");
		autominer.addEntry("                 ");
		autominer.setPrefix(ColorUtils.colorize("&7AM: " + NumberUtils.amTimeToFormattedString(prisonPlayer.getAutominerSecondsLeft())));
		obj.getScore("                 ").setScore(3);

		obj.getScore("                       ").setScore(2);

		Team progress = scoreboard.registerNewTeam("progress");
		progress.addEntry("           ");
		progress.setPrefix(PlayerUtils.getRankupProgressScoreboard(player));
		obj.getScore("           ").setScore(1);

		player.setScoreboard(scoreboard);
		registerScoreboard(player, scoreboard);
	}

	public static void removeScoreboard(Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Prison.getInstance(), () -> {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}, 10L);

	}

}
