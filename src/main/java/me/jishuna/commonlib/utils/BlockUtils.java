package me.jishuna.commonlib.utils;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class BlockUtils {
	private static final Encoder ENCODER = Base64.getEncoder();
	private static Field blockProfileField;

	public static void setSkullTextureUrl(Block block, String url) {
		BlockState state = block.getState();

		if (!(state instanceof Skull skull))
			return;

		GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(url.getBytes()), "");

		String fullUrl = new StringBuilder()
				.append("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/").append(url)
				.append("\"}}}").toString();
		String texture = ENCODER.encodeToString(fullUrl.getBytes());

		profile.getProperties().put("textures", new Property("textures", texture));

		try {
			if (blockProfileField == null) {
				blockProfileField = skull.getClass().getDeclaredField("profile");
				blockProfileField.setAccessible(true);
			}
			blockProfileField.set(skull, profile);
			state.update(false, false);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
}
