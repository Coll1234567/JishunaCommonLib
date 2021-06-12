package me.jishuna.commonlib.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import me.jishuna.commonlib.language.MessageConfig;

public class ArgumentCommandHandler extends SimpleCommandHandler {
	private final Map<String, CommandExecutor> subcommands = new HashMap<>();
	private final MessageConfig messageConfig;

	public ArgumentCommandHandler(MessageConfig messageConfig) {
		this.messageConfig = messageConfig;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 0) {
			sendUsage(sender, args);
			return true;
		} else if (args.length > 0) {
			CommandExecutor executor = this.subcommands.get(args[0]);

			if (executor == null) {
				sendUsage(sender, args);
				return true;
			}

			return executor.onCommand(sender, command, alias, Arrays.copyOfRange(args, 1, args.length + 1));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> suggestions = new ArrayList<>();

			StringUtil.copyPartialMatches(args[0], subcommands.keySet(), suggestions);
			return suggestions;
		} else if (args.length > 1) {
			CommandExecutor executor = this.subcommands.get(args[0]);

			if (executor != null && executor instanceof TabCompleter) {
				return ((TabCompleter) executor).onTabComplete(sender, command, alias,
						Arrays.copyOfRange(args, 1, args.length + 1));
			}
		}
		return null;
	}

	private void sendUsage(CommandSender sender, String[] args) {
		String msg = messageConfig.getString("command-usage");
		msg = msg.replace("%arg%", "none");
		msg = msg.replace("%args%", String.join(", ", this.subcommands.keySet()));

		sender.sendMessage(msg);
	}

	public void addArgumenExecutor(String arg, CommandExecutor exeuctor) {
		this.subcommands.put(arg, exeuctor);
	}
}
