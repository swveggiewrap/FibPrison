package com.fibonacci.prison.misc;

import com.fibonacci.prison.utils.TimeUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BannedPlayer {

	public static Map<String, BannedPlayer> bannedList = new HashMap<>();

	public final String uuid;
	public final String reason;
	public final long started;
	public final long endTime;

	public BannedPlayer(String playerUUID, String reason, long started, long duration) {
		this.uuid = playerUUID;
		this.reason = reason;
		this.started = started;
		this.endTime = duration;
		bannedList.put(uuid, this);
	}

	public BannedPlayer(String uuid, Map<String, String> info) {
		this.uuid = uuid;
		this.reason = info.get("reason");
		this.started = Long.parseLong(info.get("started"));
		this.endTime = Long.parseLong(info.get("duration"));
		bannedList.put(uuid, this);
	}

	public Map<String, String> getInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("reason", this.reason);
		map.put("started", String.valueOf(this.started));
		map.put("duration", String.valueOf(this.endTime));
		return map;
	}

	public boolean isTemporary() {
		return endTime == 0;
	}

	public long getDuration() {
		return endTime;
	}

	public long getEndLongTime() {
		return endTime;
	}

	public String getUuid() {
		return uuid;
	}

	public String getReason() {
		return reason;
	}

	public long getStarted() {
		return started;
	}

	public static boolean isBanned(Player player) {
		return bannedList.containsKey(player.getUniqueId().toString());
	}

	public static Map<String, BannedPlayer> getBannedList() {
		return bannedList;
	}

	public String getEndDate() {
		return TimeUtils.convertTime(getEndLongTime());
	}

	public static boolean isStillBanned(Player player) {
		if (bannedList.containsKey(player.getUniqueId().toString())) {
			BannedPlayer banned = BannedPlayer.getBannedList().get(player.getUniqueId().toString());
			if (banned.getDuration() == 0) {
				return true;
			}

			if (banned.getEndLongTime() >= System.currentTimeMillis()) {
				return true;
			} else {
				bannedList.remove(player.getUniqueId().toString());
				return false;
			}

		}
		return false;
	}

}
