package com.fibonacci.prison.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Warp {

	private String name;
	private Location location;

	public Warp(String name, Location location) {
		this.name = name;
		this.location = location;
	}

	public Map<String, String> getWarpInfo() {
		Map<String, String> info = new HashMap<>();
		info.put("x", String.valueOf(this.location.getX()));
		info.put("y", String.valueOf(this.location.getY()));
		info.put("z", String.valueOf(this.location.getZ()));
		info.put("pitch", String.valueOf(this.location.getPitch()));
		info.put("yaw", String.valueOf(this.location.getYaw()));
		info.put("world", this.location.getWorld().getName());
		return info;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public static Warp getFromJedisInfo(String name, Map<String, String> info) {
		Location warpLoc = new Location(Bukkit.getWorld(info.get("world")), Double.parseDouble(info.get("x")), Double.parseDouble(info.get("y")), Double.parseDouble(info.get("z")), Float.parseFloat(info.get("yaw")), Float.parseFloat(info.get("pitch")));
		return new Warp(name, warpLoc);
	}

}
