package me.jishuna.commonlib.items;

import java.util.EnumSet;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemParser {
	private static final EnumSet<Material> LEATHER_ITEMS = EnumSet.of(Material.LEATHER_HELMET,
			Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
			Material.LEATHER_HORSE_ARMOR);

	private static final Material PLAYER_HEAD = Material.PLAYER_HEAD;
	private static final Material POTION = Material.POTION;

	public static ItemStack parseItem(String string) {
		return parseItem(string, Material.DIAMOND);
	}

	public static ItemStack parseItem(String string, Material def) {
		if (string == null)
			return null;
		
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

		if (LEATHER_ITEMS.contains(material) && data.length >= 4) {
			int red = Integer.parseInt(data[1]);
			int green = Integer.parseInt(data[2]);
			int blue = Integer.parseInt(data[3]);

			builder.withDyeColor(Color.fromRGB(red, green, blue));
		}
		return builder.build();
	}

}
