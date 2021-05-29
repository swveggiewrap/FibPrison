package com.fibonacci.prison.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.fibonacci.prison.Prison;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.NumberUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class CancelInvalidCommandPacket extends PacketAdapter {

	public CancelInvalidCommandPacket() {
		super(Prison.getInstance(), PacketType.Play.Client.CHAT);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {

		if (event.getPlayer().isOp()) {
			return;
		}

		PacketContainer packet = event.getPacket();
		String message = packet.getStrings().read(0);

		if (!message.startsWith("/")) {
			return;
		}

		if (message.equalsIgnoreCase("/plugins") || message.equalsIgnoreCase("/pl")) {
			event.getPlayer().sendMessage(ColorUtils.colorize("&fPlugins (1): &aIdiotDetector"));
			event.getPlayer().sendMessage(ColorUtils.colorize("&7(&d&lFibonacci122&7) &c&lReally? This server is completely coded by me. My code is not available anywhere and will never be released. Keep Dreaming."));
			event.setCancelled(true);
			return;
		}

		if (message.equalsIgnoreCase("/ver") || message.equalsIgnoreCase("/version")) {
			event.getPlayer().sendMessage(ColorUtils.colorize("&fThis server is running NONE OF YOUR BUSINESS."));
			event.getPlayer().sendMessage(ColorUtils.colorize("&7(&d&lFibonacci122&7) &c&lGet off my server if your sole purpose is to try to leak my server. Nice try though."));
			event.setCancelled(true);
			return;
		}

		if (message.equalsIgnoreCase("/tps")) {
			double[] tps = ((CraftServer) Bukkit.getServer()).getServer().recentTps;
			event.getPlayer().sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &dRecent Server TPS"));
			String msg = ColorUtils.colorize("&7(&dLast 1 Minute&7) &e= " + NumberUtils.doubleToSuffixedNumber(tps[0]) + ", &7(&dLast 5 Minutes&7) &e= " + NumberUtils.doubleToSuffixedNumber(tps[1]) + ", &7(&dLast 20 Minutes&7) &e= " + NumberUtils.doubleToSuffixedNumber(tps[2]) + ".");
			boolean slow = false;
			for (double check : tps) {
				if (check <= 19.5) {
					slow = true;
					break;
				}
			}
			event.getPlayer().sendMessage(msg);
			if (!slow) {
				event.getPlayer().sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &eServer is not lagging, it's you lol."));
			}
			event.setCancelled(true);
			return;
		}

		if (Prison.getInstance().getPlayerCommands().contains(message.replace("/", "").split(" ")[0])) {
			return;
		}
		event.setCancelled(true);
		event.getPlayer().sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cYou have entered an invalid command."));
		MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("835658925771980830");
		if (channel == null) {
			return;
		}
		channel.sendMessage(event.getPlayer().getName() + " entered invalid command `" + message + "`").queue();

	}
}
