package com.fibonacci.prison.commands;

import com.fibonacci.prison.rank.PrisonRank;
import com.fibonacci.prison.rank.Rank;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.MessageUtils;
import com.fibonacci.prison.utils.NumberUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "ranks", requiresPlayer = true)
public class RanksCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer pPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

	    PrisonPlayer prisonPlayer = new PrisonPlayer(pPlayer.getUuid(), pPlayer.getPlayerName(), pPlayer.getRank(), pPlayer.getBlocksMined(), pPlayer.getFibs(), pPlayer.getDibs(), pPlayer.getAutominerSecondsLeft());
	    prisonPlayer.setRank(new PrisonRank(pPlayer.getRank().getLevel(), pPlayer.getRank().getPrestige(), Rank.A));


		List<String> lines = new ArrayList<>();

		PrisonRank previous = prisonPlayer.getRank();
	    PrisonRank rank = PrisonPlayerUtils.getNextRank(prisonPlayer);

	    for (int i = 0; i < 27; i++) {
	    	lines.add(ColorUtils.colorize(previous.getPrefix() + "&f -> " + rank.getPrefix() + "&f =&d " + NumberUtils.doubleToSuffixedNumber(previous.getPrice()) + " Dibs"));
	    	previous = rank;
			prisonPlayer.setRank(rank);
			rank = PrisonPlayerUtils.getNextRank(prisonPlayer);
	    }

		MessageUtils.sendPrisonFormattedMsg(player, lines);
	}
}
