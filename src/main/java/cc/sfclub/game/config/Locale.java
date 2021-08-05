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
package cc.sfclub.game.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Get a translation for requested lang.
 */
@ApiStatus.AvailableSince("0.0.1")
@RequiredArgsConstructor
public class Locale {
    @NonNull
    private final Map<String, String> fallback = new HashMap<>();
    private final Map<String, Map<String, String>> locales = new HashMap<>();

    public Map<String, String> getLocale(String locale) {
        return locales.getOrDefault(locale, fallback);
    }

    public void registerLocale(String lang, Map<String, String> locale) throws LocaleExistsException {
        locales.put(lang, locale);
    }

    public static class LocaleExistsException extends Exception {
        public LocaleExistsException(String msg) {
            super(msg + " was already registered.");
        }
    }
}
