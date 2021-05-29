package com.fibonacci.prison.commands;

import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "rankup", requiresPlayer = true)
public class RankupCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayerUtils.rankupPlayer(PrisonPlayerUtils.getPrisonPlayer(player));
	}

}
