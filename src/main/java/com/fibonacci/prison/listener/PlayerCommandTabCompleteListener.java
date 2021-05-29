package com.fibonacci.prison.listener;

import com.fibonacci.prison.Prison;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandTabCompleteListener implements Listener {

	@EventHandler
	public void onTabComplete(PlayerCommandSendEvent e) {
		if (e.getPlayer().isOp()) {
			return;
		}

		e.getCommands().clear();
		e.getCommands().addAll(Prison.getInstance().getPlayerCommands());

	}

}
