package com.fibonacci.prison.mines;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.admin.MineCommand;
import com.fibonacci.prison.utils.JedisUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineUtils {

	private static final Map<String, Mine> mineMap = new HashMap<>();

	public static void createMine(String mineName, World world, Location loc1, Location loc2, List<Material> materials, List<Integer> chances) {
		Bukkit.getScheduler().runTask(Prison.getInstance(), () -> {
			Mine mine = new Mine(mineName, loc1, loc2, world, chances, materials);
			Cuboid cuboid = mine.getCuboid();
			cuboid.setBlockTypes(mine.getLoc1(), mine.getLoc2(), chances, materials);
			mineMap.put(mineName, mine);
			JedisUtils.setHashMap("mines:" + mineName, mine.getMineInfo());
		});
	}

	public static void deleteMine(String mineName) {
		Bukkit.getScheduler().runTask(Prison.getInstance(), () -> {
			Mine mine = getMine(mineName);
			Cuboid cuboid = mine.getCuboid();
			List<Integer> chances = new ArrayList<>();
			List<Material> materials = new ArrayList<>();
			chances.add(100);
			materials.add(Material.AIR);
			cuboid.setBlockTypes(mine.getLoc1(), mine.getLoc2(), chances, materials);
			mineMap.remove(mineName);
			JedisUtils.delete("mines:" + mineName);
		});
	}

	public static void resetMine(String mineName) {
		Mine mine = getMine(mineName);
		mine.getCuboid().setBlockTypes(mine.getLoc1(), mine.getLoc2(), mine.getChances(), mine.getMaterials());
	}

	public static Mine isPlayerInMine(Location playerLocation) {
		for (Mine mine : mineMap.values()) {
			Cuboid cuboid = mine.getCuboid();
			if (cuboid.contains(playerLocation.getBlockX(), playerLocation.getBlockZ())) {
				return mine;
			}
		}
		return null;
	}

	public static boolean isBlockInMine(Location location) {
		for (Mine mine : mineMap.values()) {
			Cuboid cuboid = mine.getCuboid();
			if (cuboid.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
				return true;
			}
		}
		return false;
	}

	public static Map<String, Mine> getMines() {
		return mineMap;
	}

	public static Mine getMine(String name) {
		return mineMap.get(name);
	}

	public static void saveMines() {
		for (String str : mineMap.keySet()) {
			saveMine(str);
		}
	}

	public static void saveMine(String mineName) {
		JedisUtils.setHashMap("mines:" + mineName, getMine(mineName).getMineInfo());
	}


}
