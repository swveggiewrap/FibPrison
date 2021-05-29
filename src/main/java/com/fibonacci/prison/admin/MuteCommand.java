package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.MutedPlayer;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import com.fibonacci.prison.utils.TimeUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandInfo(name = "mute", requiresPlayer = false)
public class MuteCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(ColorUtils.colorize("&cYou must specify a player to mute."));
		} else if (args.length == 1) {
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

			new MutedPlayer(uuid, "none", System.currentTimeMillis(), 0);

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.sendMessage(ColorUtils.colorize("&7(&c&lSTAFF&7) &c&lYOU HAVE BEEN PERMANENTLY MUTED ON SERVER"));
			}
			sender.sendMessage(ColorUtils.colorize("&eYou have permanently muted " + playerName));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836406013967204414");
			channel.sendMessage(sender.getName() + " has permanently muted `" + playerName + "`. Reason:`none`.").queue();
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
			for (int i = 1; i < args.length; i++) {
				reason.append(args[i] + " ");
			}

			new MutedPlayer(uuid, reason.toString(), System.currentTimeMillis(), 0);

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.sendMessage(ColorUtils.colorize("&7(&c&lSTAFF&7) &c&lYOU HAVE BEEN PERMANENTLY MUTED ON SERVER"));
			}
			sender.sendMessage(ColorUtils.colorize("&eYou have permanently muted " + playerName));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836406013967204414");
			channel.sendMessage(sender.getName() + " has permanently muted `" + playerName + "`. Reason: `" + reason.toString() + "`.").queue();
		}

	}

}
