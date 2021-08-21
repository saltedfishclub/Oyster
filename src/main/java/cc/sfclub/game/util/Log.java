/*
 *
 *     Oyster - The universal minigame framework for spigot servers.
 *     Copyright (C) 2021 SaltedFish Club
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */

package cc.sfclub.game.util;

import cc.sfclub.game.module.i18n.Locale;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class Log {
    public static Locale defaultLocale;
    public static String defaultLang;
    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Oyster" + ChatColor.RESET + ChatColor.GRAY + "] ";

    @Deprecated
    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + message);
    }

    public static void transInfo(Object... message) {
        info(defaultLocale.translate(defaultLang, message));
    }

    public static void debug(String msg) {
        if (Boolean.getBoolean("oyster.debug")) {
            Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + ChatColor.UNDERLINE + msg);
        }
    }

    public static void transWarn(Object... message) {
        warn(defaultLocale.translate(defaultLang, message));
    }

    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.RED + message);
    }
}
