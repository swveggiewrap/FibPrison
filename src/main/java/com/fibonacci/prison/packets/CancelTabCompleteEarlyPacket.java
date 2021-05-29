package com.fibonacci.prison.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;

public class CancelTabCompleteEarlyPacket extends PacketAdapter {

	public CancelTabCompleteEarlyPacket() {
	    super(Prison.getInstance(), PacketType.Play.Client.TAB_COMPLETE);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if (event.getPacket().getStrings().getValues().get(0).equals("/")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ColorUtils.colorize("&7(&6&lServer&7) &fWe recommend that you use a later version of Minecraft for a better experience. Command lists are available in later versions for ease of server access."));

			StringBuilder builder = new StringBuilder();
			for (String str : Prison.getInstance().getPlayerCommands()) {
				builder.append("/" + str + ", ");
			}
			event.getPlayer().sendMessage(builder.toString());
		}
	}
}
