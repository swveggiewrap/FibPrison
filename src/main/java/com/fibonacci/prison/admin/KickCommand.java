package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "kick", requiresPlayer = false)
public class KickCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(ColorUtils.colorize("&c/kick <player> [reason]"));
		} else if (args.length == 1) {
			String name = args[0];
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().equalsIgnoreCase(name)) {
					PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
					if (prisonPlayer.isStaff()) {
						sender.sendMessage(ColorUtils.colorize("&cYou can't kick a fellow staff member."));
					} else {
						player.kickPlayer("Kicked\nReason: none");
					}
					break;
				}
			}
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836405819917598750");
			channel.sendMessage(sender.getName() + " has kicked `" + name + "`. Reason: `none`").queue();
		} else {
			String name = args[0];
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				builder.append(args[i] + " ");
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().equalsIgnoreCase(name)) {
					PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
					if (prisonPlayer.isStaff()) {
						sender.sendMessage(ColorUtils.colorize("&cYou can't kick a fellow staff member."));
					} else {
						player.kickPlayer("Kicked\nReason: " + builder.toString());
					}
					break;
				}
			}
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836405819917598750");
			channel.sendMessage(sender.getName() + " has kicked `" + name + "`. Reason: `" + builder.toString() + "`.").queue();

		}
	}
}
