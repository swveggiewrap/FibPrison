package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.MutedPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import com.fibonacci.prison.utils.TimeUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandInfo(name = "tempmute", requiresPlayer = false)
public class TempMuteCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length <= 1) {
			sender.sendMessage(ColorUtils.colorize("&cYou must specify a player and time to mute."));
		} else if (args.length == 2) {
			String playerName = args[0];
			Map<String, MutedPlayer> mutedPlayers = MutedPlayer.getMutedList();

			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);
			if (PlayerUtils.isStaff(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&cWhy are you trying to mute a staff member?"));
				return;
			}
			if (mutedPlayers.containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is already muted."));
				return;
			}

			new MutedPlayer(uuid, "none", System.currentTimeMillis(), TimeUtils.stringTimeToEndDate(args[1]));

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.sendMessage(ColorUtils.colorize("&7(&c&lSTAFF&7) &c&lYOU HAVE BEEN MUTED ON SERVER UNTIL " + TimeUtils.convertTime(TimeUtils.stringTimeToEndDate(args[1]))));
			}
			sender.sendMessage(ColorUtils.colorize("&eYou have muted " + playerName + " until " + TimeUtils.convertTime(TimeUtils.stringTimeToEndDate(args[1]))));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836406013967204414");
			channel.sendMessage(sender.getName() + " has temporarily muted `" + playerName + "` for `" + args[1] + "`. Reason: `none`.").queue();
		} else {

			String playerName = args[0];
			Map<String, MutedPlayer> mutedPlayers = MutedPlayer.getMutedList();

			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);
			if (PlayerUtils.isStaff(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&cWhy are you trying to mute a staff member?"));
				return;
			}

			if (mutedPlayers.containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is already muted."));
				return;
			}

			StringBuilder reason = new StringBuilder();
			for (int i = 2; i < args.length; i++) {
				reason.append(args[i] + " ");
			}

			new MutedPlayer(uuid, reason.toString(), System.currentTimeMillis(), TimeUtils.stringTimeToEndDate(args[1]));

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.sendMessage(ColorUtils.colorize("&7(&c&lSTAFF&7) &c&lYOU HAVE BEEN MUTED ON SERVER UNTIL " + TimeUtils.convertTime(TimeUtils.stringTimeToEndDate(args[1])) + ". &e&lReason: &f" + reason.toString()));
			}

			sender.sendMessage(ColorUtils.colorize("&eYou have muted " + playerName + " until " + TimeUtils.convertTime(TimeUtils.stringTimeToEndDate(args[1]))));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836406013967204414");
			channel.sendMessage(sender.getName() + " has temporarily muted `" + playerName + "` for `" + args[1] + "`. Reason: `" + reason.toString() + "`.").queue();
		}

	}

}
