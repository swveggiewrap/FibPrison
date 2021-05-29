package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandInfo(name = "report", requiresPlayer = true)
public class ReportPlayerCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cYou must specify a reason to report this player."));
		} else {
			String reportedName = args[0];
			if (reportedName.equalsIgnoreCase(player.getName())) {
				player.sendMessage(ColorUtils.colorize("&cYou can't report yourself."));
				return;
			}
			boolean detected = false;
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (reportedName.equalsIgnoreCase(online.getName())) {
					detected = true;
					break;
				}
			}

			if (!detected) {
				player.sendMessage(ColorUtils.colorize("&cYou need to specify a valid player name to report."));
				return;
			}

			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				builder.append(args[i] + " ");
			}

			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("842224212688896031");
			channel.sendMessage("@here " + player.getName() + " reported " + reportedName + " for `" + builder.toString() + "`").queue();

		}

	}
}
