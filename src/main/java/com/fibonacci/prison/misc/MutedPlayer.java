package com.fibonacci.prison.misc;

import com.fibonacci.prison.utils.TimeUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MutedPlayer {

	public static Map<String, MutedPlayer> mutedList = new HashMap<>();

	public final String uuid;
	public final String reason;
	public final long started;
	public final long endTime;

	public MutedPlayer(String playerUUID, String reason, long started, long duration) {
		this.uuid = playerUUID;
		this.reason = reason;
		this.started = started;
		this.endTime = duration;
		mutedList.put(uuid, this);
	}

	public MutedPlayer(String uuid, Map<String, String> info) {
		this.uuid = uuid;
		this.reason = info.get("reason");
		this.started = Long.parseLong(info.get("started"));
		this.endTime = Long.parseLong(info.get("duration"));
		mutedList.put(uuid, this);
	}

	public Map<String, String> getInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("reason", this.reason);
		map.put("started", String.valueOf(this.started));
		map.put("duration", String.valueOf(this.endTime));
		return map;
	}

	public boolean isTemporary() {
		return endTime != 0;
	}

	public long getDuration() {
		return endTime;
	}

	public String getUuid() {
		return uuid;
	}

	public long getEndLongTime() {
		return endTime;
	}

	public String getReason() {
		return reason;
	}

	public long getStarted() {
		return started;
	}

	public static boolean isMuted(Player player) {
		return mutedList.containsKey(player.getUniqueId().toString());
	}

	public static Map<String, MutedPlayer> getMutedList() {
		return mutedList;
	}

	public String getEndDate() {
		return TimeUtils.convertTime(getEndLongTime());
	}

	public static boolean isStillMuted(Player player) {
		if (mutedList.containsKey(player.getUniqueId().toString())) {
			MutedPlayer muted = MutedPlayer.getMutedList().get(player.getUniqueId().toString());
			if (muted.getDuration() == 0) {
				return true;
			}

			if (muted.getEndLongTime() >= System.currentTimeMillis()) {
				return true;
			} else {
				mutedList.remove(player.getUniqueId().toString());
				return false;
			}

		}
		return false;
	}



}
