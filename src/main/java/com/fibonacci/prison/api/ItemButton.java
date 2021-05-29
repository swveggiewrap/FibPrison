package com.fibonacci.prison.api;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class ItemButton {

	protected ItemStack item;
	private int slot;

	public static ItemButton create(ItemStack item, Consumer<InventoryClickEvent> listener) {
		return new ItemButton(item) {

			@Override
			public void onClick(InventoryClickEvent e) {
				listener.accept(e);
			}
		};
	}


	public ItemButton(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	protected int getSlot() {
		return slot;
	}

	protected void setSlot(int slot) {
		this.slot = slot;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public abstract void onClick(InventoryClickEvent e);

}
