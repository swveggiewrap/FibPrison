package com.fibonacci.prison.user;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.items.Keys;
import com.fibonacci.prison.rank.PrisonRank;
import com.fibonacci.prison.rank.Rank;
import com.fibonacci.prison.rank.RankUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.JedisUtils;
import com.fibonacci.prison.utils.NumberUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import com.fibonacci.prison.warps.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PrisonPlayerUtils {

	public static Map<String, PrisonPlayer> playerMap = new HashMap<>();

	public static void registerPrisonPlayer(String name, PrisonPlayer player) {
		playerMap.put(name, player);
	}

	public static void unregisterPrisonPlayer(String name) {
		playerMap.remove(name);
	}

	public static PrisonPlayer getPrisonPlayer(Player player) {
		return playerMap.get(player.getName());
	}

	public static void createPrisonPlayer(Player player) {
		if (PlayerUtils.hasPlayedBefore(player)) {
			PrisonPlayer prisonPlayer = new PrisonPlayer(player.getUniqueId().toString(), getPrisonPlayerMap(player.getUniqueId().toString()));
			prisonPlayer.getPrisonPlayerMap().remove("playername");
			prisonPlayer.getPrisonPlayerMap().put("playername", player.getName());
			registerPrisonPlayer(player.getName(), prisonPlayer);
			placePrisonPlayerInJedisCache(prisonPlayer);
		} else {
			PrisonPlayer prisonPlayer = new PrisonPlayer(player.getUniqueId().toString(), player.getName(), RankUtils.getFirstRank(), 0, 1000, 0, 60);
			registerPrisonPlayer(player.getName(), prisonPlayer);
			placePrisonPlayerInJedisCache(prisonPlayer);
			PlayerUtils.setFirstJoin(player);
			player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &e&lWelcome to &d&lFib&b&lPrison&e&l! &aThis server was coded by &d&lFibonacci122&a. Feel free to run around and test things out."));
			player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &dWant to stay up to date on new releases? Join our /discord"));
			player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &eYou have been given 1x Diamond Pickaxe and 1000 Fibs (Tokens). Use &d/ce&e to enchant with Fibs."));
			player.sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &eMine to earn Dibs (Money) in order to RankUp! Questions? Ask on Discord for the fastest response."));
			player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_PICKAXE).addItemFlagsToItem(ItemFlag.HIDE_ATTRIBUTES));
		}
	}

	public static void savePrisonPlayer(Player player) {
		PrisonPlayer prisonPlayer = getPrisonPlayer(player);
		JedisUtils.setHashMap("prisonplayer:" + player.getUniqueId().toString(), prisonPlayer.getPrisonPlayerMap());
		unregisterPrisonPlayer(player.getName());
	}

	public static void savePrisonPlayerWithoutUnregistering(Player player) {
		PrisonPlayer prisonPlayer = getPrisonPlayer(player);
		placePrisonPlayerInJedisCache(prisonPlayer);
	}

	public static PrisonRank getNextRank(PrisonPlayer prisonPlayer) {
		PrisonRank rank;
		int finalLevel;
		int finalPrestige;
		Rank letterRank;

		if (prisonPlayer.getRank().getLevel() > 0) {
			if (prisonPlayer.getRank().getPrestige() == 100) {
				if (prisonPlayer.getRank().getRank().getIndex() == 27) {
					finalLevel = prisonPlayer.getRank().getLevel() + 1;
					finalPrestige = 0;
					letterRank = Rank.A;
					rank = new PrisonRank(finalLevel, finalPrestige, letterRank);
					return rank;
				} else {
					return new PrisonRank(prisonPlayer.getRank().getLevel(), prisonPlayer.getRank().getPrestige(), prisonPlayer.getRank().getRank().getNext());
				}
			} else {
				if (prisonPlayer.getRank().getRank().getIndex() == 27) {
					finalPrestige = prisonPlayer.getRank().getPrestige() + 1;
					return new PrisonRank(prisonPlayer.getRank().getLevel(), finalPrestige, Rank.A);
				} else {
					return new PrisonRank(prisonPlayer.getRank().getLevel(), prisonPlayer.getRank().getPrestige(), prisonPlayer.getRank().getRank().getNext());
				}
			}
		}

		if (prisonPlayer.getRank().getPrestige() > 0) {
			if (prisonPlayer.getRank().getPrestige() == 100) {
				if (prisonPlayer.getRank().getRank().getIndex() == 27) {
					finalLevel = prisonPlayer.getRank().getLevel() + 1;
					finalPrestige = 0;
					letterRank = Rank.A;
					rank = new PrisonRank(finalLevel, finalPrestige, letterRank);
					return rank;
				} else {
					return new PrisonRank(0, prisonPlayer.getRank().getPrestige(), prisonPlayer.getRank().getRank().getNext());
				}
			} else {
				if (prisonPlayer.getRank().getRank().getIndex() == 27) {
					finalPrestige = prisonPlayer.getRank().getPrestige() + 1;
					return new PrisonRank(0, finalPrestige, Rank.A);
				} else {
					return new PrisonRank(0, prisonPlayer.getRank().getPrestige(), prisonPlayer.getRank().getRank().getNext());
				}
			}
		}

		if (prisonPlayer.getRank().getRank().getIndex() == 27) {
			return new PrisonRank(0, 1, Rank.A);
		}

		return new PrisonRank(0, 0, prisonPlayer.getRank().getRank().getNext());

	}

	public static void maxRank(PrisonPlayer player) {
		PrisonRank calculated = calculateMaxRank(player);
		PrisonRank previous = player.getRank();
		if (calculated == null) {
			Bukkit.getPlayer(player.getPlayerName()).sendMessage(ColorUtils.colorize("&7(&aRankup&7) &fYou cannot rankup any further."));
			return;
		}
		while (canRankup(player)) {
			rankupQuietly(player);
			Bukkit.getPlayer(player.getPlayerName()).getInventory().addItem(Keys.RANKUP_KEY);
		}
		if (!player.isStaff()) {
			Bukkit.getPlayer(player.getPlayerName()).setPlayerListName(ColorUtils.colorize(player.getRank().getPrefix() + " " + player.getPlayerName()));
		}
		Bukkit.broadcastMessage(ColorUtils.colorize("&7(&aRankup&7) " + player.getPlayerName() + " has ranked up from " + previous.getPrefix() + "to " + player.getRank().getPrefix()));
		Bukkit.getPlayer(player.getPlayerName()).sendMessage(ColorUtils.colorize("&7(&aRankup&7) &eYou ranked up from " + previous.getPrefix() + "to " + player.getRank().getPrefix()));

		PlayerUtils.refreshTabList(false);
	}

	public static double calculateMaxRankCost(PrisonPlayer player) {
		double cost = 0;
		PrisonPlayer prisonPlayer = new PrisonPlayer(player.getUuid(), player.getPlayerName(), player.getRank(), player.getBlocksMined(), player.getFibs(), player.getDibs(), player.getAutominerSecondsLeft());
		while (canRankup(prisonPlayer)) {
			cost += PrisonPlayerUtils.getNextRank(prisonPlayer).getPrice();
			rankupQuietly(prisonPlayer);
		}
		return cost;
	}

	public static PrisonRank calculateMaxRank(PrisonPlayer player) {
		PrisonPlayer prisonPlayer = new PrisonPlayer(player.getUuid(), player.getPlayerName(), player.getRank(), player.getBlocksMined(), player.getFibs(), player.getDibs(), player.getAutominerSecondsLeft());
		while (canRankup(prisonPlayer)) {
			prisonPlayer.removeDibs(prisonPlayer.getRank().getPrice());
			prisonPlayer.setRank(getNextRank(prisonPlayer));
		}

		if (player.getDibs() == prisonPlayer.getDibs()) {
			return null;
		}

		return prisonPlayer.getRank();

	}

	public static boolean canRankup(PrisonPlayer player) {
		return player.getDibs() >= player.getRank().getPrice();
	}

	public static void rankupPlayer(PrisonPlayer prisonPlayer) {
		PrisonRank rank = prisonPlayer.getRank();
		boolean teleportBack = false;
		boolean success = false;
		if (rank.getRank().getIndex() == 27) {
			teleportBack = true;
		}

		if (rank.getRank().getIndex() == 27 && rank.getPrestige() == 100) {
			teleportBack = true;
		}
		if (canRankup(prisonPlayer)) {
			rankupQuietly(prisonPlayer);
			Bukkit.getPlayer(prisonPlayer.getPlayerName()).sendMessage(ColorUtils.colorize("&7(&aRankup&7) &eYou ranked up from " + rank.getPrefix() + "&eto " + prisonPlayer.getRank().getPrefix()));
			Bukkit.broadcastMessage(ColorUtils.colorize("&7(&aRankup&7) &f" + prisonPlayer.getPlayerName() + "&7 has ranked up from " + rank.getPrefix() + "to " + prisonPlayer.getRank().getPrefix()));
			Bukkit.getPlayer(prisonPlayer.getPlayerName()).getInventory().addItem(Keys.RANKUP_KEY);
			success = true;
		} else {
			Bukkit.getPlayer(prisonPlayer.getPlayerName()).sendMessage(ColorUtils.colorize("&cYou need " + NumberUtils.doubleToSuffixedNumber(prisonPlayer.getRank().getPrice()) + " Dibs to rankup."));
		}
		PlayerUtils.refreshTabList(false);

		if (teleportBack && success) {
			Bukkit.getPlayer(prisonPlayer.getPlayerName()).teleport(WarpUtils.getWarp("spawn").getLocation());
		}

	}

	public static void rankupQuietly(PrisonPlayer prisonPlayer) {
		prisonPlayer.removeDibs(prisonPlayer.getRank().getPrice());
		prisonPlayer.setRank(getNextRank(prisonPlayer));
	}

	public static Map<String, String> getPrisonPlayerMap(String playerUUID) {
		return JedisUtils.getHashMap("prisonplayer:" + playerUUID);
	}

	public static void placePrisonPlayerInJedisCache(PrisonPlayer player) {
		JedisUtils.setHashMap("prisonplayer:" + player.getUuid(), player.getPrisonPlayerMap());
	}


}
