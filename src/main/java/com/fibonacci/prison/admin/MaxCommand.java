package com.fibonacci.prison.admin;

import com.fibonacci.prison.api.ItemBuilder;
import com.fibonacci.prison.commands.CommandInfo;
import com.fibonacci.prison.commands.FibCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

@CommandInfo(name = "max", requiresPlayer = true, permission = "fibprison.admin")
public class MaxCommand extends FibCommand {

	@Override
	public void execute(Player player, String[] args) {
		ItemBuilder builder = new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayName("&d&lMaxed Pickaxe").addItemFlagsToItem(ItemFlag.HIDE_ATTRIBUTES).addAllEnchants();
		player.getInventory().addItem(builder);

	}
}
