package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandInfo(name = "ban", requiresPlayer = false)
public class BanCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(ColorUtils.colorize("&cYou must specify a player to ban."));
		} else if (args.length == 1) {
			String playerName = args[0];
			Map<String, BannedPlayer> bannedPlayers = BannedPlayer.getBannedList();

			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);

			if (PlayerUtils.isStaff(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&cWhy are you trying to ban a staff member?"));
				return;
			}

			if (bannedPlayers.containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is already banned."));
				return;
			}

			new BannedPlayer(uuid, "none", System.currentTimeMillis(), 0);

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.kickPlayer("YOU HAVE BEEN PERMANENTLY BANNED FROM SERVER");
			}
			sender.sendMessage(ColorUtils.colorize("&eYou have permanently banned " + playerName));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836405819917598750");
			channel.sendMessage(sender.getName() + " has permanently banned `" + playerName + "`. Reason: `none`").queue();
		} else {

			String playerName = args[0];
			Map<String, BannedPlayer> bannedPlayers = BannedPlayer.getBannedList();

			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);

			if (PlayerUtils.isStaff(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&cWhy are you trying to ban a staff member?"));
				return;
			}

			if (bannedPlayers.containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is already banned."));
				return;
			}

			StringBuilder reason = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				reason.append(args[i] + " ");
			}

			new BannedPlayer(uuid, reason.toString(), System.currentTimeMillis(), 0);

			Player player = Bukkit.getPlayer(playerName);
			if (player != null && player.isOnline()) {
				player.kickPlayer("YOU HAVE BEEN PERMANENTLY BANNED FROM SERVER");
			}

			sender.sendMessage(ColorUtils.colorize("&eYou have permanently banned " + playerName));
			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836405819917598750");
			channel.sendMessage(sender.getName() + " has permanently banned `" + playerName + "`. Reason: `" + reason.toString() + "`.").queue();
		}

	}
}
