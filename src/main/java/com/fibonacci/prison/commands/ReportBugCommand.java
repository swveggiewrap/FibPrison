package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

@CommandInfo(name = "reportbug", requiresPlayer = true)
public class ReportBugCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cYou must specify a detailed explanation of this bug and how it is occurring."));
		} else {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				builder.append(args[i] + " ");
			}

			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("842224272516055082");
			channel.sendMessage("<@318882075719499796> " + player.getName() + " reported bug `" + builder.toString() + "`").queue();

		}

	}
}
