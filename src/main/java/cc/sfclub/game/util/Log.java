package cc.sfclub.game.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class Log {
    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Oyster" + ChatColor.RESET + ChatColor.GRAY + "] ";

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + message);
    }

    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.RED + message);
    }
}
