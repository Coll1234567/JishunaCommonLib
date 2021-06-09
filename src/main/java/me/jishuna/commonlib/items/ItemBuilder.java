package me.jishuna.commonlib.items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;
	private static Field profileField;
	private static final Encoder encoder = Base64.getEncoder();

	private ItemBuilder() {
	}

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder(Material material, int amount) {
		this.item = new ItemStack(material, amount);
		this.meta = this.item.getItemMeta();
	}

	public static ItemBuilder modifyItem(ItemStack item) {
		ItemBuilder builder = new ItemBuilder();
		builder.item = item;
		builder.meta = item.getItemMeta();

		return builder;
	}

	public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);

		return this;
	}

	public ItemBuilder withName(String name) {
		this.meta.setDisplayName(name);

		return this;
	}

	public ItemBuilder addLore(List<String> lore) {
		List<String> itemLore = getLore();
		itemLore.addAll(lore);

		meta.setLore(itemLore);

		return this;
	}

	public ItemBuilder addLore(String... lore) {
		List<String> itemLore = getLore();
		for (String loreLine : lore)
			itemLore.add(loreLine);

		meta.setLore(itemLore);

		return this;
	}

	public ItemBuilder withLore(List<String> lore) {
		this.meta.setLore(lore);
		return this;
	}

	public ItemBuilder withFlags(ItemFlag... flags) {
		this.meta.addItemFlags(flags);

		return this;
	}

	public <T, Z> ItemBuilder withPersistantData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		this.meta.getPersistentDataContainer().set(key, type, value);

		return this;
	}

	public ItemBuilder withModelData(int index) {
		this.meta.setCustomModelData(index);

		return this;
	}

	public ItemBuilder withSkullTexture(String texture) {
		if (!(this.meta instanceof SkullMeta))
			return this;

		GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(texture.getBytes()), "");
		profile.getProperties().put("textures", new Property("textures", texture));

		try {
			getProfileField().set(meta, profile);
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public ItemBuilder withSkullTextureUrl(String url) {
		if (!(this.meta instanceof SkullMeta))
			return this;

		GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(url.getBytes()), "");

		String fullUrl = new StringBuilder()
				.append("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/").append(url)
				.append("\"}}}").toString();
		String texture = encoder.encodeToString(fullUrl.getBytes());

		profile.getProperties().put("textures", new Property("textures", texture));

		try {
			getProfileField().set(meta, profile);
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public ItemBuilder withPotionColor(int red, int green, int blue) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).setColor(Color.fromRGB(red, green, blue));
		return this;
	}

	public ItemStack build() {
		ItemStack finalItem = this.item;
		finalItem.setItemMeta(this.meta);

		return finalItem;
	}

	private List<String> getLore() {
		return meta.hasLore() ? meta.getLore() : new ArrayList<>();
	}

	private Field getProfileField() {
		if (profileField == null) {
			try {
				profileField = meta.getClass().getDeclaredField("profile");
				profileField.setAccessible(true);
			} catch (ReflectiveOperationException ex) {
				ex.printStackTrace();
			}
		}
		return profileField;
	}

}
