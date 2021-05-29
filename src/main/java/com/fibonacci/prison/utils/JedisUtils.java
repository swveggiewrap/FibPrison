package com.fibonacci.prison.utils;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.mines.Mine;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.misc.MutedPlayer;
import com.fibonacci.prison.warps.Warp;
import com.fibonacci.prison.warps.WarpUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JedisUtils {

	public static void setKeyValue(String key, String value) {
		Jedis jedis = Prison.getJedis();
		jedis.set(key, value);
		jedis.close();
	}

	public static String getValue(String key) {
		Jedis jedis = Prison.getJedis();
		String returnKey = jedis.get(key);
		jedis.close();
		return returnKey;
	}

	public static void setHashMap(String key, Map<String, String> value) {
		Jedis jedis = Prison.getJedis();
		jedis.hmset(key, value);
		jedis.close();
	}

	public static Map<String, String> getHashMap(String key) {
		Jedis jedis = Prison.getJedis();
		Map<String, String> map = jedis.hgetAll(key);
		jedis.close();
		return map;
	}

	public static void delete(String key) {
		Jedis jedis = Prison.getJedis();
		jedis.del(key);
		jedis.close();
	}


	public static void loadMines() {
		Jedis jedis = Prison.getJedis();
		ScanParams params = new ScanParams().match("mines:*").count(100);
		List<String> results = new ArrayList<>();
		boolean finished = false;
		String cur = ScanParams.SCAN_POINTER_START;
		while (!finished) {
			ScanResult<String> scanResult = jedis.scan(cur, params);
			for (String str : scanResult.getResult()) {
				String name = str.replace("mines:", "");
				Map<String, String> info = getHashMap(str);
				Mine mine = Mine.getMineFromInfo(name, info);
				MineUtils.getMines().put(name, mine);
			}
			cur = scanResult.getCursor();
			if (cur.equals("0")) {
				finished = true;
			}
		}

		jedis.close();
	}

	public static void loadWarps() {
		Jedis jedis = Prison.getJedis();
		ScanParams params = new ScanParams().match("warps:*").count(100);
		List<String> results = new ArrayList<>();
		boolean finished = false;
		String cur = ScanParams.SCAN_POINTER_START;

		while (!finished) {
			ScanResult<String> scanResult = jedis.scan(cur, params);
			for (String str : scanResult.getResult()) {
				String name = str.replace("warps:", "");
				Map<String, String> info = getHashMap(str);
				Warp mine = Warp.getFromJedisInfo(name, info);
				WarpUtils.warps.put(name, mine);
			}
			cur = scanResult.getCursor();
			if (cur.equals("0")) {
				finished = true;
			}
		}

		jedis.close();
	}

	public static void loadMutedPlayers() {
		Jedis jedis = Prison.getJedis();
		ScanParams params = new ScanParams().match("mutedplayers:*").count(100);
		List<String> results = new ArrayList<>();
		boolean finished = false;
		String cur = ScanParams.SCAN_POINTER_START;
		while (!finished) {
			ScanResult<String> scanResult = jedis.scan(cur, params);
			for (String str : scanResult.getResult()) {
				String name = str.replace("mutedplayers:", "");
				Map<String, String> info = getHashMap(str);
				new MutedPlayer(name, info);
			}
			cur = scanResult.getCursor();
			if (cur.equals("0")) {
				finished = true;
			}
		}

		jedis.close();
	}

	public static void loadBannedPlayers() {
		Jedis jedis = Prison.getJedis();
		ScanParams params = new ScanParams().match("bannedplayers:*").count(100);
		List<String> results = new ArrayList<>();
		boolean finished = false;
		String cur = ScanParams.SCAN_POINTER_START;
		while (!finished) {
			ScanResult<String> scanResult = jedis.scan(cur, params);
			for (String str : scanResult.getResult()) {
				String name = str.replace("bannedplayers:", "");
				Map<String, String> info = getHashMap(str);
				new BannedPlayer(name, info);
			}
			cur = scanResult.getCursor();
			if (cur.equals("0")) {
				finished = true;
			}
		}
		jedis.close();
	}

	public static void savePunishedPlayers() {
		Jedis jedis = Prison.getJedis();
		for (MutedPlayer player : MutedPlayer.getMutedList().values()) {
			jedis.hmset("mutedplayers:" + player.getUuid(), player.getInfo());
		}

		for (BannedPlayer player : BannedPlayer.getBannedList().values()) {
			jedis.hmset("bannedplayers:" + player.getUuid(), player.getInfo());
		}
		jedis.close();
	}




}
