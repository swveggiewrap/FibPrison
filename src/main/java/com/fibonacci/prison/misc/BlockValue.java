package com.fibonacci.prison.misc;

import org.bukkit.Material;

public enum BlockValue {
	STONE(0.1),
	COBBLESTONE(0.1),
	IRON_ORE(0.2),
	COAL_ORE(0.25),
	COAL(0.25),
	GOLD_ORE(0.40),
	LAPIS_LAZULI(0.1),
	LAPIS_ORE(0.5),
	DIAMOND(0.75),
	DIAMOND_ORE(0.75),
	REDSTONE(0.05),
	REDSTONE_ORE(0.5),
	EMERALD(1),
	EMERALD_ORE(1),
	IRON_INGOT(1.5),
	GOLD_INGOT(1.75),
	COAL_BLOCK(2),
	IRON_BLOCK(3),
	GOLD_BLOCK(4),
	LAPIS_BLOCK(5),
	DIAMOND_BLOCK(6.5),
	REDSTONE_BLOCK(1),
	EMERALD_BLOCK(7.5),
	OBSIDIAN(10);

	double basePrice;

	BlockValue(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public static BlockValue fromMaterial(Material material) {
		for (BlockValue blockValue : values()) {
			if (material.name().equals(blockValue.name())) {
				return blockValue;
			}
		}
		return null;
	}

}
