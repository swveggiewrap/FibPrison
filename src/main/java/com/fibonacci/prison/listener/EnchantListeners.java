package com.fibonacci.prison.listener;

import com.fibonacci.prison.api.ItemUtils;
import com.fibonacci.prison.enchants.CEUtils;
import com.fibonacci.prison.enchants.CustomEnchants;
import com.fibonacci.prison.mines.MineUtils;
import com.fibonacci.prison.misc.BlockValue;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.ColorUtils;
import com.fibonacci.prison.utils.SellUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class EnchantListeners implements Listener {

	@EventHandler (priority = EventPriority.LOW)
	public void onAutoSell(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			e.setCancelled(true);
			return;
		}

		if (!MineUtils.isBlockInMine(e.getBlock().getLocation())) {
			return;
		}
		e.setExpToDrop(1);
		Player player = e.getPlayer();
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		if (player.getInventory().getItemInMainHand().getItemMeta() == null) {
			return;
		}
		if (ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.AUTOSELL.getNamespace())) {
			e.getPlayer().giveExp(e.getExpToDrop());
			e.setExpToDrop(0);
			//if has explosion, dont sell yet
			if (!ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.EXPLOSION.getNamespace())) {
				SellUtils.autoSell(prisonPlayer, e.getBlock().getType());
			}

			if (!ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.FORTUNE.getNamespace())) {
				return;
			}

			ItemStack stack = new ItemStack(e.getBlock().getType(), ItemUtils.getCELevel(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.FORTUNE.getNamespace()));
			e.getBlock().getDrops().clear();
			SellUtils.autoSell(PrisonPlayerUtils.getPrisonPlayer(e.getPlayer()), stack);
		}
	}

	public void performExplosion(Block block, Player player) {
		Location loc = block.getLocation();
		long blocks = -1;
		int cavLevel = ItemUtils.getCELevel(player.getInventory().getItemInMainHand(), CustomEnchants.CAVITY.getNamespace());
		List<Collection<ItemStack>> drops = new ArrayList<>();
		for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
			for (int y = loc.getBlockY(); y >= loc.getBlockY() - cavLevel; y--) {
				for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++) {
					Location l = new Location(loc.getWorld(), x, y, z);
					if (l.getBlock().getType().isAir()) {
						continue;
					}
					if (!MineUtils.isBlockInMine(l)) {
						continue;
					}
					drops.add(l.getBlock().getDrops());
					blocks++;
				}
			}
		}

		for (Collection<ItemStack> coll : drops) {
			for (ItemStack itemStack : coll) {
				player.getInventory().addItem(itemStack);
			}
		}
		PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
		if (CEUtils.hasEnchant(CustomEnchants.FIBBAGE, player.getInventory().getItemInMainHand())) {
			prisonPlayer.addFibs(CustomEnchants.FIBBAGE.getPDCLevel(player.getInventory().getItemInMainHand()));
		}
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.2F, 2);
		PrisonPlayerUtils.getPrisonPlayer(player).incrementBlocks(blocks);

	}

	public void performExplosionAutoSell(Player player, PrisonPlayer prisonPlayer, Block block) {
		Location loc = block.getLocation();
		double sellAmount = 0;
		double multi = prisonPlayer.getRank().getSellMultiplier();
		long blocks = -1;
		int cavLevel = ItemUtils.getCELevel(player.getInventory().getItemInMainHand(), CustomEnchants.CAVITY.getNamespace());
		for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
			for (int y = loc.getBlockY(); y >= loc.getBlockY() - cavLevel; y--) {
				for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++) {
					Location l = new Location(loc.getWorld(), x, y, z);
					if (l.getBlock().getType().isAir()) {
						continue;
					}
					if (!MineUtils.isBlockInMine(l)) {
						continue;
					}
					sellAmount += BlockValue.fromMaterial(l.getBlock().getType()).getBasePrice() * multi;
					l.getBlock().setType(Material.AIR);
					blocks++;
				}
			}
		}
		prisonPlayer.addDibs(sellAmount);
		if (CEUtils.hasEnchant(CustomEnchants.FIBBAGE, player.getInventory().getItemInMainHand())) {
			prisonPlayer.addFibs(CustomEnchants.FIBBAGE.getPDCLevel(player.getInventory().getItemInMainHand()));
		}
		prisonPlayer.incrementBlocks(blocks);
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.2F, 2);
	}

	@EventHandler
	public void onExplosion(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			e.setCancelled(true);
			return;
		}

		if (!MineUtils.isBlockInMine(e.getBlock().getLocation())) {
			return;
		}

		Player player = e.getPlayer();
		Random random = new Random();
		int chance = random.nextInt(51);
		if (ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.MAGNIFY.getNamespace())) {
			if (CEUtils.hasEnchant(CustomEnchants.AUTOSELL, player.getInventory().getItemInMainHand())) {
				performExplosionAutoSell(player, PrisonPlayerUtils.getPrisonPlayer(player), e.getBlock());
			} else {
				performExplosion(e.getBlock(), player);
			}
			return;
		}

		if (!ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.EXPLOSION.getNamespace())) {
			return;
		}
		if (chance <= ItemUtils.getCELevel(player.getInventory().getItemInMainHand(), CustomEnchants.EXPLOSION.getNamespace())) {
			if (ItemUtils.hasPDCEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.AUTOSELL.getNamespace())) {
				PrisonPlayer prisonPlayer = PrisonPlayerUtils.getPrisonPlayer(player);
				performExplosionAutoSell(player, prisonPlayer, e.getBlock());
			} else {
				performExplosion(e.getBlock(), player);
			}

		} else {

			if (CEUtils.hasEnchant(CustomEnchants.AUTOSELL, player.getInventory().getItemInMainHand())) {
				SellUtils.autoSell(PrisonPlayerUtils.getPrisonPlayer(player), e.getBlock().getType());
			} else {
				player.getInventory().addItem(e.getBlock().getDrops().toArray(new ItemStack[0]));
			}

		}
	}

	@EventHandler
	public void onPickaxeBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
			e.setCancelled(true);
			return;
		}

		if (ItemUtils.hasPDCEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.SHATTER_PROOF.getNamespace())) {
			return;
		}

		if (!ItemUtils.hasPDCEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.DURABILITY.getNamespace())) {
			return;
		}

		Player player = e.getPlayer();
		if (!(player.getInventory().getItemInMainHand().getItemMeta() instanceof org.bukkit.inventory.meta.Damageable)) {
			return;
		}

		org.bukkit.inventory.meta.Damageable meta = (Damageable) player.getInventory().getItemInMainHand().getItemMeta();
		if (meta.getDamage() >= player.getInventory().getItemInMainHand().getType().getMaxDurability() - 50) {
			player.sendMessage(ColorUtils.colorize("&7(&c&l!&7)&c Your pickaxe is going to break soon! Use /repair now!"));
		}

	}

	@EventHandler
	public void onExpLevel(PlayerLevelChangeEvent e) {
		if (!CEUtils.hasEnchant(CustomEnchants.REPUTATION, e.getPlayer().getInventory().getItemInMainHand())) {
			return;
		}
		if (e.getPlayer().getLevel() >= 25) {
			int level = ItemUtils.getCELevel(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.REPUTATION.getNamespace());
			SellUtils.exchangeXP(e.getPlayer(), level);
		}
	}

}
