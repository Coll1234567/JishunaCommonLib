package me.jishuna.commonlib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemParser {

	private static final Material PLAYER_HEAD = Material.PLAYER_HEAD;

	public static ItemStack parseItem(String string) {
		return parseItem(string, Material.DIAMOND);
	}

	public static ItemStack parseItem(String string, Material def) {
		String[] data = string.split(",");
		if (data.length == 0) {
			return new ItemStack(def);
		}

		Material material = Material.matchMaterial(data[0]);
		if (material == null) {
			material = def;
		}

		ItemBuilder builder = new ItemBuilder(material);
		if (material == PLAYER_HEAD && data.length > 1) {
			builder.withSkullTextureUrl(data[1]);
		}

		return builder.build();
	}

}
