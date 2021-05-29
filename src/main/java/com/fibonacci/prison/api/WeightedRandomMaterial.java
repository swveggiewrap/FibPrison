package com.fibonacci.prison.api;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedRandomMaterial<Material> {

	private final NavigableMap<Integer, Material> map = new TreeMap<>();
	private final Random random;
	private int total = 0;

	public WeightedRandomMaterial() {
		this(new Random());
	}

	public WeightedRandomMaterial(Random random) {
		this.random = random;
	}

	public WeightedRandomMaterial<Material> add(int weight, Material resultMaterial) {
		if (weight <= 0) return this;
		total += weight;
		map.put(total, resultMaterial);
		return this;
	}

	public Material next() {
		int value = random.nextInt(total);
		return map.higherEntry(value).getValue();
	}

}
