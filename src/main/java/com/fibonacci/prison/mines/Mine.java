package com.fibonacci.prison.mines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.*;

public class Mine {

	public String mineName;
	private Location loc1;
	private Location loc2;
	private World world;
	private List<Integer> chances;
	private List<Material> materials;

	public Mine(String mineName, Location loc1, Location loc2, World world, List<Integer> chances, List<Material> materials) {
		this.mineName = mineName;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.world = world;
		this.chances = chances;
		this.materials = materials;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public Location getLoc1() {
		return loc1;
	}

	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}

	public Location getLoc2() {
		return loc2;
	}

	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Cuboid getCuboid() {
		return new Cuboid(loc1, loc2);
	}

	public List<Integer> getChances() {
		return chances;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public Map<String, String> getMineInfo() {
		Map<String, String> mineInfo = new HashMap<>();
		mineInfo.put("loc1x", String.valueOf(loc1.getBlockX()));
		mineInfo.put("loc1y", String.valueOf(loc1.getBlockY()));
		mineInfo.put("loc1z", String.valueOf(loc1.getBlockZ()));
		mineInfo.put("loc2x", String.valueOf(loc2.getBlockX()));
		mineInfo.put("loc2y", String.valueOf(loc2.getBlockY()));
		mineInfo.put("loc2z", String.valueOf(loc2.getBlockZ()));
		mineInfo.put("world", world.getName());
		StringBuilder builder = new StringBuilder("");
		for (int chance : chances) {
			builder.append(chance + ",");
		}
		mineInfo.put("chances", builder.toString());

		StringBuilder materialBuilder = new StringBuilder("");
		for (Material material : materials) {
			materialBuilder.append(material.name() + ",");
		}
		mineInfo.put("materials", materialBuilder.toString());
		return mineInfo;
	}

	public static Mine getMineFromInfo(String mineName, Map<String, String> mineInfo) {
		World world = Bukkit.getWorld(mineInfo.get("world"));
		Location loc1 = new Location(world, Double.parseDouble(mineInfo.get("loc1x")), Double.parseDouble(mineInfo.get("loc1y")), Double.parseDouble(mineInfo.get("loc1z")));
		Location loc2 = new Location(world, Double.parseDouble(mineInfo.get("loc2x")), Double.parseDouble(mineInfo.get("loc2y")), Double.parseDouble(mineInfo.get("loc2z")));
		List<Material> materials = new ArrayList<>();
		List<Integer> chances = new ArrayList<>();
		List<String> materialStringList = Arrays.asList(mineInfo.get("materials").split(",").clone());
		for (String str : materialStringList) {
			materials.add(Material.getMaterial(str));
		}

		List<String> chancesStringList = Arrays.asList(mineInfo.get("chances").split(",").clone());
		for (String str : chancesStringList) {
			chances.add(Integer.parseInt(str));
		}

		return new Mine(mineName, loc1, loc2, world, chances, materials);
	}




}
