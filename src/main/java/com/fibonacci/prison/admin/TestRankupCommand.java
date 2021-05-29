package com.fibonacci.prison.admin;

import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.rank.PrisonRank;
import com.fibonacci.prison.rank.RankRegistry;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "testrankup", requiresPlayer = true, permission = "fibprison.admin")
public class TestRankupCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);

		if (args.length == 0) {
			prisonPlayer.addDibs(PrisonPlayerUtils.getNextRank(prisonPlayer).getPrice());
			PrisonPlayerUtils.rankupPlayer(prisonPlayer);
		} else if (args.length == 3) {
			if (isInt(args[0]) && isInt(args[1]) || isInt(args[2])) {
				if (Integer.parseInt(args[2]) > 27 || Integer.parseInt( args[2]) < 1) {
					player.sendMessage(ColorUtils.colorize("&cIndex must be between 1(A) and 27(Free)"));
					return;
				}
				prisonPlayer.setRank(new PrisonRank(Integer.parseInt(args[0]), Integer.parseInt(args[1]), RankRegistry.integerRankMap.get(Integer.parseInt(args[2]))));
			}
		} else {
			player.sendMessage(ColorUtils.colorize("&c/testrankup or /testrankup <level> <prestige> <letterIndex>"));
		}
		return;
	}

	public boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
