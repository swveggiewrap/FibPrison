package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.misc.MutedPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandInfo(name = "unmute", requiresPlayer = false)
public class UnMuteCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(ColorUtils.colorize("&cYou must specify a player to unmute."));
		} else if (args.length == 1) {
			String playerName = args[0];
			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);

			if (MutedPlayer.getMutedList().containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&eYou have unmuted " + playerName));
				MutedPlayer.getMutedList().remove(uuid);
			} else {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is not muted."));
			}

			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836406013967204414");
			channel.sendMessage(sender.getName() + " has unmuted `" + playerName + "`.").queue();

		} else {
			sender.sendMessage(ColorUtils.colorize("&c/unmute <player>"));
		}

	}

}
