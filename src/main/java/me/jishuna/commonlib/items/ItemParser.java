package me.jishuna.commonlib.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemParser {

	private static final Material PLAYER_HEAD = Material.PLAYER_HEAD;
	private static final Material POTION = Material.POTION;

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

		if (material == POTION && data.length >= 4) {
			int red = Integer.parseInt(data[1]);
			int green = Integer.parseInt(data[2]);
			int blue = Integer.parseInt(data[3]);

			builder.withPotionColor(red, green, blue);
		}

		return builder.build();
	}

}
