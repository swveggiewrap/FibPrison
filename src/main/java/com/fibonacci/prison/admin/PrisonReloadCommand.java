package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import org.bukkit.entity.Player;

@CommandInfo(name = "prisonreload", requiresPlayer = true, permission = "fibprison.admin")
public class PrisonReloadCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		Prison.getInstance().reloadConfig();
	}
}
