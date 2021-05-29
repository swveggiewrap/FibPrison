package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@CommandInfo(name = "message", requiresPlayer = true)
public class MessageCommand extends FibCommand {

	public static Map<Player, Player> convos = new HashMap<>();

	@Override
	public void execute(Player player, String[] args) {
		if (args.length <= 1) {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /message <player> <msg>"));
		} else {
			String playerName = args[0];
			if (playerName.equalsIgnoreCase(player.getName())) {
				player.sendMessage(ColorUtils.colorize("&cWhy are you messaging yourself lol?"));
				return;
			}
			if (Bukkit.getPlayer(playerName) != null) {
				if (Bukkit.getPlayer(playerName).isOnline()) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						builder.append(args[i] + " ");
					}

					Player receiver = Bukkit.getPlayer(playerName);
					player.sendMessage(ColorUtils.colorize("&7(&d&lMSG&7) &eYou&f -> &e" + receiver.getName() + "&f: &d" + builder.toString()));
					receiver.sendMessage(ColorUtils.colorize("&7(&d&lMSG&7) &e" + player.getName() + "&f -> &eYou&f: &d" + builder.toString()));
					convos.put(player, receiver);
					convos.put(receiver, player);

					MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("842222999502585918");
					channel.sendMessage(player.getName() + " sent " + receiver.getName() + " a message. `" + builder.toString() + "`").queue();

				} else {
					player.sendMessage(ColorUtils.colorize("&cThat player is not online."));
				}
			} else {
				player.sendMessage(ColorUtils.colorize("&cThat player is not online."));
			}
		}
	}
}
