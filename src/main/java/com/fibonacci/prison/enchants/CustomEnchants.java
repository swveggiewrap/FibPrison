package com.fibonacci.prison.enchants;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnchants {


	public static final CE AUTOSELL = new AutoSell();
	public static final CE EXPLOSION = new Explosion();
	public static final CE DURABILITY = new Durability();
	public static final CE EFFICIENCY = new Efficiency();
	public static final CE FLY = new Fly();
	public static final CE SATISFIED = new Satisfied();
	public static final CE NIGHT_VISION = new NightVision();
	public static final CE FIBBAGE = new Fibbage();
	public static final CE SHATTER_PROOF = new ShatterProof();
	public static final CE REPUTATION = new Reputation();
	public static final CE FORTUNE = new Fortune();
	public static final CE HASTE = new Haste();
	public static final CE MAGNIFY = new Magnify();
	public static final CE CAVITY = new Cavity();

	public static void register() {
		for (Enchantment enchantment : CE.enchantMap.values()) {
			boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);
			if (!registered) {
				registerEnchantment(enchantment);
			}
		}
	}

	public static void registerEnchantment(Enchantment enchantment) {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchantment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
