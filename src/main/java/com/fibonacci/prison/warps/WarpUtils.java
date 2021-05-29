package com.fibonacci.prison.warps;

import com.fibonacci.prison.utils.JedisUtils;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class WarpUtils {

	public static Map<String, Warp> warps = new HashMap<>();

	public static Warp getWarp(String name) {
		return warps.get(name);
	}

	public static void createWarp(String name, Location location) {
		Warp warp = new Warp(name, location);
		warps.put(name, warp);
		JedisUtils.setHashMap("warps:" + name, warp.getWarpInfo());
	}

	public static void deleteWarp(String warpName) {
		Warp warp = warps.get(warpName);
		if (warp != null) {
			warps.remove(warpName);
			JedisUtils.delete("warps:" + warpName);
		}
	}


}
