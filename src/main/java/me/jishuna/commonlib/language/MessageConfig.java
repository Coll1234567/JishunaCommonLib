package me.jishuna.commonlib.language;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class MessageConfig {

	private Map<String, String> stringMap = new HashMap<>();
	private Map<String, List<String>> stringListMap = new HashMap<>();

	private final File file;
	private YamlConfiguration configuration;

	public MessageConfig(File file) {
		this.file = file;
		this.configuration = YamlConfiguration.loadConfiguration(file);
	}

	public void refresh() {
		this.stringMap.clear();
		this.stringListMap.clear();

		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}

	public String getString(String key) {
		return this.stringMap.computeIfAbsent(key,
				mapKey -> ChatColor.translateAlternateColorCodes('&', this.configuration.getString(key, "Error - Missing key: " + key)));
	}

	public List<String> getStringList(String key) {
		return this.stringListMap.computeIfAbsent(key, mapKey -> parseStringList(key));
	}

	private List<String> parseStringList(String key) {
		List<String> list = new ArrayList<>();

		for (String string : this.configuration.getStringList(key)) {
			list.add(ChatColor.translateAlternateColorCodes('&', string));
		}
		return list;
	}

}
