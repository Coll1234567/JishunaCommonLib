package me.jishuna.commonlib;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;

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

	public ItemStack build() {
		ItemStack finalItem = this.item;
		finalItem.setItemMeta(this.meta);

		return finalItem;
	}

	private List<String> getLore() {
		return meta.hasLore() ? meta.getLore() : new ArrayList<>();
	}

}
