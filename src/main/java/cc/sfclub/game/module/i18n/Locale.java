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
package cc.sfclub.game.module.i18n;

import cc.sfclub.game.util.Log;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.Properties;

/**
 * Get a translation for requested lang.
 */
@ApiStatus.AvailableSince("0.1.0")
@AllArgsConstructor
public class Locale {
    @NonNull
    private Properties fallback;
    private final Map<String, Properties> locales;

    private Properties getLocale(String locale) {
        return locales.getOrDefault(locale, fallback);
    }

    public String translateNoArg(String locale, String key) {
        return ChatColor.translateAlternateColorCodes('&', getLocale(locale).getProperty(key, fallback.getProperty(key, key)));
    }

    public String translate(String locale, String key, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(getLocale(locale).getProperty(key, fallback.getProperty(key, ChatColor.RED + "Invalid: " + key + ChatColor.RESET)), args));
    }

    public void registerLocale(String lang, Properties locale) {
        if (locales.containsKey(lang)) {
            Log.warn("Duplicate locale was found! " + lang);
        }
        locales.put(lang, locale);
    }

    public void setFallback(Properties fallback) {
        this.fallback = fallback;
    }
}
