package com.fibonacci.prison.commands;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class FibCommand implements CommandExecutor {
	private final CommandInfo info;

	public FibCommand() {
		info = getClass().getDeclaredAnnotation(CommandInfo.class);
		Objects.requireNonNull(info, "Commands must have CommandInfo annotations");
	}

	public CommandInfo getInfo() {
		return info;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {

		if (!info.permission().isEmpty()) {
			if (!sender.hasPermission(info.permission())) {
				sender.sendMessage(ColorUtils.colorize("&7(&c&l!&7) &cYou have entered an invalid command."));
				return true;
			}
		}

		if (info.requiresPlayer()) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ColorUtils.colorize("&cYou must be a player to execute this command."));
				return true;
			}

			execute((Player) sender, args);
			return true;
		}

		execute(sender, args);
		return true;
	}

	public void execute(Player player, String[] args) {}
	public void execute(CommandSender sender, String[] args) {}


}
