package me.jishuna.commonlib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

public class FileUtils {

	/**
	 * Copies a file from a plugin jar into the plugins data folder. Preserves yml
	 * comments
	 * 
	 * @param source       The source plugin
	 * @param resourceName The name of the desired file, including extension.
	 * @return an optional containing the file, or an empty optional if an error
	 *         occurs.
	 */

	@Nonnull
	public static Optional<File> copyResource(Plugin source, String resourceName) {
		File resourceFile = new File(source.getDataFolder(), resourceName);

		File parent = new File(resourceFile.getParent());
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (!resourceFile.exists()) {
			try {
				resourceFile.createNewFile();
				try (InputStream in = source.getResource(resourceName);
						OutputStream out = new FileOutputStream(resourceFile)) {
					if (in != null)
						ByteStreams.copy(in, out);
				}

			} catch (IOException ex) {
				source.getLogger().severe(
						"Encountered " + ex.getClass().getSimpleName() + " while copying file " + resourceName + ".");
				ex.printStackTrace();
				return Optional.empty();
			}
		}
		return Optional.of(resourceFile);
	}
}
