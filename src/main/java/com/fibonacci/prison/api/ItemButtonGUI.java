package com.fibonacci.prison.api;

import com.fibonacci.prison.Prison;
import com.fibonacci.prison.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemButtonGUI implements Listener {

	public static final ItemStack FILLER = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ");

	private final Inventory inventory;
	private List<ItemButton> buttons = new ArrayList<>();

	public ItemButtonGUI(Inventory inventory) {
		this.inventory = inventory;
		Bukkit.getPluginManager().registerEvents(this, Prison.getInstance());
	}

	public ItemButtonGUI(int size, String name) {
		this(Bukkit.createInventory(null, size, ColorUtils.colorize(name)));
	}

	public Inventory getInventory() {
		return inventory;
	}


	public void addButton(ItemButton button, int slot) {
		button.setSlot(slot);
		buttons.add(button);
		inventory.setItem(slot, button.getItem());
	}

	public void addButton(int slot, ItemButton button) {
		addButton(button, slot);
	}

	public void fillEmptySpace() {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER);
			}
		}
	}

	public void removeButton(ItemButton button) {
		buttons.remove(button);
		inventory.remove(button.getItem());
	}


	public List<ItemButton> getButtons() {
		return buttons;
	}

	public void update() {
		for (ItemButton button : buttons) {
			inventory.setItem(button.getSlot(), button.getItem());
		}
	}

	public void open(Player player) {
		player.openInventory(inventory);
	}

	public void clear() {
		inventory.clear();
		buttons.clear();
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!inventory.equals(e.getView().getTopInventory())) {
			return;
		}

		if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR && !e.getClickedInventory().equals(inventory)) {
			e.setCancelled(true);
			return;
		}

		if (!inventory.equals(e.getClickedInventory()) && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
			e.setCancelled(true);
		}

		if (e.getInventory().equals(e.getClickedInventory())) {
			for (ItemButton button : buttons) {
				if (e.getSlot() == button.getSlot()) {
					button.onClick(e);
					break;
				}
			}
		}

		if (inventory.getHolder() == null) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		player.updateInventory();
	}

}
