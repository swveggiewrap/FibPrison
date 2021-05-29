package com.fibonacci.prison.commands;

import com.fibonacci.prison.scoreboard.PlayerScoreboard;
import org.bukkit.entity.Player;

@CommandInfo(name = "sbtoggle", requiresPlayer = true)
public class SBToggleCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (PlayerScoreboard.scoreboards.containsKey(player.getName())) {
			PlayerScoreboard.removeFromScoreboards(player);
		} else {
			PlayerScoreboard.getNewPlayerScoreboard(player);
		}
	}
}
