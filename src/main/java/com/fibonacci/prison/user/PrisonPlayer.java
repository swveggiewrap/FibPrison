package com.fibonacci.prison.user;

import com.fibonacci.prison.enchants.CE;
import com.fibonacci.prison.rank.PrisonRank;
import com.fibonacci.prison.rank.RankRegistry;
import com.fibonacci.prison.staff.StaffPrefix;
import com.fibonacci.prison.utils.MiscUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PrisonPlayer {

	public final String uuid;
	public String playerName;
	public PrisonRank rank;
	public long blocksMined;
	public double fibs;
	public double dibs;
	public int staffIndex;
	public long autominerSeconds;
	public boolean isAutomining;
	/* 0 = not staff
	1 = helper
	2 = mod
	3 = owner
	 */
	public PrisonPlayer(final String uuid, String playerName, PrisonRank rank, long blocksMined, double fibs, double dibs, long autominerSeconds) {
		this.uuid = uuid;
		this.playerName = playerName;
		this.rank = rank;
		this.blocksMined = blocksMined;
		this.fibs = fibs;
		this.dibs = dibs;
		this.staffIndex = 0;
		this.autominerSeconds = 60;
		this.isAutomining = false;
	}

	public PrisonPlayer(final String uuid, Map<String, String> jedisMap) {
		this.uuid = uuid;
		this.playerName = jedisMap.get("playername");
		this.rank = new PrisonRank(Integer.parseInt(jedisMap.get("fiblevel")), Integer.parseInt(jedisMap.get("prestige")), RankRegistry.integerRankMap.get(Integer.parseInt(jedisMap.get("rank"))));
		this.blocksMined = Long.parseLong(jedisMap.get("blocksmined"));
		this.fibs = Double.parseDouble(jedisMap.get("fibs"));
		this.dibs = Double.parseDouble(jedisMap.get("dibs"));
		jedisMap.putIfAbsent("staff", "0");
		this.staffIndex = Integer.parseInt(jedisMap.get("staff"));
		jedisMap.putIfAbsent("autominer", "60");
		this.autominerSeconds = Long.parseLong(jedisMap.get("autominer"));
		this.isAutomining = false;
	}

	public Map<String, String> getPrisonPlayerMap() {
		Map<String, String> prMap = new HashMap<>();
		prMap.put("playername", playerName);
		prMap.put("fiblevel", String.valueOf(rank.getLevel()));
		prMap.put("prestige", String.valueOf(rank.getPrestige()));
		prMap.put("rank", String.valueOf(rank.getRank().getIndex()));
		prMap.put("blocksmined", String.valueOf(blocksMined));
		prMap.put("fibs", String.valueOf(fibs));
		prMap.put("dibs", String.valueOf(dibs));
		prMap.put("staff", String.valueOf(staffIndex));
		prMap.put("autominer", String.valueOf(autominerSeconds));
		return prMap;
	}

	public String getUuid() {
		return uuid;
	}

	public String getPlayerName() {
		return playerName;
	}

	public PrisonRank getRank() {
		return rank;
	}

	public long getBlocksMined() {
		return blocksMined;
	}

	public double getFibs() {
		return fibs;
	}

	public double getDibs() {
		return dibs;
	}

	public void incrementBlocks(long amount) {
		this.blocksMined = blocksMined + amount;
	}

	public void incrementBlocks() {
		this.blocksMined = blocksMined + 1;
	}

	public void addFibs(double amount) {
		this.fibs = fibs + amount;
	}

	public void addDibs(double amount) {
		this.dibs = dibs + amount;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setRank(PrisonRank rank) {
		this.rank = rank;
	}

	public void setBlocksMined(long blocksMined) {
		this.blocksMined = blocksMined;
	}

	public void setFibs(double fibs) {
		this.fibs = fibs;
	}

	public void setDibs(double dibs) {
		this.dibs = dibs;
	}

	public void removeDibs(double amount) {
		this.dibs = dibs - amount;
	}

	public void removeFibs(double amount) {
		this.fibs = fibs - amount;
	}

	public boolean canBuyEnchant(CE ce, Player player) {
		return this.fibs >= (ce.getNextLevelPrice(player.getInventory().getItemInMainHand()));
	}

	public boolean isStaff() {
		return staffIndex > 0;
	}

	public void setStaffIndex(int index) {
		this.staffIndex = index;
	}

	public int getRankScore() {
		return rank.getRankScore();
	}

	public String getStaffPrefix() {
		if (staffIndex == 1) {
			return StaffPrefix.HELPER.getPrefix();
		} else if (staffIndex == 2) {
			return StaffPrefix.MOD.getPrefix();
		} else if (staffIndex == 3) {
			return StaffPrefix.OWNER.getPrefix();
		} else {
			return "";
		}
	}

	public long getAutominerSecondsLeft() {
		return autominerSeconds;
	}

	public void takeAwayAMSecond() {
		this.autominerSeconds = autominerSeconds - 1;
	}

	public void addAMSeconds(long seconds) {
		this.autominerSeconds += seconds;
	}

	public boolean isInAM() {
		return isAutomining;
	}

	public void setAutomining(boolean mining) {
		this.isAutomining = mining;
		if (mining) {
			MiscUtils.automining.remove(playerName);
		} else {
			MiscUtils.automining.add(playerName);
		}
	}

}
