package me.jishuna.commonlib.utils;

import java.io.File;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileUtils {

	@Nonnull
	public static Optional<YamlConfiguration> loadResource(Plugin source, String resourceName) {
		try {
			source.saveResource(resourceName, false);
		} catch (IllegalArgumentException ex) {
			//TODO What should we do with this
		}
		File resourceFile = new File(source.getDataFolder(), resourceName);

		if (!resourceFile.exists()) {
			return Optional.empty();
		}
		return Optional.of(YamlConfiguration.loadConfiguration(resourceFile));
	}
}
