package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.entity.Player;

@CommandInfo(name = "reply", requiresPlayer = true)
public class ReplyCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /message <player> <msg>"));
		} else {
			Player receiver = MessageCommand.convos.get(player);
			if (receiver == null) {
				player.sendMessage(ColorUtils.colorize("&cYou have received a message since being on the server yet. There is nobody to reply to."));
				return;
			}

			if (receiver.isOnline()) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					builder.append(args[i] + " ");
				}

				player.sendMessage(ColorUtils.colorize("&7(&d&lMSG&7) &eYou&f -> &e" + receiver.getName() + "&f: &d" + builder.toString()));
				receiver.sendMessage(ColorUtils.colorize("&7(&d&lMSG&7) &e" + player.getName() + "&f -> &eYou&f: &d" + builder.toString()));
				MessageCommand.convos.put(player, receiver);
				MessageCommand.convos.put(receiver, player);

				MessageChannel channel = Prison.getInstance().getJDA().getTextChannelById("842222999502585918");
				channel.sendMessage(player.getName() + " sent " + receiver.getName() + " a message. `" + builder.toString() + "`").queue();

			} else {
				player.sendMessage(ColorUtils.colorize("&cThat player is not online anymore."));
			}

		}

	}
}
