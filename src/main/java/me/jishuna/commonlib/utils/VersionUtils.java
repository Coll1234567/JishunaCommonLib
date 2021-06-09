package me.jishuna.commonlib.utils;

import org.bukkit.Bukkit;

public class VersionUtils {

	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
}
