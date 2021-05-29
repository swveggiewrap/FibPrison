package com.fibonacci.prison.admin;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import com.fibonacci.prison.items.KitItems;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "mine", requiresPlayer = true, permission = "fibprison.admin")
public class MineCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /mine create <name> <material> <chance>... or /mine delete"));
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("delete")) {
				MineUtils.deleteMine(MineUtils.isPlayerInMine(player.getLocation()).getMineName());
			} else {
				player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /mine create <name> <material> <chance>... or /mine delete"));
			}
		} else if (args.length == 2) {
			player.sendMessage(ColorUtils.colorize("&cIncorrect usage. /mine create <name> <material> <chance>... or /mine delete"));
		} else {
			if (loc1 == null || loc2 == null) {
				player.sendMessage(ColorUtils.colorize("&cBoth locations are not yet defined."));
				return;
			}
			if (!isEven(args.length)) {
				player.sendMessage(ColorUtils.colorize("&cCommand must consist of /mine create <minename> and an even number of arguments after that."));
				return;
			}

			List<Material> materials = new ArrayList<>();
			List<Integer> chances = new ArrayList<>();

			for (int i = 2; i < args.length; i = i + 2) {
				if (!isMaterial(args[i])) {
					player.sendMessage(ColorUtils.colorize("&cYou have invalid materials in your arguments."));
					return;
				}
				materials.add(Material.getMaterial(args[i]));
			}

			for (int i = 3; i < args.length; i = i + 2) {
				if (!isInteger(args[i])) {
					player.sendMessage(ColorUtils.colorize("&cYou have invalid integers in your arguments."));
					return;
				}
				chances.add(Integer.parseInt(args[i]));
			}

			MineUtils.createMine(args[1], player.getWorld(), loc1, loc2, materials, chances);
			loc1 = null;
			loc2 = null;
		}
	}

	public static Location loc1 = null;
	public static Location loc2 = null;

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isMaterial(String s) {
		return Material.getMaterial(s.toUpperCase()) != null;
	}

	public boolean isEven(int i) {
		return (i % 2) == 0;
	}
}
