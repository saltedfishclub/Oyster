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

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LocaleLoader {
    public static final Locale loadAsLocale(ZipFile zipFile) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        Map<String, String> fallback = new HashMap<>();
        Map<String, Map<String, String>> locales = new HashMap<>();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();
            String[] fn = ze.getName().split("\\.");
            if (fn.length != 2) {
                Log.warn("Invalid file: " + ze.getName());
                continue;
            }

            if (fn[0].equals("default")) {
                fallback = parseAsMap(read(zipFile, ze));
            } else {
                locales.put(fn[0], parseAsMap(read(zipFile, ze)));
                Log.debug("Loaded locale: " + fn[0]);
            }
        }
        Log.debug(locales.entrySet().size() + " was loaded.");
        return new Locale(fallback, locales);
    }

    private static final Map<String, String> parseAsMap(String str) {
        return Arrays.stream(str.split("\n")).collect(Collectors.toMap(e -> e.split("=")[0], e -> {
            String[] arr = e.split("=");
            if (arr.length == 2) {
                return arr[1];
            }
            if (arr.length > 2) {
                StringJoiner joiner = new StringJoiner("=");
                for (int i = 1; i < arr.length; i++) {
                    joiner.add(arr[i]);
                }
                return joiner.toString();
            }
            return "Invalid Pattern.";
        }));
    }

    private static final String read(ZipFile zip, ZipEntry zipEntry) {
        try {
            InputStream is = zip.getInputStream(zipEntry);
            byte[] buffer = new byte[4096];
            StringBuilder builder = new StringBuilder();
            int i;
            while ((i = is.read(buffer)) != -1) {
                builder.append(new String(buffer));
            }
            is.close();
            return builder.toString();
        } catch (Throwable T) {
            T.printStackTrace();
            Log.warn("Can't load " + zipEntry.getName() + " as a locale. " + zip.getName());
            return "";
        }
    }
}
