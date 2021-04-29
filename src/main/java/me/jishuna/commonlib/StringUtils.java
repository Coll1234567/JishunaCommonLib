package me.jishuna.commonlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class StringUtils {
	private static Map<Integer, Pattern> regexCache = new HashMap<>();

	public static List<String> splitString(String string, int size) {
		Pattern pattern = regexCache.computeIfAbsent(size,
				key -> Pattern.compile("(?<![^\\s]).{1," + size + "}(?![^\\s])"));

		List<String> result = new ArrayList<String>();
		String prev = "";

		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			String next = ChatColor.getLastColors(prev) + matcher.group().trim();
			result.add(next);
			prev = next;
		}
		return result;
	}

}
