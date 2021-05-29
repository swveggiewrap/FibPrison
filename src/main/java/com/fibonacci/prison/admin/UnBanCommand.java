package com.fibonacci.prison.admin;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.misc.BannedPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "unban", requiresPlayer = false)
public class UnBanCommand extends FibCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			if (!PrisonPlayerUtils.getPrisonPlayer((Player) sender).isStaff()) {
				return;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(ColorUtils.colorize("&cYou must specify a player to unban."));
		} else if (args.length == 1) {
			String playerName = args[0];
			String uuid = PlayerUtils.getPlayerUUIDIfPlayedBefore(playerName);

			if (BannedPlayer.getBannedList().containsKey(uuid)) {
				sender.sendMessage(ColorUtils.colorize("&eYou have unbanned " + playerName));
				BannedPlayer.getBannedList().remove(uuid);
			} else {
				sender.sendMessage(ColorUtils.colorize("&c" + playerName + " is not banned."));
			}

			MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("836405819917598750");
			channel.sendMessage(sender.getName() + " has unbanned `" + playerName + "`.").queue();

		} else {
			sender.sendMessage(ColorUtils.colorize("&c/unban <player>"));
		}

	}

}
