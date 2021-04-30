package me.jishuna.commonlib;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;

public class MaterialSets {

	public static Set<Material> MEAT = createSet(Material.RABBIT, Material.COOKED_RABBIT, Material.RABBIT_STEW,
			Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.MUTTON, Material.COOKED_MUTTON, Material.CHICKEN,
			Material.COOKED_CHICKEN, Material.BEEF, Material.COOKED_BEEF, Material.COD, Material.COOKED_COD,
			Material.SALMON, Material.COOKED_SALMON, Material.SPIDER_EYE, Material.PUFFERFISH, Material.TROPICAL_FISH,
			Material.ROTTEN_FLESH);

	@SafeVarargs
	private static <T> Set<T> createSet(T... items) {
		return Arrays.asList(items).stream().collect(Collectors.toSet());
	}
}
