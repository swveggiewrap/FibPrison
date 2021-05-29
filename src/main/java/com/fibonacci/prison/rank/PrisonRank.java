package com.fibonacci.prison.rank;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.MessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import sun.management.counter.perf.PerfStringCounter;

import java.util.logging.Level;

public class PrisonRank {

	private int fibLevel;
	private int prestige;
	private Rank rank;
	private long price;
	private String prefix;
	private long multiplier;

	public PrisonRank(int level, int prestige, Rank rank) {
		this.fibLevel = level;
		this.prestige = prestige;
		this.rank = rank;
		this.prefix = MessageUtils.getPrefix(level, prestige, rank);
		double rankMulti;
		if (rank.getIndex() == 1) {
			rankMulti = 1;
		} else {
			rankMulti = 1.25;
		}
		this.multiplier = (long) (Math.round(fibLevel * 4) + (prestige * 2.5) + (rank.getIndex() * rankMulti));

		long prestigeMulti;
		long levelMulti;

		FileConfiguration config = Prison.getInstance().getConfig();
		double prestigeIncrease = config.getDouble("prestige-increase");
		double levelIncrease = config.getDouble("level-increase");
		long rankPrice = config.getLong("rank-prices." + rank.getLetter());

		if (level > 0) {
			levelMulti = level;
			if (prestige > 0) {
				prestigeMulti = prestige;
				this.price = Math.round((levelMulti * levelIncrease) + ((prestigeIncrease * prestigeMulti) * rankPrice));
			} else {
				this.price = Math.round((levelMulti * levelIncrease) * rankPrice);
			}
		} else {
			prestigeMulti = prestige;
			if (prestige > 0) {
				this.price = Math.round((prestigeMulti * prestigeIncrease) * rankPrice);
			} else {
				this.price = rankPrice;
			}
		}
	}

	public int getLevel() {
		return fibLevel;
	}

	public void setFibLevel(int level) {
		this.fibLevel = level;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int prestige) {
		this.prestige = prestige;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public long getPrice() {
		return price;
	}

	public String getPrefix() {
		return prefix;
	}

	public double getSellMultiplier() {
		return multiplier;
	}

	public int getRankScore() {
		return (fibLevel * 11000) + (prestige * 100) + (rank.getIndex());
	}




}
